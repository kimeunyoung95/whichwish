package com.example.win10_pc.whichwish;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PushActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.alarmBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager= (NotificationManager)PushActivity.this.getSystemService(PushActivity.this.NOTIFICATION_SERVICE);
                Intent intent1 = new Intent(PushActivity.this.getApplicationContext(),MainActivity.class); //인텐트 생성.

                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity( PushActivity.this,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setSmallIcon(R.drawable.on).setTicker("HETT").setWhen(System.currentTimeMillis())
                        .setNumber(1).setContentTitle("아리따움").setContentText("헤어팩, 매니큐어, 스킨을 사야합니다.")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
                //해당 부분은 API 4.1버전부터 작동합니다.

//setSmallIcon - > 작은 아이콘 이미지
//setTicker - > 알람이 출력될 때 상단에 나오는 문구.
//setWhen -> 알림 출력 시간.
//setContentTitle-> 알림 제목
//setConentText->푸쉬내용

                notificationManager.notify(1, builder.build()); // Notification send
            }
        });
    }}


