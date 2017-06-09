package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.UserDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.UpdateException;
import cn.edu.swpu.cins.event.analyse.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by muyi on 17-6-6.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int updatePwd(String username, String password) throws BaseException {

        int num=userDao.updatePwd(username,password);
        if(num<1){

            throw new UpdateException("修改密码失败");
        }

        return num;

    }

}