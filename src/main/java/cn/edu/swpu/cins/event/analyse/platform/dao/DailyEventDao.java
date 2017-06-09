package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-5.
 */
@Repository
@Mapper
public interface DailyEventDao {
    static String TABLE_NAME = " daily_event ";
    static String SELECT_FIELD = " id,url,theme,main_view,follow_count,post_type,created_time,source,collection_status,post_time ";

    @Select({" select ", SELECT_FIELD
            , " from ", TABLE_NAME
            , " order by DATE(post_time) desc , follow_count desc "
            , " limit #{offset},#{limit}"})
    List<DailyEvent> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME})
    int selectCount();

    @Select({" select follow_count,post_time from "
            , TABLE_NAME
            , " where source = #{source} and post_time >= #{startTime} and post_time < #{endTime} "
            , " order by post_time asc"})
    List<DailyEvent> selectEventsBetweenTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("source") String source);

    @Update({"update ", TABLE_NAME,
            "set collection_status=1"
            , "where id=#{id}"})
    int updateCollectStatus(int id);

    List<DailyEvent> selectByRules(List<String> rules);
}
