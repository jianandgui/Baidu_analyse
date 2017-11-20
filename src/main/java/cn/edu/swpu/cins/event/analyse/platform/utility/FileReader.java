package cn.edu.swpu.cins.event.analyse.platform.utility;

import cn.edu.swpu.cins.event.analyse.platform.dao.SpecialPostEventDao;
import cn.edu.swpu.cins.event.analyse.platform.model.view.FileChartPoint;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FileReader {

    @Autowired
    private SpecialPostEventDao specialPostEventDao;

    public List<FileChartPoint> dateAndFollowCout(List<String> urlOfPosts) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.FileReader("/opt/baidu-event/trend_file.json"));
//        BufferedReader reader = new BufferedReader(new java.io.FileReader("/home/yang/file/baidu_test.json"));

        StringBuilder stringBuilder = new StringBuilder();
        String str = reader.readLine();
        //先将所有的都读取到内存
        while (str != null) {
            stringBuilder.append(str);
            str = reader.readLine();
        }
        //生成json源
        JSONObject dataJson = null;
        try {
            dataJson = new JSONObject(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //传入需要查询热度的url
//        String url = "http://tieba.baidu.com/p/4753089223";
        //获取该对象的信息
        JSONObject followCount = null;
        List<FileChartPoint> fileChartPointList = new ArrayList<>(urlOfPosts.size());
        try {
            for (String urlOfPost : urlOfPosts) {
                followCount = dataJson.getJSONObject(urlOfPost);
                HashMap<String, Integer> followCountMap = new HashMap<>();
                String theme = specialPostEventDao.selectThemeByUrl(urlOfPost);
                //删除首尾{ }
                StringBuilder follow = new StringBuilder(followCount.toString()).deleteCharAt(0);
                follow.deleteCharAt(follow.length() - 1);
                //分离后面的日期 count数
                String[] followMap=follow.toString().split(",");
                //循环将每个日期对应的count都生成一个键值对 放入map
                for (String s : followMap) {
                    String[] spiltDateCount = s.split(":");
                    //去除空格
                    followCountMap.put(spiltDateCount[0].replaceAll("\"",""), Integer.valueOf(spiltDateCount[1]));
                }
                FileChartPoint fileChartPoint = new FileChartPoint(followCountMap,theme);
                fileChartPointList.add(fileChartPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileChartPointList;
    }
}
