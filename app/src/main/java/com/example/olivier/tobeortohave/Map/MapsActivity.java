package com.example.olivier.tobeortohave.Map;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Data.Magasin;
import com.example.olivier.tobeortohave.MainActivity;
import com.example.olivier.tobeortohave.R;
import com.example.olivier.tobeortohave.Search.SearchActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        ClusterManager.OnClusterClickListener<Magasin>,
        ClusterManager.OnClusterInfoWindowClickListener<Magasin>,
        ClusterManager.OnClusterItemClickListener<Magasin>,
        ClusterManager.OnClusterItemInfoWindowClickListener<Magasin> {

    private GoogleMap mMap;

    public static final String ARG_QUERY = "query";

    ArrayList<Magasin> magasin;

    ClusterManager<Magasin> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fetchShop(getIntent().getExtras().getString(ARG_QUERY));

    }

    @Override
    protected void onResume() {
        super.onResume();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        mClusterManager = new ClusterManager<>(this, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        for (int i = 0; i < magasin.size(); i++) {

            Magasin offsetItem = magasin.get(i);
            mClusterManager.addItem(offsetItem);

        }

        mClusterManager.cluster();

    }

    private void fetchShop(String query){

        DBHelper DB = new DBHelper(this);

        try {
            DB.createDataBase();

            DB.openDataBase();

            magasin = (ArrayList<Magasin>) DB.getMagasins(query);

            DB.close();


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onClusterItemClick(Magasin magasin) {

        Intent myIntent = new Intent(MapsActivity.this, SearchActivity.class);

        startActivity(myIntent);

        System.out.println("Click Cluster");

        return true;

    }

    @Override
    public boolean onClusterClick(Cluster<Magasin> cluster) {
        return false;
    }

    @Override
    public void onClusterInfoWindowClick(Cluster<Magasin> cluster) {

    }

    @Override
    public void onClusterItemInfoWindowClick(Magasin magasin) {

    }
}
