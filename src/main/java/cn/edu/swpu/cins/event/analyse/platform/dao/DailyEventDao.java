package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-5.
 */
@Repository
@Mapper
public interface DailyEventDao {
    public static String TABLE_NAME=" daily_event ";
    public static String SELECT_FIELD=" id,url,theme,main_view,follow_count,post_type,created_time,source,collection_status,post_time ";

    @Select({"select ",SELECT_FIELD," from ",TABLE_NAME," limit #{offset},#{limit}"})
    List<DailyEvent> selectAll(@Param("offset") int offset,@Param("limit") int limit);

    @Select({"select count(id) from ",TABLE_NAME})
    int selectCount();
}
