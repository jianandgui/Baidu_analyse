package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.UserException;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.Role;
import cn.edu.swpu.cins.event.analyse.platform.model.view.RoleList;

import javax.security.auth.message.AuthException;

/**
 * Created by muyi on 17-6-6.
 */
public interface UserService{


    public int updatePwd(String username,String password) throws BaseException;

    public int insertUser(User user) throws UserException;

    public RoleList queryAll() throws UserException;

    public int changeRole(Role role) throws UserException;
}
