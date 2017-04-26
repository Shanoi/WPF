package com.example.olivier.tobeortohave;

import android.graphics.Color;
import android.os.RecoverySystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar bar = (ProgressBar) findViewById(R.id.bar);

        bar.setProgress(99);

//        PieChart chart = (PieChart) findViewById(R.id.chart);
//
//        int[] dataObjects = {5,89,6,64,14,25,36,78,9,20};
//
//        List<PieEntry> entries = new ArrayList<PieEntry>();
//
//        int i = 0;
//
//        for (int data : dataObjects) {
//
//            // turn your data into Entry objects
//            entries.add(new PieEntry(i, data));
//            i++;
//        }
//
//        PieDataSet dataSet = new PieDataSet(entries, "Label"); // add entries to dataset
//        dataSet.setColor(Color.BLUE);
//        dataSet.setValueTextColor(Color.BLACK); // styling, ...
//        dataSet.setSliceSpace(3f);
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//
//        PieData lineData = new PieData(dataSet);
//        chart.setData(lineData);
//        chart.invalidate(); // refresh
//
//        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    }
}
