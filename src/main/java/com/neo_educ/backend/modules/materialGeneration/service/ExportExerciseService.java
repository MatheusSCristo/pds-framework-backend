package com.neo_educ.backend.modules.materialGeneration.service;

import com.neo_educ.backend.modules.materialGeneration.dto.ExportExerciseDTO;
import jakarta.mail.internet.MimeMessage;
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
import java.util.List;

@Service
public class ExportExerciseService {

    @Autowired
    private JavaMailSender mailSender;

    public Boolean export(ExportExerciseDTO exportExerciseDTO) {
        try {
            ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 750);

            List<String> exercises = exportExerciseDTO.selectedExercises();
            int count = 1;
            for (String exercise : exercises) {
                contentStream.showText("Exercício " + count++ + ":");
                contentStream.newLine();
                for (String line : exercise.split("\n")) {
                    contentStream.showText(line.trim());
                    contentStream.newLine();
                }
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();
            document.save(pdfOutput);
            document.close();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(exportExerciseDTO.studentEmail());
            helper.setSubject("Exercícios de Inglês");
            helper.setText("Olá! Segue em anexo os exercícios gerados especialmente para você.", true);
            helper.addAttachment("exercicios.pdf", new ByteArrayResource(pdfOutput.toByteArray()));

            mailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
