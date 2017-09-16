package com.example.win10_pc.whichwish;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by kyi42 on 2017-09-17.
 */

public class SettingData implements Parcelable{
    @SerializedName("array")
    private ArrayList<WishListViewItem> arrayList;

    public SettingData() {

    }

    public ArrayList<WishListViewItem> getData(){
        return  (arrayList != null) ? arrayList : new ArrayList<WishListViewItem>();
    }
    public void setData(ArrayList<WishListViewItem> arrayList)
    {
        this.arrayList = arrayList != null ? arrayList : new ArrayList<WishListViewItem>();
    }
    protected SettingData(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SettingData> CREATOR = new Creator<SettingData>() {
        @Override
        public SettingData createFromParcel(Parcel in) {
            return new SettingData(in);
        }

        @Override
        public SettingData[] newArray(int size) {
            return new SettingData[size];
        }
    };
    public String toString(){
        return new GsonBuilder().create().toJson(this);
    }
}
