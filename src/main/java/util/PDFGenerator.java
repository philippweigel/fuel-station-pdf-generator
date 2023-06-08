package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.CompleteData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDFGenerator {
    double pricePerKwh = 0.15; // Set the price per kWh

    public void generateInvoice(CompleteData completeData, String customerName) throws DocumentException, FileNotFoundException {
        String downloadsFolder = System.getProperty("user.home") + "\\Downloads"; // Path to the Downloads folder
        String fileName = "Invoice_" + completeData.getCustomerId() + ".pdf"; // Replace ":" with "-"
        String filePath = downloadsFolder + "\\" + fileName; // Construct the file path
        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        Paragraph title = new Paragraph("Invoice", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Customer Name: " + customerName, contentFont));
        document.add(new Paragraph("Total Kwh: " + completeData.getTotalKwh(), contentFont));

        double totalPrice = completeData.getTotalKwh() * pricePerKwh; // Calculate the total price
        document.add(new Paragraph("Price per kWh: $" + pricePerKwh, contentFont));
        document.add(new Paragraph("Total Price: $" + totalPrice, contentFont));


        document.close();
        System.out.println("Invoice created: " + fileName);
    }
}
