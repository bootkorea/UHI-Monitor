package com.example.heatislandmap;

import static com.example.heatislandmap.GetTemp.avg_temp_arr;
import static com.example.heatislandmap.NameDictionary.REGION_NAME;
import static com.example.heatislandmap.NameDictionary.GU_DICT;
import static com.example.heatislandmap.ReadGeojson.DONG_GU;
import static com.example.heatislandmap.ReadGeojson.DONG_TEMP;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HeatIslandAlg {
    private static Map<String, Double> avgTemp_all_regions = new LinkedHashMap<>(); // 모든 구의 12 ~ 16시 기온의 평균을 저장
    protected static Map<String, Double> tempDiff = new HashMap<>(); // 각 동 + 동과 구의 기온 차 저장

    protected static void Do() { // 동과 해당 동이 속하는 구의 기온 차이 구하는 함수

        for(int i = 0; i < REGION_NAME.size(); i++) { // 모든 구의 12 ~ 16시 기온의 평균 계산
            Double sum = 0.0;

            for (int j = 0; j < 5; j++) {
                sum += avg_temp_arr[i][j]; // avg_temp_arr의 각 행에는 각 구의 12 ~ 16시 기온의 평균이 저장돼 있으므로
            }

            NameDictionary.Gu_Dictionary();
            String region = GU_DICT.get(REGION_NAME.get(i)); // REGION_NAME은 영어이므로 한글로

            Double average = sum / 5.0; // 12 ~ 16시 이므로 5로 나누기

            avgTemp_all_regions.put(region, average);
        }

        for (Map<String, String> DongGuMap : DONG_GU) {
            for (Map.Entry<String, String> entry : DongGuMap.entrySet()) {
                String dong = entry.getKey();
                String gu = entry.getValue();

                Double dongTemp = DONG_TEMP.get(dong); // 해당 동의 (센서들의 평균)온도
                Double guTemp = avgTemp_all_regions.get(gu); // 해당 구의 평균 온도

                Double temperatureDifference = dongTemp - guTemp; // 구의 평균 온도와 동의 온도 차이 계산

                temperatureDifference = Math.round(temperatureDifference * 100.0) / 100.0; // 소수점 셋째 자리에서 반올림
                tempDiff.put(dong, temperatureDifference);
            }
        }
    }
}