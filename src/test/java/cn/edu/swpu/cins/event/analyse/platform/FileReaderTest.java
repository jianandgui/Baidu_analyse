package cn.edu.swpu.cins.event.analyse.platform;


import com.sun.javafx.collections.MappingChange;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileReaderTest {


    @Test
    public void test() throws IOException, JSONException {

        BufferedReader reader = new BufferedReader(new FileReader("/home/yang/file/baidu_test.json"));
        StringBuilder stringBuilder = new StringBuilder();
        String str = reader.readLine();
        //先将所有的都读取到内存
        while (str != null) {
            stringBuilder.append(str);
            str = reader.readLine();
        }

        //生成json源
        JSONObject dataJson = new JSONObject(stringBuilder.toString());
        //传入需要查询热度的url
        String url = "http://tieba.baidu.com/p/4753089223";
        //获取该对象的信息
        JSONObject followCount = dataJson.getJSONObject(url);
        HashMap<String, Integer> followCountMap = new HashMap<>();
        //删除首尾{ }
        StringBuilder follow = new StringBuilder(followCount.toString()).deleteCharAt(0);
        follow.deleteCharAt(follow.length() - 1);


        //分离后面的日期 count数
        String[] followMap=follow.toString().split(",");

        //循环将每个日期对应的count都生成一个键值对 放入map
        for (String s : followMap) {
            String[] spiltDateCount = s.split(":");
            followCountMap.put(spiltDateCount[0], Integer.valueOf(spiltDateCount[1]));
        }

        for (Map.Entry map : followCountMap.entrySet()) {
            System.out.println("键为 ："+ map.getKey()+", "+"值为 ："+map.getValue());
        }

    }
}
