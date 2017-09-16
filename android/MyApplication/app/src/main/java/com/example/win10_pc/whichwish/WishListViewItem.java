package com.example.win10_pc.whichwish;

/**
 * Created by kyi42 on 2017-09-15.
 */

public class WishListViewItem{
    private String title;//해야할 일
    private String content;//map 위치

    protected WishListViewItem(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }
}
