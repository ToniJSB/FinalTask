package org.example.service;
import java.io.FileOutputStream;
import java.util.List;

import org.example.Constants;
import org.example.models.HistorialMedico;
import org.example.models.Paciente;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Utility class for creating PDF documents.
 */
public class PdfCreator {

    /**
     * Generates a unique file name for a PDF document.
     * 
     * @param name the base name for the file.
     * @return a unique file name.
     */
    public static String getUniqueFileName(String name) {
        String[] files = Constants.PDF_FOLDER.list();
        if (files == null) {
            return name + ".pdf";
        }
        int i = 1;
        String newName = name + ".pdf";
        while (true) {
            boolean found = false;
            for (String file : files) {
                if (file.equals(newName)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return newName;
            }
            newName = name + " (" + i + ").pdf";
            i++;
        }
    }

    /**
     * Creates a PDF document for a patient with a list of medical history records.
     * 
     * @param paciente the patient for whom the PDF is to be created.
     * @param historialMedico the list of medical history records.
     */
    public static void create(Paciente paciente, List<HistorialMedico> historialMedico) {
        try {
            Document document = new Document();


            String fileName = getUniqueFileName(paciente.getNombre());
            String filePath = Constants.PDF_FOLDER+"/" + fileName;
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Paciente"));
            document.add(new Paragraph("Nombre: " + paciente.getNombre()));
            document.add(new Paragraph("Edad: " + paciente.getEdad()));
            document.add(new Paragraph("Historial Médico"));
            PdfPTable table = new PdfPTable(3);
            table.addCell(new PdfPCell(new Paragraph("Fecha")));
            table.addCell(new PdfPCell(new Paragraph("Diagnóstico")));
            table.addCell(new PdfPCell(new Paragraph("Tratamiento")));
            for (HistorialMedico historial : historialMedico) {
                table.addCell(new PdfPCell(new Paragraph(historial.getFechaVisita().toString())));
                table.addCell(new PdfPCell(new Paragraph(historial.getDiagnostico())));
                table.addCell(new PdfPCell(new Paragraph(historial.getTratamiento())));
            }
            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a PDF document for a patient with a single medical history record.
     * 
     * @param paciente the patient for whom the PDF is to be created.
     * @param historialMedico the medical history record.
     */
    public static void create(Paciente paciente, HistorialMedico historialMedico) {
        try {
            Document document = new Document();


            String fileName = getUniqueFileName(paciente.getNombre() + "_" + historialMedico.getFechaVisita().toString());
            String filePath = Constants.PDF_FOLDER+"/" + fileName;
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("Paciente"));
            document.add(new Paragraph("Nombre: " + paciente.getNombre()));
            document.add(new Paragraph("Edad: " + paciente.getEdad()));
            document.add(new Paragraph("Historial Médico"));
            PdfPTable table = new PdfPTable(3);
            table.addCell(new PdfPCell(new Paragraph("Fecha")));
            table.addCell(new PdfPCell(new Paragraph("Diagnóstico")));
            table.addCell(new PdfPCell(new Paragraph("Tratamiento")));
            table.addCell(new PdfPCell(new Paragraph(historialMedico.getFechaVisita().toString())));
            table.addCell(new PdfPCell(new Paragraph(historialMedico.getDiagnostico())));
            table.addCell(new PdfPCell(new Paragraph(historialMedico.getTratamiento())));
            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
