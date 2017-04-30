package com.example.olivier.tobeortohave.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Olivier on 29/04/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;
    private TextView name;

    public MenuViewHolder(View itemView, ImageView img, TextView name) {
        super(itemView);

        this.img = img;
        this.name = name;

    }

    public ImageView getImg() {
        return img;
    }

    public TextView getName() {
        return name;
    }
}
