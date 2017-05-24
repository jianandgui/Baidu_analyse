package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.JwtAuthenticationRequest;
import cn.edu.swpu.cins.event.analyse.platform.model.view.JwtAuthenticationResponse;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ResultData;
import cn.edu.swpu.cins.event.analyse.platform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by muyi on 17-5-23.
 */
@RestController
@RequestMapping
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public ResultData createTeacherAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        User user = userDao.queryByName(authenticationRequest.getUsername());
        if (user == null) {
            return new ResultData(false, "没有该用户信息，请确认后登录");
        }

        final String token = authService.userLogin(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(token!=null) {

            String username = user.getUsername();
            String role = user.getRole();
            return new ResultData(true, new JwtAuthenticationResponse(token, username, role));
        }

        return new ResultData(false,"请验证用户名和密码");
    }


}
