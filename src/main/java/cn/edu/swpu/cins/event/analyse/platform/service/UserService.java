package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;

/**
 * Created by muyi on 17-6-6.
 */
public interface UserService{


    public int updatePwd(String username,String password) throws BaseException;
}
