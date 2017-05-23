package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.config.util.JwtTokenUtil;
import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.JwtUserFactory;
import cn.edu.swpu.cins.event.analyse.platform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by muyi on 17-5-23.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Override
    public String userLogin(String username, String password) {
//        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
//        // Perform the security
//        final Authentication authentication =authenticationManager.authenticate(upToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user=userDao.queryByName(username);

        if(password.equals(user.getPassword())){
            // Reload password post-security so we can generate token
            final UserDetails userDetails = JwtUserFactory.createUser(userDao.queryByName(username));
            final String token = jwtTokenUtil.generateToken(userDetails);
            return token;

        }

        return null;
    }


}
