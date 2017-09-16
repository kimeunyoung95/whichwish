package com.example.win10_pc.whichwish;


import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

import android.widget.ListView;


import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    WishListAdapter wishListAdapter = new WishListAdapter();

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

        loadData();

        listView.setAdapter(wishListAdapter);

        //리스트뷰 설정

        wishListAdapter.addItem("test1", "content1");
        wishListAdapter.addItem("test2", "content2");
        wishListAdapter.addItem("test1", "content1");
        wishListAdapter.addItem("test2", "content2");

        wishListAdapter.addItem("test1", "content1");

        wishListAdapter.addItem("test2", "content2");


        wishListAdapter.addItem("test1", "content1");

        wishListAdapter.addItem("test2", "content2");


        wishListAdapter.addItem("test1", "content1");

        wishListAdapter.addItem("test2", "content2");

        //TEST

    }


    @Override

    protected void onDestroy() {

        super.onDestroy();

        saveData();

    }


    public void saveData() {

        try {

            FileOutputStream fos = new FileOutputStream("t.tmp");

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(wishListAdapter.getListViewItems());

            oos.close();

        } catch (FileNotFoundException e) {


        } catch (IOException e) {


        }

    }


    public void loadData() {

        try {

            FileInputStream fis = new FileInputStream("t.tmp");

            ObjectInputStream ois = new ObjectInputStream(fis);

            wishListAdapter.setData((ArrayList<WishListViewItem>) ois.readObject());

            ois.close();

        } catch (FileNotFoundException e) {


        } catch (IOException e) {


        } catch (ClassNotFoundException e) {


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