package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.util.List;

public class VO<T> {

    //返回事件列表和总页数

    private List<T> EventPageList;
    private int pages;


    public VO(List<T> eventPageList, int pages) {
        EventPageList = eventPageList;
        this.pages = pages;
    }


    public VO() {
    }

    public List<T> getEventPageList() {
        return EventPageList;
    }

    public void setEventPageList(List<T> eventPageList) {
        EventPageList = eventPageList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
