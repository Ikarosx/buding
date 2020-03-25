package cn.budingcc.auth.config;

import cn.budingcc.auth.dao.BdUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Ikaros
 * @date 2020/3/16 9:15
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    BdUserRepository bdUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return bdUserRepository.findByUserName(username);
    }
}
