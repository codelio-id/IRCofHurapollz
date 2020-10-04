package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("avatar_urls")
    @Expose
    private AuthorAvatar avatarUrl;

    public AuthorModel() {
    }

    public AuthorModel(int id, String name, String link, AuthorAvatar avatarUrl) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.avatarUrl = avatarUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public AuthorAvatar getAvatarUrl() {
        return avatarUrl;
    }
}
