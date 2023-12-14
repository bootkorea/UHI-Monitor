package com.example.heatislandmap;

import static com.example.heatislandmap.NameDictionary.DONG_DICT;
import static com.example.heatislandmap.ReadGeojson.DONG_HUMID;
import static com.example.heatislandmap.ReadGeojson.DONG_TEMP;
import static com.example.heatislandmap.ReadGeojson.DONG_COUNT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CalcAverageTemp {
    protected static double avg_temp; //원하는 시간의 원하는 구의 모든 센서에서 계산한 AVG_TEMP를 더해서 평균 낸 값
    protected static double avg_humid;
    protected static class MyUrlCaller{
        public void callUrl(String autonomous, String date) throws Exception { // S-Dot을 통해 원하는 지역구의 날짜(시간)의 평균기온 구해서 return
            try {
                // 반드시 순서 대로 호출
                String urlBuilder = "http://openapi.seoul.go.kr:8088" + "/" + URLEncoder.encode("4948525443616c703336686f4f716e", "UTF-8") +
                        "/" + URLEncoder.encode("json", "UTF-8") + //요청 파일 타입
                        "/" + URLEncoder.encode("IotVdata017", "UTF-8") + //서비스 명
                        "/" + URLEncoder.encode("1", "UTF-8") + //요청 시작 위치
                        "/" + URLEncoder.encode("200", "UTF-8") + //요청 종료 위치 (각 구의 센서가 최대 160개 정도 있어서 200으로 잡음)
                        "/" + URLEncoder.encode(autonomous, "UTF-8")+ //자치구
                        "/" + URLEncoder.encode(date,"UTF-8"); // 일시 ("날짜_시간" 형식 ex: "2023-11-01_23")

                URL url = new URL(urlBuilder);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");

                BufferedReader rd;
                // response 코드가 정상 이면 200~300사이
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                }

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONObject data = jsonObject.getJSONObject("IotVdata017");
                JSONArray row = data.getJSONArray("row");

                int list_total_count = data.getInt("list_total_count"); // 해당 자치구, 일시에 해당하는 센서의 개수
                int actual_count = list_total_count; // 구한 센서의 개수 중 잘못된 데이터를 가진 센서를 제외한 실질적인 센서의 개수
                double sum_of_AVG_TEMP = 0.0; // 센서에서 계산한 모든 AVG_TEMP의 합을 저장하는 변수
                double sum_of_AVG_HUMID = 0.0; // 센서에서 계산한 모든 AVG_HUMID의 합을 저장하는 변수

                try {
                    for(int i = 0; i < list_total_count; i++) {
                        JSONObject object = row.getJSONObject(i);
                        try {
                            Double AVG_TEMP = object.getDouble("AVG_TEMP");
                            Double AVG_HUMID = object.getDouble("AVG_HUMI");

                            if (AVG_TEMP == -40) { //AVG_TEMP가 -40인 경우 actual_count 감소 (잘못된 데이터 인듯 함)
                                actual_count--;
                                continue;
                            }

                            // Dong_name이 영어니까 번역필요
                            NameDictionary.Dong_Dictionary();
                            String Dong_Name = DONG_DICT.get(object.getString("ADMINISTRATIVE_DISTRICT"));

                            if (DONG_COUNT.containsKey(Dong_Name)) {
                                DONG_COUNT.put(Dong_Name, DONG_COUNT.get(Dong_Name) + 1); // 1씩 더하고, 나중에 DONG_TEMP 값에서 DONG_COUNT 값으로 나누면 해당 동의 평균 온도 나온다
                                DONG_TEMP.put(Dong_Name, DONG_TEMP.get(Dong_Name) + AVG_TEMP); // 계속 더해주기
                                DONG_HUMID.put(Dong_Name, DONG_HUMID.get(Dong_Name) + AVG_HUMID);
                            }

                            sum_of_AVG_TEMP += AVG_TEMP;
                            sum_of_AVG_HUMID += AVG_HUMID;
                        } catch (JSONException e){ // AVG_TEMP가 비어있으면 actual_count 감소
                            actual_count--;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                avg_temp = sum_of_AVG_TEMP / actual_count; //원하는 지역의 평균기온(AVG_TEMP)
                avg_humid = sum_of_AVG_HUMID / actual_count;

                rd.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}