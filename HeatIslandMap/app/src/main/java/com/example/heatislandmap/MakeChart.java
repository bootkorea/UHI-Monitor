package com.example.heatislandmap;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MakeChart {
    private static Random random = new Random();
    //Chart 생성 및 초기화
    protected static void setChart(@NonNull LineChart ch){
        List<String> TimeValues = Arrays.asList("12:00", "13:00", "14:00", "15:00", "16:00"); //원하는 시간대.. (차트의 x축 담당 List)
        ch.getDescription().setEnabled(false);

        XAxis xAxis = ch.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(TimeValues));
        xAxis.setLabelCount(5); // x축 개수(시간대)를 늘리면 그 개수 만큼 여기도 늘리기
        xAxis.setSpaceMin(0.1f);
        xAxis.setSpaceMax(0.1f);

        ch.getAxisRight().setEnabled(false);

        Legend legend = ch.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setForm(Legend.LegendForm.CIRCLE);

        ch.invalidate();
        AddEntries.addValue(ch);
    }

    protected static void setDataSet(LineData data, LineDataSet lineDataSet){
        data.addDataSet(lineDataSet);

        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(true);

        int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        lineDataSet.setColor(randomColor);
        lineDataSet.setCircleColor(randomColor);
    }
}