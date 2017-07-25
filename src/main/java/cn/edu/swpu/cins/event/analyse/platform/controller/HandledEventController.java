package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;
import cn.edu.swpu.cins.event.analyse.platform.service.HandledEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lp-deepin on 17-5-22.
 */
@RestController
@RequestMapping("/event")
public class HandledEventController {
    private HandledEventService handledEventService;

    @Autowired
    public HandledEventController(HandledEventService handledEventService) {
        this.handledEventService = handledEventService;
    }

    @GetMapping("/handledEvent/{page}")
    public ResponseEntity<?> getHandledEvents(@PathVariable("page") int page,
                                              @RequestParam (required = false,name="isHandled",defaultValue = "2") int isHandled,
                                              @RequestParam(required = false,name = "isFeedBack",defaultValue = "2") int isReflect,
                                              @RequestParam(required = false,name = "more",defaultValue = "0") int more,
                                              @RequestParam (required = false,name="isAll",defaultValue = "true") boolean isAll) {
        try {
            List<HandledEventPage> list;
            list = handledEventService.getHandledEvents(page,more,isHandled,isReflect,isAll);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = {"/handledEvent/pageCount"})
    public ResponseEntity<?> getPageCount(@RequestParam(required = false,name = "more",defaultValue = "0") int more) {
        try {
            return new ResponseEntity<>(handledEventService.getPageCount(more), HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','VIP')")
    @PostMapping("/handledEvent/{id}/handle")
    public ResponseEntity<?> handle(@PathVariable int id, @RequestBody HandledEventPage handledEventPage) {
        try{
            handledEventService.handle(handledEventPage);
            return new ResponseEntity<>("处置成功", HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> ParamExceptionHandler(NumberFormatException e) {
        return new ResponseEntity<Object>("参数错误", HttpStatus.BAD_REQUEST);
    }

}
