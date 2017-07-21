package cn.edu.swpu.cins.event.analyse.platform.utility.chart.generator.impl;

import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.utility.chart.generator.ChartGenerator;
import cn.edu.swpu.cins.event.analyse.platform.utility.chart.style.BaseChartStyle;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LLPP on 2017/7/20.
 */
@Component
public class ChartGeneratorImpl implements ChartGenerator{
    private final long DAY = 86400000;
    private static final String CHART_DISPLAY_DATE_PATTERN = "yyyy-MM-dd";
    @Autowired
    Map<String,BaseChartStyle> chartStyleMap; //图的样式类对象与其beanid的映射集

    /**
     *  根据传入点列表与参数生成特定jfreechart图表
     * @param list
     * @param title
     * @param chartType
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws OperationFailureException
     */
    @Override
    public JFreeChart generateChart(List<ChartPoint> list
            , String title
            , ChartTypeEnum chartType) throws ParseException,IOException,OperationFailureException {
        JFreeChart chart = null;

        if(chartType == null)
            throw new OperationFailureException();

        String styleId = chartType.getStyleBeanId();
        chart = chartStyleMap
                .get(styleId)
                .getChart(title);

        addPointSeriesToChart(list,chart);

        if(chart != null)
            return chart;
        else
            throw new OperationFailureException();
    }

    /**
     * 将jfreechart转化为BASE64编码格式的字符串
     * @param chart
     * @return
     * @throws IOException
     */
    @Override
    public String chartToBASE64(JFreeChart chart) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String img = null;

        ChartUtilities.writeChartAsPNG(outputStream, chart, 800, 350);
        outputStream.flush();
        outputStream.close();

        byte[] bytes = outputStream.toByteArray();
        img = encoder.encode(bytes);
        return img;
    }

    /**
     * 生成折线图坐标集方法,将事件按天数分组
     * @param events events list
     * @param begin miliseconds of begin time
     * @param end miliseconds of end time
     * @param dataType 统计数据类型如跟帖量
     * @return
     */
    @Override
    public List<ChartPoint> getChartPoints(List<DailyEvent> events, long begin, long end, ChartDataTypeEnum dataType) {

        long curDay = begin; //当前天
        int dayCount = (int) ((end - begin) / DAY) + 1;//区间天数
        List<ChartPoint> chartPoints = new ArrayList<ChartPoint>(dayCount);
        int[] day = new int[dayCount];//每天的统计量
        int dayNo = 0;//当前天对应index
        int count = 0;//统计量

        for (DailyEvent event : events) {
            long time = event.getPostTime().getTime();
            if (time - curDay < DAY) {
                //事件的发帖时间在当天则统计
                if (ChartDataTypeEnum.POSTCOUNT.equals(dataType)) {
                    count++;
                } else if (ChartDataTypeEnum.FOLOWCOUNT.equals(dataType)) {
                    count += event.getFollowCount();
                }

            } else {
                //清算count,生成相应的chartresult插入list
                day[dayNo] = count;
                if (ChartDataTypeEnum.POSTCOUNT.equals(dataType)) {
                    count = 1;
                } else if (ChartDataTypeEnum.FOLOWCOUNT.equals(dataType)) {
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
        DateFormat displayFormat = new SimpleDateFormat(CHART_DISPLAY_DATE_PATTERN);
        for (int cnt : day) {
            ChartPoint point = new ChartPoint();
            point.setX(displayFormat.format(new Date(beginTime)));
            point.setY(cnt);
            chartPoints.add(point);
            beginTime += DAY;
        }

        return chartPoints;
    }

    private void addPointSeriesToChart(List<ChartPoint> list, JFreeChart jFreeChart) throws ParseException{
        XYDataset dataset = createDataSet(list,"");
        XYPlot xyPlot = jFreeChart.getXYPlot();
        int seriesSize = xyPlot.getDatasetCount();
        xyPlot.setDataset(seriesSize,dataset);
    }

    private XYDataset createDataSet(List<ChartPoint> list , String SeriesName) throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TimeSeries timeseries = new TimeSeries(SeriesName);

        if(list != null && list.size() > 0){
            for (ChartPoint point : list ) {
                Date date = dateFormat.parse(point.getX());
                timeseries.add(new Day(date), point.getY());
            }
        }

        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(timeseries);
        return timeseriescollection;
    }
}
