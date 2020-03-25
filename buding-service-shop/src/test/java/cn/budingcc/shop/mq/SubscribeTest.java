package cn.budingcc.shop.mq;

import cn.budingcc.framework.domain.shop.Good;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Ikaros
 * @date 2020/3/2 15:49
 */
public class SubscribeTest {
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
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);
        // 声明队列，在之前我们都是指定名字的
        // 但在日志系统中，我们并不关心过去的数据，所以我们只需要一个临时队列
        // 通过调用空参的queueDeclare方法可以得到一个
        // 非持久，独占，未使用时自动删除的随机名字队列
        // 通过getQueue获取队列名字
        String queue = channel.queueDeclare().getQueue();
        // 这里的param3为routingKey，但是在fanout类型里会被忽略
        channel.queueBind(queue, EXCHANGE, "");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            Good good = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(good);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        // 这里队列名字使用上面获得的随机名字
        channel.basicConsume(queue, false, deliverCallback, consumerTag -> {
        });
    }
}
