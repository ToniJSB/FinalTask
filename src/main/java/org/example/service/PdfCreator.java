package org.example.service;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.Utils;
import org.example.models.HistorialMedico;
import org.example.models.Paciente;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class PdfCreator {
    public static void main(String[] args) {



    }

    public void genPDF(Paciente paciente, List<HistorialMedico> historialCompletoMedico){
        // Generar el PDF
        try {
            Document document= new Document();
            PdfWriter.getInstance(document, new FileOutputStream("HistorialMedico.pdf"));
            document.open();

            // Título del documento
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph title = new Paragraph("Historial Médico de Pacientes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Espacio en blanco
            document.add(new Paragraph(" "));

            // Tabla de pacientes
            PdfPTable tablaPacientes = new PdfPTable(4);
            tablaPacientes.setWidthPercentage(100);
            tablaPacientes.setSpacingBefore(10f);
            tablaPacientes.setSpacingAfter(10f);

            // Encabezados de la tabla de pacientes
            addTableHeader(tablaPacientes, "ID", "Nombre", "Apellidos", "Fecha de Nacimiento");

            // Llenar la tabla de pacientes
            tablaPacientes.addCell(String.valueOf(paciente.getDni()));
            tablaPacientes.addCell(paciente.getFullName());
            tablaPacientes.addCell(Utils.DateFormat.dateAsStringDateF(Utils.DateFormat.asDate(paciente.getbDate())));

            document.add(tablaPacientes);

            // Espacio en blanco
            document.add(new Paragraph(" "));

            // Tabla de historiales médicos
            PdfPTable tablaHistoriales = new PdfPTable(4);
            tablaHistoriales.setWidthPercentage(100);
            tablaHistoriales.setSpacingBefore(10f);
            tablaHistoriales.setSpacingAfter(10f);

            // Encabezados de la tabla de historiales
            addTableHeader(tablaHistoriales, "ID Paciente", "Fecha de Visita", "Diagnóstico", "Tratamiento");

            // Llenar la tabla de historiales
            for (HistorialMedico historial : historialCompletoMedico) {
                tablaHistoriales.addCell(String.valueOf(historial.getDiagnostico()));
                tablaHistoriales.addCell(Utils.DateFormat.dateAsStringDateF(historial.getFechaVisita()));
                tablaHistoriales.addCell(historial.getDiagnostico());
                tablaHistoriales.addCell(historial.getTratamiento());
            }

            document.add(tablaHistoriales);

            // Cerrar el documento
            document.close();

            System.out.println("PDF generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorderWidth(2);
            cell.setPhrase(new Phrase(header));
            table.addCell(cell);
        }
    }

}
