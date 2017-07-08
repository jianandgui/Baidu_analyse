package cn.edu.swpu.cins.event.analyse.platform.model.persistence;

import lombok.Data;

import java.util.Date;

@Data
public class HandledEvent {
    private int id;
    private String theme;
    private String mainView;
    private String url;
    private String handledCondition;
    private short feedbackCondition;
    private Date collectedTime;
    private Date handledTime;
    private String detail;
    private String eventHandler;
    private int dailyEventId;
    private String recorder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getMainView() {
        return mainView;
    }

    public void setMainView(String mainView) {
        this.mainView = mainView;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHandledCondition() {
        return handledCondition;
    }

    public void setHandledCondition(String handledCondition) {
        this.handledCondition = handledCondition;
    }

    public short getFeedbackCondition() {
        return feedbackCondition;
    }

    public void setFeedbackCondition(short feedbackCondition) {
        this.feedbackCondition = feedbackCondition;
    }

    public Date getCollectedTime() {
        return collectedTime;
    }

    public void setCollectedTime(Date collectedTime) {
        this.collectedTime = collectedTime;
    }

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(Date handledTime) {
        this.handledTime = handledTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(String eventHandler) {
        this.eventHandler = eventHandler;
    }

    public int getDailyEventId() {
        return dailyEventId;
    }

    public void setDailyEventId(int dailyEventId) {
        this.dailyEventId = dailyEventId;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    @Override
    public String toString() {
        return "HandledEvent{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", mainView='" + mainView + '\'' +
                ", url='" + url + '\'' +
                ", handledCondition='" + handledCondition + '\'' +
                ", feedbackCondition=" + feedbackCondition +
                ", collectedTime=" + collectedTime +
                ", handledTime=" + handledTime +
                ", detail='" + detail + '\'' +
                ", eventHandler='" + eventHandler + '\'' +
                ", dailyEventId=" + dailyEventId +
                ", recorder='" + recorder + '\'' +
                '}';
    }
}
