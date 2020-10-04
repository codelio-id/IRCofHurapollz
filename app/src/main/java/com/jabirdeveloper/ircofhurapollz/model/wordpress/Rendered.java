package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rendered implements Serializable {

    @SerializedName("rendered")
    @Expose
    private String rendered;

    public Rendered() {
    }

    public Rendered(String rendered) {
        this.rendered = rendered;
    }

    public String getRendered() {
        return rendered;
    }

}
