package com.example.olivier.tobeortohave.Magasins.FragsAndActivity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.olivier.tobeortohave.Data.Magasin;
import com.example.olivier.tobeortohave.R;

/**
 * Created by Olivier on 07/05/2017.
 */

public class ListShopViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mIdView;
    public final TextView mContentView;
    public Magasin mItem;

    public ListShopViewHolder(View view) {
        super(view);
        mView = view;
        mIdView = (TextView) view.findViewById(R.id.id);
        mContentView = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}
