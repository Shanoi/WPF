package com.example.olivier.tobeortohave.Stats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopListActivity;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisCurrencyFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisDayFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisNumberFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisPeopleFormatter;
import com.example.olivier.tobeortohave.MainActivity;
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
import java.util.StringTokenizer;

public class StatsActivity extends Activity implements OnChartValueSelectedListener {

    private DatePicker datePicker;

    private TextView setDate;

    private String month;

    private String year;

    private BarChart caChart;

    private BarChart employeesChart;

    private BarChart maintenanceChart;

    private BarChart prodrenvChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);

        setDate = (TextView) findViewById(R.id.date_time_set);

        caChart = (BarChart) findViewById(R.id.caChart);

        caChart.setOnChartValueSelectedListener(this);

        employeesChart = (BarChart) findViewById(R.id.employeesChart);

        employeesChart.setOnChartValueSelectedListener(this);

        maintenanceChart = (BarChart) findViewById(R.id.maintenanceChart);

        maintenanceChart.setOnChartValueSelectedListener(this);

        prodrenvChart = (BarChart) findViewById(R.id.prodrenvChart);

        prodrenvChart.setOnChartValueSelectedListener(this);

        generateDataChart(caChart, new AxisCurrencyFormatter());
        generateDataChart(maintenanceChart, new AxisCurrencyFormatter());
        generateDataChart(employeesChart, new AxisPeopleFormatter());
        generateDataChart(prodrenvChart, new AxisNumberFormatter());

        Date d = new Date();

        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");

        month = dateFormatMonth.format(d);
        year = dateFormatYear.format(d);

        ArrayList<ArrayList<BarEntry>> barEntries = fetchData(month + "/" + year);

        setDate.setText(month + "/" + year);

        setDatatoChart(caChart, barEntries.get(0), getString(R.string.CA));
        setDatatoChart(maintenanceChart, barEntries.get(2), getString(R.string.coutMaintenance));
        setDatatoChart(employeesChart, barEntries.get(1), getString(R.string.nbEmployés));
        setDatatoChart(prodrenvChart, barEntries.get(3), getString(R.string.nbProdRenv));


    }

    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<ArrayList<BarEntry>> barEntries = fetchData(month + "/" + year);

        setDatatoChart(caChart, barEntries.get(0), getString(R.string.CA));
        setDatatoChart(maintenanceChart, barEntries.get(2), getString(R.string.coutMaintenance));
        setDatatoChart(employeesChart, barEntries.get(1), getString(R.string.nbEmployés));
        setDatatoChart(prodrenvChart, barEntries.get(3), getString(R.string.nbProdRenv));

        caChart.invalidate();
        maintenanceChart.invalidate();
        employeesChart.invalidate();
        prodrenvChart.invalidate();

    }

    private ArrayList<ArrayList<BarEntry>> fetchData(String date) {

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

            Cursor cursor = DB.fetchStat(date);


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

    public void onChoose(View view) {

        Intent myIntent = new Intent(StatsActivity.this, ShopListActivity.class);

        myIntent.putExtra(ShopListActivity.ARG_QUERY, "SELECT * FROM magasin");

        startActivity(myIntent);

    }

    public void onClick(View view) {

        final View dialogView = View.inflate(this, R.layout.dialog_date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setView(dialogView);
        alertDialog.setTitle("Choose Date");

        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

        datePicker.updateDate(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);

        datePicker.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                int monTmp = datePicker.getMonth() + 1;

                if (monTmp < 10) {

                    month = "0" + monTmp;
                    year = Integer.toString(datePicker.getYear());


                } else {

                    month = Integer.toString(monTmp);
                    year = Integer.toString(datePicker.getYear());

                }

                setDate.setText(month + "/" + year);

                ArrayList<ArrayList<BarEntry>> barEntries = fetchData(month + "/" + year);

                setDatatoChart(caChart, barEntries.get(0), getString(R.string.CA));
                setDatatoChart(maintenanceChart, barEntries.get(2), getString(R.string.coutMaintenance));
                setDatatoChart(employeesChart, barEntries.get(1), getString(R.string.nbEmployés));
                setDatatoChart(prodrenvChart, barEntries.get(3), getString(R.string.nbProdRenv));

                caChart.invalidate();
                maintenanceChart.invalidate();
                employeesChart.invalidate();
                prodrenvChart.invalidate();

                //System.out.println("OK : " + datePicker.);

            }
        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Lorsque l'on cliquera sur annuler on quittera l'application

                alertDialog.dismiss();

            }
        });

        alertDialog.show();

    }


}
