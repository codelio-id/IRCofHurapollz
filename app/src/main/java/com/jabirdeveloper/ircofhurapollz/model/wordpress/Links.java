package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Links implements Serializable {

    @SerializedName("self")
    @Expose
    private List<Href> self;

    @SerializedName("collection")
    @Expose
    private List<Href> collection;

    @SerializedName("author")
    @Expose
    private List<Href> author;

    @SerializedName("replies")
    @Expose
    private List<Href> replies;

    @SerializedName("wp:featuredmedia")
    @Expose
    private List<Href> featuredMedia;

    @SerializedName("wp:terms")
    @Expose
    private List<WP> terms;

    public Links() {
    }

    public Links(List<Href> self, List<Href> collection, List<Href> author, List<Href> replies, List<Href> featuredMedia, List<WP> terms) {
        this.self = self;
        this.collection = collection;
        this.author = author;
        this.replies = replies;
        this.featuredMedia = featuredMedia;
        this.terms = terms;
    }

    public List<Href> getSelf() {
        return self;
    }

    public List<Href> getCollection() {
        return collection;
    }

    public List<Href> getAuthor() {
        return author;
    }

    public List<Href> getReplies() {
        return replies;
    }

    public List<Href> getFeaturedMedia() {
        return featuredMedia;
    }

    public List<WP> getTerms() {
        return terms;
    }
}
