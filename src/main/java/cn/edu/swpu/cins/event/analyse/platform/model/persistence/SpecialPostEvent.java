package cn.edu.swpu.cins.event.analyse.platform.model.persistence;

import lombok.Data;

import java.util.Date;

@Data
public class SpecialPostEvent {

    private int id;
    private String url;
    private String theme;
    private String mainView;
    private int followCount;
    private String postType;
    private Date createdTime;
    private Date postTime;
    private Date lastFollowTime;
    private String source;
    private short collectionStatus;

    public SpecialPostEvent(int id, String url, String theme, String mainView, int followCount, String postType, Date createdTime, Date postTime, Date lastFollowTime, String source, short collectionStatus) {
        this.id = id;
        this.url = url;
        this.theme = theme;
        this.mainView = mainView;
        this.followCount = followCount;
        this.postType = postType;
        this.createdTime = createdTime;
        this.postTime = postTime;
        this.lastFollowTime = lastFollowTime;
        this.source = source;
        this.collectionStatus = collectionStatus;
    }

    public SpecialPostEvent() {
    }
}
