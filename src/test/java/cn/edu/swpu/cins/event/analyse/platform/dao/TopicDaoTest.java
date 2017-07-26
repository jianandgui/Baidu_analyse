package cn.edu.swpu.cins.event.analyse.platform.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicDaoTest {
    @Test
    public void selectAll() throws Exception {

        System.out.println(topicDao.selectAll());
    }

    @Autowired
    TopicDao topicDao;

    @Test
    public void test_insert() throws Exception {
//        String[] strings = {"test","test","test"};
//        for (int i = 0 ;i < 5 ; i++){
//            Topic topic = new Topic();
//            topic.setName("testName" + i);
//            topic.setRegion("Region" + i);
//            topic.setRules(Arrays.asList(strings));
//            topicDao.insertTopic(topic);
//        }
//
//        List<Topic> list = topicDao.selectAll();
//        list.forEach(
//                System.out::println
//        );
    }

    @Test
    public void test_delete()throws Exception{
//        Integer[] arr = {1,2,3};
//        topicDao.deleteByIds(Arrays.asList(arr));
    }


}