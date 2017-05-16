package com.example.olivier.tobeortohave.Search;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopListActivity;
import com.example.olivier.tobeortohave.R;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<SpinnerItemDpt> dpts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner = (Spinner) findViewById(R.id.dptChoice);

        fetchDpt();

        AdapterSpinner myAdapter = new AdapterSpinner(SearchActivity.this, 0,
                dpts);
        spinner.setAdapter(myAdapter);

    }

    private void fetchDpt() {

        DBHelper DB = new DBHelper(this);

        dpts = new ArrayList<>();

        dpts.add(new SpinnerItemDpt("", getString(R.string.choose_dpt)));

        try {
            DB.createDataBase();

            DB.openDataBase();

            Cursor cursor = DB.fetchDpt();


            while (!cursor.isAfterLast()) {

                dpts.add(new SpinnerItemDpt(cursor.getString(2), cursor.getString(0)));

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

}
