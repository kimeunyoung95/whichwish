package com.example.win10_pc.whichwish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {
    Button button;
    EditText et1, et2, et3;
    String mAddr, mLat, mLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        et1 = (EditText) findViewById(R.id.title_et);
        et2 = (EditText) findViewById(R.id.content_et);
        et3 = (EditText) findViewById(R.id.where_et);
        button = (Button) findViewById(R.id.ok_btn);
        Intent location = getIntent();
        mAddr = location.getStringExtra("addr");
        mLat = location.getStringExtra("lat");
        mLng = location.getStringExtra("lng"); a
        et3.setText(mAddr);
        //객체선언
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                intent.putExtra("title", et1.getText().toString());
                intent.putExtra("content", et2.getText().toString());
                intent.putExtra("where", et3.getText().toString());
                intent.putExtra("lat", mLat);
                intent.putExtra("lng", mLng);
                startActivity(intent);
            }
        });
        //리스너 설정
    }
}
