package cn.budingcc.shop.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author Ikaros
 * @date 2020/3/2 13:09
 */
public class Worker2 {
    private final static String WORK_QUEUE_NAME = "work_persistent";
    private final static String USER_NAME = "Ikaros";
    private final static String PASSWORD = "newLife2016";
    
    public static void main(String[] args) throws Exception {
        // a consumer that receives messages
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            try {
                work(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println("[√] Done");
            }
        };
        channel.basicConsume(WORK_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
    
    private static void work(String message) throws InterruptedException {
        for (char c : message.toCharArray()) {
            if (c == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
