package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by lp-deepin on 17-5-14.
 */
@Mapper
@Repository
public interface HandledEventDao {
    public static String TABLE_NAME=" handled_event ";
    public static String SELECT_FIELD=" id,handled_condition,feedback_condition,collected_time,handled_time,detail,remark,daily_event_id ";
    public static String INSERT_FIELD=" handled_condition,feedback_condition,collected_time,handled_time,detail,remark,daily_event_id ";

    @Insert({"insert into ",TABLE_NAME, " ( ",INSERT_FIELD," ) " +
            "value(#{handledCondition},#{feedbackCondition},#{collectedTime}" +
            ",#{handledTime},#{detail},#{remark},#{dailyEventId})"})
    public int insertHandledEvent(HandledEvent handledEvent);

    @Select({"SELECT ",SELECT_FIELD
            ," FROM ",TABLE_NAME
            ," where daily_event_id=#{dailyId} limit 1"})
    public HandledEvent selectByDailyEvent(int dailyId);
}
