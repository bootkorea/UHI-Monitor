package com.example.heatislandmap;

import static com.example.heatislandmap.ReadGeojson.DONG_COUNT;
import static com.example.heatislandmap.ReadGeojson.DONG_TEMP;
import static com.example.heatislandmap.NameDictionary.REGION_NAME;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetTemp {
    protected static Map<String, Double> sortedTemp = new LinkedHashMap<>(); // 기온이 높은 순으로 모든 지역의 "구 이름, 기온" 얻어올 HashMap
    protected static Double[][] avg_temp_arr = new Double[REGION_NAME.size()][5]; //각 자치구 별 12 ~ 16시 평균 기온 저장 배열 "구 이름, 기온" (5는 12 ~ 16시, 총 5시간을 의미)
    //protected static Double[] avg_temp_arr = new Double[REGION_NAME.size()];
    private static LocalDate yesterday = LocalDate.now().minusDays(1); // 어제 날짜 가져오기(오류 뜨는거 무시해도 실행 됨)
    protected static class TempThread extends Thread{
        public void run() {
            try {
                Map<String, Double> region_temp = new HashMap<>();

                String date_of_yesterday = yesterday.toString(); //어제 날짜 ("2023-11-01" 형식)

                double avg_temp = 0.0; //평균 기온
                double avg_of_avgTemp; //평균 기온의 평균

                for (int i = 0; i < 3; i++) { // *** 요 반복 횟수가 많을수록 실행시간 증가.. *** ( 원래는 i < REGION_NAME.size() )
                    double sum = 0.0; // 각 시각에 구한 평균기온의 합
                    String autonomous = null; //자치구 이름

                    for(int j = 0; j < 5; j++) { //12시 ~ 16시 평균기온 구할 예정
                        int time = 12 + j; //원하는 시간대 설정 가능
                        //int time = 12; //원하는 시간대 설정 가능 (여기에 12 대신 현재 시각(몇 시 인지만) 구해서 넣으면 됨)
                        String date = date_of_yesterday + "_" + String.valueOf(time); // 인자 형식 갖추기 위해서
                        autonomous = REGION_NAME.get(i); //온도를 얻고자 하는 자치구

                        CalcAverageTemp.MyUrlCaller urlCaller = new CalcAverageTemp.MyUrlCaller();
                        avg_temp = urlCaller.callUrl(autonomous, date); // autonomous의 평균 기온 구하기 (2번째 인자는 "2023-11-01_23"와 같은 형식)

                        avg_temp_arr[i][j] = avg_temp; //i 번째 자치구의 j(12 ~ 16) 시의 평균 기온, 즉 특정 구의 모든 센서에서, 특정 시간에 계산한 AVG_TEMP의 평균
                        //avg_temp_arr[i] = avg_temp; //i 번째 자치구의 특정 시간에 계산한 AVG_TEMP의 평균
                        sum += avg_temp;
                    }

                    avg_of_avgTemp = sum / 5; // 각 자치구 별 12 ~ 16시의 평균 기온의 평균
                    region_temp.put(autonomous, avg_of_avgTemp); //hash map에 자치구 이름과 평균 기온 삽입
                }

                //각 동의 평균기온 구하기
                DONG_TEMP.forEach((key, value) -> {
                    Integer count = DONG_COUNT.get(key);
                    if (count != 0) {
                        Double avgTemp = value / count;
                        DONG_TEMP.put(key, avgTemp); // 나눈 값으로 DONG_TEMP 갱신
                    }
                });
                // 동 424개 중 29개가 DONG_TEMP = 0

                // region_temp에 넣은 데이터를("지역구, 평균 기온") 가진 List 생성
                List<Map.Entry<String, Double>> entryList = new ArrayList<>(region_temp.entrySet());

                //평균 기온을 기준으로 내림차순으로 정렬
                entryList.sort(Map.Entry.<String, Double>comparingByValue().reversed());

                for (Map.Entry<String, Double> entry : entryList) { // 높은 기온 순으로 sortedTemp에 저장
                    sortedTemp.put(entry.getKey(), Math.round(entry.getValue() * 100.0) / 100.0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}