package com.example.win10_pc.whichwish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WishListAdapter wishListAdapter = new WishListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listview);
        final View header = getLayoutInflater().inflate(R.layout.wishlistview_header, null, false);
        header.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Search_Location.class);
                //intent add Activity
                startActivity(intent);
            }
        });
        listView.addHeaderView(header);
        listView.setAdapter(wishListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null){
            wishListAdapter.addItem(intent.getExtras().getString("title"), intent.getExtras().getString("context"));
        }
    }
}
