package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.SpecialEventPage;
import cn.edu.swpu.cins.event.analyse.platform.model.view.VO;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialEventService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event/specialEvent")
public class SpecialEventController {

    @Autowired
    private SpecialEventService specialEventService;

    /*
    根据前端的需求现在将页数和事件列表一同返回
     */

    //获取专题事件的集合
    @PostMapping("/{page}")
    public ResponseEntity<?> getTopics(@PathVariable int page
            , @RequestBody SpecialEventPage specialEventPage) {
        try {
            VO vo= specialEventService.getSpecialEvent(page,false , specialEventPage.getMore(),specialEventPage.getIds());
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

//    @GetMapping("/pageCount")
//    public ResponseEntity<?> getTopics(@RequestParam(value = "more" , required = false , defaultValue = "0") int more) {
//        try {
//            int pageCount = specialEventService.getPageCount(more);
//            return new ResponseEntity<>(pageCount, HttpStatus.OK);
//        } catch (BaseException e) {
//            return new ResponseEntity<>(e.getMessage(), e.getStatus());
//        }
//    }

}
