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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
