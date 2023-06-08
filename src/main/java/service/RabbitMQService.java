package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitMQService {

    public final static String COMPLETE_DATA_QUEUE_NAME = "complete-data";

    public void consumeMessage(Channel channel, String queueName, DeliverCallback deliverCallback) {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {

            });
        } catch (IOException e) {
            System.err.println("Failed to consume message from queue " + queueName);
            e.printStackTrace();
        }
    }

}
