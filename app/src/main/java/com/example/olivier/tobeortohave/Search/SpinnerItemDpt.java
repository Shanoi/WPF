package com.example.olivier.tobeortohave.Search;

/**
 * Created by Olivier on 14/05/2017.
 */

public class SpinnerItemDpt {

    private String nomDpt;
    private String numDpt;
    private boolean selected;

    public SpinnerItemDpt(String nomDpt, String numDpt) {
        this.nomDpt = nomDpt;
        this.numDpt = numDpt;
        this.selected = false;
    }

    public String getDpt() {
        return nomDpt + " (" + numDpt + ")";
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
