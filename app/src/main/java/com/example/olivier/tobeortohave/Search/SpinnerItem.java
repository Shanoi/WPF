package com.example.olivier.tobeortohave.Search;

/**
 * Created by Olivier on 16/05/2017.
 */

public class SpinnerItem {

    protected String element1;
    private boolean selected;

    public SpinnerItem(String element1) {
        this.element1 = element1;
        this.selected = false;
    }

    public String getElement() {
        return element1;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
