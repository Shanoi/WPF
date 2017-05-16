package com.example.olivier.tobeortohave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopListActivity;
import com.example.olivier.tobeortohave.Map.MapsActivity;
import com.example.olivier.tobeortohave.Search.SearchActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);



       //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gridNews);

        //MenuAdapter menuAdapter = new MenuAdapter();

        //recyclerView.setAdapter(menuAdapter);

        //gridView.setAdapter(ListAdapter);

      // recyclerView.setAdapter(ListAdapter);

      //  recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


       /* ProgressBar bar = (ProgressBar) findViewById(R.id.bar);

        bar.setProgress(99);*/

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

    public void openStats(View view) {

        System.out.println("OPENSTATSm");

    }

    public void openStores(View view) {



        Intent myIntent = new Intent(MainActivity.this, ShopListActivity.class);

        myIntent.putExtra(ShopListActivity.ARG_QUERY, "SELECT * FROM magasin");

        startActivity(myIntent);

    }

    public void openMap(View view) {

        Intent myIntent = new Intent(MainActivity.this, MapsActivity.class);

        myIntent.putExtra(MapsActivity.ARG_QUERY, "SELECT * FROM magasin");

        startActivity(myIntent);

    }

    public void openSearch(View view) {

        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);

        startActivity(myIntent);


    }
}
