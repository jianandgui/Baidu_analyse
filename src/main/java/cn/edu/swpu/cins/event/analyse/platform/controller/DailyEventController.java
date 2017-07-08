package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyPageEvent;
import cn.edu.swpu.cins.event.analyse.platform.service.DailyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-7.
 */
@RestController
@RequestMapping("/event")
public class DailyEventController {
    private DailyEventService dailyEventService;

    @Autowired
    public DailyEventController(DailyEventService dailyEventService) {
        this.dailyEventService = dailyEventService;
    }

    @GetMapping(value = {"/dailyEvent/page/{pageNum}"})
    public ResponseEntity<?> getDailyEventList(@PathVariable("pageNum") int pageNum){
        try{
            List<DailyPageEvent> resultList = dailyEventService.getDailyEventsByPage(pageNum);
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }
    }

    @GetMapping(value = {"/dailyEvent/pageCount"})
    public ResponseEntity<?> getPageCount(){
        try {
            return new ResponseEntity<>(dailyEventService.getPageCount(),HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }
    }

    @PostMapping(value = {"/dailyEvent/{id}/{recorder}/collect"})
    public ResponseEntity<?> collectEvent(@PathVariable String recorder,@PathVariable("id") int id){
        try {
            dailyEventService.collectEvent(recorder,id);
        }catch (BaseException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }
        return new ResponseEntity<Object>("归集成功",HttpStatus.OK);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> ParamExceptionHandler(NumberFormatException e) {
        return new ResponseEntity<Object>("参数错误", HttpStatus.BAD_REQUEST);
    }

}
