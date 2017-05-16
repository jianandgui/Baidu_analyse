package cn.edu.swpu.cins.event.analyse.platform.model.persistence;

import lombok.Data;

import java.util.Date;

@Data
public class HandledEvent {
    private int id;
    private String handledCondition;
    private short feedbackCondition;
    private Date collectedTime;
    private Date handledTime;
    private String detail;
    private String remark;
    private int dailyEventId;
    private String recorder;


    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDailyEventId() {
        return dailyEventId;
    }

    public void setDailyEventId(int dailyEventId) {
        this.dailyEventId = dailyEventId;
    }
}
