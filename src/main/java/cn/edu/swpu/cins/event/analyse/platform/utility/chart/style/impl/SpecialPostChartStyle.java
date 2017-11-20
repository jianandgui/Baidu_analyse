package cn.edu.swpu.cins.event.analyse.platform.utility.chart.style.impl;

import cn.edu.swpu.cins.event.analyse.platform.utility.chart.style.BaseChartStyle;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.SimpleDateFormat;

/**
 * Created by LLPP on 2017/11/20.
 */
@Component
public class SpecialPostChartStyle implements BaseChartStyle{
    @Override
    public JFreeChart getChart(String tittle) {

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(tittle, null, null, null, false, true, true);
        XYPlot xyplot = jfreechart.getXYPlot();
        xyplot.setDomainGridlinesVisible(false);
        //设置折线样式
        XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylinerenderer.setSeriesShape(0,new Ellipse2D.Double(-4,-4,8,8));
        xylinerenderer.setBaseShapesVisible(true);
        xylinerenderer.setSeriesPaint(0, Color.RED);
        xylinerenderer.setUseFillPaint(true);

        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();
        dateaxis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));         //水平底部标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));  //垂直标题
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1)); //横坐标间隔
        dateaxis.setUpperMargin(0.01);

        ValueAxis rangeAxis = xyplot.getRangeAxis();//获取柱状
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//避免y轴出现小数
        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));//设置标题字
        return jfreechart;
    }
}
