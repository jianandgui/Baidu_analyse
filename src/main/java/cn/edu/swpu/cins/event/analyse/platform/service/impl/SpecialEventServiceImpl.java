package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.TopicDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.Topic;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muyi on 17-6-7.
 */
@Service
public class SpecialEventServiceImpl implements SpecialEventService {

    private DailyEventDao dailyEventDao;
    private TopicDao topicDao;

    @Autowired
    public SpecialEventServiceImpl(DailyEventDao dailyEventDao, TopicDao topicDao) {
        this.dailyEventDao = dailyEventDao;
        this.topicDao = topicDao;
    }

    @Override
    public List<DailyEvent> getSpecialEvent() throws BaseException {

        List<Topic> topics=topicDao.selectAll();
        List<DailyEvent> dailyEvents=new ArrayList<DailyEvent>();
        for (Topic topic:topics){
            dailyEvents.addAll(getEventByRules(topic));
        }
        return dailyEvents;
    }


    //根据填写专题的规则获取事件信息（事件均未处置）
    public List<DailyEvent> getEventByRules(Topic topicsRules){

        List<DailyEvent> list=new ArrayList<DailyEvent>();
        List<DailyEvent> dailyEvents;
        List<String> rules=topicsRules.getRules();

        for(String rule : rules) {
            dailyEvents = dailyEventDao.selectByRules(rule);
            for (DailyEvent dailyEvent : dailyEvents) {
                int num = 0;
                for (DailyEvent dailyEvent1 : list) {
                    if (dailyEvent.getMainView().equals(dailyEvent1.getMainView())) {
                        ++num;
                    }
                }
                if(num==0){
                    list.add(dailyEvent);
                }
            }
        }



        return list;
    }
}
