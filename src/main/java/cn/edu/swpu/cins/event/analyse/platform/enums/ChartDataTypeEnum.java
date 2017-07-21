package cn.edu.swpu.cins.event.analyse.platform.enums;

public enum ChartDataTypeEnum {
    FOLOWCOUNT("跟帖量"),
    POSTCOUNT("发帖量"),
    DOUBLELINE("双折线图")
    ;

    private String dataType;

    ChartDataTypeEnum(String dataType) {
        this.dataType = dataType;
    }

    public static boolean isInclude(String dataType){
        for (ChartDataTypeEnum data : ChartDataTypeEnum.values()){
            if(data.getDataType().equals(dataType)){
                return true;
            }
        }
        return false;
    }

    public String getDataType() {
        return dataType;
    }

    public static ChartDataTypeEnum getDataType(String dataType) {
        for (ChartDataTypeEnum chartDataTypeEnum : ChartDataTypeEnum.values()){
            if(chartDataTypeEnum.getDataType().equals(dataType))
                return chartDataTypeEnum;
        }
        return null;
    }
}
