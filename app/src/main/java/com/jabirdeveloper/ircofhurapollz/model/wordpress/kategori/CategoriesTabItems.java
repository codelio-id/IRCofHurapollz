package com.jabirdeveloper.ircofhurapollz.model.wordpress.kategori;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesTabItems {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("object_id")
    @Expose
    private String objectId;

    @SerializedName("child_items")
    @Expose
    private List<CategoriesTabItems> childItem;

    public CategoriesTabItems() {
    }

    public CategoriesTabItems(String url, String title, String objectId, List<CategoriesTabItems> childItem) {
        this.url = url;
        this.title = title;
        this.objectId = objectId;
        this.childItem = childItem;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getObjectId() {
        return objectId;
    }

    public List<CategoriesTabItems> getChildItem() {
        return childItem;
    }
}
