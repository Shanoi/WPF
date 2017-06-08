package com.example.olivier.tobeortohave.Map.FragAndAct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Data.Magasin;
import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopDetailActivity;
import com.example.olivier.tobeortohave.Magasins.FragsAndActivity.ShopDetailFragment;
import com.example.olivier.tobeortohave.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * An activity representing a list of maps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ShopDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class mapListActivity extends FragmentActivity implements OnMapReadyCallback, ShopDetailFragment.NotifySel,
        ClusterManager.OnClusterClickListener<Magasin>,
        ClusterManager.OnClusterInfoWindowClickListener<Magasin>,
        ClusterManager.OnClusterItemClickListener<Magasin>,
        ClusterManager.OnClusterItemInfoWindowClickListener<Magasin> {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private GoogleMap mMap;

    public static final String ARG_QUERY = "query";

    ArrayList<Magasin> magasin;

    ClusterManager<Magasin> mClusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fetchShop(getIntent().getExtras().getString(ARG_QUERY));

        System.out.println("Extra : " + getIntent().getExtras().getString(ARG_QUERY));

        if (findViewById(R.id.map_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mClusterManager = new ClusterManager<>(this, mMap);

        mClusterManager.setRenderer(new Renderer(this, mMap, mClusterManager));

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        //LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < magasin.size(); i++) {

            Magasin offsetItem = magasin.get(i);
            mClusterManager.addItem(offsetItem);
            //builder.include(offsetItem.getPosition());

        }

        //LatLngBounds bounds = builder.build();

        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));

    }

    private void fetchShop(String query) {

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

        if (mTwoPane) {
            Bundle arguments = new Bundle();
                        /*arguments.putString(ShopDetailFragment.ARG_ITEM_NOM, holder.mItem.getNom());
                        arguments.putInt(ShopDetailFragment.ARG_ITEM_ID, holder.mItem.getId());*/
            arguments.putParcelable(ShopDetailFragment.ARG_SHOP, magasin);
            ShopDetailFragment fragment = new ShopDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map_detail_container, fragment)
                    .commit();
            return true;

        }

        return false;

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

        if (!mTwoPane) {

            Intent intent = new Intent(this, ShopDetailActivity.class);
                        /*intent.putExtra(ShopDetailFragment.ARG_ITEM_NOM, holder.mItem.getNom());
                        intent.putExtra(ShopDetailFragment.ARG_ITEM_ID, holder.mItem.getId());*/
            intent.putExtra(ShopDetailFragment.ARG_SHOP, magasin);

            startActivity(intent);
        }

    }

    @Override
    public void notifySel() {
        //Nothing to do
    }
}
