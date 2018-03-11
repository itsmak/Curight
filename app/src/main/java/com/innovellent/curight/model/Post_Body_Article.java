package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 2/24/2018.
 */

public class Post_Body_Article {


    private int userid;

    private String category;

    public Post_Body_Article(int userid, String category) {
        this.userid = userid;
        this.category = category;
    }




}
