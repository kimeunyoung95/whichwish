package com.example.win10_pc.whichwish;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AlarmReceiver extends BroadcastReceiver {
    Context context;
    ArrayList<WishListViewItem> wishListViewItems;
    PendingIntent pendingIntent;
    String title;
    String content;
    double lat;
    double lon;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("log", "TEST");
        pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        this.context = context;
        loadData();
        //조건문
//        if (compare() == true) {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//            builder.setContentTitle(title)
//                    .setContentText(content)
//                    .setTicker("상태바 한줄 메시지")
//                    .setPriority(Notification.PRIORITY_HIGH)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
//                    .setContentIntent(null)
//                    .setAutoCancel(true)
//                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                    .setWhen(System.currentTimeMillis())
//                    .setAutoCancel(true);
//
//            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            nm.notify(1234, builder.build());
//        }
    }

    public void loadData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type listOfWishes = new TypeToken<List<WishListViewItem>>() {
        }.getType();
        wishListViewItems = new Gson().fromJson(preferences.getString("WISH_LIST", ""), listOfWishes);
    }

    public boolean compare() {
        double dist;
        WishListViewItem wishListViewItem;
        Log.i("size", Integer.toString(wishListViewItems.size()));
        for (int i = 0; i < wishListViewItems.size(); i++) {
            wishListViewItem = wishListViewItems.get(i);
            dist = calDistance(lat, lon, Double.parseDouble(wishListViewItem.getLat()), Double.parseDouble(wishListViewItem.getLon()));
            if ((int)dist <= 1000) {
                title = wishListViewItem.getTitle();
                content = wishListViewItem.getContent();
                return true;
            }
        }
        return false;
    }

    public double calDistance(double lat1, double lon1, double lat2, double lon2) {
        Log.i("check", String.valueOf(lat1) + ":" + String.valueOf(lon1) + ":" + String.valueOf(lat2) + " + " + String.valueOf(lon2));
        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환
        Log.i("dist", String.valueOf((int)dist));
        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad) {
        return (double) (rad * (double) 180d / Math.PI);
    }

    private final LocationListener mLocationListener = new LocationListener(){

        @Override
        public void onLocationChanged(Location location) {
            if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                if (compare() == true) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle(title)
                            .setContentText(content)
                            .setTicker("상태바 한줄 메시지")
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.wwicon)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.wwicon))
                            .setContentIntent(null)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                            .setWhen(System.currentTimeMillis())
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    nm.notify(1234, builder.build());
                }
            }
            Log.i("gps", String.valueOf(lat) + " : " + String.valueOf(lon));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
