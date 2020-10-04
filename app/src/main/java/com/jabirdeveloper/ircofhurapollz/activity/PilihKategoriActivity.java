package com.jabirdeveloper.ircofhurapollz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;

public class PilihKategoriActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_kategori);
        SharedPreferences settings = getSharedPreferences("settings", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pilih Kategori");
        setSupportActionBar(toolbar);
        RadioGroup rgPilihKategori = findViewById(R.id.rg_kategori);
        Button btnSimpan = findViewById(R.id.btn_simpan);

        for (int i = 1; i < AppConfig.KATEGORI_LIST.size(); i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(AppConfig.KATEGORI_LIST.get(i).getKategori());
            rb.setId(View.generateViewId());
            rb.setPadding(16,0,0,0);
            rgPilihKategori.addView(rb);
        }

        btnSimpan.setOnClickListener(v -> {
            RadioButton rb = findViewById(rgPilihKategori.getCheckedRadioButtonId());
            if (rb != null){
                SharedPreferences.Editor editor = settings.edit();
                String kategori = rb.getText().toString();
                for (int i = 0; i < AppConfig.KATEGORI_LIST.size(); i++) {
                    if (kategori.equals(AppConfig.KATEGORI_LIST.get(i).getKategori())){
                        String kategori_id = AppConfig.KATEGORI_LIST.get(i).getId();
                        editor.putString("kategori", kategori_id);
                        editor.apply();
                        startActivity(new Intent(PilihKategoriActivity.this, MainActivity.class));
                        finish();
                    }
                }
            } else {
                Toast.makeText(this, "Pilih kategori", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
