package cn.edu.swpu.cins.event.analyse.platform.model.view;

import lombok.Data;

import java.util.HashMap;

@Data
public class FileChartPoint {

    private HashMap<String, Integer> map;
    private String theme;

    public FileChartPoint(HashMap<String, Integer> map, String theme) {
        this.map = map;
        this.theme = theme;
    }

    public FileChartPoint() {
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Integer> map) {
        this.map = map;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
