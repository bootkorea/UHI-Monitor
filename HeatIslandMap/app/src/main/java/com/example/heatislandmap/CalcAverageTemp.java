package com.example.heatislandmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CalcAverageTemp {
    private static double avg_temp;//원하는 지역의 평균기온(AVG_TEMP)

    protected static class MyUrlCaller{
        public double callUrl(String autonomous, String date) throws Exception { // S-Dot을 통해 원하는 지역구의 날짜(시간)의 평균기온 구해서 return
            try {
                // 반드시 순서 대로 호출
                String urlBuilder = "http://openapi.seoul.go.kr:8088" + "/" + URLEncoder.encode("4948525443616c703336686f4f716e", "UTF-8") +
                        "/" + URLEncoder.encode("json", "UTF-8") + //요청 파일 타입
                        "/" + URLEncoder.encode("IotVdata017", "UTF-8") + //서비스 명
                        "/" + URLEncoder.encode("1", "UTF-8") + //요청 시작 위치
                        "/" + URLEncoder.encode("200", "UTF-8") + //요청 종료 위치 (각 구의 센서가 최대 160개 정도 있어서 200으로 잡음)
                        "/" + URLEncoder.encode(autonomous, "UTF-8")+ //자치구
                        "/" + URLEncoder.encode(date,"UTF-8"); // 일시 ("날짜_시간" 형식 ex: "2023-11-01_23")
                //http://openapi.seoul.go.kr:8088/4948525443616c703336686f4f716e/xml/IotVdata017/1/50/Dobong-gu/2023-11-01_23

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

                try {
                    for(int i = 0; i < list_total_count; i++) {
                        JSONObject object = row.getJSONObject(i);

                        //AVG_TEMP가 -40인 경우 예외처리 (아마 잘못된 데이터 인듯 함)
                        if(object.getDouble("AVG_TEMP") == -40) { throw new Exception(); }
                        sum_of_AVG_TEMP += object.getDouble("AVG_TEMP");
                    }
                } catch (Exception e) { // AVG_TEMP가 비어있거나 -40인 경우 actual_count 감소
                    actual_count--;
                }
                avg_temp = sum_of_AVG_TEMP / actual_count;

                rd.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return avg_temp;
        }
    }
}