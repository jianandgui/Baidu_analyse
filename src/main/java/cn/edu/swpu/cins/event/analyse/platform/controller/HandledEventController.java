package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.HandledEventPage;
import cn.edu.swpu.cins.event.analyse.platform.service.HandledEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getHandledEvents(@PathVariable("page") int page) {
        try {
            List<HandledEventPage> list = null;
            list = handledEventService.getHandledEvents(page);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
