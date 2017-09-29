package cn.edu.swpu.cins.event.analyse.platform.utility;

import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MapToList {

    public List<ChartPoint> mapToList(HashMap<String, Integer> map) {
        List<ChartPoint> chartPointList = new ArrayList<>(map.size());
        map.entrySet()
                .forEach(stringIntegerEntry ->
                        chartPointList.add(new ChartPoint(stringIntegerEntry.getKey(), stringIntegerEntry.getValue())));

        return chartPointList;
    }
}
