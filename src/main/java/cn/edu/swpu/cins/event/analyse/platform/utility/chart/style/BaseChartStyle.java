package cn.edu.swpu.cins.event.analyse.platform.utility.chart.style;

import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import org.jfree.chart.JFreeChart;

import java.util.List;

/**
 * Created by LLPP on 2017/7/20.
 */
public interface BaseChartStyle {

    public JFreeChart getChart(String tittle);
}
