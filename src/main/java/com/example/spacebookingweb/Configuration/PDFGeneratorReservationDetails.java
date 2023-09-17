package com.example.spacebookingweb.Configuration;

import com.example.spacebookingweb.Database.View.ReservationDetailsView;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class PDFGeneratorReservationDetails {
    // List to hold all Students
    private List<ReservationDetailsView> reservationDetailsViewList;

    public byte[] generate(LocalDate reservationStartDate, LocalDate reservationEndDate) throws DocumentException, IOException {
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

        document.add(new Paragraph("Wprowadzenie \n", fontTextBold));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Raport ten ma na celu przedstawienie analizy i " +
                "informacji dotyczacych rezerwacji miejsc biurowych w naszej firmie w " +
                "okresie od " + reservationStartDate + " do " + reservationEndDate + ". Rezerwacje miejsc biurowych " +
                "sa kluczowym elementem zarzadzania zasobami biurowymi i wplywaja na wydajnosc " +
                "oraz komfort pracy naszych pracownikow.\n",
                fontText));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Podsumowanie Rezerwacji\n", fontTextBold));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("W okresie objetym tym raportem firma miala ogolem " + reservationDetailsViewList.size() + " rezerwacji miejsc biurowych. \n",
                fontText));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Podsumowanie \n", fontTextBold));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Rezerwacje miejsc biurowych odgrywaja istotną role w efektywnym zarzadzaniu zasobami biurowymi naszej firmy. " +
                "Analiza danych pozwolila nam zrozumiec, kiedy i dlaczego pracownicy rezerwuja miejsca biurowe. " +
                "Mozemy wykorzystac te informacje, aby zoptymalizować dostępnosc miejsc biurowych i zwiekszyc komfort pracy naszych pracownikow. \n",
                fontText));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Zalacznik nr. 1\n", fontTextBold));
        document.add(new Paragraph(""));
        document.add(new Paragraph("Tabela przedstawia dane zebrane z okresu od: " +
                reservationStartDate + " do " +
                reservationEndDate + ". \n", fontTextBold));
        document.add(new Paragraph("\n"));


        // Creating a table of 8 columns
        PdfPTable table = new PdfPTable(8);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 2, 2, 2, 2, 2, 2, 2, 2 });
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
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Surname", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Space Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Space Type", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Is Height Adjustable", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Floor Number", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        // Iterating over the list of students
        for (ReservationDetailsView view : reservationDetailsViewList) {
            table.addCell(String.valueOf(view.getReservationId()));
            table.addCell(view.getName());
            table.addCell(view.getSurname());
            table.addCell(view.getSpaceName());
            table.addCell(view.getSpaceType());
            table.addCell(String.valueOf(view.getIsHeightAdjustable()));
            table.addCell(view.getFloorNum());
            table.addCell(String.valueOf(view.getReservationDate()));
        }
        // Adding the created table to document
        document.add(table);

        // Closing the document
        document.close();

        return byteArrayOutputStream.toByteArray();
    }
}
