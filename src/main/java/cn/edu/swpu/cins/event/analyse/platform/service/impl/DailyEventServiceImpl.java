package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.HandledEventDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.*;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyEventPage;
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

    private int pageSize;
    private DailyEventDao dailyEventDao;
    private HandledEventDao handledEventDao;

    @Autowired
    public DailyEventServiceImpl(DailyEventDao dailyEventDao
            , HandledEventDao handledEventDao
            , @Value("${event.service.page-count}") int pageSize) {
        this.dailyEventDao = dailyEventDao;
        this.handledEventDao = handledEventDao;
        this.pageSize = pageSize;
    }

    @Override
    public List<DailyEventPage> getDailyEventsByPage(int page, int more) throws BaseException {

        int pageSize = this.pageSize;

        if(more>0){
            pageSize += more;
        }

        if (page <= 0) {
            throw new IlleagalArgumentException();
        }

        List<DailyEvent> list = dailyEventDao.selectAll((--page) * pageSize, pageSize);

        if (list.size() <= 0) {
            throw new NoEventException();
        } else {
            return list.stream()
                    .map(DailyEventPage::new)
                    .collect(toList());
        }
    }

    @Override
    public int getPageCount(int more) throws BaseException {

        int pageSize = this.pageSize;

        if(more>0){
            pageSize += more;
        }

        try {
            int eventCount = dailyEventDao.selectCount();

            int pageCount = eventCount / pageSize;

            if (eventCount % pageSize != 0) {
                pageCount++;
            }

            return pageCount;
        } catch (Exception e) {
            throw new BaseException("内部错误", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,BaseException.class})
    public int collectEvent(String mainView, String type, String recorder, int dailyEventId,String table) throws BaseException {
        //todo 1判断recorder是否合法
        if (recorder.length()>10){
            throw new IlleagalArgumentException("记录人不存在");
        }

        //1判断recorder是否合法，2判断事件是否已经归集，3执行插入语句 4将对应event的归并状态改变
        if (handledEventDao.selectByDailyEvent(dailyEventId) != null) {
            throw new RepeatlyCollectException();
        }

        HandledEvent handledEvent = new HandledEvent();
        handledEvent.setEventHandler("");
        handledEvent.setDetail("");
        handledEvent.setCollectedTime(new Date());
        handledEvent.setHandledCondition("未处置");
        handledEvent.setFeedbackCondition((short) 0);
        handledEvent.setRecorder(recorder);
        handledEvent.setDailyEventId(dailyEventId);
        handledEvent.setHandledTime(null);

        int insertCount = handledEventDao.insertHandledEvent(handledEvent);
        int updateCount ;
        int updateDailyCount ;
        if(table.equals("daily")){
            table = "daily_event";
        }else {
            table = "special_post_event";
        }
        updateCount = dailyEventDao.updateCollectStatus(dailyEventId,table);
        updateDailyCount = dailyEventDao.updateMainViewAndPostTypeById(dailyEventId,mainView,type,table);

        if (insertCount + updateCount + updateDailyCount!= 3) {
            throw new OperationFailureException();
        }

        return 1;
    }

}
