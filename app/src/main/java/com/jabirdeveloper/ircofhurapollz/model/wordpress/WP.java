package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WP implements Serializable {
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;

    @SerializedName("embeddable")
    @Expose
    private boolean embeddable;

    @SerializedName("href")
    @Expose
    private String href;

    public WP() {
    }

    public WP(String taxonomy, boolean embeddable, String href) {
        this.taxonomy = taxonomy;
        this.embeddable = embeddable;
        this.href = href;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public boolean isEmbeddable() {
        return embeddable;
    }

    public String getHref() {
        return href;
    }
}
