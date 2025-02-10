package com.oficina.domain.service;

import com.oficina.domain.Participante;
import com.oficina.domain.Workshop;
import com.oficina.domain.repository.WorkshopRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CertificadoService {

    @Autowired
    private WorkshopRepository workshopRepository;

    public byte[] generateCertificatesZip(Long workshopId) throws IOException {
        Workshop workshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new IllegalArgumentException("Workshop não encontrado com id: " + workshopId));

        ByteArrayOutputStream zipBaos = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(zipBaos)) {

            for (Participante participante : workshop.getParticipantes()) {
                byte[] pdfBytes = generateCertificatePdf(workshop, participante);
                ZipEntry entry = new ZipEntry("certificado_" + participante.getRa() + ".pdf");
                zipOut.putNextEntry(entry);
                zipOut.write(pdfBytes);
                zipOut.closeEntry();
            }
        }
        return zipBaos.toByteArray();
    }

    private byte[] generateCertificatePdf(Workshop workshop, Participante participante) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {

                try (InputStream logoStream = getClass().getResourceAsStream("/logo.png")) {
                    if (logoStream != null) {
                        PDImageXObject logo = PDImageXObject.createFromByteArray(doc, logoStream.readAllBytes(), "utfpr_logo");
                        contentStream.drawImage(logo, 200, 700, 200, 100);
                    }
                }

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 22);
                contentStream.newLineAtOffset(150, 650);
                contentStream.showText("CERTIFICADO DE PARTICIPAÇÃO");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 14);
                contentStream.newLineAtOffset(80, 600);
                contentStream.showText("Certificamos que " + participante.getName() + " (RA: " + participante.getRa() + ")");
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("participou com êxito do workshop \"" + workshop.getTitulo() + "\"");
                contentStream.newLineAtOffset(0, -25);
                contentStream.showText("realizado em " + workshop.getData() + " com duração de " + workshop.getDuracao() + "horas.");
                contentStream.endText();

                contentStream.setLineWidth(1f);
                contentStream.moveTo(220, 200);
                contentStream.lineTo(420, 200);
                contentStream.stroke();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
                contentStream.newLineAtOffset(270, 180);
                contentStream.showText("Assinatura do Instrutor");
                contentStream.endText();
            }

            ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();
            doc.save(pdfBaos);
            return pdfBaos.toByteArray();
        }
    }
}
