package cn.edu.swpu.cins.event.analyse.platform.enums;

/**
 * Created by lp-deepin on 17-5-22.
 */
public enum FeedbackEnum {
    FEEDBACKED("已反馈",(short)1),
    UNFEEDBACK("未反馈",(short)0)
    ;
    private String feedbackCondition;
    private short index;

    FeedbackEnum(String feedbackCondition, short index) {
        this.feedbackCondition = feedbackCondition;
        this.index = index;
    }

    public String getFeedbackCondition() {
        return feedbackCondition;
    }

    public void setFeedbackCondition(String feedbackCondition) {
        this.feedbackCondition = feedbackCondition;
    }

    public short getIndex() {
        return index;
    }
    public void setIndex(short index) {
        this.index = index;
    }

    public static String getFeedbackByIndex(short index){
        for (FeedbackEnum feedbackEnum :FeedbackEnum.values()){
            if(feedbackEnum.getIndex()==index){
                return feedbackEnum.getFeedbackCondition();
            }
        }
        return FeedbackEnum.UNFEEDBACK.getFeedbackCondition();
    }
    public static short getIndexByFeedback(String feedbackCondition){
        for (FeedbackEnum feedbackEnum :FeedbackEnum.values()){
            if(feedbackCondition.equals(feedbackEnum.getFeedbackCondition())){
                return feedbackEnum.getIndex();
            }
        }
        return FeedbackEnum.UNFEEDBACK.getIndex();
    }
}
