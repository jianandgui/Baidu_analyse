package cn.edu.swpu.cins.event.analyse.platform.dao;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPostEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpecialPostEventDaoTest {

    @Mock
    private SpecialPostEventDao specialPostEventDao;

    @Mock
    private SpecialPostDao specialPostDao;


    //尝试使用TDD开发模式
    //查询所有事件
    @Test
    public void test_selectSpecialPostEvents_success(){
        int pageNum = 0;
        int count = 5;

//        List<SpecialPostEvent> specialPostEvents = mock(List.class);
//        when(specialPostEventDao.selectAll(pageNum,count)).thenReturn(specialPostEvents);
//        assertThat(specialPostEventDao.selectAll(pageNum,count),is(specialPostEvents));
//        verify(specialPostEventDao.selectAll(pageNum,count));

//        SpecialPost specialPost = mock(SpecialPost.class);
//        when(specialPostDao.insertSpecialPost(specialPost));

    }

}