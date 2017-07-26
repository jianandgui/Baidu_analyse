package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.util.List;

public class VO {

    private List<HandledEventPage> handledEventPageList;
    private int pages;

    public List<HandledEventPage> getHandledEventPageList() {
        return handledEventPageList;
    }

    public void setHandledEventPageList(List<HandledEventPage> handledEventPageList) {
        this.handledEventPageList = handledEventPageList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public VO(List<HandledEventPage> handledEventPageList, int pages) {
        this.handledEventPageList = handledEventPageList;
        this.pages = pages;
    }

    public VO() {
    }
}
