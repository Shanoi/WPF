package com.example.olivier.tobeortohave.Stats;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisCurrencyFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisDayFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisNumberFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisPeopleFormatter;
import com.example.olivier.tobeortohave.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatsActivity extends Activity implements OnChartValueSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        BarChart caChart = (BarChart) findViewById(R.id.caChart);

        caChart.setOnChartValueSelectedListener(this);

        BarChart employeesChart = (BarChart) findViewById(R.id.employeesChart);

        employeesChart.setOnChartValueSelectedListener(this);

        BarChart maintenanceChart = (BarChart) findViewById(R.id.maintenanceChart);

        maintenanceChart.setOnChartValueSelectedListener(this);

        BarChart prodrenvChart = (BarChart) findViewById(R.id.prodrenvChart);

        prodrenvChart.setOnChartValueSelectedListener(this);

        generateDataChart(caChart, new AxisCurrencyFormatter());
        generateDataChart(maintenanceChart, new AxisCurrencyFormatter());
        generateDataChart(employeesChart, new AxisPeopleFormatter());
        generateDataChart(prodrenvChart, new AxisNumberFormatter());

        ArrayList<ArrayList<BarEntry>> barEntries = fetchData(4);

        setDatatoChart(caChart, barEntries.get(0), getString(R.string.CA));
        setDatatoChart(maintenanceChart, barEntries.get(2), getString(R.string.coutMaintenance));
        setDatatoChart(employeesChart, barEntries.get(1), getString(R.string.nbEmployés));
        setDatatoChart(prodrenvChart, barEntries.get(3), getString(R.string.nbProdRenv));


    }

    private ArrayList<ArrayList<BarEntry>> fetchData(int idShop) {

        int cpt = 0;

        DBHelper DB = new DBHelper(this);

        ArrayList<ArrayList<BarEntry>> data = new ArrayList<>();

        ArrayList<BarEntry> caValues = new ArrayList<>();
        ArrayList<BarEntry> employeesValues = new ArrayList<>();
        ArrayList<BarEntry> maintenanceValues = new ArrayList<>();
        ArrayList<BarEntry> prodrenvValues = new ArrayList<>();

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchStat();


            while (!cursor.isAfterLast()) {

                caValues.add(new BarEntry(cpt, cursor.getFloat(2), cursor.getString(7)));
                employeesValues.add(new BarEntry(cpt, cursor.getFloat(3), cursor.getString(7)));
                maintenanceValues.add(new BarEntry(cpt, cursor.getFloat(4), cursor.getString(7)));
                prodrenvValues.add(new BarEntry(cpt, cursor.getFloat(5), cursor.getString(7)));

                cpt++;

                cursor.moveToNext();

            }

            cursor.close();

            DB.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        data.add(caValues);
        data.add(employeesValues);
        data.add(maintenanceValues);
        data.add(prodrenvValues);

        return data;

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void generateDataChart(BarChart mChart, IAxisValueFormatter valueFormatter) {

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new AxisNameShopFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new AxisCurrencyFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(valueFormatter);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        /*YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); */ // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

    }

    private void setDatatoChart(BarChart barChart, ArrayList<BarEntry> barEntries, String title) {

        BarDataSet set1;
        set1 = new BarDataSet(barEntries, title);
        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        barChart.setData(data);

    }


}
