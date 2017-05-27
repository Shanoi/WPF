package com.example.olivier.tobeortohave.Map.FragAndAct;

import android.content.Context;

import com.example.olivier.tobeortohave.Data.Magasin;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Olivier on 27/05/2017.
 */

public class Renderer extends DefaultClusterRenderer<Magasin> {

    public Renderer(Context context, GoogleMap map, ClusterManager<Magasin> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(Magasin shop, MarkerOptions markerOptions) {

        markerOptions.title(shop.getNom());
        super.onBeforeClusterItemRendered(shop, markerOptions);

    }


}
