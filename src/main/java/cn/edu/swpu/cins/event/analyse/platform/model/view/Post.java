package cn.edu.swpu.cins.event.analyse.platform.model.view;

/**
 * Created by LLPP on 2017/11/18.
 */
public class Post {
    private int index;
    private String url;
    private String theme;
    private String imageContent;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
}
