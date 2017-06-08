package com.example.olivier.tobeortohave.Magasins.FragsAndActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.olivier.tobeortohave.R;

/**
 * An activity representing a single Shop detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ShopListActivity}.
 */
public class ShopDetailActivity extends AppCompatActivity implements ShopDetailFragment.NotifySel {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            arguments.putParcelable(ShopDetailFragment.ARG_SHOP, getIntent().getExtras().getParcelable(ShopDetailFragment.ARG_SHOP));

            ShopDetailFragment fragment = new ShopDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shop_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void notifySel() {
        //Nothing to do
    }
}
