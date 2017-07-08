package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.UserException;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.UpdateException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.Role;
import cn.edu.swpu.cins.event.analyse.platform.model.view.RoleList;
import cn.edu.swpu.cins.event.analyse.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muyi on 17-6-6.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int updatePwd(String username, String password) throws BaseException {

        try{
            int num=userDao.updatePwd(username,password);
            if(num==1){
                return num;
            }
            return 0;
        }catch (Exception e){
            throw new UpdateException();
        }
    }

    @Override
    public int insertUser(User user) throws UserException {
        try{
            user.setRole("ROLE_"+user.getRole());
            if(userDao.queryByName(user.getUsername())!=null){
                return 0;
            }
            return userDao.addUser(user);
        }catch (Exception e){
            throw new UserException("服务器内部异常", HttpStatus.FORBIDDEN);
        }

    }

    @Override
    public RoleList queryAll() throws UserException,AuthException {
        try{
            List<Role> roles=userDao.queryAll();
            List<Role> VIP=new ArrayList<Role>();
            List<Role> NORMAL=new ArrayList<Role>();
            for (Role role:roles) {
                if(role.getRole().equals("ROLE_NORMAL")){
                    role.setRole("NORMAL");
                    NORMAL.add(role);
                }
                else {
                    role.setRole("VIP");
                    VIP.add(role);
                }

            }
            RoleList roleList=new RoleList(VIP,NORMAL);
            return roleList;
        }catch (Exception e){
            throw new UserException("服务器内部异常", HttpStatus.FORBIDDEN);
        }

    }

    @Override
    public int changeRole(Role role) throws UserException {
        try{
            if(role.getRole().equals("VIP")){
                role.setRole("ROLE_NORMAL");
            }
            else {
                role.setRole("ROLE_VIP");
            }
            return userDao.updateRole(role);
        }catch (Exception e){
            throw new UserException("服务器内部异常", HttpStatus.FORBIDDEN);
        }

    }

}
