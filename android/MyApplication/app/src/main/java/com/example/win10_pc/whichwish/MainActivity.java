package com.example.win10_pc.whichwish;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<WishListViewItem> arrayList = new ArrayList<WishListViewItem>();
    WishListAdapter wishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
        Button add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Location_Search.class);
                startActivity(intent);
            }

        });

//        final View header = getLayoutInflater().inflate(R.layout.wishlistview_header, null, false);
//        //객체 생성
//        header.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, Location_Search.class);
//                //intent add Activity
//                startActivity(intent);
//            }
//        });
//        listView.addHeaderView(header);
        //헤더 추가
        wishListAdapter = new WishListAdapter();
        loadData2();
        listView.setAdapter(wishListAdapter);
        //리스트뷰 설정
        wishListAdapter.addItem("test1", "content1");
        wishListAdapter.addItem("test2", "content2");
        //TEST
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData2();
    }
    public void saveData2(){
        SettingData data = new SettingData();
        data.setData(wishListAdapter.getListViewItems());
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("setting", data.toString()).apply();
    }
    public void loadData2(){
        String json = PreferenceManager.getDefaultSharedPreferences(this).getString("setting", null);
        SettingData data2 = new GsonBuilder().create().fromJson(json, SettingData.class);
        arrayList = data2.getData();
    }

    public void saveData() {
        try {
            FileOutputStream fos = new FileOutputStream("./t.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(wishListAdapter.getListViewItems());
            oos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadData() {
        try {
            FileInputStream fis = new FileInputStream(".\t.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            wishListAdapter.setData((ArrayList<WishListViewItem>) ois.readObject());
            ois.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "IOException", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(getApplicationContext(), "ClassNotFoundException", Toast.LENGTH_SHORT).show();
        }
    }


    @Override

    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            wishListAdapter.addItem(intent.getExtras().getString("title"), intent.getExtras().getString("where"));
        }
    }
}