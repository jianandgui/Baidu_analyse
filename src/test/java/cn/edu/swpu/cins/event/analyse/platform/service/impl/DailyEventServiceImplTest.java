package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.HandledEventDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.RepeatlyCollectException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import cn.edu.swpu.cins.event.analyse.platform.service.DailyEventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by lp-deepin on 17-5-7.
 */
@RunWith(MockitoJUnitRunner.class)
public class DailyEventServiceImplTest {
//    static long DAY=86400000L;

    DailyEventService dailyEventService;

    @Mock
    private DailyEventDao dailyEventDao;

    @Mock
    private HandledEventDao handledEventDao;

    @Before
    public void init(){
        this.dailyEventService=new DailyEventServiceImpl(dailyEventDao,handledEventDao,5);
    }

//    @Test
//    public void should_collect_success() throws Exception {
//        HandledEvent handledEvent = new HandledEvent();
//        Mockito.when(handledEventDao.selectByDailyEvent(1)).thenReturn(null);
//        dailyEventService.collectEvent("林峰",1);
//    }
//
//    @Test(expected = RepeatlyCollectException.class)
//    public void should_throw_repeatCollectException_when_collectEvent() throws Exception{
//        Mockito.when(handledEventDao.selectByDailyEvent(1)).thenReturn(new HandledEvent());
//        dailyEventService.collectEvent("小明",1);
//    }

//    @Test
//    public void test_chart_result() throws Exception{
//
//        Date date = new Date();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE,-20);
//        long begin =calendar.getTime().getTime();
//        calendar.add(Calendar.DATE,12);
//        long end = calendar.getTime().getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<DailyEvent> events = dailyEventDao.selectByGivenTimes(format.format(new Date(begin)),format.format(new Date(end)));
////        getChart(events,begin,end);
//    }

    //todo 另一版本的getchart
//    public List<ChartResult> getChart(List<DailyEvent> events,long begin,long end){
//        List<ChartResult> resultlist= new ArrayList<ChartResult>();
//
//        long curDay = begin; //当前天
//        int dayCount = (int)((end - begin)/DAY)+1;//区间天数
//        int[] day = new int[dayCount];//每天的统计量
//        int dayNo = 0;//当前天对应index
//        int count =0;//统计量
//
//        for(int i=0;i<events.size();i++){
//            long time = events.get(i).getPostTime().getTime();
//            if(time-curDay<DAY){
//                //事件的发帖时间在当天则统计
//                count++;
//            }else {
//                //清算count,生成相应的chartresult插入list
//                day[dayNo]=count;
//                count=1;
//                int dayMin=(int)((time-curDay)/DAY);
//                curDay+=dayMin*DAY;
//                dayNo+=dayMin;
//            }
//        }
//
//        if(dayNo<day.length)
//        day[dayNo] = count;
//
//        long beginTime = begin;
//        for (int cnt:day) {
//            ChartResult result= new ChartResult(cnt,beginTime);
//            resultlist.add(result);
//            beginTime+=DAY;
//        }
//
//        return null;
//    }
}