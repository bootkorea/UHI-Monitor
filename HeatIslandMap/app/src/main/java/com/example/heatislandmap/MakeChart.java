package com.example.heatislandmap;

import android.graphics.Color;

import androidx.annotation.NonNull;

import static com.example.heatislandmap.GetTemp.sortedFeellike;
import static com.example.heatislandmap.GetTemp.sortedHumid;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MakeChart {
    private static Random random = new Random();
    //Chart 생성 및 초기화
    protected static void setChart(@NonNull LineChart chart){
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        LineData lineData = new LineData();

        List<ArrayList<Entry>> entryList = new ArrayList<>();
        ArrayList<String> xAxis = new ArrayList<>();

        List<Map.Entry<String, Double>> entry = new ArrayList<>(sortedTemp.entrySet());
        //평균 기온을 기준으로 내림차순으로 정렬
        entry.sort(Map.Entry.<String, Double>comparingByValue().reversed());

        ArrayList<Entry> entry1 = new ArrayList<>();
        //ArrayList<Entry> entry2 = new ArrayList<>();
        ArrayList<Entry> entry3 = new ArrayList<>();

        for(int i = 0; i < 5; ++i) {
            entry1.add(new Entry(i, entry.get(i).getValue().floatValue()));
            //entry2.add(new Entry(i, sortedHumid.get(entry.get(i).getKey()).floatValue()));
            entry3.add(new Entry(i, sortedFeellike.get(entry.get(i).getKey()).floatValue()));
            xAxis.add(new String(GU_DICT.get(entry.get(i).getKey())));
        }
        entry1.add(new Entry(5, (float)HEAT_ISLAND_AVG));
        xAxis.add(new String("평균"));

        entryList.add(entry1);
        //entryList.add(entry2);
        entryList.add(entry3);

        XAxis xaxis = chart.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularity(1f);
        xaxis.setValueFormatter(new IndexAxisValueFormatter(xAxis));
        xaxis.setTextSize(10f);
        xaxis.setDrawGridLines(false);


        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setDrawGridLines(false);
        axisLeft.setTextSize(8f);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawLabels(false);

        LineDataSet lineDataSet = new LineDataSet(entryList.get(0),"기온");
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawValues(true);
        lineDataSet.setDrawCircles(true);
        int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        lineDataSet.setColor(randomColor);
        lineDataSet.setCircleColor(randomColor);
        lineDataSet.setValueTextSize(10f);

        lineData.addDataSet(lineDataSet);

        LineDataSet lineDataSet2 = new LineDataSet(entryList.get(1),"체감온도");
        lineDataSet2.setLineWidth(3);
        lineDataSet2.setDrawValues(true);
        lineDataSet2.setDrawCircles(true);
        randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        lineDataSet2.setColor(randomColor);
        lineDataSet2.setCircleColor(randomColor);
        lineDataSet2.setValueTextSize(10f);

        lineData.addDataSet(lineDataSet2);

        chart.getDescription().setEnabled(false);

        chart.setData(lineData);
        chart.invalidate();
    }
}