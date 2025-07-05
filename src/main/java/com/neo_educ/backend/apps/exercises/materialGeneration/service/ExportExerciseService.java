package com.neo_educ.backend.apps.exercises.materialGeneration.service;

import com.neo_educ.backend.apps.english.materialGeneration.dto.ExportExerciseDTO;
import com.neo_educ.backend.apps.english.student.entity.StudentEntity;
import com.neo_educ.backend.apps.english.student.repository.StudentRepository;
import com.neo_educ.backend.apps.english.teacher.entity.TeacherEntity;
import com.neo_educ.backend.apps.english.teacher.repository.TeacherRepository;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExportExerciseService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private static final float MARGIN = 50f;
    private static final float FONT_SIZE = 12f;
    private static final float LEADING = 14f;
    private static final PDType1Font FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    public Boolean export(ExportExerciseDTO exportExerciseDTO, String teacherEmail) {
        try {
            Optional<TeacherEntity> teacherOptional = teacherRepository.findByEmail(teacherEmail);
            TeacherEntity teacher = teacherOptional.get();

            Optional<StudentEntity> studentOptional = studentRepository.findByEmailAndTeacher(exportExerciseDTO.studentEmail(), teacher);

            if (studentOptional.isEmpty()) {
                throw new EntityNotFoundException();
            }

            byte[] pdfBytes = generatePDF(exportExerciseDTO.selectedExercises());
            sendEmailWithAttachment(exportExerciseDTO.studentEmail(), pdfBytes);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] generatePDF(List<String> exercises) throws Exception {
        ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();

        try {
            PDFWriter writer = new PDFWriter(document, MARGIN, FONT_SIZE, LEADING, FONT);

            for (String exercise : exercises) {
                writer.writeExercise(exercise);
                writer.addExtraSpace();
            }

            writer.close();
            document.save(pdfOutput);

            return pdfOutput.toByteArray();
        } finally {
            document.close();
            pdfOutput.close();
        }
    }

    private void sendEmailWithAttachment(String studentEmail, byte[] pdfBytes) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(studentEmail);
        helper.setSubject("Exercícios de Inglês");
        helper.setText("Olá! Segue em anexo os exercícios gerados especialmente para você.", true);
        helper.addAttachment("exercicios.pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(message);
    }

    /**
     * Classe auxiliar para escrever no PDF com controle de páginas e quebra de linha
     */
    private static class PDFWriter {
        private final PDDocument document;
        private final float margin;
        private final float fontSize;
        private final float leading;
        private final PDType1Font font;
        private final float pageWidth;
        private final float pageHeight;
        private final float contentWidth;

        private PDPageContentStream contentStream;
        private float currentY;
        private boolean isTextBegun = false;

        public PDFWriter(PDDocument document, float margin, float fontSize, float leading, PDType1Font font)
                throws Exception {
            this.document = document;
            this.margin = margin;
            this.fontSize = fontSize;
            this.leading = leading;
            this.font = font;

            // Obter dimensões da página
            PDRectangle pageSize = PDRectangle.A4;
            this.pageWidth = pageSize.getWidth();
            this.pageHeight = pageSize.getHeight();
            this.contentWidth = pageWidth - (2 * margin);

            // Criar primeira página
            addNewPage();
        }

        public void writeExercise(String exercise) throws Exception {
            String[] lines = exercise.split("\n");

            for (String line : lines) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()) {
                    addLineBreak();
                } else {
                    writeText(trimmedLine);
                }
            }
        }

        public void addExtraSpace() throws Exception {
            addLineBreak();
        }

        private void writeText(String text) throws Exception {
            List<String> wrappedLines = wrapText(text);

            for (String line : wrappedLines) {
                // Verificar se precisa de nova página
                if (currentY < margin + leading) {
                    addNewPage();
                }

                ensureTextBegun();
                contentStream.showText(line);
                addLineBreak();
            }
        }

        private void addLineBreak() throws Exception {
            if (currentY < margin + leading) {
                addNewPage();
            } else {
                currentY -= leading;
                if (isTextBegun) {
                    contentStream.newLineAtOffset(0, -leading);
                }
            }
        }

        private void addNewPage() throws Exception {
            if (isTextBegun) {
                contentStream.endText();
                isTextBegun = false;
            }

            if (contentStream != null) {
                contentStream.close();
            }

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            currentY = pageHeight - margin;
        }

        private void ensureTextBegun() throws Exception {
            if (!isTextBegun) {
                contentStream.setFont(font, fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, currentY);
                isTextBegun = true;
            }
        }

        private List<String> wrapText(String text) {
            List<String> lines = new ArrayList<>();

            try {
                // Verificar se o texto cabe em uma linha
                float textWidth = font.getStringWidth(text) / 1000 * fontSize;
                if (textWidth <= contentWidth) {
                    lines.add(text);
                    return lines;
                }

                // Quebrar em palavras
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
                            currentLine = new StringBuilder(word);
                        } else {
                            // Palavra muito longa - forçar quebra por caracteres
                            lines.addAll(breakLongWord(word));
                            currentLine = new StringBuilder();
                        }
                    }
                }

                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                }

            } catch (Exception e) {
                // Em caso de erro, retornar o texto original
                lines.clear();
                lines.add(text);
            }

            return lines;
        }

        private List<String> breakLongWord(String word) {
            List<String> parts = new ArrayList<>();
            StringBuilder currentPart = new StringBuilder();

            try {
                for (char c : word.toCharArray()) {
                    String testPart = currentPart.toString() + c;
                    float testWidth = font.getStringWidth(testPart) / 1000 * fontSize;

                    if (testWidth <= contentWidth) {
                        currentPart.append(c);
                    } else {
                        if (currentPart.length() > 0) {
                            parts.add(currentPart.toString());
                            currentPart = new StringBuilder().append(c);
                        } else {
                            parts.add(String.valueOf(c));
                        }
                    }
                }

                if (currentPart.length() > 0) {
                    parts.add(currentPart.toString());
                }
            } catch (Exception e) {
                parts.clear();
                parts.add(word);
            }

            return parts;
        }

        public void close() throws Exception {
            if (isTextBegun) {
                contentStream.endText();
            }
            if (contentStream != null) {
                contentStream.close();
            }
        }
    }
}