package cn.edu.swpu.cins.event.analyse.platform.model.view;

import cn.edu.swpu.cins.event.analyse.platform.enums.FeedbackEnum;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.HandledEvent;
import lombok.Data;

/**
 * Created by lp-deepin on 17-5-22.
 */
@Data
public class HandledEventPage {
    private int id;
    private String theme;
    private String mainView;
    private String url;
    private String handledCondition;
    private String feedbackCondition;
    private long collectedTime;
    private long handledTime;
    private String recorder;
    private String detail;
    private String remark;

    public HandledEventPage() {
    }

    public HandledEventPage(int id, String theme, String mainView
            , String url, String handledCondition, String feedbackCondition
            , long collectedTime, long handledTime, String recorder
            , String detail, String remark) {
        this.id = id;
        this.theme = theme;
        this.mainView = mainView;
        this.url = url;
        this.handledCondition = handledCondition;
        this.feedbackCondition = feedbackCondition;
        this.collectedTime = collectedTime;
        this.handledTime = handledTime;
        this.recorder = recorder;
        this.detail = detail;
        this.remark = remark;
    }

    public HandledEventPage(HandledEvent handledEvent) {
        setId(handledEvent.getId());
        setTheme(handledEvent.getTheme());
        setMainView(handledEvent.getMainView());
        setUrl(handledEvent.getUrl());
        setHandledCondition(handledEvent.getHandledCondition());
        setFeedbackConditionByShort(handledEvent.getFeedbackCondition());
        setCollectedTime(handledEvent.getCollectedTime().getTime());
        //todo handletime 可能为空
        setHandledTime(handledEvent.getHandledTime().getTime());
        setRecorder(handledEvent.getRecorder());
        setDetail(handledEvent.getDetail());
        setRemark(handledEvent.getRemark());
    }

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

    public String getFeedbackCondition() {
        return feedbackCondition;
    }

    public void setFeedbackConditionByShort(String feedbackCondition) {
        this.feedbackCondition = feedbackCondition;
    }

    public void setFeedbackConditionByShort(short feedbackCondition) {
        this.feedbackCondition = FeedbackEnum.getFeedbackByIndex(feedbackCondition);
    }

    public long getCollectedTime() {
        return collectedTime;
    }

    public void setCollectedTime(long collectedTime) {
        this.collectedTime = collectedTime;
    }

    public long getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(long handledTime) {
        this.handledTime = handledTime;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
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
}
