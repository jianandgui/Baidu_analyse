package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecialPostDaoTest {

    @Autowired
    SpecialPostDao specialPostDao;


    @Test
    public void insertSpecialPost() throws Exception {
        SpecialPost specialPost = new SpecialPost();
        specialPost.setName("test1");
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        specialPost.setUrl(list);

        assertEquals(1,specialPostDao.insertSpecialPost(specialPost));
    }

    @Test
    public void selectAllSpecialPost() throws Exception {
        log.info(specialPostDao.selectAllSpecialPost().toString());
    }

    @Test
    public void deleteByIds() throws Exception {
        List ids = Arrays.asList("2", "3");
        assertEquals(2,specialPostDao.deleteByIds(ids));
    }

    @Test
    public void test_specialPostDao() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.add(25);
        List<SpecialPost> list1=specialPostDao.selectSpecialPostByIds(list);
        log.info(list1.toString());
    }

}