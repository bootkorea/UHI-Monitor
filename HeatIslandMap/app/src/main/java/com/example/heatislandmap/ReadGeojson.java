package com.example.heatislandmap;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadGeojson {
    protected static ArrayList<Map<String, String>> DONG_GU = new ArrayList<>(); // 동과 구 매치 (동을 포함하는 구 구분)
    protected static Map<String, Double> DONG_TEMP = new HashMap<>(); // 동과 기온 매치 (구와 기온 차 계산할 때 이용)
    protected static Map<String, Double> DONG_HUMID = new HashMap<>(); // 동과 습도 매치
    protected static Map<String, Integer> DONG_COUNT = new HashMap<>(); // 동과 센서 개수 매치 (동의 평균기온 구할 때 사용)

    protected String readGeoJsonFile(@NonNull Context context) { //GeoJsonFile 읽기
        String json = null;
        try {
            InputStream is = context.getAssets().open("sggnm.geojson");

            int size = is.available(); // 파일 사이즈
            byte[] buffer = new byte[size]; // 파일 사이즈만큼 버퍼 생성

            is.read(buffer); // InputStreamer 데이터를 읽기
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    protected void parseGeoJson(String json) { //GeoJsonFile에서 구, 행정동 추출
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray features = jsonObject.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");

                String admName = properties.getString("SIG_ENG_NM"); // 시, 구, 행정동 추출

                String guName = admName; // 두번째 요소인 구 추출
                String dongName = admName; // 마지막 요소인 행정동 추출

                Map<String, String> dong_gu = new HashMap<>();
                dong_gu.put(dongName, guName);

                DONG_GU.add(dong_gu);
                DONG_TEMP.put(dongName, 0.0);
                DONG_HUMID.put(dongName, 0.0);
                DONG_COUNT.put(dongName, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}