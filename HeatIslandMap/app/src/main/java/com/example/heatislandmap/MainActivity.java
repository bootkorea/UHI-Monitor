package com.example.heatislandmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.util.FusedLocationSource;

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
    protected static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    protected static NaverMap naverMap;
    protected static Marker marker = new Marker();

    private EditText searchEditText;
    private Button searchButton;
    private LineChart chart;
    private FusedLocationSource locationSource;

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

        searchButton.setOnClickListener(new View.OnClickListener() { // button 클릭 시 입력한 장소에 해당 하는 좌표 표시
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                try {
                    String encodedAddress = URLEncoder.encode(query, "UTF-8");
                    String urlStr = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode" + "?query=" + encodedAddress;

                    AddrToCoord.ConnectThread thread = new AddrToCoord.ConnectThread(urlStr); //주소를 좌표로 변환
                    thread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        chart = (LineChart) findViewById(R.id.LineChart);

        GetTemp.TempThread tempThread = new GetTemp.TempThread();
        tempThread.start();
        try {
            tempThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MakeChart.setChart(chart);
    }

    //Map
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
}