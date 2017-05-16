package com.example.olivier.tobeortohave.Magasins.FragsAndActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olivier.tobeortohave.DBGestion.DBHelper;
import com.example.olivier.tobeortohave.Data.Magasin;
import com.example.olivier.tobeortohave.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Shops. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ShopDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ShopListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.shop_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.shop_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        ArrayList<Magasin> magasin;

        DBHelper DB = new DBHelper(this);

        try {
            DB.createDataBase();

            DB.openDataBase();

            magasin = (ArrayList<Magasin>) DB.getMagasins();

            DB.close();

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);

            recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(magasin));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<ListShopViewHolder> {

        private final List<Magasin> mValues;

        public SimpleItemRecyclerViewAdapter(List<Magasin> items) {
            mValues = items;
        }

        @Override
        public ListShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shop_list_content, parent, false);
            return new ListShopViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ListShopViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getNom());
            holder.mContentView.setText(mValues.get(position).getAdresse());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        /*arguments.putString(ShopDetailFragment.ARG_ITEM_NOM, holder.mItem.getNom());
                        arguments.putInt(ShopDetailFragment.ARG_ITEM_ID, holder.mItem.getId());*/
                        arguments.putParcelable(ShopDetailFragment.ARG_SHOP, holder.mItem);
                        ShopDetailFragment fragment = new ShopDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.shop_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ShopDetailActivity.class);
                        /*intent.putExtra(ShopDetailFragment.ARG_ITEM_NOM, holder.mItem.getNom());
                        intent.putExtra(ShopDetailFragment.ARG_ITEM_ID, holder.mItem.getId());*/
                        intent.putExtra(ShopDetailFragment.ARG_SHOP, holder.mItem);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }


    }
}
