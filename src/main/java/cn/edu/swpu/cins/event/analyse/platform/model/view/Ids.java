package cn.edu.swpu.cins.event.analyse.platform.model.view;

import lombok.Data;

import java.util.List;

@Data
public class Ids {
   private List<Integer> ids;

    public Ids() {
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
