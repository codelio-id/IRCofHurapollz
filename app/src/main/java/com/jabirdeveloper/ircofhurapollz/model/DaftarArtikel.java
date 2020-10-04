package com.jabirdeveloper.ircofhurapollz.model;

import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.Constants;

import java.util.List;

public class DaftarArtikel {
    private List<WpPostModel> slider, postPilihan;
    private WpPostModel post;
    private UnifiedNativeAd iklan;
    private int tipe;

    public DaftarArtikel(int tipe) {
        this.tipe = tipe;
    }

    public DaftarArtikel(){
        tipe = Constants.TIPE_HURAPOLLZ;
    }

    public DaftarArtikel(List<WpPostModel> slider) {
        this.slider = slider;
        tipe = Constants.TIPE_SLIDER;
    }

    public DaftarArtikel(WpPostModel post, int tipe) {
        this.post = post;
        this.tipe = tipe;
    }

    public DaftarArtikel(UnifiedNativeAd iklan) {
        this.iklan = iklan;
        tipe = Constants.TIPE_IKLAN;
    }

    public DaftarArtikel(List<WpPostModel> postPilihan, int tipe) {
        this.postPilihan = postPilihan;
        this.tipe = tipe;
    }

    public List<WpPostModel> getSlider() {
        return slider;
    }

    public WpPostModel getPost() {
        return post;
    }

    public UnifiedNativeAd getIklan() {
        return iklan;
    }

    public List<WpPostModel> getPostPilihan() {
        return postPilihan;
    }

    public int getTipe() {
        return tipe;
    }
}
