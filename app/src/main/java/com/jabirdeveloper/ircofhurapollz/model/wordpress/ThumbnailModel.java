package com.jabirdeveloper.ircofhurapollz.model.wordpress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThumbnailModel {

    @SerializedName("guid")
    @Expose
    private Rendered guid;

    public ThumbnailModel() {
    }

    public ThumbnailModel(Rendered guid) {
        this.guid = guid;
    }

    public Rendered getGuid() {
        return guid;
    }

}
