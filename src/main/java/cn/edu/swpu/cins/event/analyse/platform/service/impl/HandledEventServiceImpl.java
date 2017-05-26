package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.HandledEventDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;
import cn.edu.swpu.cins.event.analyse.platform.service.HandledEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    public List<HandledEventPage> getHandledEvents(int page) throws BaseException{
        if(page<=0){
            throw new IlleagalArgumentException();
        }

        List<HandledEvent> list = handledEventDao.selectAll((--page)* pageSize, pageSize);

        if(list.size()<=0){
            throw new NoEventException();
        }else {
            return list.stream()
                    .map(HandledEventPage::new)
                    .collect(toList());
        }
    }

    @Override
    public int getPageCount() throws BaseException {
        try {
            int eventCount = handledEventDao.selectCount();

            int pageCount = eventCount/pageSize;

            if(eventCount%pageSize!=0){
                pageCount++;
            }

            return pageCount;
        }catch (Exception e){
            throw new BaseException("内部错误", HttpStatus.NOT_FOUND);
        }
    }
}
