package cn.edu.swpu.cins.event.analyse.platform.enums;

/**
 * Created by LLPP on 2017/6/21.
 */
public enum ChartTypeEnum {
    DOUBLE_MONTH("doubleMonthChartStyle"),
    SINGLE_MONTH("singleMonthChartStyle"),
    SPECIAL_POST("specialPostChartStyle")
    ;

    String styleBeanId;

    ChartTypeEnum(String styleBeanId) {
        this.styleBeanId = styleBeanId;
    }

    public String getStyleBeanId() {
        return styleBeanId;
    }

    public void setStyleBeanId(String styleBeanId) {
        this.styleBeanId = styleBeanId;
    }


}
