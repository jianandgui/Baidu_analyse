package cn.edu.swpu.cins.event.analyse.platform.enums;

public enum ChartDataEnum {
    FOLOWCOUNT("跟帖量"),
    POSTCOUNT("发帖量")
    ;

    private String dataType;

    ChartDataEnum(String dataType) {
        this.dataType = dataType;
    }

    public static boolean isInclude(String dataType){
        for (ChartDataEnum data : ChartDataEnum.values()){
            if(data.getDataType().equals(dataType)){
                return true;
            }
        }
        return false;
    }

    public String getDataType() {
        return dataType;
    }
}
