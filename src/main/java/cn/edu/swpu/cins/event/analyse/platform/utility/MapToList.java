package cn.edu.swpu.cins.event.analyse.platform.utility;

import cn.edu.swpu.cins.event.analyse.platform.dao.SpecialPostEventDao;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPostEvent;
import cn.edu.swpu.cins.event.analyse.platform.model.view.ChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.model.view.FileChartPoint;
import cn.edu.swpu.cins.event.analyse.platform.model.view.SpecialPostEventChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MapToList {


    @Autowired
    private SpecialPostEventDao specialPostEventDao;

    public List<SpecialPostEventChart> mapToList(List<FileChartPoint> fileChartPoints) {

/*
        //总的事件坐标点集合
        List<SpecialPostEventChart> chartPointList = new ArrayList<>();
        map.entrySet()
                .forEach(stringIntegerEntry ->
                        chartPointList.add(new ChartPoint(stringIntegerEntry.getKey(), stringIntegerEntry.getValue())));
*/



        //mapList.stream().forEach(map -> new SpecialPostEventChart()map.entrySet().forEach(stringIntegerEntry ->));

        /*for (HashMap<String, Integer> map : mapList) {
            SpecialPostEventChart specialPostEventChart = new SpecialPostEventChart();
            map.entrySet().forEach(stringIntegerEntry -> specialPostEventChart.setChartPoint() );
        }*/

        //FileChartPoint
        //HashMap<date,count>  String theme


        List<SpecialPostEventChart> specialPostEventChartList = new ArrayList<>(fileChartPoints.size());
        for (FileChartPoint fileChartPoint : fileChartPoints) {

            //获取map
            HashMap<String, Integer> map = fileChartPoint.getMap();
            //获取主题名
            String theme = fileChartPoint.getTheme();
            //设置接受类
            SpecialPostEventChart specialPostEventChart = new SpecialPostEventChart();
            //赋上主题名
            specialPostEventChart.setTheme(theme);
            List<ChartPoint> chartPointList = new ArrayList<>();
            //将每个map都转化为list
            map.entrySet().forEach(stringIntegerEntry -> chartPointList.add(new ChartPoint(stringIntegerEntry.getKey(),stringIntegerEntry.getValue())));
            specialPostEventChart.setChartPoint(chartPointList);
            specialPostEventChartList.add(specialPostEventChart);
        }

        return specialPostEventChartList;
    }
}
