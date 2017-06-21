package cn.edu.swpu.cins.event.analyse.platform.utility;

import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataEnum;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartTypeEnum;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by LLPP on 2017/6/21.
 */
public class ChartGenerator {

    public static JFreeChart generateChart(List<ChartPoint> list , String title , String seriesName  , ChartTypeEnum chartType) throws ParseException{
        JFreeChart chart = null;
        if(chartType.equals(ChartTypeEnum.SINGLE_MONTH)){
            chart = generateSingleMonthChart(list,title);
        }else if(chartType.equals(ChartTypeEnum.DOUBLE_MONTH)){
            chart = generateDoubleMonthChart(list,title,seriesName);
        }
        return chart;
    }

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

    public static JFreeChart generateSingleMonthChart(List<ChartPoint> list , String Title) throws ParseException{
        XYDataset xyDataset = createDataSet(list,"");

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(Title, "日期", "数量", xyDataset, true, true, true);
        XYPlot xyplot = jfreechart.getXYPlot();
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylinerenderer.setBaseShape(new Rectangle(10, 10));
        xylinerenderer.setBaseShapesVisible(true);
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 2)); //横坐标间隔
        dateaxis.setUpperMargin(0.01);

        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getLegend().setVisible(false);
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字
        return jfreechart;
    }

    public static JFreeChart generateDoubleMonthChart(List<ChartPoint> list , String Title , String seriesName) throws ParseException{
        XYDataset xyDataset = createDataSet(list,seriesName);

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(Title, "日期", "信息数量", xyDataset, true, true, true);
        XYPlot xyplot = jfreechart.getXYPlot();
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylinerenderer.setBaseShape(new Rectangle(10, 10));
        xylinerenderer.setBaseShapesVisible(true);
        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 15));  //垂直标题
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1));
        dateaxis.setUpperMargin(0.06);

        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 20));
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
