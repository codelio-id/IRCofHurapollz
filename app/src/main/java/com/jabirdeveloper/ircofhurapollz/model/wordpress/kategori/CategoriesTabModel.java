package com.jabirdeveloper.ircofhurapollz.model.wordpress.kategori;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesTabModel {

    @SerializedName("term_id")
    @Expose
    private int termId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("items")
    @Expose
    private List<CategoriesTabItems> items;

    public CategoriesTabModel() {
    }

    public CategoriesTabModel(int termId, String name, String slug, String taxonomy, int count, List<CategoriesTabItems> items) {
        this.termId = termId;
        this.name = name;
        this.slug = slug;
        this.taxonomy = taxonomy;
        this.count = count;
        this.items = items;
    }

    public int getTermId() {
        return termId;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public int getCount() {
        return count;
    }

    public List<CategoriesTabItems> getItems() {
        return items;
    }
}
