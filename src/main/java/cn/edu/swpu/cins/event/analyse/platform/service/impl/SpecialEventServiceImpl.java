package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.TopicDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.Topic;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by muyi on 17-6-7.
 */
@Service
public class SpecialEventServiceImpl implements SpecialEventService {

    private DailyEventDao dailyEventDao;
    private TopicDao topicDao;
    private int pageSize;

    @Autowired
    public SpecialEventServiceImpl(DailyEventDao dailyEventDao, TopicDao topicDao
            , @Value("${event.service.page-count}") int pageSize) {
        this.dailyEventDao = dailyEventDao;
        this.topicDao = topicDao;
    }


    @Override
    public List<DailyEvent> getSpecialEvent(int page) throws BaseException {

        int offset = --page * pageSize;
        List<Topic> topics = topicDao.selectAll();
        HashSet<DailyEvent> dailyEvents = new HashSet<DailyEvent>();

        for (Topic topic : topics){
            dailyEvents.addAll(getEventByRules(topic));
        }

        //获得结果集
        List<DailyEvent> list = dailyEvents
                .stream()
                .filter(dailyEvent -> dailyEvent.getCollectionStatus() == 0)
                .collect(toList());

        int limit = (offset + pageSize) > list.size() ? list.size() : (offset + pageSize);

        if (offset > pageSize || offset < 0 ){
            throw new NoEventException();
        }

        List<DailyEvent> result;
        result = list.subList(offset , limit);

        return result;
    }

    @Override
    public int getPageCount() throws BaseException {

        List<Topic> topics = topicDao.selectAll();
        HashSet<DailyEvent> dailyEvents = new HashSet<DailyEvent>();

        if(topics == null){
            throw new OperationFailureException();
        }else {
            for (Topic topic : topics){
                dailyEvents.addAll(getEventByRules(topic));
            }
        }

        //获得结果集
        List<DailyEvent> list = dailyEvents
                .stream()
                .filter(dailyEvent -> dailyEvent.getCollectionStatus() == 0)
                .collect(toList());

        int pageCOunt = list.size()/5 + (list.size() % 5 == 0 ? 0 : 1);

        return pageCOunt;
    }


    //根据填写专题的规则获取事件信息（事件均未处置）
    public List<DailyEvent> getEventByRules(Topic topicsRules){
        List<DailyEvent> dailyEvents;
        List<String> rules = topicsRules.getRules();

        dailyEvents = dailyEventDao.selectByRules(rules);

        return dailyEvents;
    }
}
