package com.example.olivier.tobeortohave.Magasins.Graphs.Formatters;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Olivier on 09/05/2017.
 */

public class AxisDayFormatter implements IAxisValueFormatter {

    private BarLineChartBase<?> chart;

    public AxisDayFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        SimpleDateFormat longFormat = new SimpleDateFormat("dd/MMM/yy", Locale.getDefault());

        SimpleDateFormat shortFormat = new SimpleDateFormat("MMM/yy", Locale.getDefault());

        DateFormat df = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        Date date;

        System.out.println("PARSE : " + (int) value);

        try {
            date = df.parse(String.valueOf((int) value));
        } catch (ParseException e) {

            date = new Date();
        }

        if (chart.getVisibleXRange() > 30 * 6) {

            return shortFormat.format(date);

        } else {

            return longFormat.format(date);

        }
    }

}
