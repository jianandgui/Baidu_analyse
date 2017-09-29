package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPostEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.Ids;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class SpecialPostEventController {

    private SpecialPostEventService specialPostEventService;

    @Autowired
    public SpecialPostEventController(SpecialPostEventService specialPostEventService) {
        this.specialPostEventService = specialPostEventService;
    }

    @PostMapping("/specialPostEvents")
    public ResponseEntity queryAllByIds(@RequestBody Ids ids) throws NoEventException {
        try {
            List<SpecialPostEvent> specialPostEventList = specialPostEventService.queryAll(ids.getIds());
            return new ResponseEntity(specialPostEventList, HttpStatus.OK);
        } catch (NoEventException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
