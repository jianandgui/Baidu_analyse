package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by muyi on 17-5-23.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userDao.queryByName(username);

        if(user!=null){

            return JwtUserFactory.createUser(user);
        }

        return null;
    }
}
