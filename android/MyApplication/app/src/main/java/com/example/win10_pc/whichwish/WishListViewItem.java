package com.example.win10_pc.whichwish;

/**
 * Created by kyi42 on 2017-09-15.
 */

public class WishListViewItem {
    private String title;//해야할 일
    private String map;//map 위치
    private String lat;
    private String lon;
    private String content;

    protected WishListViewItem(String title, String content, String map, String lat, String lon) {
        this.title = title;
        this.content = content;
        this.map = map;
        this.lat = lat;
        this.lon = lon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getMap() {
        return map;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
