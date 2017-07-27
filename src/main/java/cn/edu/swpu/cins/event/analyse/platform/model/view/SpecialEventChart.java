package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.util.List;

public class SpecialEventChart {

    private String source;
    private String dataTypeName;
    private String beginTime;
    private String endTime;
    private String table;
    private List<Integer> ids;


    public SpecialEventChart(String source, String dataTypeName, String beginTime, String endTime, String table, List<Integer> ids) {
        this.source = source;
        this.dataTypeName = dataTypeName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.table = table;
        this.ids = ids;
    }

    public SpecialEventChart() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
