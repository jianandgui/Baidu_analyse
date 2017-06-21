package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.ReportService;
import cn.edu.swpu.cins.event.analyse.platform.utility.ChartGenerator;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by LLPP on 2017/6/19.
 */
@Service
public class ReportServiceImpl implements ReportService {
    private DailyEventDao dailyEventDao;
    private final String[] monthsChar = {"","一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    @Autowired
    public ReportServiceImpl(DailyEventDao dailyEventDao) {
        this.dailyEventDao = dailyEventDao;
    }

    @Override
    public Map<String, Object> getReportDataMap(int year, int issue) throws BaseException {
        try {
            Map<String, Object> reportDataMap = new HashMap<>();

            if (issue > 6 || issue < 1)
                throw new IlleagalArgumentException();

            int oddMonth = issue * 2 - 1;//奇数月份

            int evenMonth = issue * 2;//偶数月份

            Date beginTime;//检索开始日期

            Date endTime ;//检索结束日期

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

            Calendar calendar = new GregorianCalendar(year, oddMonth - 1, 1,0,0,0);//不可使用无参构造器，因为会读入系统时间的毫秒数造成干扰

            calendar.set(year, oddMonth - 1, 1,0,0,0); //Month value is 0-based. e.g., 0 for January.

            beginTime = calendar.getTime();

            calendar.set(year, evenMonth, 1,0,0,0);

            endTime = calendar.getTime();

            List<DailyEvent> list = dailyEventDao.selectEventsBetweenTime(dateFormat.format(beginTime), dateFormat.format(endTime), "百度贴吧", false);

            List<DailyEvent> oddList = list.stream()
                    .filter(dailyEvent -> {
                        calendar.setTime(dailyEvent.getPostTime());
                        return calendar.get(Calendar.MONTH) % 2 == 0;
                    })
                    .collect(Collectors.toList());//奇数月事件列表

            List<DailyEvent> evenList = list.stream()
                    .filter(dailyEvent -> {
                        calendar.setTime(dailyEvent.getPostTime());
                        return calendar.get(Calendar.MONTH) % 2 != 0;
                    })
                    .collect(Collectors.toList());//偶数月事件列表

            int oddEventCount = oddList.size();//奇数月事件数目

            int evenEventCount = evenList.size();//偶数月事件数目

            int oddCommentCount = (int) oddList
                    .stream()
                    .mapToInt(DailyEvent::getFollowCount)
                    .summaryStatistics()
                    .getSum();//奇数月事件评论数

            int evenCommentCount = (int) evenList
                    .stream()
                    .mapToInt(DailyEvent::getFollowCount)
                    .summaryStatistics()
                    .getSum();//偶数月事件评论数

            int oddHandledCount = (int) oddList
                    .stream()
                    .filter(dailyEvent -> (dailyEvent.getCollectionStatus() == 1))
                    .count();//偶数月事件处置数

            int evenHandledCount = (int) evenList
                    .stream()
                    .filter(dailyEvent -> (dailyEvent.getCollectionStatus() == 1))
                    .count();//偶数月事件处置数

            int heatCount = (int) list
                    .stream()
                    .filter(dailyEvent -> (dailyEvent.getFollowCount() > 20))
                    .count();//热点事件数

            int eventCount = evenEventCount + oddEventCount;//统计事件总数

            String oddMonthChar = monthsChar[oddMonth];//奇月中文数字

            String evenMonthChar = monthsChar[evenMonth];//偶数月中文数字

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
            String generateDate = dateTimeFormatter.format(LocalDateTime.now());//报表生成日期

            calendar.setTime(endTime);
            calendar.add(Calendar.DATE,-1);
            Date endOfEvenMonth = calendar.getTime();
            List<ChartPoint> pointList = ChartServiceImpl.getChart(list, beginTime.getTime(), endOfEvenMonth.getTime(), ChartDataEnum.POSTCOUNT.getDataType());
            JFreeChart doubleMonthChart = ChartGenerator.generateDoubleMonthChart(pointList, "专题信息量趋势图", "专题信息量趋势");

            calendar.setTime(beginTime);
            calendar.add(Calendar.MONTH,1);
            calendar.add(Calendar.DATE,-1);
            Date endOfOddMonth = calendar.getTime();
            List<ChartPoint> pointList1 = ChartServiceImpl.getChart(oddList, beginTime.getTime(), endOfOddMonth.getTime(), ChartDataEnum.POSTCOUNT.getDataType());
            JFreeChart oddMonthChart = ChartGenerator.generateSingleMonthChart(pointList1, oddMonthChar + "月份贴吧主题数趋势图");

            List<ChartPoint> pointList2 = ChartServiceImpl.getChart(oddList, beginTime.getTime(), endOfOddMonth.getTime(), ChartDataEnum.FOLOWCOUNT.getDataType());
            JFreeChart oddCommentChart = ChartGenerator.generateSingleMonthChart(pointList2, oddMonthChar + "月份贴吧跟帖数趋势图");

            calendar.setTime(endOfOddMonth);
            calendar.add(Calendar.DATE,1);
            Date beginOfEvenMonth = calendar.getTime();
            List<ChartPoint> pointList3 = ChartServiceImpl.getChart(evenList, beginOfEvenMonth.getTime(), endOfEvenMonth.getTime(), ChartDataEnum.POSTCOUNT.getDataType());
            JFreeChart evenMonthChart = ChartGenerator.generateSingleMonthChart(pointList3, evenMonthChar + "月份贴吧主题数趋势图");

            List<ChartPoint> pointList4 = ChartServiceImpl.getChart(evenList, beginOfEvenMonth.getTime(), endOfEvenMonth.getTime(), ChartDataEnum.FOLOWCOUNT.getDataType());
            JFreeChart evenCommentChart = ChartGenerator.generateSingleMonthChart(pointList4, evenMonthChar + "月份贴吧跟帖数趋势图");

            reportDataMap.put("year", year);
            reportDataMap.put("generateDate", generateDate);
            reportDataMap.put("oddMonth", oddMonth);
            reportDataMap.put("evenMonth", evenMonth);
            reportDataMap.put("oddMonthChar", oddMonthChar);
            reportDataMap.put("evenMonthChar", evenMonthChar);
            reportDataMap.put("oddCommentCount", oddCommentCount);
            reportDataMap.put("evenCommentCount", evenCommentCount);
            reportDataMap.put("evenHandledCount", evenHandledCount);
            reportDataMap.put("oddHandledCount", oddHandledCount);
            reportDataMap.put("oddEventCount", oddEventCount);
            reportDataMap.put("evenEventCount", evenEventCount);
            reportDataMap.put("heatCount", heatCount);
            reportDataMap.put("eventCount", eventCount);
            reportDataMap.put("doubleMonthChart", ChartGenerator.chartToString(doubleMonthChart));
            reportDataMap.put("oddMonthChart", ChartGenerator.chartToString(oddMonthChart));
            reportDataMap.put("oddCommentChart", ChartGenerator.chartToString(oddCommentChart));
            reportDataMap.put("evenCommentChart", ChartGenerator.chartToString(evenCommentChart));
            reportDataMap.put("evenMonthChart", ChartGenerator.chartToString(evenMonthChart));

            return reportDataMap;
        } catch (ParseException e) {

        } catch (IOException e) {

        } catch (BaseException e) {

        }
        return null;
    }
}
