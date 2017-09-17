
package com.example.win10_pc.whichwish;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kyi42 on 2017-09-15.
 */


public class WishListAdapter extends BaseAdapter {
    private ArrayList<WishListViewItem> listViewItems;
    Context context;

    public WishListAdapter(Context context) {
        listViewItems = new ArrayList<WishListViewItem>();
        this.context = context;
    }

    public WishListAdapter(ArrayList<WishListViewItem> listViewItems, Context context) {
        if (listViewItems != null) {
            this.listViewItems = listViewItems;
        } else
            this.listViewItems = new ArrayList<WishListViewItem>();
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<WishListViewItem> wishListViewItems) {
        this.listViewItems = wishListViewItems;
    }

    public ArrayList<WishListViewItem> getListViewItems() {
        return listViewItems;
    }

    @Override
    public int getCount() {
        if (listViewItems == null) {
            return 0;
        } else {

            return listViewItems.size();
        }
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

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.wishlistview_cancelitem, viewGroup, false);
        }
        TextView num_tv = (TextView) view.findViewById(R.id.num_tv);
        TextView title_tv = (TextView) view.findViewById(R.id.title_tv);
        TextView content_tv = (TextView) view.findViewById(R.id.content_tv);
        final Button del_btn = (Button) view.findViewById(R.id.del_btn);
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delItem(pos);
            }
        });
        WishListViewItem wishListViewItem = listViewItems.get(i);
        num_tv.setText(Integer.toString(i + 1));
        title_tv.setText(wishListViewItem.getTitle());
        content_tv.setText(wishListViewItem.getMap());
        return view;
    }

    public void addItem(String title, String content, String map, String lat, String lon) {
        WishListViewItem item = new WishListViewItem(title, content, map, lat, lon);
        item.setTitle(title);
        item.setContent(content);
        item.setMap(map);
        item.setLat(lat);
        item.setLon(lon);
        listViewItems.add(item);
    }

    public void delItem(int index) {
        listViewItems.remove(index);
        notifyDataSetChanged();
        //저장
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type listOfWishes = new TypeToken<List<WishListViewItem>>() {
        }.getType();
        String strWishes = new Gson().toJson(getListViewItems(), listOfWishes);
//        strWishes = new Gson().toJson(new ArrayList<WishListViewItem>(), listOfWishes);
        Log.i("save", strWishes);
        preferences.edit().putString("WISH_LIST", strWishes).apply();
    }
}