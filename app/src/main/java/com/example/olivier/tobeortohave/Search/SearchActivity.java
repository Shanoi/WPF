package com.example.olivier.tobeortohave.Search;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopListActivity;
import com.example.olivier.tobeortohave.R;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<SpinnerItemTwoElements> dpts;
    private ArrayList<SpinnerItem> regions;
    private ArrayList<SpinnerItem> villes;

    private TextView keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        keyWord = (TextView) findViewById(R.id.keyWord);

        Spinner spinnerDpt = (Spinner) findViewById(R.id.dptChoice);
        Spinner spinnerRegion = (Spinner) findViewById(R.id.regionChoice);
        Spinner spinnerVille = (Spinner) findViewById(R.id.villeChoice);

        fetchDpt();
        fetchRegion();
        fetchVille();

        AdapterSpinnertTwoElements adapterDpt = new AdapterSpinnertTwoElements(SearchActivity.this, 0,
                dpts);
        spinnerDpt.setAdapter(adapterDpt);

        AdapterSpinnerOneElement adapterRegion = new AdapterSpinnerOneElement(SearchActivity.this, 0,
                regions);
        spinnerRegion.setAdapter(adapterRegion);

        AdapterSpinnerOneElement adapterVille = new AdapterSpinnerOneElement(SearchActivity.this, 0,
                villes);
        spinnerVille.setAdapter(adapterVille);

    }

    private void fetchDpt() {

        DBHelper DB = new DBHelper(this);

        dpts = new ArrayList<>();

        dpts.add(new SpinnerItemTwoElements("", getString(R.string.choose_dpt)));

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchDpt();


            while (!cursor.isAfterLast()) {

                dpts.add(new SpinnerItemTwoElements(cursor.getString(2), cursor.getString(0)));

                cursor.moveToNext();

            }

            cursor.close();

            DB.close();
        } catch (IOException | SQLException | java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    private void fetchRegion() {

        DBHelper DB = new DBHelper(this);

        regions = new ArrayList<>();

        regions.add(new SpinnerItem("(" + getString(R.string.choose_reg) + ")"));

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchRegion();


            while (!cursor.isAfterLast()) {

                regions.add(new SpinnerItem(cursor.getString(1)));

                cursor.moveToNext();

            }

            cursor.close();

            DB.close();
        } catch (IOException | SQLException | java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    private void fetchVille() {

        DBHelper DB = new DBHelper(this);

        villes = new ArrayList<>();

        villes.add(new SpinnerItem("(" + getString(R.string.choose_ville) + ")"));

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchVille();


            while (!cursor.isAfterLast()) {

                villes.add(new SpinnerItem(cursor.getString(0)));

                cursor.moveToNext();

            }

            cursor.close();

            DB.close();
        } catch (IOException | SQLException | java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public void openSearch(View view) {

        Intent myIntent = new Intent(SearchActivity.this, ShopListActivity.class);

        myIntent.putExtra(ShopListActivity.ARG_QUERY, "SELECT * FROM magasin");

        startActivity(myIntent);

    }

    public void resetAll(View view) {

        resetDpts();
        resetRegions();
        resetVilles();

    }

    public void resetRegions(View view) {

        resetRegions();

    }

    public void resetDpt(View view) {

        resetDpts();

    }

    public void resetVille(View view) {

        resetVilles();

    }

    private void resetRegions() {

        for (int i = 0; i < regions.size(); i++) {

            regions.get(i).setSelected(false);

        }

    }

    private void resetDpts() {

        for (int i = 0; i < dpts.size(); i++) {

            dpts.get(i).setSelected(false);

        }

    }

    private void resetVilles() {

        for (int i = 0; i < villes.size(); i++) {

            villes.get(i).setSelected(false);

        }

    }


}
