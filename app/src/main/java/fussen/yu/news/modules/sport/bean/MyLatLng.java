package fussen.yu.news.modules.sport.bean;

import java.io.Serializable;

 public class MyLatLng implements Serializable {
    public final double latitude;
    public final double longitude;
    
    public float speed;
    public float altitude;
    public long timestamp;
    public long sportTime;
    public int stepCount;

    public long timeInterval;

    public MyLatLng(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
