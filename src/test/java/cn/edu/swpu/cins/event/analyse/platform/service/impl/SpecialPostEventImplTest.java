package cn.edu.swpu.cins.event.analyse.platform.service.impl;


import cn.edu.swpu.cins.event.analyse.platform.dao.SpecialPostDao;
import cn.edu.swpu.cins.event.analyse.platform.dao.SpecialPostEventDao;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPostEvent;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostEventService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpecialPostEventImplTest {

    @Mock
    private SpecialPostDao specialPostDao;

    @Mock
    private SpecialPostEventDao specialPostEventDao;

    private SpecialPostEventService service;

    @Before
    public void setUp() throws Exception{
        service = new SpecialPostEventServiceImpl(specialPostDao, specialPostEventDao);
    }

    @Test
    public void test_getSpecialPostEventBySpecialPostIds_success() throws Exception {

        List<String> urls = mock(List.class);
        List<Integer> ids = mock(List.class);
        List<SpecialPostEvent> specialPostEventList = mock(List.class);
        when(specialPostDao.selectSpecialPostByIds(ids)).thenReturn(urls);
        when(specialPostEventDao.selectAll(urls)).thenReturn(specialPostEventList);

        assertThat(service.queryAll(ids), is(specialPostEventList));
        verify(specialPostDao).selectSpecialPostByIds(anyList());
        verify(specialPostEventDao).selectAll(urls);
    }
}
