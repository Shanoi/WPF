package com.example.olivier.tobeortohave.Search;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

import com.example.olivier.tobeortohave.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String[] select_qualification = {
                "Select Qualification", "10th / Below", "12th", "Diploma", "UG",
                "PG", "Phd"};
        Spinner spinner = (Spinner) findViewById(R.id.dptChoice);

        ArrayList<SpinnerItem> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            SpinnerItem stateVO = new SpinnerItem();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        AdpaterSpinner myAdapter = new AdpaterSpinner(SearchActivity.this, 0,
                listVOs);
        spinner.setAdapter(myAdapter);

    }

}
