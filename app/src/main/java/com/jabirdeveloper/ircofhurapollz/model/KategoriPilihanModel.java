package com.jabirdeveloper.ircofhurapollz.model;

import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.Constants;

import java.util.List;

public class KategoriPilihanModel {

    private List<WpPostModel> horizonal;
    private WpPostModel vertikal;
    private int tipe;

    public KategoriPilihanModel(List<WpPostModel> horizonal) {
        this.horizonal = horizonal;
        tipe = Constants.TIPE_KATEGORI;
    }

    public KategoriPilihanModel(WpPostModel vertikal) {
        this.vertikal = vertikal;
        tipe = Constants.TIPE_LIST;
    }

    public List<WpPostModel> getHorizonal() {
        return horizonal;
    }

    public WpPostModel getVertikal() {
        return vertikal;
    }

    public int getTipe() {
        return tipe;
    }
}
