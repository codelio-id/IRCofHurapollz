package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("post")
    @Expose
    private int post;

    @SerializedName("parent")
    @Expose
    private int parent;

    @SerializedName("author")
    @Expose
    private int author;

    @SerializedName("author_name")
    @Expose
    private String authorName;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;

    @SerializedName("content")
    @Expose
    private Rendered content;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("author_avatar_urls")
    @Expose
    private AuthorAvatar authorAvatar;

    public CommentsModel() {
    }

    public CommentsModel(int id, int post, int parent, int author, String authorName, String date, String dateGmt, Rendered content, String link, AuthorAvatar authorAvatar) {
        this.id = id;
        this.post = post;
        this.parent = parent;
        this.author = author;
        this.authorName = authorName;
        this.date = date;
        this.dateGmt = dateGmt;
        this.content = content;
        this.link = link;
        this.authorAvatar = authorAvatar;
    }

    public int getId() {
        return id;
    }

    public int getPost() {
        return post;
    }

    public int getParent() {
        return parent;
    }

    public int getAuthor() {
        return author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getDate() {
        return date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public Rendered getContent() {
        return content;
    }

    public String getLink() {
        return link;
    }

    public AuthorAvatar getAuthorAvatar() {
        return authorAvatar;
    }

}
