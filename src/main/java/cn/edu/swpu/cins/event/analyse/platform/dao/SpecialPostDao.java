package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpecialPostDao {

    static String TABLE_NAME = "special_post";
    static String SELECT_FIELD = "id,name,url";
    static String INSERT_FIELD = "name,url";

    @Insert({
            "insert into " , TABLE_NAME ,
            " ( " ,INSERT_FIELD , " ) " ,
            "value ( #{name} , #{url , javaType = List , jdbcType = VARCHAR} )"
    })
    int insertSpecialPost(SpecialPost specialPost);

    //专帖页面显示所有专帖名
    @Select({
            " select ",
            SELECT_FIELD ,
            " from ",TABLE_NAME
    })
    List<SpecialPost> selectAllSpecialPost();

    int deleteByIds(List<Integer> ids);

    List<SpecialPost> selectSpecialPostByIds(List<Integer> ids);

    int updateSpecialPost(@Param("id") Integer id, @Param("urls") List<String> urls);
}
