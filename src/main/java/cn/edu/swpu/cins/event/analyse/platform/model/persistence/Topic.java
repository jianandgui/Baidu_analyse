package cn.edu.swpu.cins.event.analyse.platform.model.persistence;

import java.util.List;

public class Topic {
    private int id;
    private String name;
    private String region;
    private List<String> rules;

    public Topic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", rules=" + rules +
                '}';
    }
}
