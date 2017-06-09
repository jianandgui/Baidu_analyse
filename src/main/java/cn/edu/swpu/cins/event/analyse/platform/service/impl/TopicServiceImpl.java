package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.TopicDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.*;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.Topic;
import cn.edu.swpu.cins.event.analyse.platform.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private int pageSize;
    private TopicDao topicDao;

    @Autowired
    private DailyEventDao dailyEventDao;

    @Autowired
    public TopicServiceImpl(TopicDao topicDao
            , @Value("${event.service.page-count}") int pageSize) {
        this.pageSize = pageSize;
        this.topicDao = topicDao;
    }

    @Override
    public int addTopic(Topic topic) throws BaseException {
        try {
            String name = topic.getName();
            String region = topic.getRegion();
            List<String> rules = topic.getRules();

            if (rules == null || name == null || region == null
                    || region.length() > 45 || name.length() > 45
                    || "".equals(region.trim()) || "".equals(name.trim())) {
                throw new IlleagalArgumentException();
            }

            //排除空字符串
            rules = rules.stream()
                    .filter(rule -> !"".equals(rule.trim()))
                    .collect(Collectors.toList());

            topic.setRules(rules);
            topic.setName(name.trim());
            topic.setRegion(region.trim());

            int insertCount = topicDao.insertTopic(topic);

            if (insertCount < 0) {
                throw new IlleagalArgumentException();
            }


            return insertCount;

        } catch (BaseException be) {
            throw be;
        } catch (DuplicateKeyException de) {
            throw new RepeatlyCollectException("重复添加");
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailureException();
        }
    }


    @Override
    public List<Topic> getTopics() throws BaseException {

        List<Topic> list = topicDao.selectAll();

        if (list.size() <= 0) {
            throw new NoEventException();
        } else {
            return list;
        }
    }


    @Override
    public int deleteTopics(List<Integer> ids) throws BaseException {
        try {

            if (ids == null) {
                throw new IlleagalArgumentException();
            }

            int deleteCount = topicDao.deleteByIds(ids);

            if (deleteCount < 0) {
                throw new IlleagalArgumentException();
            }

            return deleteCount;

        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            throw new OperationFailureException();
        }
    }
}
