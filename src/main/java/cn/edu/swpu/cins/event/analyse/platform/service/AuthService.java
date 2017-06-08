package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;

/**
 * Created by muyi on 17-5-23.
 */
public interface AuthService {

    //这个类用于获取授权
    public String userLogin(String username,String password) throws BaseException;


}
