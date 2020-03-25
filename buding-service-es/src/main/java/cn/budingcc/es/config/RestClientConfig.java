package cn.budingcc.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ikaros
 * @date 2020/2/25 09:20
 */
@Configuration
public class RestClientConfig {
    @Value("${buding.elasticsearch.hostlist}")
    private String hostlist;
    
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 解析hostlist配置信息
        HttpHost[] httpHostArray = getHttpHosts();
        // 创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHostArray));
    }
    
    @Bean
    public RestClient restClient() {
        // 解析hostlist配置信息
        HttpHost[] httpHostArray = getHttpHosts();
        return RestClient.builder(httpHostArray).build();
    }
    
    private HttpHost[] getHttpHosts() {
        String[] split = hostlist.split(",");
        // 创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        return httpHostArray;
    }
    
}
