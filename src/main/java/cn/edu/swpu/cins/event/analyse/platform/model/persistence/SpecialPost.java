package cn.edu.swpu.cins.event.analyse.platform.model.persistence;

import lombok.Data;

import java.util.List;

@Data
public class SpecialPost {

    private int id;
    private List<String> url;
    private String name;

    public SpecialPost(int id, List<String> url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public SpecialPost() {
    }
}
