package com.example.win10_pc.whichwish;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    ArrayList<WishListViewItem> wishListViewItems;
    AlarmManager alarmManager;
    WishListAdapter wishListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmManager = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        Alarm();
        //알람매니저 설정 로컬 푸쉬
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
        Button add_btn = (Button) findViewById(R.id.add_btn);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        //객체화 및 리스너 설정
        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Location_Search.class);
                startActivity(intent);
            }

        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Type listOfWishes = new TypeToken<List<WishListViewItem>>() {
        }.getType();
        wishListViewItems = new Gson().fromJson(preferences.getString("WISH_LIST", ""), listOfWishes);
        //데이터 로드 완료
        try {
            wishListAdapter = new WishListAdapter(wishListViewItems, getApplicationContext());
        } catch (NullPointerException e) {
            wishListAdapter = new WishListAdapter(getApplicationContext());
        }
        listView.setAdapter(wishListAdapter);
        //리스트뷰 설정 완료

        //TEST
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    public void saveData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Type listOfWishes = new TypeToken<List<WishListViewItem>>() {
        }.getType();
        String strWishes = new Gson().toJson(wishListAdapter.getListViewItems(), listOfWishes);
//        strWishes = new Gson().toJson(new ArrayList<WishListViewItem>(), listOfWishes);
        Log.i("save", strWishes);
        preferences.edit().putString("WISH_LIST", strWishes).apply();
    }

    public void loadData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Type listOfWishes = new TypeToken<List<WishListViewItem>>() {
        }.getType();
        ArrayList<WishListViewItem> wishListViewItems = new Gson().fromJson(preferences.getString("WISH_LIST", ""), listOfWishes);
    }

    public void Alarm() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }//스플래쉬로 이동
//        SmartLocation.with(getApplicationContext()).location().start(new OnLocationUpdatedListener() {
//            @Override
//            public void onLocationUpdated(Location location) {
//
//            }
//        });
        PendingIntent alarmIntent;
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        long after = 1000 * 10;
        long t = SystemClock.elapsedRealtime();
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, t, after, alarmIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getExtras().getString("title") != null) {
            wishListAdapter.addItem(intent.getExtras().getString("title"), intent.getExtras().getString("content"),
                    intent.getExtras().getString("where"), intent.getExtras().getString("lat"), intent.getExtras().getString("lng"));
        }
        saveData();
    }
}