package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HandledEventDaoTest {
    @Autowired
    HandledEventDao handledEventDao;
    @Test
    public void testInsertHandledEvent() throws Exception{
//        for(int i=0;i<10;i++){
//            HandledEvent event= new HandledEvent();
//            event.setCollectedTime(new Date());
//            event.setHandledTime(new Date());
//            event.setDailyEventId(5);
//            event.setFeedbackConditionByShort((short)0);
//            event.setHandledCondition("test"+i);
//            event.setDetail("test"+i);
//            event.setRemark("test"+i);
//            handledEventDao.insertHandledEvent(event);
//        }
    }

    @Test
    public void test_selectAll() throws Exception{
        List<HandledEvent> handledEvents = handledEventDao.selectAll(0,5);
        handledEvents.forEach(
                System.out::println
        );

    }
}