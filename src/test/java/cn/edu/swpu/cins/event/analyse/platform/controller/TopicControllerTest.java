package cn.edu.swpu.cins.event.analyse.platform.controller;

import cn.edu.swpu.cins.event.analyse.platform.service.TopicService;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TopicControllerTest {


    @MockBean
    private TopicService topicService;

    @Autowired
    private TopicController topicController;

    private MockMvc mockMvc;

    @Before
    public void initialize(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }
    @Test
    public void getTopics() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/event/topic/list")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

}