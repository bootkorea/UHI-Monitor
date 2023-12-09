package com.example.heatislandmap;

import static com.example.heatislandmap.GetTemp.sortedTemp;
import static com.example.heatislandmap.HeatIslandAlg.tempDiff;
import static com.example.heatislandmap.NameDictionary.GU_DICT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static double HEAT_ISLAND_TEMP1 = 0.0; //임의로 설정한 열섬기준온도, -0.5도를 열섬기준 온도로 잡았음, -0.5도가 넘어가는 지역은 열섬지역으로 간주
    public static double HEAT_ISLAND_TEMP2 = 0.0;
    public static double HEAT_ISLAND_AVG = 0.0;

    private String jsonDataOfArea; //지역&온도를 담은 HashMap을 json형태로 바꾼 것
    private String areaName; //검색창에 입력할 지역명
    private BarChart chart;
    private double latitude, longitude; //검색한 지역의 위도, 경도

    private EditText searchEditText;
    private Button searchButton, searchButton2;
    private WebView myWebView;

    private Spinner UHI_spinner;
    private static double[] UHI_threshold = {0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0, 2.2, 2.4, 2.6, 2.8, 3.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        loadData();
        configureWebView();
        setupSearchArea();
        // MakeChart.setChart(chart);

        ReadGeojson readGeojson = new ReadGeojson();
        readGeojson.parseGeoJson(readGeojson.readGeoJsonFile(this)); // Geojson 파일 읽어서 행정동 parse하기

        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() { // 이렇게 안하면 메인스레드 멈춤
                    public void run() {
                        // 백그라운드 스레드에서 실행할 작업
                        GetTemp.TempThread tempThread = new GetTemp.TempThread(); // thread 안쓰면 오류 떴던거로 기억
                        tempThread.start(); // 기온 가져오기
                        try {
                            tempThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        // 작업이 끝나면 UI 스레드에 결과를 전달
                        runOnUiThread(new Runnable() {
                            public void run() {
                                // UI 스레드에서 처리할 작업
                                loadData();
                                MakeChart.setChart(chart);
                                chart.invalidate();
                                //HeatIslandAlg.Do(); // 동과 구의 기온차이 계산 (GetTemp에서 반복 횟수 줄였을 때 에러뜨니까, 줄일려면 주석처리)
                            }
                        });
                    }
                }).start();
            }
        });

        UHI_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HEAT_ISLAND_TEMP1 = Float.parseFloat(parent.getItemAtPosition(position).toString());
                HEAT_ISLAND_TEMP2 = HEAT_ISLAND_TEMP1 / 2.0;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                HEAT_ISLAND_TEMP1 = 0.0;
                HEAT_ISLAND_TEMP2 = HEAT_ISLAND_TEMP1 / 2.0;
            }
        });
    }

    private void initializeViews() {
        myWebView = findViewById(R.id.webview);
        chart = (BarChart) findViewById(R.id.BarChart); // LineChart는 외부(MPAndroidChart)에서 가져옴
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        searchButton2 = findViewById(R.id.searchButton2);
        UHI_spinner = findViewById(R.id.UHI_spinner);
        ArrayAdapter UHIAdapter = ArrayAdapter.createFromResource(this, R.array.UHI_threshold, android.R.layout.simple_spinner_dropdown_item);
        UHIAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UHI_spinner.setAdapter(UHIAdapter);
    }

    private void configureWebView() {
        WebSettings mWebSettings = myWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(true);

        myWebView.loadUrl("file:///android_asset/www/heatmap.html");

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // JavaScript 함수 호출, index.js에 있는 loadMapData 호출
                myWebView.loadUrl("javascript:loadMapData('" + jsonDataOfArea + "', '" + HEAT_ISLAND_TEMP1 + "', '" + HEAT_ISLAND_TEMP2 + "', '" + HEAT_ISLAND_AVG + "')");
            }
        });
    }

    private void loadData() {
        //HashMap을 자바스크립트로 넘기려면 json형태로 변환한 후 넘겨야함
        Gson gson = new Gson();

        //jsonDataOfArea = gson.toJson(tempDiff); // tempDiff : 동 이름 + 동과 구와의 기온 차 저장 돼있는 Map ( 동 기준으로 하려면 이거로 데이터 작업해서 json에 넘겨야 함 )
        NameDictionary.Gu_Dictionary();
        Map<String, Double> changedKeys = new HashMap<>(); // sortedTemp에 구 이름이 영어여서, 한글로 바꿔 저장할 Map

        for (String key : sortedTemp.keySet()) { // sortedTemp : 구 이름 + 구 기온 저장 돼있는 Map
            String newKey = GU_DICT.get(key); // 번역한 key를 get
            Double value = sortedTemp.get(key); // key에 해당하는 기존 value값 get

            changedKeys.put(newKey, value); // 새로운 키에 기존 값 할당
        }
        jsonDataOfArea = gson.toJson(changedKeys); // gson에 데이터 보내기

        myWebView.loadUrl("javascript:loadMapData('" + jsonDataOfArea + "', '" + HEAT_ISLAND_TEMP1 + "', '" + HEAT_ISLAND_TEMP2 + "', '" + HEAT_ISLAND_AVG + "')");
    }

    // searchEditText에 지역명(areaName)을 쓰고 searchButton을 클릭하면 해당 지역의 위도, 경도를 불러옴.
    // 입력예시) 종로구3가(x), 종로 3가(o)
    private void setupSearchArea(){
        searchButton.setOnClickListener(v -> {
            areaName = searchEditText.getText().toString();
            try {
                String encodedAddress = URLEncoder.encode(areaName, "UTF-8");
                String urlStr = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode" + "?query=" + encodedAddress;

                //지역의 좌표정보를 얻어오려면 스레드를 사용해야함
                AddrToCoord.ConnectThread thread = new AddrToCoord.ConnectThread(urlStr, new AddrToCoord.CoordListener() {
                    @Override
                    public void onCoordReceived(double lat, double lng) {
                        //latitude와 longitude변수에 좌표를 저장합니다.
                        latitude = lat;
                        longitude = lng;

                        //이렇게 하면 ConnectThread에서 좌표를 받아오는 작업이 완료된 후, 메인 스레드에서 웹뷰(해당 지역으로 카메라를 이동시킴)를 업데이트
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myWebView.loadUrl("javascript:moveCameraToArea('" + latitude + "', '" + longitude + "', '" + areaName + "')");
                            }
                        });
                    }
                });
                thread.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}