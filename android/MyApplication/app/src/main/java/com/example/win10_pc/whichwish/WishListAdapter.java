package com.example.win10_pc.whichwish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kyi42 on 2017-09-15.
 */

public class WishListAdapter extends BaseAdapter {
    private ArrayList<WishListViewItem> listViewItems = new ArrayList<WishListViewItem>();

    public WishListAdapter(){

    }
    public void setData(ArrayList<WishListViewItem> wishListViewItems){
        this.listViewItems = wishListViewItems;
    }
    public ArrayList<WishListViewItem> getListViewItems(){
        return listViewItems;
    }
    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.wishlistview_item, viewGroup, false);
        }

        TextView title_tv = (TextView)view.findViewById(R.id.title_tv);
        TextView content_tv = (TextView)view.findViewById(R.id.content_tv);

        WishListViewItem wishListViewItem = listViewItems.get(i);
        title_tv.setText(wishListViewItem.getTitle());
        content_tv.setText(wishListViewItem.getContent());

        return view;
    }
    public void addItem(String title, String content){
        WishListViewItem item = new WishListViewItem();
        item.setTitle(title);
        item.setContent(title);

        listViewItems.add(item);
    }
    public void delItem(int index){
        listViewItems.remove(index);
    }
}
