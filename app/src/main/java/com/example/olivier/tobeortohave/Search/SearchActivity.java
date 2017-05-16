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

        myIntent.putExtra(ShopListActivity.ARG_QUERY, queryBuilder());

        startActivity(myIntent);

    }

    public void resetAll(View view) {

        resetDpts();
        resetRegions();
        resetVilles();

        keyWord.setText("");

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

    private String queryBuilder() {

        /*Cursor cursor = myDataBase.rawQuery("SELECT I.* \n" +
                "FROM magasin I\n" +
                "INNER JOIN (SELECT num_departement \n" +
                "            FROM departements\n" +
                "\t\t\tWHERE num_region = 7) E\n" +
                "    ON I.codePostale LIKE E.num_departement || '%'", null);*/

        String query = "";
        String subQuery;

        /*query = "SELECT * " +
                "FROM magasin M " +
                "INNER JOIN (SELECT num_departement \n" +
                "            FROM departements\n" +
                "\t\t\tWHERE num_region = 7) E\n" +
                "    ON M.codePostale LIKE E.num_departement || '%'" +
                "WHERE magasinName LIKE '%" + keyWord.getText() + "%' OR " +
                "adresseMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                "Ville LIKE '%" + keyWord.getText() + "%' OR " +
                "codePostale LIKE '%" + keyWord.getText() + "%' OR " +
                "TelephoneMagasin '%" + keyWord.getText() + "%' OR " +
                "mailMagasin '%" + keyWord.getText() + "%' OR " +
                "pageWeb '%" + keyWord.getText() + "%'";*/

        /*query = "SELECT * " +
                "FROM (SELECT *\n" +
                "FROM magasin M \n" +
                "INNER JOIN (SELECT *\n" +
                "            FROM (SELECT * \n" +
                "\t\t\t\t\tFROM regions R\n" +
                "\t\t\t\t\tINNER JOIN departements D\n" +
                "\t\t\t\t\tWHERE R.num_region = D.num_region)) E\n" +
                "                ON M.codePostale LIKE E.num_departement || '___'\n" +
                "ORDER BY idMagasin) " +
                "WHERE magasinName LIKE '%" + keyWord.getText() + "%' OR " +
                "adresseMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                "Ville LIKE '%" + keyWord.getText() + "%' OR " +
                "codePostale LIKE '%" + keyWord.getText() + "%' OR " +
                "TelephoneMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                "mailMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                "pageWeb LIKE '%" + keyWord.getText() + "%' OR " +
                "num_departement LIKE '%" + keyWord.getText() + "%' OR " +
                "'nom:1' LIKE '%" + keyWord.getText() + "%' OR " +
                "nom LIKE '%" + keyWord.getText() + "%'";*/

        ArrayList<String> reg = new ArrayList<>();

        for (int i = 0; i < regions.size(); i++) {

            if (regions.get(i).isSelected()) {

                reg.add(regions.get(i).getElement1());

            }

        }

        ArrayList<String> deps = new ArrayList<>();

        for (int i = 0; i < dpts.size(); i++) {

            if (dpts.get(i).isSelected()) {

                deps.add(dpts.get(i).getElement1());

            }

        }

        ArrayList<String> vils = new ArrayList<>();

        for (int i = 0; i < villes.size(); i++) {

            if (villes.get(i).isSelected()) {

                vils.add(villes.get(i).getElement1());

            }

        }

        if (!keyWord.getText().toString().equals("")) {

            subQuery = "SELECT * " +
                    "FROM (SELECT *\n" +
                    "FROM magasin M \n" +
                    "INNER JOIN (SELECT *\n" +
                    "            FROM (SELECT * \n" +
                    "\t\t\t\t\tFROM regions R\n" +
                    "\t\t\t\t\tINNER JOIN departements D\n" +
                    "\t\t\t\t\tWHERE R.num_region = D.num_region)) E\n" +
                    "                ON M.codePostale LIKE E.num_departement || '___'\n" +
                    "ORDER BY idMagasin) " +
                    "WHERE magasinName LIKE '%" + keyWord.getText() + "%' OR " +
                    "adresseMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                    "Ville LIKE '%" + keyWord.getText() + "%' OR " +
                    "codePostale LIKE '%" + keyWord.getText() + "%' OR " +
                    "TelephoneMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                    "mailMagasin LIKE '%" + keyWord.getText() + "%' OR " +
                    "pageWeb LIKE '%" + keyWord.getText() + "%' OR " +
                    "num_departement LIKE '%" + keyWord.getText() + "%' OR " +
                    "'nom:1' LIKE '%" + keyWord.getText() + "%' OR " +
                    "nom LIKE '%" + keyWord.getText() + "%'";


        } else {

            subQuery = "SELECT * " +
                    "FROM magasin M \n" +
                    "INNER JOIN (SELECT *\n" +
                    "            FROM (SELECT * \n" +
                    "\t\t\t\t\tFROM regions R\n" +
                    "\t\t\t\t\tINNER JOIN departements D\n" +
                    "\t\t\t\t\tWHERE R.num_region = D.num_region)) E\n" +
                    "                ON M.codePostale LIKE E.num_departement || '___'\n" +
                    "ORDER BY idMagasin";

        }

        if (!reg.isEmpty() | !deps.isEmpty() | !vils.isEmpty()) {

            query = "SELECT * " +
                    "FROM (" + subQuery + ")" +
                    " WHERE ";

        } else{

            query = subQuery;

        }

        StringBuilder queryFill = new StringBuilder();

        if (!reg.isEmpty()) {

            queryFill.append("nom = ").append("'").append(reg.get(0)).append("'");

            for (int i = 1; i < reg.size(); i++) {

                queryFill.append(" OR nom = ").append("'").append(reg.get(i)).append("'");

            }

            if (!deps.isEmpty() | !vils.isEmpty()) {

                queryFill.append(" OR ");

            }

        }

        if (!deps.isEmpty()) {

            queryFill.append("nom_departement = ").append("'").append(deps.get(0)).append("'");

            for (int i = 1; i < deps.size(); i++) {

                queryFill.append(" OR nom_departement = ").append("'").append(deps.get(i)).append("'");

            }

            if (!vils.isEmpty()) {

                queryFill.append(" OR ");

            }

        }

        if (!vils.isEmpty()) {

            queryFill.append("Ville = ").append("'").append(vils.get(0)).append("'");

            for (int i = 1; i < vils.size(); i++) {

                queryFill.append(" OR Ville = ").append("'").append(vils.get(0)).append("'");

            }

        }

        query += queryFill.toString();

        System.out.println(query);

        return query;

    }
}
