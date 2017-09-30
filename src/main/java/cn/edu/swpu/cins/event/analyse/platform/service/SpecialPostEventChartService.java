package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.model.view.SpecialPostEventChart;

import java.io.IOException;
import java.util.List;

public interface SpecialPostEventChartService {

    List<SpecialPostEventChart> getChartPoints(List<String> url) throws NoEventException, IOException;
}
