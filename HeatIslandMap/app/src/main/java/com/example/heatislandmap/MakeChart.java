package com.example.heatislandmap;

import android.graphics.Color;

import androidx.annotation.NonNull;
import static com.example.heatislandmap.GetTemp.sortedTemp;
import static com.example.heatislandmap.MainActivity.HEAT_ISLAND_AVG;
import static com.example.heatislandmap.NameDictionary.GU_DICT;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MakeChart {
    private static Random random = new Random();
    //Chart 생성 및 초기화
    protected static void setChart(@NonNull BarChart chart){
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawBorders(false);

        BarData barData;
        BarDataSet barDataSet;
        ArrayList barEntriesArrayList = new ArrayList<>();
        ArrayList xAxis = new ArrayList<>();

        List<Map.Entry<String, Double>> entryList = new ArrayList<>(sortedTemp.entrySet());
        //평균 기온을 기준으로 내림차순으로 정렬
        entryList.sort(Map.Entry.<String, Double>comparingByValue().reversed());

        for(int i = 0; i < 5; ++i) {
            barEntriesArrayList.add(new BarEntry(i, entryList.get(i).getValue().floatValue()));
            xAxis.add(new String(GU_DICT.get(entryList.get(i).getKey())));
        }
        barEntriesArrayList.add(new BarEntry(5, (float)HEAT_ISLAND_AVG));
        xAxis.add(new String("서울시 평균"));

        XAxis xaxis = chart.getXAxis();
        xaxis.setValueFormatter(new IndexAxisValueFormatter(xAxis));
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setTextSize(12f);
        xaxis.setDrawGridLines(false);

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setDrawGridLines(false);
//        axisLeft.setDrawAxisLine(false);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawLabels(false);

        barDataSet = new BarDataSet(barEntriesArrayList, "핫플레이스 TOP5");

        barDataSet.setColors(Color.argb(255, 255, 0, 0), Color.argb(215, 255, 0, 0),Color.argb(175, 255, 0, 0),Color.argb(135, 255, 0, 0),Color.argb(85, 255, 0, 0),Color.argb(255, 0, 0, 255));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);
        chart.getDescription().setEnabled(false);

        barData = new BarData(barDataSet);

        chart.setData(barData);
    }
}