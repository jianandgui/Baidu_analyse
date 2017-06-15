package cn.edu.swpu.cins.event.analyse.platform.enums;

public enum  EventTableEnum {
    DAILY_EVENT("dailyEvent"),
    HANDLED_EVENT("handledEvent"),
    SPECIAL_EVENT("specialEvent")
            ;

    private String eventTable;

    EventTableEnum(String eventTable) {
        this.eventTable = eventTable;
    }

    public static boolean isInclude(String eventTable){
        for (EventTableEnum table : EventTableEnum.values()){
            if(table.getEventTable().equals(eventTable)){
                return true;
            }
        }
        return false;
    }

    public String getEventTable() {
        return eventTable;
    }
}
