package cn.budingcc.cms.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ikaros
 * @date 2020/3/2 20:12
 */
@Configuration
public class RabbitConfig {
    public final static String BD_THYMELEAF_EXCHANGE = "bd_thymeleaf_exchange";
    public final static String BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY = "bd_thymeleaf_good_insert_routing_key";
    public final static String BD_THYMELEAF_GOOD_DELETE_ROUTING_KEY = "bd_thymeleaf_good_delete_routing_key";
    public final static String BD_THYMELEAF_SAVE_HTML_TO_NGINX_ROUTING_KEY = "bd_thymeleaf_save_html_to_nginx_routing_key";
    public final static String BD_THYMELEAF_SAVE_HTML_TO_NGINX_QUEUE = "bd_thymeleaf_save_html_to_nginx_queue";
    public final static String BD_THYMELEAF_GOOD_INSERT_QUEUE = "bd_thymeleaf_good_insert_queue";
    
    @Bean
    public DirectExchange bdCmsPageExchange() {
        return new DirectExchange(BD_THYMELEAF_EXCHANGE);
    }
    
    @Bean
    public Queue bdThymeleafGoodInsertQueue() {
        return new Queue(BD_THYMELEAF_GOOD_INSERT_QUEUE);
    }
    
    @Bean
    public Queue bdThymeleafSaveHtmlToNginxQueue() {
        return new Queue(BD_THYMELEAF_SAVE_HTML_TO_NGINX_QUEUE);
    }
    
    @Bean
    public Binding bindingGoodInsert(Queue bdThymeleafGoodInsertQueue, DirectExchange bdCmsPageExchange) {
        return BindingBuilder.bind(bdThymeleafGoodInsertQueue).to(bdCmsPageExchange).with(BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY);
    }
    
    @Bean
    public Binding bindingSaveHtmlToNginx(Queue bdThymeleafSaveHtmlToNginxQueue, DirectExchange bdCmsPageExchange) {
        return BindingBuilder.bind(bdThymeleafSaveHtmlToNginxQueue).to(bdCmsPageExchange).with(BD_THYMELEAF_SAVE_HTML_TO_NGINX_ROUTING_KEY);
    }
}
