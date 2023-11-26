package com.example.heatislandmap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetTemp {
    protected static final List<String> REGION_NAME = Arrays.asList( //서울에 있는 25개 구
            "Dobong-gu", "Dongdaemun-gu", "Dongjak-gu", "Eunpyeong-gu",
            "Gangbuk-gu", "Gangdong-gu", "Gangnam-gu","Gangseo-gu",
            "Geumcheon-gu", "Guro-gu", "Gwanak-gu", "Gwangjin-gu",
            "Jongno-gu", "Jung-gu", "Jungnang-gu", "Mapo-gu",
            "Nowon-gu", "Seocho-gu", "Seodaemun-gu", "Seongbuk-gu",
            "Seongdong-gu", "Songpa-gu", "Yangcheon-gu",
            "Yeongdeungpo-gu", "Yongsan-gu"
    );
    protected static Map<String, Double> sortedTemp = new LinkedHashMap<>(); // 상위 3개 지역의 "구 이름 ,기온" 얻어올 HashMap
    protected static Double[][] avg_temp_arr = new Double[REGION_NAME.size()][5]; //각 자치구 별 최근 5시간 평균 기온 저장 배열
    private static LocalDate yesterday = LocalDate.now().minusDays(1); // 어제 날짜 가져오기(오류 뜨는거 무시해도 실행 됨)

    protected static class TempThread extends Thread{
        public void run() {
            try {
                Map<String, Double> region_temp = new HashMap<>();
                String autonomous = null; //자치구 이름
                String date_of_yesterday = yesterday.toString(); //어제 날짜 ("2023-11-01" 형식)

                double avg_temp = 0.0; //평균 기온
                double avg_of_avgTemp; //평균 기온의 평균

                for (int i = 0; i < REGION_NAME.size(); i++) {
                    double sum = 0.0; // 각 시각에 구한 평균기온의 합
                    autonomous = REGION_NAME.get(i); //온도를 얻고자 하는 자치구

                    for(int j = 0; j < 5; j++) { //12시 ~ 16시 평균기온 구할 예정
                        //원하는 시간대..
                        int time = 12 + j;
                        String date = date_of_yesterday + "_" + String.valueOf(time); // 인자 형식 갖추기 위해서

                        CalcAverageTemp.MyUrlCaller urlCaller = new CalcAverageTemp.MyUrlCaller();
                        avg_temp = urlCaller.callUrl(autonomous, date); // autonomous의 평균 기온 구하기 (2번째 인자는 "2023-11-01_23"와 같은 형식)

                        avg_temp_arr[i][j] = avg_temp;
                        sum += avg_temp;
                    }

                    avg_of_avgTemp = sum / 5; // 각 자치구 별 12 ~ 16시의 평균 기온의 평균
                    region_temp.put(autonomous, avg_of_avgTemp); //hash map에 자치구 이름과 평균 기온 삽입
                }
                // region_temp에 넣은 데이터를("지역구, 평균 기온") 가진 List 생성
                List<Map.Entry<String, Double>> entryList = new ArrayList<>(region_temp.entrySet());

                //평균 기온을 기준으로 내림차순으로 정렬
                entryList.sort(Map.Entry.<String, Double>comparingByValue().reversed());

                int cnt = 0;
                for (Map.Entry<String, Double> entry : entryList) { // 상위 3개 지역만 sortedTemp에 추가
                    sortedTemp.put(entry.getKey(), Math.round(entry.getValue() * 100.0) / 100.0);

                    cnt++;
                    if (cnt == 3) break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}