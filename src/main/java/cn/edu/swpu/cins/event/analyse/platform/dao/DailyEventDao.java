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
    static String SELECT_FIELD = " id,url,theme,main_view,follow_count,post_type,created_time,source,collection_status,post_time,last_follow_time";


//    DATE(post_time)
    @Select({" select ", SELECT_FIELD
            , " from ", TABLE_NAME
            , " order by DATE(post_time) desc, follow_count desc,post_time desc "
            , " limit #{offset},#{limit}"})
    List<DailyEvent> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME})
    int selectCount();

    //todo 暂不对来源分类
    List<DailyEvent> selectByGivenTimes(@Param("startTime") String startTime
            , @Param("endTime") String endTime
            , @Param("source") String source
            , @Param("isCollected") boolean isCollected);

    @Update({"UPDATE ", TABLE_NAME,
            "set collection_status=1"
            , "where id=#{id}"})
    int updateCollectStatus(int id);

    @Update({"UPDATE ", TABLE_NAME,
            "SET main_view = #{mainView} , post_type = #{postType}"
            , "WHERE id=#{id}"})
    int updateMainViewAndPostTypeById(@Param("id") int id,@Param("mainView") String mainView, @Param("postType") String postType);

    List<DailyEvent> selectByRules(List<String> rules);

    List<DailyEvent> selectByRegions(List<String> regions);
}
