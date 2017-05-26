package com.example.olivier.tobeortohave.Map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Data.Magasin;
import com.example.olivier.tobeortohave.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final String ARG_QUERY = "query";

    ArrayList<Magasin> magasin;

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

        ClusterManager<Magasin> mClusterManager;

        mClusterManager = new ClusterManager<>(this, mMap);

        //mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        LatLng tmpL;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < magasin.size(); i++) {

            /*double lat = ALS.get(i).getLat();
            double lng = ALS.get(i).getLng();*/

            Magasin offsetItem = magasin.get(i);
            mClusterManager.addItem(offsetItem);
            builder.include(offsetItem.getPosition());

            /*mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener(){

                @Override
                public boolean onClusterItemClick(ClusterItem clusterItem) {

                    final Magasin station = ((Magasin) clusterItem);

                    new AlertDialog.Builder(MapsActivity.this)
                            .setTitle(station.getName())
                            .setMessage(station.getAddress())
                            .setCancelable(false)
                            .setPositiveButton("Aller", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent myIntent = new Intent(MapsActivity.this, MapsActivity_Itineraire.class);
                                    myIntent.putExtra("Station", station );
                                    startActivity(myIntent);
                                }
                            })
                            .setNegativeButton("Fermer", null)
                            .create().show();

                    return true;

                }

            });*/

            double lat = magasin.get(i).getLatitude();
            double lng = magasin.get(i).getLongitude();

            tmpL = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(tmpL)
                    .title(magasin.get(i).getNom())
                    .snippet(magasin.get(i).getAdresse() + "\n" + magasin.get(i).getTelephone()));

        }

        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));


        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/



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

}
