package com.example.heatislandmap;

import static com.example.heatislandmap.GetTemp.avg_temp_arr;
import static com.example.heatislandmap.GetTemp.sortedTemp;
import static com.example.heatislandmap.NameDictionary.REGION_NAME;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddEntries { //Chart xAxis 에 Value 추가
    protected static void addValue(LineChart ch){
        LineData data = new LineData();
        ch.setData(data);

        ArrayList<String> region = new ArrayList<>();
        Set<String> keys = sortedTemp.keySet(); // 구 이름 가져와서 keys에 저장

        List<ArrayList<Entry>> entryList = new ArrayList<>(); // Entry 리스트를 담을 리스트

        int cnt = 0;
        for (String key : keys) { //상위 3개 지역만 entry에 추가
            ArrayList<Entry> entry = new ArrayList<>();

            addEntry(entry, key, entryList); // 각 entry를 entryList에 추가하는 함수 호출
            region.add(key);

            cnt++;
            if(cnt == 3) break;
        }

        for (ArrayList<Entry> entry : entryList) {
            LineDataSet lineDataSet = new LineDataSet(entry, region.get(entryList.indexOf(entry)));
            MakeChart.setDataSet(data, lineDataSet);
        }

        ch.setData(data);
        ch.invalidate();
    }

    private static void addEntry(ArrayList<Entry> entry, String key, List<ArrayList<Entry>> entryList){
        for (int i = 0; i < 5; i++) {
            entry.add(new Entry(i, avg_temp_arr[REGION_NAME.indexOf(key)][i].floatValue())); // 차트에 집어넣을 데이터 entry
        }
        entryList.add(entry);
    }
}