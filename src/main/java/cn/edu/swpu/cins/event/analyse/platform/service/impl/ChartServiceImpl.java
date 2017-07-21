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
import cn.edu.swpu.cins.event.analyse.platform.utility.chart.generator.ChartGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lp-deepin on 17-5-20.
 */
@Service
public class ChartServiceImpl implements ChartService {
    private static final DateFormat CHART_PARAMETER_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final DateFormat DATABASE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static final long DAY = 86400000L;

    private DailyEventDao dailyEventDao;
    private SpecialEventService specialEventService;
    private ChartGenerator chartGenerator;
    private int dateRange;

    @Autowired
    public ChartServiceImpl(DailyEventDao dailyEventDao
            , @Value("${event.service.chart-date-range}") int dateRange
            , SpecialEventService specialEventService, ChartGenerator chartGenerator) {
        this.chartGenerator = chartGenerator;
        this.dateRange = dateRange;
        this.dailyEventDao = dailyEventDao;
        this.specialEventService = specialEventService;
    }

    @Override
    public Map<String, List<ChartPoint>> getChartPoints(
            String source
            , String data
            , String beginTime
            , String endTime
            , String eventTable) throws BaseException {
        Map<String, List<ChartPoint>> map = new HashMap<String, List<ChartPoint>>();
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
                events = dailyEventDao.selectByGivenTimes(beginDateFormat, endDateFormat, source, false);
            } else if (EventTableEnum.HANDLED_EVENT.getEventTable().equals(eventTable)) {
                events = dailyEventDao.selectByGivenTimes(beginDateFormat, endDateFormat, source, true);
            } else if ((EventTableEnum.SPECIAL_EVENT.getEventTable().equals(eventTable))) {
                events = specialEventService.getSpecialEvent(0, true,0);
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

            if(ChartDataEnum.DOUBLELINE.getDataType().equals(data)){
                List<ChartPoint> postCountPoints = chartGenerator.getChartPoints(events, beginTimeLong, endTimeLong, ChartDataEnum.POSTCOUNT.getDataType());
                List<ChartPoint> followCountPoints = chartGenerator.getChartPoints(events, beginTimeLong, endTimeLong, ChartDataEnum.FOLOWCOUNT.getDataType());
                map.put("postCountPoints",postCountPoints);
                map.put("followCountPoints",followCountPoints);
            }else {
                List<ChartPoint> list = chartGenerator.getChartPoints(events, beginTimeLong, endTimeLong, data);
                map.put("chartPoints",list);
            }

            return map;

        } catch (IlleagalArgumentException ie) {
            throw new IlleagalArgumentException();
        } catch (ParseException pe) {
            throw new IlleagalArgumentException();
        } catch (Exception e) {
            throw new BaseException("服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
