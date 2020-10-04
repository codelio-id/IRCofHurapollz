package com.jabirdeveloper.ircofhurapollz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.BuildConfig;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);
        String kategori = settings.getString("kategori", "na");
        Random random = new Random();
        int posisi = random.nextInt(39);
        String versi = "Versi " + BuildConfig.VERSION_NAME;
        TextView tv = findViewById(R.id.tv_versi);
        TextView tv2 = findViewById(R.id.tv_copyright);
        TextView tvMotivasi = findViewById(R.id.teksMotivasi);
        tvMotivasi.setText(AppConfig.KATA_KATA[posisi]);
        tv.setText(versi);
        tv2.setText(getString(R.string.teks_memuat));

        Handler h = new Handler();
        h.postDelayed(() -> {
            if (kategori.equals("na")) {
                startActivity(new Intent(this, PilihKategoriActivity.class));
                finish();
            } else {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }

        }, 8000);


    }

}
