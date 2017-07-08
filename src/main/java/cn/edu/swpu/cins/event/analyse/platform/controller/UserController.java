package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.config.util.JwtTokenUtil;
import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.enums.UserEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.UserException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.Role;
import cn.edu.swpu.cins.event.analyse.platform.model.view.UserUpdatePwd;
import cn.edu.swpu.cins.event.analyse.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
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
                return new ResponseEntity<>(UserEnum.UPDATE_PWD_FAILED.getMsg(), HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(UserEnum.INNER_ERROR.getMsg(),e.getStatus());
        }
    }

    /*
    超级用户添加新用户
    */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) throws UserException{
        try {
            int num = userService.insertUser(user);
            if (num == 1) {
                return new ResponseEntity<>("添加用户成功", HttpStatus.OK);
            }
            return new ResponseEntity<Object>("添加用户失败", HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
        }
    }
    /**
     * 超级用户修改其他用户权限
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changeRole")
    public ResponseEntity<?> changeRole(@RequestBody Role role) throws UserException{

        try{
            int num=userService.changeRole(role);
            if(num==1){
                return new ResponseEntity<Object>("修改用户权限成功",HttpStatus.OK);
            }
            return new ResponseEntity<Object>("修改用户权限失败",HttpStatus.CONFLICT);
        }catch (Exception e){
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
        }

    }

    /**
     * 分组显示特权VIP和NORMAL用户
     *
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roleList")
    public ResponseEntity<?> listRole() throws UserException{
     try {
         return new ResponseEntity<Object>(userService.queryAll(),HttpStatus.OK);
     }catch (UserException e){
         return new ResponseEntity<Object>(e.getMessage(),HttpStatus.FORBIDDEN);
     }
    }

}
