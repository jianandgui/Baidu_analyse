package cn.edu.swpu.cins.event.analyse.platform.model.view;

import java.util.List;

public class EventDelete {

    private List<Integer> ids;

    public EventDelete(List<Integer> ids) {
        this.ids = ids;
    }

    public EventDelete() {
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
