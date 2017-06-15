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

import java.util.ArrayList;
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
        this.pageSize = pageSize;
    }


    @Override
    public List<DailyEvent> getSpecialEvent(int page, boolean getAll) throws BaseException {
        int offset = --page * pageSize;
        List<Topic> topics = topicDao.selectAll();
        List<DailyEvent> dailyEvents;
        ArrayList<String> rules = new ArrayList<>();

        for (Topic topic : topics) {
            rules.addAll(topic.getRules());
        }


        dailyEvents = getEventByRules(rules);

        //获得结果集
        List<DailyEvent> list = dailyEvents
                .stream()
                .filter(dailyEvent -> dailyEvent.getCollectionStatus() == 0)
                .collect(toList());

        //分页获取事件
        if (!getAll) {
            int limit = (offset + pageSize) > list.size() ? list.size() : (offset + pageSize);

            if (offset >= list.size() || offset < 0) {
                throw new NoEventException();
            }

            return list.subList(offset, limit);
        } else {
            return list;
        }
    }

    @Override
    public int getPageCount() throws BaseException {

        List<Topic> topics = topicDao.selectAll();
        List<DailyEvent> dailyEvents;
        ArrayList<String> rules = new ArrayList<>();

        for (Topic topic : topics) {
            rules.addAll(topic.getRules());
        }

        if (topics == null) {
            throw new OperationFailureException();
        } else {
            dailyEvents = getEventByRules(rules);
        }

        //获得结果集
        List<DailyEvent> list = dailyEvents
                .stream()
                .filter(dailyEvent -> dailyEvent.getCollectionStatus() == 0)
                .collect(toList());

        int pageCOunt = list.size() / 5 + (list.size() % 5 == 0 ? 0 : 1);

        return pageCOunt;
    }


    //根据填写专题的规则获取事件信息（事件均未处置）
    private List<DailyEvent> getEventByRules(List<String> topicsRules) {
        List<DailyEvent> dailyEvents;
        List<String> rules = topicsRules;

        dailyEvents = dailyEventDao.selectByRules(rules);

        return dailyEvents;
    }
}
