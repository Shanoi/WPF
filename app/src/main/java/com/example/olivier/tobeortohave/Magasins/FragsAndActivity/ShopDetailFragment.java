package com.example.olivier.tobeortohave.Magasins.FragsAndActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Data.Magasin;
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
    public static final String ARG_SHOP = "magasin";

    private Magasin shop;

    private ImageButton selButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShopDetailFragment() {
    }

    NotifySel mCallback;

    public interface NotifySel{
        void notifySel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (NotifySel) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_SHOP)) {

            shop = getArguments().getParcelable(ARG_SHOP);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(shop != null ? shop.getNom() : null);
            }

        }

        /*if (getArguments().containsKey(ARG_ITEM_NOM)) {

            mItem = getArguments().getString(ARG_ITEM_NOM);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setNomDpt(mItem);
            }
        }

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            idShop = getArguments().getInt(ARG_ITEM_ID);

        }*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shop_detail, container, false);

        ((TextView) rootView.findViewById(R.id.shop_name)).setText(shop.getNom());
        ((TextView) rootView.findViewById(R.id.shop_phone)).setText(shop.getTelephone());
        ((TextView) rootView.findViewById(R.id.shop_email)).setText(shop.getMail());
        ((TextView) rootView.findViewById(R.id.shop_webPage)).setText(shop.getPageWeb());
        ((TextView) rootView.findViewById(R.id.shop_address)).setText(shop.getAdresse());
        ((TextView) rootView.findViewById(R.id.shop_zipCode)).setText(shop.getPostalCode());
        ((TextView) rootView.findViewById(R.id.shop_city)).setText(shop.getVille());

        ((ImageButton) rootView.findViewById(R.id.btn_call)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String posted_by = shop.getTelephone();

                String uri = "tel:" + posted_by.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });

        ((ImageButton) rootView.findViewById(R.id.btn_mail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", shop.getMail(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

        ((ImageButton) rootView.findViewById(R.id.btn_webPage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = shop.getPageWeb();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        selButton = (ImageButton) rootView.findViewById(R.id.sel_button);

        if (shop.isSelected()) {

            selButton.setImageResource(R.drawable.ic_star_black_24dp);

        } else {

            selButton.setImageResource(R.drawable.ic_star_border_black_24dp);

        }

        selButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICK");
                if (shop.isSelected()) {

                    selButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                    setSel(shop.getId(), 0);

                } else {

                    selButton.setImageResource(R.drawable.ic_star_black_24dp);
                    setSel(shop.getId(), 1);

                }


                shop.setSelected(!shop.isSelected());

                mCallback.notifySel();

            }
        });

        BarChart caChart = (BarChart) rootView.findViewById(R.id.caChart);

        caChart.setOnChartValueSelectedListener(this);

        BarChart employeesChart = (BarChart) rootView.findViewById(R.id.employeesChart);

        employeesChart.setOnChartValueSelectedListener(this);

        BarChart maintenanceChart = (BarChart) rootView.findViewById(R.id.maintenanceChart);

        maintenanceChart.setOnChartValueSelectedListener(this);

        BarChart prodrenvChart = (BarChart) rootView.findViewById(R.id.prodrenvChart);

        prodrenvChart.setOnChartValueSelectedListener(this);


        generateDataChart(caChart, new AxisCurrencyFormatter());
        generateDataChart(maintenanceChart, new AxisCurrencyFormatter());
        generateDataChart(employeesChart, new AxisPeopleFormatter());
        generateDataChart(prodrenvChart, new AxisNumberFormatter());

        ArrayList<ArrayList<BarEntry>> barEntries = fetchData(shop.getId());

        setDatatoChart(caChart, barEntries.get(0), getString(R.string.CA));
        setDatatoChart(maintenanceChart, barEntries.get(2), getString(R.string.coutMaintenance));
        setDatatoChart(employeesChart, barEntries.get(1), getString(R.string.nbEmployés));
        setDatatoChart(prodrenvChart, barEntries.get(3), getString(R.string.nbProdRenv));

        return rootView;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void onRefresh() {

        if (shop.isSelected()) {

            selButton.setImageResource(R.drawable.ic_star_border_black_24dp);

        } else {

            selButton.setImageResource(R.drawable.ic_star_black_24dp);

        }

    }

    private ArrayList<ArrayList<BarEntry>> fetchData(int idShop) {

        SimpleDateFormat formatIO = new SimpleDateFormat("dd/MM/yy");
        DateFormat df = new SimpleDateFormat("yyMM");

        Date enDate;

        int enInt;

        DBHelper DB = new DBHelper(this.getContext());

        ArrayList<ArrayList<BarEntry>> data = new ArrayList<>();

        ArrayList<BarEntry> caValues = new ArrayList<>();
        ArrayList<BarEntry> employeesValues = new ArrayList<>();
        ArrayList<BarEntry> maintenanceValues = new ArrayList<>();
        ArrayList<BarEntry> prodrenvValues = new ArrayList<>();

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchData(idShop);


            while (!cursor.isAfterLast()) {

                enDate = formatIO.parse(cursor.getString(1));

                enInt = Integer.valueOf(df.format(enDate));

                if (enInt > 1800) {

                    enInt -= 88;

                }

                caValues.add(new BarEntry(enInt, cursor.getFloat(2)));
                employeesValues.add(new BarEntry(enInt, cursor.getFloat(3)));
                maintenanceValues.add(new BarEntry(enInt, cursor.getFloat(4)));
                prodrenvValues.add(new BarEntry(enInt, cursor.getFloat(5)));

                cursor.moveToNext();

            }

            cursor.close();

            DB.close();
        } catch (IOException | SQLException | ParseException e) {
            e.printStackTrace();
        }

        data.add(caValues);
        data.add(employeesValues);
        data.add(maintenanceValues);
        data.add(prodrenvValues);

        return data;

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

        IAxisValueFormatter xAxisFormatter = new AxisDayFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        //IAxisValueFormatter custom = new AxisCurrencyFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(valueFormatter);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
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

    private void setSel(int id, int sel) {

        DBHelper DB = new DBHelper(this.getContext());

        try {
            DB.createDataBase();

            DB.openDataBase();

            DB.setSel(id, sel);

            DB.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
