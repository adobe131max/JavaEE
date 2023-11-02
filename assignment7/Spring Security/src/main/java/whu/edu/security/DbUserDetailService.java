package whu.edu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whu.edu.entity.Role;
import whu.edu.entity.UserDto;
import whu.edu.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个UserDetailsService的Bean，从数据库中读取用户和权限信息
 */
@Service
public class DbUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }
        String[] roles = user.getRoles().stream().map(Role::getId).toArray(String[]::new);

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(getAuthorities(user))
                .build();
    }

    private static GrantedAuthority[] getAuthorities(UserDto user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            String[] authorityStrs = role.getAuthorities().split(",");
            for (String auth : authorityStrs) {
                authorities.add(new SimpleGrantedAuthority(auth));
            }
        }
        return authorities.toArray(new GrantedAuthority[authorities.size()]);
    }
}
