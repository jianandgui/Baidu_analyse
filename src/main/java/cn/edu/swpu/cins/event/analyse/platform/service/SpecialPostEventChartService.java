package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface SpecialPostEventChartService {

    List<ChartPoint> getChartPoints(String url) throws JSONException, NoEventException, IOException;
}
