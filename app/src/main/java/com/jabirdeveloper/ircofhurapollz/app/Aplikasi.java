package com.jabirdeveloper.ircofhurapollz.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.util.ModeGelap;

import java.util.ArrayList;
import java.util.List;

public class Aplikasi extends Application {

    public static final String CHANNEL_ID = "notifikasi";
    private static final String TAG = Aplikasi.class.getSimpleName();
    private InterstitialAd interstitialAd;
    private List<String> testDevice = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        testDevice.add(getString(R.string.admob_test_device));
        ModeGelap.getMode(this);
        MobileAds.initialize(this);
        loadIntertitial();
        buatChannel();
    }

    private void buatChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notifikasi = new NotificationChannel(
                    CHANNEL_ID,
                    "Notifikasi",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notifikasi.setDescription("Notifikasi untuk post terbaru");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notifikasi);
        }
    }

    synchronized public void loadIntertitial(){
        if (interstitialAd == null || !interstitialAd.isLoaded()){
            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
            RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                    .setTestDeviceIds(testDevice)
                    .build();
            MobileAds.setRequestConfiguration(requestConfiguration);
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Log.d(TAG, "onAdFailedToLoad: " + i);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.d(TAG, "onAdLoaded: loaded");
                }
            });
            interstitialAd.loadAd(new AdRequest.Builder().build());
        } else {
            Log.d(TAG, "Intertitial ads loaded");
        }
    }

    synchronized public InterstitialAd getInterstitialAd(){
        if (interstitialAd == null){
            loadIntertitial();
        }
        return interstitialAd;
    }

}
