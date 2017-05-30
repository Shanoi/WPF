package com.example.olivier.tobeortohave.Stats;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Olivier on 29/05/2017.
 */

public class AxisNameShopFormatter implements IAxisValueFormatter {

    private BarLineChartBase<?> chart;

    public AxisNameShopFormatter(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        return chart.getData().getDataSets().get(0).getEntryForIndex((int) value).getData().toString();

    }
}
