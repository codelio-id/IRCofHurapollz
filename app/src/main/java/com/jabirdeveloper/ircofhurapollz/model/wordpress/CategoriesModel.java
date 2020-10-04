package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("_links")
    @Expose
    private Links links;

    public CategoriesModel() {
    }

    public CategoriesModel(int id, int count, String description, String link, String name, String slug, Links links) {
        this.id = id;
        this.count = count;
        this.description = description;
        this.link = link;
        this.name = name;
        this.slug = slug;
        this.links = links;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Links getLinks() {
        return links;
    }
}
