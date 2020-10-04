package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorAvatar {

    @SerializedName("96")
    @Expose
    private String url;

    public AuthorAvatar() {
    }

    public AuthorAvatar(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
