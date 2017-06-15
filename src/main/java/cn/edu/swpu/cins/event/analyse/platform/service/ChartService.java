package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-20.
 */
public interface ChartService {
    /**
     * 获取坐标点列表接口
     *
     * @return
     */
    public List<ChartPoint> getChartPoints(String source, String data, String beginTime, String endTime, String eventTable) throws BaseException;
}
