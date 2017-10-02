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


}
