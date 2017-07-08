package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.User;
import cn.edu.swpu.cins.event.analyse.platform.model.view.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //添加一个用户
    public int addUser(User user);

    //查询所有用非ADMIN用户
    public List<Role> queryAll();

    public int updateRole(Role role);
}
