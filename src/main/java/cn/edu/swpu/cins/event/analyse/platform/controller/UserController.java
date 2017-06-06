package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.config.util.JwtTokenUtil;
import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.enums.UserEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.UserUpdatePwd;
import cn.edu.swpu.cins.event.analyse.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by muyi on 17-6-6.
 */
@RestController
@RequestMapping("/event/user")
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @PostMapping("/updatePwd")
    public ResponseEntity<?> updatePwd(@RequestBody UserUpdatePwd userUpdatePwd, HttpServletRequest request) {
        try {

            String authHeader = request.getHeader(this.tokenHeader);
            final String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            if(!userUpdatePwd.getOldPassword().equals(userDao.queryByName(username).getPassword())){

                return new ResponseEntity<>(UserEnum.OLDPWD_ERROR.getMsg(), HttpStatus.OK);
            }

            int num=userService.updatePwd(username,userUpdatePwd.getNewPassword());
            if(num==1) {
                return new ResponseEntity<>(UserEnum.UPDATE_PWD_SUCCESS.getMsg(), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(UserEnum.UPDATE_PWD_SUCCESS.getMsg(), HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(UserEnum.UPDATE_PWD_FAILED.getMsg(),e.getStatus());
        }
    }
}
