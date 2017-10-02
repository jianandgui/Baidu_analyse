package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyEventPage;
import cn.edu.swpu.cins.event.analyse.platform.service.DailyEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = {"/dailyEvent/{pageNum}"})
    public ResponseEntity<?> getDailyEventList(@PathVariable("pageNum") int pageNum
            ,@RequestParam(required = false,name = "more",defaultValue = "0") int more){
        try{
            List<DailyEventPage> resultList = dailyEventService.getDailyEventsByPage(pageNum,more);
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }
    }

    @GetMapping(value = {"/dailyEvent/pageCount"})
    public ResponseEntity<?> getPageCount(@RequestParam(required = false,name = "more",defaultValue = "0") int more){
        try {
            return new ResponseEntity<>(dailyEventService.getPageCount(more),HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }
    }

    @PostMapping(value = {"/dailyEvent/{id}/collect"})
    public ResponseEntity<?> collectEvent(@RequestParam("recorder") String recorder
            , @PathVariable("id") int id
            , @RequestParam("mainView") String mainView
            , @RequestParam("postType") String postType
            , @RequestParam("table") String table ){
        try {
            dailyEventService.collectEvent(mainView, postType, recorder, id,table);
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
