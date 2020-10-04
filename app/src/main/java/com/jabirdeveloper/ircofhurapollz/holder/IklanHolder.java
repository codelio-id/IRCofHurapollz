package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

public class IklanHolder extends TerbaruBaseHolder {
    
    private UnifiedNativeAdView adView;
    private AdLoader adLoader;
    private UnifiedNativeAd iklan;
    private boolean adsLoaded;

    public IklanHolder(@NonNull View itemView) {
        super(itemView);
        adView = itemView.findViewById(R.id.ad_view);
        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        loadNativeAd(context);
        if (adsLoaded){
            // Some assets are guaranteed to be in every UnifiedNativeAd.
            ((TextView) adView.getHeadlineView()).setText(daftarArtikel.getIklan().getHeadline());
            ((TextView) adView.getBodyView()).setText(daftarArtikel.getIklan().getBody());
            ((Button) adView.getCallToActionView()).setText(daftarArtikel.getIklan().getCallToAction());

            // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
            // check before trying to display them.
            NativeAd.Image icon = daftarArtikel.getIklan().getIcon();

            if (icon == null) {
                adView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                Picasso.get().load(icon.getUri()).fit().centerCrop().into(((ImageView) adView.getIconView()));
//            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }

            if (daftarArtikel.getIklan().getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(daftarArtikel.getIklan().getPrice());
            }

            if (daftarArtikel.getIklan().getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(daftarArtikel.getIklan().getStore());
            }

            if (daftarArtikel.getIklan().getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(daftarArtikel.getIklan().getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }

            if (daftarArtikel.getIklan().getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(daftarArtikel.getIklan().getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }

            // Assign native ad object to the native view.
            adView.setNativeAd(daftarArtikel.getIklan());
        }
    }

    private void loadNativeAd(Context context) {
        Toast.makeText(context, "Menyiapkan iklan", Toast.LENGTH_SHORT).show();
        AdLoader.Builder builder = new AdLoader.Builder(context, context.getString(R.string.admob_native));
        adLoader = builder.forUnifiedNativeAd(unifiedNativeAd -> {
            iklan = unifiedNativeAd;
            if (!adLoader.isLoading()) {
                Toast.makeText(context, "Iklan berhasil diambil", Toast.LENGTH_SHORT).show();
                adsLoaded = true;
            } else {
                adsLoaded = false;
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (!adLoader.isLoading()) {
                    Toast.makeText(context, "Iklan berhasil diambil", Toast.LENGTH_SHORT).show();
                    adsLoaded = true;
                } else {
                    adsLoaded = false;
                }
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().addTestDevice(context.getString(R.string.admob_test_device)).build());
    }
    
}
