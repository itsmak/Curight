package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 2/24/2018.
 */

public class Article_FEED {

    @SerializedName("articleid")
    private int articleid;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageurl")
    private String imageurl;

    @SerializedName("category")
    private String category;

    @SerializedName("articlewishlistid")
    private int articlewishlistid;

    @SerializedName("modifiedby")
    private int modifiedby;

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getArticlewishlistid() {
        return articlewishlistid;
    }

    public void setArticlewishlistid(int articlewishlistid) {
        this.articlewishlistid = articlewishlistid;
    }

    public int getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(int modifiedby) {
        this.modifiedby = modifiedby;
    }
}
