package com.example.win10_pc.whichwish;

/**
 * Created by sbk on 2017-09-16.
 */

public class MemberData {
    String addr;
    String lat;
    String lng;

    public MemberData(String _addr, String _lat, String _lng){
        addr = _addr;
        lat = _lat;
        lng = _lng;
    }

    public  void setAddr(String _addr){
        addr = _addr;
    }

    public void setLat(String _lat){
        lat = _lat;
    }

    public void setLng(String _lng){
        lng = _lng;
    }

    public String getAddr(){
        return  addr;
    }

    public String getLat(){
        return  lat;
    }

    public String getLng(){
        return lng;
    }
}
