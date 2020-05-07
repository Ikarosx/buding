package cn.budingcc.auth.client;

import cn.budingcc.framework.client.BuDingServiceEnum;
import cn.budingcc.framework.domain.ucenter.extension.BdUserExtension;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ikaros
 * @date 2020/3/26 13:22
 */
@FeignClient(value = BuDingServiceEnum.BD_SERVICE_UCENTER)
public interface UserClient {
    @GetMapping("/ucenter/user/userExtension")
    BdUserExtension getUserExtension(@RequestParam String username);
}
