package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lp-deepin on 17-5-20.
 */
@Service
public class ChartServiceImpl implements ChartService {
    private static final DateFormat CHART_PARAMETER_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateFormat CHART_DISPLAY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATABASE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static final long DAY = 86400000L;

    private DailyEventDao dailyEventDao;

    @Autowired
    public ChartServiceImpl(DailyEventDao dailyEventDao) {
        this.dailyEventDao = dailyEventDao;
    }

    @Override
    public List<ChartPoint> getChartPoints(String source, String data
            , String beginTime, String endTime) throws BaseException {
        if(!ChartDataEnum.isInclude(data)){
            throw new IlleagalArgumentException();
        }

        //todo 对日期范围的限制
        try {
            Date endTimeDate = CHART_PARAMETER_DATE_FORMAT.parse(endTime);
            Date beginTimeDate = CHART_PARAMETER_DATE_FORMAT.parse(beginTime);
            long endTimeLong = endTimeDate.getTime();
            long beginTimeLong = beginTimeDate.getTime();

            String endDateFormat;
            endDateFormat = DATABASE_DATE_FORMAT.format(new Date(endTimeLong + DAY));
            String beginDateFormat;
            beginDateFormat = DATABASE_DATE_FORMAT.format(beginTimeDate);

            List<DailyEvent> events = dailyEventDao.selectEventsBetweenTime(beginDateFormat, endDateFormat, source);
            List<ChartPoint> list = getChart(events, beginTimeLong, endTimeLong, data);

            return list;
        } catch (Exception e) {
            throw new IlleagalArgumentException();
        }
    }


    private List<ChartPoint> getChart(List<DailyEvent> events, long begin, long end, String dataType) {
        List<ChartPoint> resultlist = new ArrayList<ChartPoint>();

        long curDay = begin; //当前天
        int dayCount = (int) ((end - begin) / DAY) + 1;//区间天数
        int[] day = new int[dayCount];//每天的统计量
        int dayNo = 0;//当前天对应index
        int count = 0;//统计量

        for (DailyEvent event : events) {
            long time = event.getPostTime().getTime();
            if (time - curDay < DAY) {
                //事件的发帖时间在当天则统计
                if (ChartDataEnum.POSTCOUNT.getDataType().equals(dataType)) {
                    count++;
                } else if (ChartDataEnum.FOLOWCOUNT.getDataType().equals(dataType)) {
                    count += event.getFollowCount();
                }

            } else {
                //清算count,生成相应的chartresult插入list
                day[dayNo] = count;
                if (ChartDataEnum.POSTCOUNT.getDataType().equals(dataType)) {
                    count = 1;
                } else if (ChartDataEnum.FOLOWCOUNT.getDataType().equals(dataType)) {
                    count = event.getFollowCount();
                }
                int dayMin = (int) ((time - curDay) / DAY);
                curDay += dayMin * DAY;
                dayNo += dayMin;
            }
        }

        if (dayNo < day.length) {
            day[dayNo] = count;
        }

        long beginTime = begin;
        for (int cnt : day) {
            ChartPoint result = new ChartPoint();
            result.setX(CHART_DISPLAY_DATE_FORMAT.format(new Date(beginTime)));
            result.setY(cnt);
            resultlist.add(result);
            beginTime += DAY;
        }

        return resultlist;
    }
}
