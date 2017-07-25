package cn.edu.swpu.cins.event.analyse.platform.utility.chart.generator;

import cn.edu.swpu.cins.event.analyse.platform.enums.ChartDataTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.enums.ChartTypeEnum;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import org.jfree.chart.JFreeChart;

import java.util.List;

/**
 * Created by LLPP on 2017/7/21.
 */
public interface ChartGenerator {
    public JFreeChart generateChart(List<ChartPoint> list
            , String title
            , ChartTypeEnum chartType) throws Exception;

    public List<ChartPoint> getChartPoints(List<DailyEvent> events, long begin, long end, ChartDataTypeEnum dataType);

    public String chartToBASE64(JFreeChart chart) throws Exception;
}
