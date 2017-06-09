package cn.edu.swpu.cins.event.analyse.platform.model.view;

import cn.edu.swpu.cins.event.analyse.platform.model.persistence.DailyEvent;

import java.util.List;

public class SpecialEventPage {
    private int pageCount;
    private List<DailyEvent> list;

    public SpecialEventPage() {
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<DailyEvent> getList() {
        return list;
    }

    public void setList(List<DailyEvent> list) {
        this.list = list;
    }
}
