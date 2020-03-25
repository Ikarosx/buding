package cn.budingcc.shop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ikaros
 * @date 2020/3/2 17:35
 */
@Configuration
public class RabbitConfig {
    public final static String BD_THYMELEAF_EXCHANGE = "bd_thymeleaf_exchange";
    public final static String BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY = "bd_thymeleaf_good_insert_routing_key";
    public final static String BD_THYMELEAF_GOOD_INSERT_QUEUE = "bd_thymeleaf_good_insert_queue";
    public final static String BD_THYMELEAF_GOOD_DELETE_ROUTING_KEY = "bd_thymeleaf_good_delete_routing_key";
    
    @Bean
    public DirectExchange bdCmsPageExchange() {
        return new DirectExchange(BD_THYMELEAF_EXCHANGE);
    }
    
    @Bean
    public Queue bdThymeleafGoodInsertQueue() {
        return new Queue(BD_THYMELEAF_GOOD_INSERT_QUEUE);
    }
    
    @Bean
    public Binding bindingGoodInsertQueue(Queue bdThymeleafGoodInsertQueue, DirectExchange bdCmsPageExchange) {
        return BindingBuilder.bind(bdThymeleafGoodInsertQueue).to(bdCmsPageExchange).with(BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY);
    }
}
