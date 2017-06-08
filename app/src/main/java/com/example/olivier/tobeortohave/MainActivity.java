package com.example.olivier.tobeortohave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopListActivity;
import com.example.olivier.tobeortohave.Map.FragAndAct.mapListActivity;
import com.example.olivier.tobeortohave.Map.MapsActivity;
import com.example.olivier.tobeortohave.Search.SearchActivity;
import com.example.olivier.tobeortohave.Stats.StatsActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

    }

    public void openStats(View view) {

        Intent myIntent = new Intent(MainActivity.this, StatsActivity.class);

        startActivity(myIntent);

    }

    public void openStores(View view) {



        Intent myIntent = new Intent(MainActivity.this, ShopListActivity.class);

        myIntent.putExtra(ShopListActivity.ARG_QUERY, "SELECT * FROM magasin");

        startActivity(myIntent);

    }

    public void openMap(View view) {

        Intent myIntent = new Intent(MainActivity.this, mapListActivity.class);

        myIntent.putExtra(MapsActivity.ARG_QUERY, "SELECT * FROM magasin");

        startActivity(myIntent);

    }

    public void openSearch(View view) {

        Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);

        startActivity(myIntent);


    }
}
