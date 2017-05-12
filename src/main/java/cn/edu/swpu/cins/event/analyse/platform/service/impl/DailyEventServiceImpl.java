package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.DailyEventDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.IlleagalArgumentException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyPageEvent;
import cn.edu.swpu.cins.event.analyse.platform.service.DailyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by lp-deepin on 17-5-5.
 */
@Service
public class DailyEventServiceImpl implements DailyEventService {

    private DailyEventDao dailyEventDao;
    private int pageSize;

    @Autowired
    public DailyEventServiceImpl(DailyEventDao dailyEventDao
            ,@Value("${event.service.page-count}") int pageSize) {
        this.dailyEventDao = dailyEventDao;
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
    public int getPageCount() {

        int eventCount = dailyEventDao.selectCount();

        int pageCount = eventCount/pageSize;

        if(eventCount%pageSize!=0){
            pageCount++;
        }

        return pageCount;
    }
}
