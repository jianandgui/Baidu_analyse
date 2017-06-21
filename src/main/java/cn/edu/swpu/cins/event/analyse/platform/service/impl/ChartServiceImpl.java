package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataEnum;
import cn.edu.swpu.cins.event.analyse.platform.enums.EventTableEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.ChartService;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private SpecialEventService specialEventService;
    private int dateRange;

    @Autowired
    public ChartServiceImpl(DailyEventDao dailyEventDao
            , @Value("${event.service.chart-date-range}") int dateRange
            , SpecialEventService specialEventService) {
        this.dateRange = dateRange;
        this.dailyEventDao = dailyEventDao;
        this.specialEventService = specialEventService;
    }

    @Override
    public List<ChartPoint> getChartPoints(
            String source
            , String data
            , String beginTime
            , String endTime
            , String eventTable) throws BaseException {
        //判断数据类型是否正确
        if (!ChartDataEnum.isInclude(data)) {
            throw new IlleagalArgumentException();
        }

        try {
            //解析传入的时间字符串
            Date endTimeDate = CHART_PARAMETER_DATE_FORMAT.parse(endTime);
            Date beginTimeDate = CHART_PARAMETER_DATE_FORMAT.parse(beginTime);

            long endTimeLong = endTimeDate.getTime();
            long beginTimeLong = beginTimeDate.getTime();

            //对日期范围的限制
            if (endTimeLong - beginTimeLong < DAY || endTimeLong - beginTimeLong > dateRange * DAY) {
                throw new IlleagalArgumentException();
            }

            String endDateFormat;
            endDateFormat = DATABASE_DATE_FORMAT.format(new Date(endTimeLong + DAY));
            String beginDateFormat;
            beginDateFormat = DATABASE_DATE_FORMAT.format(beginTimeDate);

            //判断表参数是否正确
            if (!EventTableEnum.isInclude(eventTable)) {
                throw new IlleagalArgumentException();
            }

            List<DailyEvent> events = null;

            if (EventTableEnum.DAILY_EVENT.getEventTable().equals(eventTable)) {
                events = dailyEventDao.selectEventsBetweenTime(beginDateFormat, endDateFormat, source, false);
            } else if (EventTableEnum.HANDLED_EVENT.getEventTable().equals(eventTable)) {
                events = dailyEventDao.selectEventsBetweenTime(beginDateFormat, endDateFormat, source, true);
            } else if ((EventTableEnum.SPECIAL_EVENT.getEventTable().equals(eventTable))) {
                events = specialEventService.getSpecialEvent(0, true);
                events = events
                        .stream()
                        .filter((DailyEvent dailyEvent) -> {
                            long time = dailyEvent.getPostTime().getTime();
                            if (time < endTimeLong && time >= beginTimeLong)
                                return true;
                            return false;
                        })
                        .sorted(Comparator.comparing(DailyEvent::getPostTime))
                        .collect(Collectors.toList());
            }

            List<ChartPoint> list = getChart(events, beginTimeLong, endTimeLong, data);

            return list;

        } catch (IlleagalArgumentException ie) {
            throw new IlleagalArgumentException();
        } catch (ParseException pe) {
            throw new IlleagalArgumentException();
        } catch (Exception e) {
            throw new BaseException("服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    protected static List<ChartPoint> getChart(List<DailyEvent> events, long begin, long end, String dataType) {
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
