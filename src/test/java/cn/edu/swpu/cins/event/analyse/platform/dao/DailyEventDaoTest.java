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
        List<DailyEvent> list = dailyEventDao.selectAll(0,5);
        list.forEach(
                (DailyEvent dailyEvent) -> {
                    System.out.println(dailyEvent.toString());
                }
        );
    }
}