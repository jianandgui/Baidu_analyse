package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyEventDaoTest {
    @Autowired
    DailyEventDao dailyEventDao;
    @Test
    public void should_print_daily_event_list()throws Exception{
        List<DailyEvent> list = dailyEventDao.selectEventsBetweenTime("2017-4-30 00:00:00","2017-5-3 23:59:59");
        list.forEach(
                (DailyEvent dailyEvent) -> {
                    System.out.println(dailyEvent.toString());
                }
        );
    }

    @Test
    public void should_update_collectionStatus() throws Exception {
        dailyEventDao.updateCollectStatus(30);
        List<DailyEvent> list = dailyEventDao.selectAll(0,40);
        list.forEach(
                dailyEvent -> {
                    System.out.println(dailyEvent);
                }
        );
    }
}