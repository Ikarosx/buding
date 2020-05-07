package cn.budingcc.auth.service;

import cn.budingcc.auth.client.UserClient;
import cn.budingcc.auth.domain.BdUser;
import cn.budingcc.framework.domain.ucenter.BdMenu;
import cn.budingcc.framework.domain.ucenter.extension.BdUserExtension;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/16 9:15
 */
@Service("UserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    ClientDetailsService clientDetailsService;
    @Autowired
    private UserClient userClient;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, clientDetails.getAuthorities());
            }
        }
        if (StringUtils.isBlank(username)) {
            return null;
        }
        BdUserExtension userExtension = userClient.getUserExtension(username);
        if (userExtension == null) {
            return null;
        }
        String password = userExtension.getPassword();
        List<BdMenu> permissions = userExtension.getPermissions();
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        List<String> userPermission = new ArrayList<>();
        permissions.forEach(item -> userPermission.add(item.getCode()));
        String userPermissionString = StringUtils.join(userPermission.toArray(), ",");
        BdUser bdUser = new BdUser(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList(userPermissionString));
        bdUser.setNickName(userExtension.getNickName());
        bdUser.setSchoolId(userExtension.getSchoolId());
        bdUser.setStudentId(userExtension.getStudentId());
        bdUser.setUserPic(userExtension.getUserPic());
        return bdUser;
    }
}
