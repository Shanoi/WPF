package com.example.olivier.tobeortohave.Magasins.Graphs.Formatters;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class AxisNumberFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;

    public AxisNumberFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " u.";
    }
}
