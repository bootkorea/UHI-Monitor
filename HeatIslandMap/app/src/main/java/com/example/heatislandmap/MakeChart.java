package com.example.heatislandmap;

import static com.example.heatislandmap.GetTemp.REGION_NAME;
import static com.example.heatislandmap.GetTemp.avg_temp_arr;
import static com.example.heatislandmap.GetTemp.sortedTemp;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MakeChart {
    private static Random random = new Random();

    //Chart 생성 및 초기화
    protected static void setChart(@NonNull LineChart ch){
        //원하는 시간대.. (차트의 x축 담당 List)
        List<String> TimeValues = Arrays.asList("12:00", "13:00", "14:00", "15:00", "16:00");
        ch.getDescription().setEnabled(false);

        XAxis xAxis = ch.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(TimeValues));
        xAxis.setLabelCount(5);
        xAxis.setSpaceMin(0.1f);
        xAxis.setSpaceMax(0.1f);

        ch.getAxisRight().setEnabled(false);

        Legend legend = ch.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setForm(Legend.LegendForm.CIRCLE);

        ch.invalidate();
        addValue(ch);
    }

    //Chart xAxis 에 Value 추가
    private static void addValue(LineChart ch){
        LineData data = new LineData();
        ch.setData(data);

        ArrayList<String> region = new ArrayList<>();
        Set<String> keys = sortedTemp.keySet();

        List<ArrayList<Entry>> entryList = new ArrayList<>(); // Entry 리스트를 담을 리스트

        for (String key : keys) {
            ArrayList<Entry> entry = new ArrayList<>();
            addEntry(entry, key, entryList); // 각 entry를 entryList에 추가하는 함수 호출

            region.add(key);
        }

        for (ArrayList<Entry> entry : entryList) {
            LineDataSet lineDataSet = new LineDataSet(entry, region.get(entryList.indexOf(entry)));
            setDataSet(data, lineDataSet);
        }

        ch.setData(data);
        ch.invalidate();
    }

    private static void addEntry(ArrayList<Entry> entry, String key, List<ArrayList<Entry>> entryList){
        for (int i = 0; i < 5; i++) {
            entry.add(new Entry(i, avg_temp_arr[REGION_NAME.indexOf(key)][i].floatValue()));
        }
        entryList.add(entry);
    }

    private static void setDataSet(LineData data, LineDataSet lineDataSet){
        data.addDataSet(lineDataSet);

        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(true);

        int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        lineDataSet.setColor(randomColor);
        lineDataSet.setCircleColor(randomColor);
    }
}