package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.util.List;

public class VO<T> {

    //返回事件列表和总页数

    private List<T> EventPageList;
    private int pages;

    public List<T> EventPageList() {
        return EventPageList;
    }

    public void EventPageList(List<T> EventPageList) {
        this.EventPageList = EventPageList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public VO(List<T> EventPageList, int pages) {
        this.EventPageList = EventPageList;
        this.pages = pages;
    }

    public VO() {
    }
}
