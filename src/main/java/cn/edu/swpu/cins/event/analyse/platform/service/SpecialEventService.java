package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;

import java.util.List;

/**
 * Created by muyi on 17-6-7.
 */
public interface SpecialEventService{

    public List<DailyEvent> getSpecialEvent(int page, boolean getAll ,int more) throws BaseException;

    public int getPageCount(int more) throws BaseException;
}
