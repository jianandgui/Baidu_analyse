package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.Topic;
import cn.edu.swpu.cins.event.analyse.platform.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event/topic")
public class TopicController {

    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getTopics() {
        try {
            List<Topic> list = topicService.getTopics();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTopic(@RequestBody Topic topic) {
        try {
            topicService.addTopic(topic);
            return new ResponseEntity<>("添加成功", HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    @PostMapping("/delete")
    public ResponseEntity<?> deleteTopics(@RequestBody List<Integer> ids) {
        try {
            topicService.deleteTopics(ids);
            return new ResponseEntity<>("删除成功", HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
