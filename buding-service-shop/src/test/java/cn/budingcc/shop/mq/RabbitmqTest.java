package cn.budingcc.shop.mq;

import cn.budingcc.framework.domain.shop.Good;
import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

/**
 * @author Ikaros
 * @date 2020/3/2 10:47
 */
public class RabbitmqTest {
    private final static String WORK_QUEUE_NAME = "work_persistent";
    private final static String PUBLISH_EXCHANGE_NAME = "log";
    private final static String PUBLISH_QUEUE_NAME = "publish_persistent";
    private final static String USER_NAME = "Ikaros";
    private final static String PASSWORD = "newLife2016";
    public final static String BD_THYMELEAF_EXCHANGE = "bd_thymeleaf_exchange";
    public final static String BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY = "bd_thymeleaf_good_insert_routing_key";
    
    @Test
    public void publishTest() throws Exception {
        // 通过连接工厂创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 测试用rabbitmq服务在本机
        factory.setHost("localhost");
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        try (Connection connection = factory.newConnection();
             // 通过连接创建通道
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(BD_THYMELEAF_EXCHANGE, BuiltinExchangeType.DIRECT);
            Good good = new Good();
            good.setGoodName("Ikaros");
            good.setDescription("我老婆");
            good.setImageUrl("a5sd5ad--");
            good.setPrice(650.0);
            channel.basicPublish(BD_THYMELEAF_EXCHANGE, BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(good));
        }
    }
    
    @Test
    public void helloWordSendTest() throws Exception {
        // a producer that sends a single message
        
        // 通过连接工厂创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 测试用rabbitmq服务在本机
        factory.setHost("localhost");
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        try (Connection connection = factory.newConnection();
             // 通过连接创建通道
             Channel channel = connection.createChannel()) {
            /*
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1:队列名称
             * param2:是否持久化
             * param3:队列是否独占此连接
             * param4:队列不再使用时是否自动删除此队列
             * param5:队列参数
             */
            channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);
            String message = "Hello World!";
            /*
             * 消息发布方法参数1和2先不提，Exchange和routingKey
             * param1:Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange将消息转发到指定的消息队列
             * param3:消息包含的属性
             * param4:消息体
             */
            String str = "";
            for (int i = 0; i < 10000; i++) {
                str = ".";
                if (i % 2 == 0) {
                    str = "........";
                }
                channel.basicPublish("", WORK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, str.getBytes());
            }
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
    
    @Test
    public void helloWordReceiveTest() throws Exception {
        // a consumer that receives messages
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(WORK_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(WORK_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
