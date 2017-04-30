package com.example.olivier.tobeortohave.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olivier.tobeortohave.Adapters.ViewHolders.MenuViewHolder;
import com.example.olivier.tobeortohave.Data.MenuElement;
import com.example.olivier.tobeortohave.R;

import java.util.List;

/**
 * Created by Olivier on 29/04/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> implements AdapterView.OnItemClickListener {

    private List<MenuElement> menuElements;

    Context context;

    public MenuAdapter() {

        MenuElement.values();

        this.menuElements = menuElements;

    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_grid_item, parent, false);

        context = parent.getContext();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);

        return new MenuViewHolder(convertView, img, name);


    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        holder.getImg().setImageResource(MenuElement.values()[position].getImg());

        holder.getName().setText(context.getResources().getString(MenuElement.values()[position].getName()));


    }

    @Override
    public int getItemCount() {
        return MenuElement.values().length;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
