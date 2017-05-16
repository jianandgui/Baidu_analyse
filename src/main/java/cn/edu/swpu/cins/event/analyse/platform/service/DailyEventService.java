package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyPageEvent;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-5.
 */
public interface DailyEventService {
    /**
     * 获取日常事件列表接口
     * @param page
     * @return
     */
    public List<DailyPageEvent> getDailyEventsByPage(int page) throws BaseException;

    /**
     * 获取页数接口
     * @return
     */
    public int getPageCount() throws BaseException;

    /**
     * 归集事件接口
     * @return
     */
    public int collectEvent(String recorder,int dailyEventId) throws BaseException;
}
