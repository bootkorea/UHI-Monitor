package com.example.heatislandmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final List<LatLng> COORDS_1 = Arrays.asList( // 이런 식으로 센서좌표를 따와서 구역 지정해주기
            new LatLng(37.5734571, 126.975335),
            new LatLng(37.5738912, 126.9825649),
            new LatLng(37.5678124, 126.9812127),
            new LatLng(37.5694007, 126.9739434)
    );
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    EditText searchEditText;
    TextView txtMsg;
    Button searchButton;
    private LineChart chart, chart2;
    private Thread thread;
    Marker marker = new Marker();
    Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient("9f1fnoqugk"));

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this); //이걸떄려줘야하는거같음

        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = myWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);
        myWebView.loadUrl("file:///android_asset/www/heatmap.html");


        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        txtMsg = findViewById(R.id.txtMsg);

        searchButton.setOnClickListener(new View.OnClickListener() { // button 클릭 시 입력한 장소에 해당하는 좌표 표시
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                try {
                    String encodedAddress = URLEncoder.encode(query, "UTF-8");
                    String urlStr = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode" + "?query=" + encodedAddress;

                    ConnectThread thread = new ConnectThread(urlStr);
                    thread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //chart
        chart = (LineChart) findViewById(R.id.LineChart);
        chart = (LineChart) findViewById(R.id.LineChart);
        chart2 = (LineChart) findViewById(R.id.LineChart2);

        setChart(chart);
        setChart(chart2);
    }

    //address to coordinate
    class ConnectThread extends Thread {
        Handler handler = new Handler(Looper.getMainLooper());
        String urlStr;

        public ConnectThread(String inStr){
            urlStr = inStr;
        }

        public void run(){
            try{
                request(urlStr);

                handler.post(new Runnable(){
                    public void run(){
                        LatLng location = new LatLng(latitude, longitude); // 장소의 좌표 가져오기

                        // 해당 좌표(해당 지역의 행정구역)로 카메라 이동
                        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(location,9)
                                .animate(CameraAnimation.Fly, 1000);

                        naverMap.moveCamera(cameraUpdate);

                        // 마커 이동
                        marker.setPosition(new LatLng(latitude, longitude));
                        marker.setMap(naverMap);
                    }
                });
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void request(String urlStr) throws JSONException { // request 보내서 좌표값 얻어오기
        StringBuilder output = new StringBuilder();
        try{
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "hfpnvnkly3");
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", "Epu60893t1A5E8jnbBJptW2pCWJ9f8fORNK1QETY");
            connection.setConnectTimeout(LOCATION_PERMISSION_REQUEST_CODE);
            connection.setReadTimeout(LOCATION_PERMISSION_REQUEST_CODE);

            int response = connection.getResponseCode();

            if (response == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(output.toString());
        JSONArray addresses = jsonObject.getJSONArray("addresses");
        JSONObject address = addresses.getJSONObject(0);

        String lng = address.getString("x");
        String lat = address.getString("y");
        txtMsg.setText("lat : " + lat + "\nlng : " + lng);

        longitude = address.getDouble("x");
        latitude = address.getDouble("y");
    }


    //map
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.5666102,126.9783881), // 대상 지점
                9 // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);

        int temperature = 11, color;
        PolygonOverlay polygon = new PolygonOverlay();
        polygon.setCoords(COORDS_1);

        if(temperature > 10) // '10도 이상 차이가 난다면' 같은 기준 정해서 조건문에 넣으면 될 듯
            color = ResourcesCompat.getColor(getResources(), R.color.red, getTheme());
        else color = ResourcesCompat.getColor(getResources(), R.color.blue, getTheme());

        polygon.setColor(ColorUtils.setAlphaComponent(color, 50));
        polygon.setOutlineColor(color);

        polygon.setMap(naverMap);

        //서울시청에 마커를 설치하기
        marker.setPosition(new LatLng(37.5666102, 126.9783881));
        marker.setMap(naverMap);

        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
    }


    //chart
    private void setChart(LineChart ch){
        ch.setBackgroundColor(Color.rgb(212,244,250));
        ch.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        ch.getAxisRight().setEnabled(false);
        ch.animateXY(2000,2000);

        ch.invalidate();

        LineData data = new LineData();
        ch.setData(data);
        ch.getDescription().setEnabled(false);

        feedMultiple(ch);
    }

    private void addEntry(LineChart ch) {
        LineData data = ch.getData();

        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            //addEntry에 데이터 집어넣는건가봄
            //ArrayList에 온도 등등 집어넣어서 chart마다 다른 데이터 표현하게 만들어야함
            //data.addEntry(new Entry(set.getEntryCount(), (float) ArrayList[idx].value), 0); 대충 이런 식
            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            ch.notifyDataSetChanged();
            ch.setVisibleXRangeMaximum(5);
            ch.moveViewToX(data.getEntryCount());
        }
    }

    private LineDataSet createSet() { // chart 꾸미기
        LineDataSet set = new LineDataSet(null, "Temperature");

        set.setColor(Color.parseColor("#FFFF0000"));
        set.setCircleColor(R.color.black);
        set.setCircleRadius(2f);
        set.setValueTextSize(9f);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        return set;
    }

    private void feedMultiple(LineChart ch) {
        if (thread != null)
            thread.interrupt();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addEntry(ch);
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(runnable);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (thread != null)
            thread.interrupt();
    }
}