package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.model.view.DailyPageEvent;
import cn.edu.swpu.cins.event.analyse.platform.service.DailyEventService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lp-deepin on 17-5-7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyEventControllerTest {
    @MockBean
    private DailyEventService dailyEventService;

    @Autowired
    private DailyEventController controller;

    private MockMvc mockMvc;

    @Before
    public void initialize(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void should_get_events_return_OK()throws Exception{
        int page = 1;
        DailyPageEvent event = new DailyPageEvent();
        List<DailyPageEvent> list = Lists.newArrayList(event);
        when(dailyEventService.getDailyEventsByPage(1)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/event/dailyEvent/page/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("${0}").value(list));
    }
}