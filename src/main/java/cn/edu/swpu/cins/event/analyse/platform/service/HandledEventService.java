package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;

import java.util.List;

public interface HandledEventService {

    List<HandledEventPage> getHandledEvents(int page) throws BaseException;

    int getPageCount() throws BaseException;

    int handle(HandledEventPage handledEventPage) throws BaseException;
}
