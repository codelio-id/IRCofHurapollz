package com.jabirdeveloper.ircofhurapollz.model;

import java.util.List;

public class ArtikelPost {
    private String status;

    private String title;

    private String content;

    private List<Integer> categories;

    private int featured_media;

    public ArtikelPost(String status, String title, String content, List<Integer> categories, int featured_media) {
        this.status = status;
        this.title = title;
        this.content = content;
        this.categories = categories;
        this.featured_media = featured_media;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public void setFeatured_media(int featured_media) {
        this.featured_media = featured_media;
    }
}
