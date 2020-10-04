package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Href implements Serializable {

    @SerializedName("href")
    @Expose
    private String href;

    public Href() {
    }

    public Href(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }
}
