package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class SearchingCenter {

    public String getSearchtext() {
        return searchtext;
    }

    public void setSearchtext(String searchtext) {
        this.searchtext = searchtext;
    }

    public SearchingCenter(String searchtext) {

        this.searchtext = searchtext;
    }

    private String searchtext;
}
