package cn.edu.swpu.cins.event.analyse.platform.model.view;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;
import lombok.Data;

/**
 * Created by lp-deepin on 17-5-7.
 */
@Data
public class DailyEventPage {
    private int id;
    private String url;
    private String theme;
    private String mainView;
    private int followCount;
    private String postType;
    private long postTime;
    private long lastFollowTime;
    private String source;
    private short collectionStatus;

    public DailyEventPage() {
    }

    public DailyEventPage(DailyEvent dailyEvent) {
        id=dailyEvent.getId();
        url=dailyEvent.getUrl();
        theme=dailyEvent.getTheme();
        mainView=dailyEvent.getMainView();
        followCount=dailyEvent.getFollowCount();
        postType =dailyEvent.getPostType();
        source=dailyEvent.getSource();
        collectionStatus =dailyEvent.getCollectionStatus();
        postTime=dailyEvent.getPostTime().getTime();
        lastFollowTime = dailyEvent.getLastFollowTime().getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public short getCollectionStatus() {
        return collectionStatus;
    }

    public void setCollectionStatus(short collectionStatus) {
        this.collectionStatus = collectionStatus;
    }

    public long getLastFollowTime() {
        return lastFollowTime;
    }

    public void setLastFollowTime(long lastFollowTime) {
        this.lastFollowTime = lastFollowTime;
    }

    @Override
    public String toString() {
        return "DailyEventPage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", theme='" + theme + '\'' +
                ", mainView='" + mainView + '\'' +
                ", followCount=" + followCount +
                ", postType='" + postType + '\'' +
                ", postTime=" + postTime +
                ", source='" + source + '\'' +
                ", collectionStatus=" + collectionStatus +
                '}';
    }
}
