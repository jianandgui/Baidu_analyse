package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;

import java.util.Map;

/**
 * Created by LLPP on 2017/6/19.
 */
public interface ReportService {
    public Map<String ,Object> getReportDataMap(int year, int issue) throws Exception;
}
