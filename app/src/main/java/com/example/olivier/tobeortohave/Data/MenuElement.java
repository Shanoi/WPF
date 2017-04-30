package com.example.olivier.tobeortohave.Data;

import com.example.olivier.tobeortohave.R;

/**
 * Created by Olivier on 29/04/2017.
 */

public enum MenuElement {

    SEARCH(R.string.Rechercher,R.drawable.ic_search_black_24dp),
    STATS(R.string.Statistiques,R.drawable.ic_insert_chart_black_24dp),
    MAP(R.string.Carte,R.drawable.ic_map_black_24dp),
    SHOPS(R.string.Magasins,R.drawable.ic_store_black_24dp);

    private int name;


    private int img;

    MenuElement(int name, int img) {

        this.name = name;
        this.img = img;

    }

    public int getImg() {
        return img;
    }

    public int getName() {
        return name;
    }

}
