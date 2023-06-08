import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.rabbitmq.client.Channel;
import config.RabbitMQConfig;
import model.CompleteData;
import service.DatabaseService;
import service.RabbitMQService;
import util.CompleteDataProcessor;
import util.PDFGenerator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {

        RabbitMQService rabbitMQService = new RabbitMQService();
        DatabaseService databaseService = new DatabaseService();
        PDFGenerator pdfGenerator = new PDFGenerator();
        CompleteDataProcessor processor = new CompleteDataProcessor(databaseService, pdfGenerator);

        try {
            Channel channel = RabbitMQConfig.setupRabbitMQChannel();
            rabbitMQService.consumeMessage(channel, RabbitMQService.COMPLETE_DATA_QUEUE_NAME, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                try {
                    CompleteData completeData = new ObjectMapper().readValue(message, CompleteData.class);
                    processor.processCompleteData(completeData);
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                }

            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();

        }
    }

}
