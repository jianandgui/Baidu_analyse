package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by lp-deepin on 17-5-20.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChartServiceImplTest {

    ChartServiceImpl chartService;

    @Mock
    DailyEventDao dailyEventDao;

    @Before
    public void setUp() throws Exception {
        chartService = new ChartServiceImpl(dailyEventDao,62);
    }

    @Test
    public void should_get_chart_point_list_success() throws Exception {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date baseDate = dateFormat.parse("05/07/2017");
        long baseDateLong = baseDate.getTime();
        Random random = new Random();

        List<DailyEvent> list = new ArrayList<DailyEvent>();
        for (int i =0;i<10;i++){
            DailyEvent dailyEvent = new DailyEvent();
            //在13天范围内的随机日期
            dailyEvent.setPostTime(new Date(baseDateLong+random.nextInt(14)*86400000L));
            dailyEvent.setSource("baidu");
            //随机生成跟帖量为0-99
            dailyEvent.setFollowCount(random.nextInt(100));
            list.add(dailyEvent);
        }

        list = list.stream().sorted(comparing(DailyEvent::getPostTime)).collect(Collectors.toList());

        Mockito.when(dailyEventDao.selectEventsBetweenTime("2017-05-07 00:00:00","2017-05-21 00:00:00","baidu",false)).thenReturn(list);

        List<ChartPoint> rsltlist = chartService.getChartPoints("baidu","跟帖量","05/07/2017","05/20/2017","dailyEvent");

    }
}