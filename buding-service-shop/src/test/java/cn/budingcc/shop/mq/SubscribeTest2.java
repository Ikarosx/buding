package cn.budingcc.shop.mq;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author Ikaros
 * @date 2020/3/2 15:49
 */
public class SubscribeTest2 {
    private final static String USER_NAME = "Ikaros";
    private final static String PASSWORD = "newLife2016";
    public static final String EXCHANGE = "log";
    
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);
        String queue = channel.queueDeclare().getQueue();
        // 这里的param3为routingKey，但是在fanout类型里会被忽略
        channel.queueBind(queue, EXCHANGE, "");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
        });
    }
}
