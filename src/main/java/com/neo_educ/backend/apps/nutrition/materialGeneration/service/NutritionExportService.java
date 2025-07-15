package com.neo_educ.backend.apps.nutrition.materialGeneration.service;

import com.neo_educ.backend.apps.nutrition.materialGeneration.dto.ExportMealPlanDTO;
import com.neo_educ.backend.apps.nutrition.nutritionist.entity.NutritionistEntity;
import com.neo_educ.backend.apps.nutrition.nutritionist.repository.NutritionistRepository;
import com.neo_educ.backend.apps.nutrition.patient.repository.PatientRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NutritionExportService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private NutritionistRepository nutritionistRepository;

    // Constantes de formatação do PDF
    private static final float MARGIN = 50f;
    private static final float FONT_SIZE = 12f;
    private static final float LEADING = 15f;
    private static final PDType1Font FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private static final PDType1Font FONT_BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

    /**
     * Orquestra a geração e o envio do PDF do plano alimentar.
     * @param exportDTO DTO com os dados do paciente e do plano alimentar.
     * @param nutritionistEmail Email do nutricionista autenticado para verificação.
     * @return true se o envio foi bem-sucedido, false caso contrário.
     */
    public Boolean exportMealPlan(ExportMealPlanDTO exportDTO, String nutritionistEmail) {
        try {
            NutritionistEntity nutritionist = nutritionistRepository.findByEmail(nutritionistEmail)
                .orElseThrow(() -> new EntityNotFoundException("Nutricionista não encontrado."));

            patientRepository.findByEmailAndNutritionist(exportDTO.patientEmail(), nutritionist)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado ou não pertence a este nutricionista."));

            byte[] pdfBytes = generatePDF(exportDTO.mealPlanTitle(), exportDTO.mealPlanContent());
            
            sendEmailWithAttachment(exportDTO.patientEmail(), pdfBytes);

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Logar o erro é uma boa prática
            return false;
        }
    }

    private byte[] generatePDF(String title, String content) throws IOException {
        ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDFWriter writer = new PDFWriter(document, MARGIN, FONT_SIZE, LEADING, FONT, FONT_BOLD);

            writer.writeTitle(title);
            writer.addExtraSpace();
            writer.writeContent(content);

            writer.close();
            document.save(pdfOutput);

            return pdfOutput.toByteArray();
        } finally {
            pdfOutput.close();
        }
    }

    private void sendEmailWithAttachment(String patientEmail, byte[] pdfBytes) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(patientEmail);
        helper.setSubject("Seu Plano Alimentar Personalizado");
        helper.setText("Olá! Conforme nossa consulta, segue em anexo o seu novo plano alimentar.", true);
        helper.addAttachment("plano_alimentar.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }

    /**
     * Classe auxiliar para escrever no PDF com controle de páginas e quebra de linha.
     */
    private static class PDFWriter {
        private final PDDocument document;
        private final float margin;
        private final float fontSize;
        private final float leading;
        private final PDType1Font font;
        private final PDType1Font fontBold;
        private final float pageWidth;
        private final float pageHeight;
        private final float contentWidth;

        private PDPageContentStream contentStream;
        private float currentY;
        private boolean isTextBegun = false;

        public PDFWriter(PDDocument document, float margin, float fontSize, float leading, PDType1Font font, PDType1Font fontBold)
                throws IOException {
            this.document = document;
            this.margin = margin;
            this.fontSize = fontSize;
            this.leading = leading;
            this.font = font;
            this.fontBold = fontBold;

            PDRectangle pageSize = PDRectangle.A4;
            this.pageWidth = pageSize.getWidth();
            this.pageHeight = pageSize.getHeight();
            this.contentWidth = pageWidth - (2 * margin);

            addNewPage();
        }

        public void writeTitle(String title) throws IOException {
            ensureTextBegun();
            contentStream.setFont(fontBold, fontSize + 4);
            writeText(title);
            contentStream.setFont(font, fontSize);
        }

        public void writeContent(String text) throws IOException {
            String[] lines = text.split("\n");
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    addLineBreak();
                } else {
                    writeText(line.trim());
                }
            }
        }

        public void addExtraSpace() throws IOException {
            addLineBreak();
        }

        private void writeText(String text) throws IOException {
            List<String> wrappedLines = wrapText(text);
            for (String line : wrappedLines) {
                if (currentY < margin + leading) {
                    addNewPage();
                }
                ensureTextBegun();
                contentStream.showText(line);
                addLineBreak();
            }
        }

        private void addLineBreak() throws IOException {
            if (isTextBegun) {
                contentStream.newLineAtOffset(0, -leading);
            }
            currentY -= leading;
        }

        private void addNewPage() throws IOException {
            if (contentStream != null) {
                if (isTextBegun) {
                    contentStream.endText();
                }
                contentStream.close();
            }

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            currentY = pageHeight - margin;
            isTextBegun = false;
        }

        private void ensureTextBegun() throws IOException {
            if (!isTextBegun) {
                contentStream.setFont(font, fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, currentY);
                isTextBegun = true;
            }
        }

        private List<String> wrapText(String text) throws IOException {
            List<String> lines = new ArrayList<>();
            if (text == null || text.isEmpty()) return lines;

            float textWidth = font.getStringWidth(text) / 1000 * fontSize;
            if (textWidth <= contentWidth) {
                lines.add(text);
                return lines;
            }

            String[] words = text.split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
                float testWidth = font.getStringWidth(testLine) / 1000 * fontSize;

                if (testWidth <= contentWidth) {
                    currentLine = new StringBuilder(testLine);
                } else {
                    if (currentLine.length() > 0) {
                        lines.add(currentLine.toString());
                    }
                    currentLine = new StringBuilder(word);
                    // Lidar com palavras que são mais longas que a própria linha
                    while(font.getStringWidth(currentLine.toString()) / 1000 * fontSize > contentWidth) {
                        int breakPoint = findBreakPoint(currentLine.toString());
                        lines.add(currentLine.substring(0, breakPoint));
                        currentLine = new StringBuilder(currentLine.substring(breakPoint));
                    }
                }
            }
            if (currentLine.length() > 0) {
                lines.add(currentLine.toString());
            }
            return lines;
        }
        
        private int findBreakPoint(String longWord) throws IOException {
            int breakPoint = 0;
            for(int i=0; i < longWord.length(); i++) {
                float width = font.getStringWidth(longWord.substring(0, i+1)) / 1000 * fontSize;
                if(width > contentWidth) {
                    breakPoint = i;
                    break;
                }
            }
            return breakPoint > 0 ? breakPoint : longWord.length();
        }

        public void close() throws IOException {
            if (contentStream != null) {
                if (isTextBegun) {
                    contentStream.endText();
                }
                contentStream.close();
            }
        }
    }
}