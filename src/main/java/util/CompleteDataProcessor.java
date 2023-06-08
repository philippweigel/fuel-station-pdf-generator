package util;

import com.itextpdf.text.DocumentException;
import model.CompleteData;
import service.DatabaseService;

import java.io.IOException;

public class CompleteDataProcessor {

    private final DatabaseService databaseService;
    private final PDFGenerator pdfGenerator;

    public CompleteDataProcessor(DatabaseService databaseService, PDFGenerator pdfGenerator) {
        this.databaseService = databaseService;
        this.pdfGenerator = pdfGenerator;
    }

    public void processCompleteData(CompleteData completeData) throws IOException, DocumentException {
        if (completeData != null) {
            String customerName = databaseService.getCustomerNameFromCompleteData(completeData);
            if (customerName != null) {
                pdfGenerator.generateInvoice(completeData, customerName);
            } else {
                System.out.println("Invalid Customer Id received");
            }
        } else {
            System.out.println("Invalid CompleteData received");
        }
    }
}
