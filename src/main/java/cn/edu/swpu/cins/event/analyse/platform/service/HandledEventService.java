package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;

import java.util.List;

public interface HandledEventService {
    /**
     * 获取处置事件列表接口
     * @param page
     * @return
     * @throws BaseException
     */
    List<HandledEventPage> getHandledEvents(int page, int more,int isHandled,int isFeedBack,boolean isAll) throws BaseException;

    /**
     * 获取处置事件页数接口
     * @return
     * @throws BaseException
     */
    int getPageCount(int more) throws BaseException;

    /**
     * 处置业务接口
     * @return
     * @throws BaseException
     */
    int handle(HandledEventPage handledEventPage) throws BaseException;


    /**
     * 根据ｉd 批量删除事件
     * @param ids
     * @return
     * @throws BaseException
     */
    int deleteEvents(List<Integer> ids) throws BaseException;
}
