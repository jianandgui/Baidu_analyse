package cn.edu.swpu.cins.event.analyse.platform.utility;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

@Service
public class FileReader {

    public HashMap<String, Integer> dateAndFollowCout(String urlOfPost) throws IOException {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //传入需要查询热度的url
//        String url = "http://tieba.baidu.com/p/4753089223";
        //获取该对象的信息
        JSONObject followCount = null;
        try {
            followCount = dataJson.getJSONObject(urlOfPost);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, Integer> followCountMap = new HashMap<>();
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

        return followCountMap;
    }
}
