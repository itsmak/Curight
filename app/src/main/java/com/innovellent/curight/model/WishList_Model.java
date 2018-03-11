package com.innovellent.curight.model;

/**
 * Created by Mak on 3/2/2018.
 */

public class WishList_Model {

    private int articleid;
    private String title;
    private String description;
    private String imageurl;
    private String category;
    private int articlewishlistid;
    private int modifiedby;

    public WishList_Model(int articleid, String title, String description, String imageurl, String category, int articlewishlistid, int modifiedby) {
        this.articleid = articleid;
        this.title = title;
        this.description = description;
        this.imageurl = imageurl;
        this.category = category;
        this.articlewishlistid = articlewishlistid;
        this.modifiedby = modifiedby;
    }

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
