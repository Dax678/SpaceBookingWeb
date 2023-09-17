package com.example.spacebookingweb.Configuration;

import com.example.spacebookingweb.Database.Entity.Space;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PDFGeneratorSpaceDetails {
    List<Space> spaceList = new ArrayList<>();


    public byte[] generate() throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Creating the Object of Document
        Document document = new Document(PageSize.A4);

        // Getting instance of PdfWriter
        PdfWriter.getInstance(document, byteArrayOutputStream);

        // Opening the created document to modify it
        document.open();

        // Creating font
        // Setting font style and size
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(16);

        Font fontTextBold = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTextBold.setSize(11);
        fontTextBold.setStyle(Font.BOLD);

        Font fontText = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontText.setSize(11);

        // Creating paragraph
        Paragraph paragraph = new Paragraph("Raport dotyczacy Rezerwacji Miejsc Biurowych", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n", fontText));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Zalacznik nr. 1\n", fontTextBold));
        document.add(new Paragraph(""));
        document.add(new Paragraph("Tabela przedstawia aktualne dane przesstrzeni biurowych dla pietra: ", fontTextBold));
        document.add(new Paragraph("\n"));

        // Creating a table of 8 columns
        PdfPTable table = new PdfPTable(6);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2, 2, 2, 2, 2 });
        table.setSpacingBefore(5);

        // Create Table Cells for table header
        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.ORANGE);
        cell.setPadding(5);

        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nazwa", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Typ", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Liczba Monitorow", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Regulacja Wysokosci", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Czy jest aktywne?", font));
        table.addCell(cell);

        // Iterating over the list of students
        for (Space space : spaceList) {
            table.addCell(String.valueOf(space.getId()));
            table.addCell(space.getName());
            table.addCell(space.getType());
            table.addCell(String.valueOf(space.getMonitorNumber()));
            table.addCell(space.getIsHeightAdjustable() ? "Tak" : "Nie");
            table.addCell(space.getIsAvailable() ? "Aktywne" : "Wylaczone");
        }
        // Adding the created table to document
        document.add(table);

        // Closing the document
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
