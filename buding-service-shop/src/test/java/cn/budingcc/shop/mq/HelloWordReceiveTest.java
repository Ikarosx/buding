package cn.budingcc.shop.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author Ikaros
 * @date 2020/3/2 11:43
 */
public class HelloWordReceiveTest {
    private final static String HELLO_WORD_QUEUE_NAME = "hello";
    private final static String USER_NAME = "Ikaros";
    private final static String PASSWORD = "newLife2016";
    
    public static void main(String[] args) throws Exception {
        // a consumer that receives messages
        // 同producer
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername(USER_NAME);
        factory.setPassword(PASSWORD);
        // 这里不使用try-with-resources写法是为了能够持续监听队列
        // 不然进程会马上结束
        // 这里有个小插曲，我刚才是和producer一样用的junit测试
        // 虽然可以看到消费完成，但是无法执行回调函数输出接收值
        // 就是因为junit执行完后进程会马上退出，子进程也gg
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(HELLO_WORD_QUEUE_NAME, false, false, false, null);
        // 编写回调函数，用到了函数式编程的写法
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        // 指定消费队列与消费完成的回调函数，取消后的回调函数
        channel.basicConsume(HELLO_WORD_QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
