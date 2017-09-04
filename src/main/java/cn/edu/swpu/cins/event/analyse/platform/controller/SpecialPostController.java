package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import cn.edu.swpu.cins.event.analyse.platform.model.view.DeleteIds;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/event/specialPost")
public class SpecialPostController {

    @Autowired
    private SpecialPostService specialPostService;

    @PostMapping("/add")
    public ResponseEntity addSpecialPost(@RequestBody SpecialPost specialPost){
        try {
            specialPostService.addSpecialPost(specialPost);
            return new ResponseEntity("添加成功", HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/list")
    public ResponseEntity queryAllSpecialPost(){
        try{
            List<SpecialPost> specialPosts = specialPostService.querySpecialPosts();
            return new ResponseEntity(specialPosts,HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity(e.getMessage(),e.getStatus());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity deleteByIds(@RequestBody DeleteIds ids){
        try{
            specialPostService.deleteSpecialPostByIds(ids.getIds());
            return new ResponseEntity("删除成功", HttpStatus.OK);
        }catch (BaseException e){
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }
}