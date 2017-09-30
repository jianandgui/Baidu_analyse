package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.model.view.FileChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.model.view.SpecialPostEventChart;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostEventChartService;
import cn.edu.swpu.cins.event.analyse.platform.utility.FileReader;
import cn.edu.swpu.cins.event.analyse.platform.utility.MapToList;
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
    public List<SpecialPostEventChart> getChartPoints(List<String> urls) throws IOException, NoEventException {

        if (urls.isEmpty() || urls.size() > 5) {
            throw new IllegalArgumentException("参数异常！");
        }
        for (String url : urls) {
            if (url == null || url.equals("") || url.equals(" ")) {
                throw new IllegalArgumentException("参数异常！");
            }
        }
        List<FileChartPoint> fileChartPointList= null;
        List<SpecialPostEventChart> chartPointList = null;

        try{
            fileChartPointList = fileReader.dateAndFollowCout(urls);

        }catch (IOException e){
            throw new IOException("读取文件失败！");
        }

        if (fileChartPointList.isEmpty()) {
            throw new NoEventException("没有该事件！");
        }
        chartPointList = mapToList.mapToList(fileChartPointList);
        return chartPointList;
    }
}
