package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-22.
 */
public interface HandledEventService {
    List<HandledEventPage> getHandledEvents(int page) throws BaseException;
}
