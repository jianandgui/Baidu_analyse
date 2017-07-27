package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;

import java.util.List;
import java.util.Map;

/**
 * Created by lp-deepin on 17-5-20.
 */
public interface ChartService {
    /**
     * 获取坐标点列表接口
     *
     * @return
     */
    public Map<String, List<ChartPoint>> getChartPoints(String source, String dataTypeName, String beginTime, String endTime, String eventTable,List<Integer> ids) throws BaseException;
}
