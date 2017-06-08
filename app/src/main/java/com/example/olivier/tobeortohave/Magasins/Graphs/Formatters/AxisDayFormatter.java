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

        if (value > 1712) {

            value += 88;

        }

        SimpleDateFormat shortFormat = new SimpleDateFormat("MMM/yy", Locale.getDefault());

        DateFormat df = new SimpleDateFormat("yyMM", Locale.getDefault());
        Date date;

        try {
            date = df.parse(String.valueOf((int) value));
        } catch (ParseException e) {

            date = new Date();
        }

        return shortFormat.format(date);

    }

}
