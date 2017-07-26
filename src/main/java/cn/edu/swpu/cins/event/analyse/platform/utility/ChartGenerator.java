package cn.edu.swpu.cins.event.analyse.platform.utility;

import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.impl.ChartServiceImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import sun.misc.BASE64Encoder;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class ChartGenerator {
    private static final long DAY = 86400000L;
    private static final DateFormat CHART_DISPLAY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *  获得
     * @param list
     * @param title
     * @param chartType
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws OperationFailureException
     */


    public static JFreeChart generateChart(List<ChartPoint> list
            , String title
            , ChartTypeEnum chartType) throws ParseException,IOException,OperationFailureException{
        JFreeChart chart = null;

        if(chartType.equals(ChartTypeEnum.SINGLE_MONTH)){
            chart = generateSingleMonthChart(list,title);
        }else if(chartType.equals(ChartTypeEnum.DOUBLE_MONTH)){
            chart = generateDoubleMonthChart(list,title);
        }

        if(chart != null)
            return chart;
        else
            throw new OperationFailureException();
    }

    /**
     * 将jfreechart对象转换为Base64编码字符串
     * @param chart
     * @return
     * @throws IOException
     */
    public static String chartToString(JFreeChart chart) throws IOException{
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
     * 生成折线图坐标集方法
     * @param events events list
     * @param begin miliseconds of begin time
     * @param end miliseconds of end time
     * @param dataType 统计数据类型如跟帖量
     * @return
     */
    public static List<ChartPoint> getChartPoints(List<DailyEvent> events, long begin, long end, String dataType) {
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
                if (ChartDataTypeEnum.POSTCOUNT.getDataType().equals(dataType)) {
                    count++;
                } else if (ChartDataTypeEnum.FOLOWCOUNT.getDataType().equals(dataType)) {
                    count += event.getFollowCount();
                }

            } else {
                //清算count,生成相应的chartresult插入list
                day[dayNo] = count;
                if (ChartDataTypeEnum.POSTCOUNT.getDataType().equals(dataType)) {
                    count = 1;
                } else if (ChartDataTypeEnum.FOLOWCOUNT.getDataType().equals(dataType)) {
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

    private static JFreeChart generateSingleMonthChart(List<ChartPoint> list , String Title) throws ParseException{
        XYDataset xyDataset = createDataSet(list,"");

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(Title, null, null, xyDataset, false, true, true);
        XYPlot xyplot = jfreechart.getXYPlot();
        xyplot.setDomainGridlinesVisible(false);
        //设置折线样式
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylinerenderer.setSeriesShape(0,new Ellipse2D.Double(-4,-4,8,8));
        xylinerenderer.setBaseShapesVisible(true);
        xylinerenderer.setBasePaint(Color.BLUE);
        xylinerenderer.setSeriesPaint(0,Color.RED);
        xylinerenderer.setUseFillPaint(true);

        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 2)); //横坐标间隔
        dateaxis.setUpperMargin(0.01);

        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字
        return jfreechart;
    }

    private static JFreeChart generateDoubleMonthChart(List<ChartPoint> list , String Title) throws ParseException{
        XYDataset xyDataset = createDataSet(list,"");

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(Title, null, null, xyDataset, false, true, true);
        XYPlot xyplot = jfreechart.getXYPlot();

        //设置折线样式
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylinerenderer.setSeriesShape(0,new Ellipse2D.Double(-4,-4,8,8));
        xylinerenderer.setBaseShapesVisible(true);
        xylinerenderer.setBasePaint(Color.BLUE);
        xylinerenderer.setSeriesPaint(0,Color.RED);
        xylinerenderer.setUseFillPaint(true);
        //设置刻度样式
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1));
        dateaxis.setUpperMargin(0.06);

        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字

        return jfreechart;
    }

    private static XYDataset createDataSet(List<ChartPoint> list , String SeriesName) throws ParseException{
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
