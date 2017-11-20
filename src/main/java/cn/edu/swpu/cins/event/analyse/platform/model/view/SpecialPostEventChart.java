package cn.edu.swpu.cins.event.analyse.platform.model.view;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPostEventChart {

    private List<ChartPoint> chartPoint;
    private String theme;

    public SpecialPostEventChart(List<ChartPoint> chartPoint, String theme) {
        this.chartPoint = chartPoint;
        this.theme = theme;
    }

    public SpecialPostEventChart() {
    }

    public List<ChartPoint> getChartPoint() {
        return chartPoint;
    }

    public void setChartPoint(List<ChartPoint> chartPoint) {
        this.chartPoint = chartPoint;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
