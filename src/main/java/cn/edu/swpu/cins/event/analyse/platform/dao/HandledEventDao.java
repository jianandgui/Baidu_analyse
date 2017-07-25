package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-14.
 */
@Mapper
@Repository
public interface HandledEventDao {
    static String TABLE_NAME = " handled_event ";
    static String JOIN_TABLE_NAME = " daily_event ";
    static String JOIN_SELECT_FIELD = " de.theme as `theme` ,de.main_view as `main_view`,de.url as `url`,de.post_type as `post_type`,de.post_time as `post_time`," +
            " he.id,he.handled_condition,he.feedback_condition,he.collected_time,he.handled_time,he.detail,he.event_handler,he.recorder ";
    static String INSERT_FIELD = " handled_condition,feedback_condition,collected_time,handled_time,detail,event_handler,daily_event_id,recorder ";

    @Insert({"insert into ", TABLE_NAME, " ( ", INSERT_FIELD, " ) " +
            "value(#{handledCondition},#{feedbackCondition},#{collectedTime}" +
            ",#{handledTime},#{detail},#{eventHandler},#{dailyEventId},#{recorder})"})
    int insertHandledEvent(HandledEvent handledEvent);

    @Select({"SELECT ", JOIN_SELECT_FIELD
            , " FROM ", TABLE_NAME, " as he "
            , " LEFT JOIN ", JOIN_TABLE_NAME, " as de "
            , " ON he.daily_event_id = de.id "
            , " ORDER BY collected_time DESC"
            , " LIMIT #{offset} , #{limit}"})
    List<HandledEvent> selectAll(@Param("offset") int offset, @Param("limit") int limit);

    @Select({" SELECT id " +
            "FROM ", TABLE_NAME,
            " WHERE daily_event_id = #{dailyEventId} LIMIT 1"})
    HandledEvent selectByDailyEvent(int dailyEventId);

    //todo 暂不不对来源分类
    @Select({"SELECT ", JOIN_SELECT_FIELD
            , " FROM ", TABLE_NAME, " as he "
            , " LEFT JOIN ", JOIN_TABLE_NAME, " as de "
            , " ON he.daily_event_id = de.id "
            , " WHERE post_time >= #{startTime} AND post_time < #{endTime}"
            , " ORDER BY post_time ASC"})
    List<HandledEvent> selectByGivenTimes(@Param("startTime") String startTime
            , @Param("endTime") String endTime
            , @Param("source") String source);

    @Select({" SELECT COUNT(id) " +
            "FROM ", TABLE_NAME})
    int selectCount();

    @Update({"UPDATE ", TABLE_NAME
            , " SET handled_condition = #{handledCondition} , feedback_condition = #{feedbackCondition} " +
            ", handled_time = #{handledTime} , detail = #{detail} , event_handler = #{eventHandler} "
            , " WHERE id = #{id}"})
    int updateHandledEvent(HandledEvent handledEvent);

    int deleteByIds(List<Integer> ids);
}
