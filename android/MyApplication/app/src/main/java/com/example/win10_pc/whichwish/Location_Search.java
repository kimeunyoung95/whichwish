package com.example.win10_pc.whichwish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Location_Search extends AppCompatActivity {

    EditText search_text;
    Button search_btn;
    Button display_map_btn;
    ListView search_list;

    String lats[];
    String lngs[];
    String addrs[];
    String mLat = null;
    String mLng = null;
    String mAddr = null;
    public static String defaultUrl = "https://apis.daum.net/local/v1/search/keyword.json?apikey=633886287cfa03f9c8ed9a5e7fe978ce&query=";
    Handler handler = new Handler();
    int jsonResultsLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location__search);

        search_text = (EditText)findViewById(R.id.search_text);
        search_btn = (Button)findViewById(R.id.search_btn);
        search_list = (ListView)findViewById(R.id.search_list);


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_text.getText().toString() != null) {
                    String userStr = search_text.getText().toString();
                    String urlStr = defaultUrl + userStr;
                    ConnectThread thread = new ConnectThread(urlStr);
                    thread.start();
                }
            }
        });
    }

    class ConnectThread extends  Thread{
        String urlStr;
        public ConnectThread(String inStr){
            urlStr = inStr;
        }
        public void run(){
            try {
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        findLatLng(output);


                    }
                });

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        private String request(String urlStr){
            StringBuilder output = new StringBuilder();
            try{
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Accept-Charset", "UTF-8");

                    int resCode = conn.getResponseCode();

                    Log.d("resCode", String.valueOf(resCode));

                    if(resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null){
                                break;
                            }
                            output.append(line + "\n");
                        }

                        reader.close();
                        conn.disconnect();
                    }
                }
            }catch (Exception ex){
                Log.e("SampleHTTP", "Exception in processing response.", ex);
                ex.printStackTrace();
            }

            return output.toString();
        }

        private void findLatLng(String output){
            Log.d("output", output);
            try{
                JSONObject jsonObject = new JSONObject(output);
                JSONObject channel = new JSONObject(jsonObject.getString("channel"));
                JSONArray items = new JSONArray(channel.getString("item"));
                jsonResultsLength = items.length();
                if(jsonResultsLength > 0){
                    addrs = new String[jsonResultsLength];
                    lats = new String[jsonResultsLength];
                    lngs = new String[jsonResultsLength];

                    for(int i = 0; i < jsonResultsLength; i++){
                        String addr = items.getJSONObject(i).getString("title");
                        String lat = items.getJSONObject(i).getString("latitude");
                        String lng = items.getJSONObject(i).getString("longitude");
                        addrs[i] = addr;
                        lats[i] = lat;
                        lngs[i] = lng;
                    }

                }
                if(jsonResultsLength > 0){
                    ArrayList<MemberData> datas = new ArrayList<MemberData>();
                    for(int i = 0; i < jsonResultsLength; i++){
                        datas.add(new MemberData(addrs[i], lats[i], lngs[i]));
                    }
                    final LocationAdapter adapter = new LocationAdapter(getLayoutInflater(), datas);
                    search_list.setAdapter(adapter);
                    search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(Location_Search.this, "CHECK", Toast.LENGTH_SHORT).show();
                            mLat = lats[position];
                            mLng = lngs[position];
                            mAddr = addrs[position];

                            Intent intent = new Intent(Location_Search.this, Map.class);
                            intent.putExtra("addr", mAddr);
                            intent.putExtra("lat", mLat);
                            intent.putExtra("lng", mLng);
                            intent.putExtra("search_map", search_text.getText().toString());
                            startActivityForResult(intent, 9);
                        }
                    });
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 9 && resultCode == RESULT_OK){
            String userStr = intent.getStringExtra("search");
            search_text.setText(intent.getStringExtra("search"));
            String urlStr = defaultUrl + userStr;
            ConnectThread thread = new ConnectThread(urlStr);
            thread.start();
        }
    }
}