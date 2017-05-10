package com.example.olivier.tobeortohave.Magasins.FragsAndActivity;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Data.Magasin;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.AxisDayFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.DayAxisValueFormatter;
import com.example.olivier.tobeortohave.Magasins.Graphs.Formatters.MyAxisValueFormatter;
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

/**
 * A fragment representing a single Shop detail screen.
 * This fragment is either contained in a {@link ShopListActivity}
 * in two-pane mode (on tablets) or a {@link ShopDetailActivity}
 * on handsets.
 */
public class ShopDetailFragment extends Fragment implements OnChartValueSelectedListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_NOM = "item_nom";

    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private String mItem;

    private int idShop;

    protected BarChart mChart;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShopDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_NOM)) {

            mItem = getArguments().getString(ARG_ITEM_NOM);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem);
            }
        }

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            idShop = getArguments().getInt(ARG_ITEM_ID);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.shop_detail)).setText(mItem);
        }

        mChart = (BarChart) rootView.findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(this);

        generateDataChart();

        ArrayList<BarEntry> yVals1 = fetchData();

        BarDataSet set1;
        set1 = new BarDataSet(yVals1, "The year 2017");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        mChart.setData(data);

//
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(true);
//
//        mChart.getDescription().setEnabled(false);
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        mChart.setMaxVisibleValueCount(60);
//
//        // scaling can now only be done on x- and y-axis separately
//        mChart.setPinchZoom(false);
//
//        mChart.setDrawGridBackground(false);
//        // mChart.setDrawYLabels(false);
//
//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setLabelCount(7);
//        xAxis.setValueFormatter(xAxisFormatter);
//
//        IAxisValueFormatter custom = new MyAxisValueFormatter();
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });



        /*ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 12 + 1; i++) {
            float mult = (1);
            float val = (float) (Math.random() * mult);

            yVals1.add(new BarEntry(i, val));

        }

        BarDataSet set1;
        set1 = new BarDataSet(yVals1, "The year 2017");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        mChart.setData(data);*/

        return rootView;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private ArrayList<BarEntry> fetchData() {

        System.out.println("Fetch");

        String request = "SELECT * FROM Informations WHERE idMagasin = 1";

        SimpleDateFormat formatIO = new SimpleDateFormat("dd/MM/yy");
        DateFormat df = new SimpleDateFormat("yyMM");

        Date enDate;

        int enInt;

        DBHelper DB = new DBHelper(this.getContext());

        ArrayList<BarEntry> values = new ArrayList<>();

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchData(2);


            while (!cursor.isAfterLast()) {
                System.out.println("DATAAS : " + cursor.getString(1));
                enDate = formatIO.parse(cursor.getString(1));

                enInt = Integer.valueOf(df.format(enDate));

                System.out.println("EN INT : " + enInt);

                if (enInt > 1800) {

                    values.add(new BarEntry(enInt - 88, cursor.getFloat(2)));

                }else{

                    values.add(new BarEntry(enInt, cursor.getFloat(2)));

                }

                cursor.moveToNext();

            }

            cursor.close();

            DB.close();
        } catch (IOException | SQLException | ParseException e) {
            e.printStackTrace();
        }

        return values;

    }

    private BarData generateData(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry(i, (float) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private void generateDataChart() {

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

        IAxisValueFormatter xAxisFormatter = new AxisDayFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

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

        System.err.println("generate");

    }
}
