package cn.edu.swpu.cins.event.analyse.platform.model.persistence;

import lombok.Data;

import java.util.Date;

/**
 * Created by lp-deepin on 17-5-5.
 */
@Data
public class DailyEvent {
    private int id;
    private String url;
    private String theme;
    private String mainView;
    private int followCount;
    private String postType;
    private Date createdTime;
    private Date postTime;
    private String source;
    private short collectionStatus;

    @Override
    public String toString() {
        return "DailyEvent{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", theme='" + theme + '\'' +
                ", mainView='" + mainView + '\'' +
                ", followCount=" + followCount +
                ", postType='" + postType + '\'' +
                ", createdTime=" + createdTime +
                ", postTime=" + postTime +
                ", source='" + source + '\'' +
                ", collectionStatus=" + collectionStatus +
                '}';
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
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
}
