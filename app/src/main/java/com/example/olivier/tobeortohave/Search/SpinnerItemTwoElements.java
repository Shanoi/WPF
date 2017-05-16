package com.example.olivier.tobeortohave.Search;

/**
 * Created by Olivier on 14/05/2017.
 */

public class SpinnerItemTwoElements extends SpinnerItem {

    private String element2;

    public SpinnerItemTwoElements(String element1, String element2) {
        super(element1);
        this.element2 = element2;
    }

    @Override
    public String getElement() {
        return element1 + " (" + element2 + ")";
    }


}
