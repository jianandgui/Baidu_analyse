package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostEventChartService;
import cn.edu.swpu.cins.event.analyse.platform.utility.FileReader;
import cn.edu.swpu.cins.event.analyse.platform.utility.MapToList;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class SpecialPostEventChartImpl implements SpecialPostEventChartService {

    private FileReader fileReader;
    private MapToList mapToList;

    @Autowired
    public SpecialPostEventChartImpl(FileReader fileReader, MapToList mapToList) {
        this.fileReader = fileReader;
        this.mapToList = mapToList;
    }

    @Override
    public List<ChartPoint> getChartPoints(String url) throws IOException, JSONException, NoEventException {

        if (url == null || url.equals("") || url.equals(" ")) {
            throw new IllegalArgumentException("参数异常！");
        }
        HashMap<String, Integer> map = null;
        List<ChartPoint> chartPointList = null;

        try{
            map = fileReader.dateAndFollowCout(url);

        }catch (IOException e){
            throw new IOException("读取文件失败！");
        }

        if (map.isEmpty()) {
            throw new NoEventException("没有该事件！");
        }
        chartPointList = mapToList.mapToList(map);
        return chartPointList;
    }
}
