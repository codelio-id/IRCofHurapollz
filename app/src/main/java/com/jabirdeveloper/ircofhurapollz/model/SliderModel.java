package com.jabirdeveloper.ircofhurapollz.model;

import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;

public class SliderModel {
    private WpPostModel items;
    private String thumbnail;

    public SliderModel(WpPostModel items, String thumbnail) {
        this.items = items;
        this.thumbnail = thumbnail;
    }

    public WpPostModel getItems() {
        return items;
    }

    public String getThumbnail() {
        return thumbnail;
    }

}
