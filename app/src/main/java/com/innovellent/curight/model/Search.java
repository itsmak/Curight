package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class Search {

    private String id,name,category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Search(String id, String name, String category) {

        this.id = id;
        this.name = name;
        this.category = category;
    }
}
