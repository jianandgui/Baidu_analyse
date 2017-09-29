package cn.edu.swpu.cins.event.analyse.platform.model.view;

/**
 * Created by lp-deepin on 17-5-20.
 */
public class ChartPoint {
    private String x;
    private long y;

    public ChartPoint(String key, Integer value) {
        this.x = key;
        this.y = value;
    }

    public ChartPoint() {
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }
}
