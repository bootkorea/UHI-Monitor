package com.example.heatislandmap;

import static com.example.heatislandmap.CalcAverageTemp.avg_temp;
import static com.example.heatislandmap.CalcAverageTemp.avg_humid;
import static com.example.heatislandmap.MainActivity.HEAT_ISLAND_AVG;
import static com.example.heatislandmap.ReadGeojson.DONG_COUNT;
import static com.example.heatislandmap.ReadGeojson.DONG_TEMP;
import static com.example.heatislandmap.NameDictionary.REGION_NAME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetTemp {
    protected static ArrayList<Double> windSpeed = new ArrayList<>();
    protected static Map<String, Double> sortedTemp = new LinkedHashMap<>(); // 기온이 높은 순으로 모든 지역의 "구 이름, 기온" 얻어올 HashMap
    protected static Map<String, Double> sortedHumid = new LinkedHashMap<>();
    protected static Map<String, Double> sortedFeellike = new LinkedHashMap<>();
    protected static Double[] avg_temp_arr = new Double[REGION_NAME.size()]; //각 자치구 별 12 ~ 16시 평균 기온 저장 배열 "구 이름, 기온" (5는 12 ~ 16시, 총 5시간을 의미)
    protected static Double[] avg_humid_arr = new Double[REGION_NAME.size()];
    protected static Double[] feel_like_temp = new Double[REGION_NAME.size()];
    private static LocalDate yesterday = LocalDate.now().minusDays(2); // 어제 날짜 가져오기(오류 뜨는거 무시해도 실행 됨)

    protected static class TempThread extends Thread{
        public void run() {
            try {
                Map<String, Double> region_temp = new HashMap<>();
                Map<String, Double> region_humid = new HashMap<>();
                Map<String, Double> region_feellike = new HashMap<>();
                OpenWeatherTest.doInBackground();

                String date_of_yesterday = yesterday.toString(); //어제 날짜 ("2023-11-01" 형식)

                LocalTime current_time = LocalTime.now();
                int current_hour = current_time.getHour();
                HEAT_ISLAND_AVG = 0.0;

                String DATE = "";
                for (int i = 0; i < REGION_NAME.size(); i++) {
                    String autonomous = null; //자치구 이름
                    int time = current_hour; //원하는 시간대 설정 가능
                    if(time > 2 && time < 10) time = 10;

                    String date = date_of_yesterday + "_" + String.valueOf(time); // 인자 형식 갖추기 위해서
                    autonomous = REGION_NAME.get(i); //온도를 얻고자 하는 자치구
                    // REGION_NAME: 영어 구 이름

                    CalcAverageTemp.MyUrlCaller urlCaller = new CalcAverageTemp.MyUrlCaller();
                    urlCaller.callUrl(autonomous, date); // autonomous의 평균 기온 구하기 (2번째 인자는 "2023-11-01_23"와 같은 형식)

                    avg_temp_arr[i] = avg_temp; //i 번째 자치구의 j(12 ~ 16) 시의 평균 기온, 즉 특정 구의 모든 센서에서, 특정 시간에 계산한 AVG_TEMP의 평균
                    avg_humid_arr[i] = avg_humid;
                    feel_like_temp[i] = CalcFeelLike.winter(avg_temp, windSpeed.get(i));

                    HEAT_ISLAND_AVG += avg_temp; // 각 자치구 별 12 ~ 16시의 평균 기온의 평균

                }
                HEAT_ISLAND_AVG /= REGION_NAME.size();
                HEAT_ISLAND_AVG = Math.round(HEAT_ISLAND_AVG * 100.0) / 100.0;

                for(int i = 0; i < REGION_NAME.size(); ++i) {
                    region_temp.put(REGION_NAME.get(i), avg_temp_arr[i]); //hash map에 자치구 이름과 평균 기온 삽입
                    region_humid.put(REGION_NAME.get(i), avg_humid_arr[i]);
                    region_feellike.put(REGION_NAME.get(i), feel_like_temp[i]);
                }

                //각 동의 평균기온 구하기
                DONG_TEMP.forEach((key, value) -> {
                    Integer count = DONG_COUNT.get(key);
                    if (count != 0) {
                        Double avgTemp = value / count;
                        DONG_TEMP.put(key, avgTemp); // 나눈 값으로 DONG_TEMP 갱신
                    }
                });

                // region_temp에 넣은 데이터를("지역구, 평균 기온") 가진 List 생성
                List<Map.Entry<String, Double>> entryList = new ArrayList<>(region_temp.entrySet());
                List<Map.Entry<String, Double>> entryList2 = new ArrayList<>(region_humid.entrySet());
                List<Map.Entry<String, Double>> entryList3 = new ArrayList<>(region_feellike.entrySet());

                for (Map.Entry<String, Double> entry : entryList) { // 높은 기온 순으로 sortedTemp에 저장
                    sortedTemp.put(entry.getKey(), Math.round(entry.getValue() * 100.0) / 100.0);
                }
                for (Map.Entry<String, Double> entry : entryList2) { // 높은 기온 순으로 sortedTemp에 저장
                    sortedHumid.put(entry.getKey(), Math.round(entry.getValue() * 100.0) / 100.0);
                }
                for (Map.Entry<String, Double> entry : entryList3) { // 높은 기온 순으로 sortedTemp에 저장
                    sortedFeellike.put(entry.getKey(), Math.round(entry.getValue() * 100.0) / 100.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}