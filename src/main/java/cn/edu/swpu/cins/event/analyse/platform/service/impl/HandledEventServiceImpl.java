package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.HandledEventDao;
import cn.edu.swpu.cins.event.analyse.platform.enums.FeedbackEnum;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;
import cn.edu.swpu.cins.event.analyse.platform.service.HandledEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lp-deepin on 17-5-22.
 */
@Service
public class HandledEventServiceImpl implements HandledEventService {

    private HandledEventDao handledEventDao;
    private final int pageSize;

    @Autowired
    public HandledEventServiceImpl(HandledEventDao handledEventDao,@Value("${event.service.page-count}") int pageSize) {
        this.handledEventDao = handledEventDao;
        this.pageSize = pageSize;
    }

    @Override
    public List<HandledEventPage> getHandledEvents(int page,int more,int isHandled,int isFeedBack,boolean isAll) throws BaseException{

        int pageSize = this.pageSize;

        if(more>0){
            pageSize += more;
        }

        if(page<=0){
            throw new IlleagalArgumentException();
        }

        List<HandledEvent> list = handledEventDao.selectAll((--page)* pageSize, pageSize);



        if(list.size()<=0){
            throw new NoEventException();
        }else {
            return findByConditions(list,isHandled,isFeedBack,isAll)
                    .stream()
                    .map(HandledEventPage::new)
                    .collect(toList());
        }
    }

    //单独抽离一个方法出来作判断是否选择情况

    //约定字段码　：　　０代表未处置或者未反馈　１代表已处置和已经反馈　　２代表未处置和已经作出处置的事件都要查询出来

    public  List<HandledEvent> findByConditions(List<HandledEvent> handledEventList,int isHandled,int isFeedBack,boolean isAll ){

        if(isAll){
            return handledEventList;
        }
        if(isHandled==0){
           if(isFeedBack==0) {
               return handledEventList.stream()
                       .filter(handledEvent -> handledEvent.getHandledCondition().equals("未处置") && handledEvent.getFeedbackCondition() == 0)
                       .collect(toList());
           }else {
               return handledEventList.stream()
                       .filter(handledEvent -> handledEvent.getHandledCondition().equals("未处置") )
                       .collect(toList());
           }
        }
        if(isHandled==1){
            if(isFeedBack==0){
                return handledEventList.stream()
                        .filter(handledEvent -> !handledEvent.getHandledCondition().equals("未处置") && handledEvent.getFeedbackCondition()==0)
                        .collect(toList());
            }else if(isFeedBack==1){
                return handledEventList.stream()
                        .filter(handledEvent -> !handledEvent.getHandledCondition().equals("未处置") && handledEvent.getFeedbackCondition()==1)
                        .collect(toList());
            }else {
                return handledEventList.stream()
                        .filter(handledEvent -> !handledEvent.getHandledCondition().equals("未处置"))
                        .collect(toList());
            }
        }
        if(isHandled==2){
            if(isFeedBack==0){
                return handledEventList.stream()
                        .filter(handledEvent -> handledEvent.getFeedbackCondition()==0)
                        .collect(toList());
            }
            if(isFeedBack==1){
                return handledEventList.stream()
                        .filter(handledEvent -> handledEvent.getFeedbackCondition()==1)
                        .collect(toList());
            }
            if(isFeedBack==2){
                return handledEventList;
            }
        }



        return null;
    }

    @Override
    public int getPageCount(int more) throws BaseException {

        int pageSize = this.pageSize;

        if(more>0){
            pageSize += more;
        }

        try {
            int eventCount = handledEventDao.selectCount();

            int pageCount = eventCount/pageSize;

            if(eventCount%pageSize!=0){
                pageCount++;
            }

            return pageCount;
        }catch (Exception e){
            throw new BaseException("内部错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public int handle(HandledEventPage handledEventPage) throws BaseException {

        String eventHandler= handledEventPage.getEventHandler();
        String detail = handledEventPage.getDetail();
        String handledCondition = handledEventPage.getHandledCondition();
        String condition = handledEventPage.getFeedbackCondition();
        short conditionShort = FeedbackEnum.getIndexByFeedback(condition);

        if(eventHandler == null || detail == null || handledCondition == null || conditionShort == -1){
            throw new IlleagalArgumentException("参数不可为空");
        }else if(eventHandler.length()>10||detail.length()>100||handledCondition.length()>30){
            throw new IlleagalArgumentException("输入长度超出限制");
        }

        HandledEvent handledEvent = new HandledEvent();
        handledEvent.setEventHandler(eventHandler);
        handledEvent.setDetail(detail);
        handledEvent.setFeedbackCondition(conditionShort);
        handledEvent.setHandledTime(new Date());
        handledEvent.setHandledCondition(handledCondition);
        handledEvent.setId(handledEventPage.getId());
        int updateCount = handledEventDao.updateHandledEvent(handledEvent);

        if(updateCount<1){
            throw new OperationFailureException();
        }

        return 0;
    }
}
