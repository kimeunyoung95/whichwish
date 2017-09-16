package com.example.win10_pc.whichwish;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    String mAddr;
    String mLat;
    String mLng;

    Button search_btn;
    EditText search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        final Intent outIntent = getIntent();
        mAddr = outIntent.getStringExtra("addr");
        mLat = outIntent.getStringExtra("lat");
        mLng = outIntent.getStringExtra("lng");



        search_text = (EditText)findViewById(R.id.search_text2);
        search_text.setText(outIntent.getStringExtra("search_map"));
        search_btn = (Button)findViewById(R.id.search_btn2);

        Button next_btn = (Button)findViewById(R.id.next_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(Map.this, WriteActivity.class);
                writeIntent.putExtra("addr", mAddr);
                writeIntent.putExtra("lat", mLat);
                writeIntent.putExtra("lng", mLng);
                startActivity(writeIntent);

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_text.getText().toString() != null) {
                    outIntent.putExtra("search", search_text.getText().toString());
                    setResult(RESULT_OK, outIntent);
                    finish();
                }
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng location = new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(mAddr);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}