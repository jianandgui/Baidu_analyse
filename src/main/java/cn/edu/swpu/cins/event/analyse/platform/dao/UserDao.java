package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by muyi on 17-5-23.
 */
@Mapper
@Repository
public interface UserDao {

    //查询用户 根据用户名
    public User queryByName(String username);

    //根据用户名修改密码
    public int updatePwd(@Param("username") String username, @Param("password") String password);


}
