package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.HandledEventDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.exception.RepeatlyCollectException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyPageEvent;
import cn.edu.swpu.cins.event.analyse.platform.service.DailyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DailyEventServiceImpl implements DailyEventService {

    private DailyEventDao dailyEventDao;
    private int pageSize;
    private HandledEventDao handledEventDao;

    @Autowired
    public DailyEventServiceImpl(DailyEventDao dailyEventDao
            ,HandledEventDao handledEventDao
            ,@Value("${event.service.page-count}") int pageSize) {
        this.dailyEventDao = dailyEventDao;
        this.handledEventDao = handledEventDao;
        this.pageSize = pageSize;
    }

    @Override
    public List<DailyPageEvent> getDailyEventsByPage(int page) throws BaseException{

        if(page<=0){
            throw new IlleagalArgumentException();
        }

        List<DailyEvent> list = dailyEventDao.selectAll((--page)* pageSize, pageSize);

        if(list.size()<=0){
            throw new NoEventException();
        }else {
            return list.stream()
                    .map(DailyPageEvent::new)
                    .collect(toList());
        }
    }

    @Override
    public int getPageCount() throws BaseException {
        try {
            int eventCount = dailyEventDao.selectCount();

            int pageCount = eventCount/pageSize;

            if(eventCount%pageSize!=0){
                pageCount++;
            }

            return pageCount;
        }catch (Exception e){
            throw new BaseException("内部错误", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public int collectEvent(String recorder,int dailyEventId) throws BaseException{
        //todo 异常抛出
        //1判断recorder是否合法，2判断事件是否已经归集，3执行插入语句 4将对应event的归并状态改变
        if(handledEventDao.selectByDailyEvent(dailyEventId)!=null){
            throw new RepeatlyCollectException();
        }

        HandledEvent handledEvent = new HandledEvent();
        handledEvent.setRemark("");
        handledEvent.setDetail("");
        handledEvent.setCollectedTime(new Date());
        handledEvent.setHandledCondition("未处置");
        handledEvent.setFeedbackCondition((short)0);
        handledEvent.setRecorder(recorder);
        handledEvent.setDailyEventId(dailyEventId);
        handledEvent.setHandledTime(null);

        handledEventDao.insertHandledEvent(handledEvent);

        dailyEventDao.updateCollectStatus(dailyEventId);

        return 1;
    }

}
