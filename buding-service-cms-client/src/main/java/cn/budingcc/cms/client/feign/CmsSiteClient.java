package cn.budingcc.cms.client.feign;

import cn.budingcc.framework.client.BuDingServiceEnum;
import cn.budingcc.framework.domain.cms.CmsSite;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Ikaros
 * @date 2020/3/3 13:01
 */
@FeignClient(value = BuDingServiceEnum.BD_SERVICE_CMS)
public interface CmsSiteClient {
    
    @GetMapping("cms/site/{id}")
    CmsSite getCmsSiteById(@PathVariable String id);
    
}
