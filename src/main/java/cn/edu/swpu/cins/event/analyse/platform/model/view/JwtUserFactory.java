package cn.edu.swpu.cins.event.analyse.platform.model.view;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

/**
 * Created by muyi on 17-5-23.
 */
public class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser createUser(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(ToGrantedAuthorities(user.getRole()))
        );
    }



    private static GrantedAuthority ToGrantedAuthorities(String authorities) {

        return new SimpleGrantedAuthority(authorities);

    }
}
