package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostEventChartService;
import cn.edu.swpu.cins.event.analyse.platform.utility.FileReader;
import cn.edu.swpu.cins.event.analyse.platform.utility.MapToList;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpecialPostEventChartServiceTest {

    @Mock
    private FileReader fileReader;

    @Mock
    private MapToList mapToList;

    private SpecialPostEventChartService service;

    @Before
    public void setUp() {
        service = new SpecialPostEventChartImpl(fileReader,mapToList);
    }

    @Test
    public void test_return_chartPoints_success() throws IOException, JSONException {
        List<ChartPoint> chartPointList = mock(List.class);
        HashMap<String, Integer> map = mock(HashMap.class);

        when(mapToList.mapToList(map)).thenReturn(chartPointList);
        when(fileReader.dateAndFollowCout(anyString())).thenReturn(map);
        assertThat(service.getChartPoints(anyString()), is(chartPointList));
        verify(fileReader).dateAndFollowCout(anyString());
    }
}
