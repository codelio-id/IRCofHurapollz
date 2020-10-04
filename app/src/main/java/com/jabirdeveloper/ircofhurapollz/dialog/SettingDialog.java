package com.jabirdeveloper.ircofhurapollz.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.MainActivity;
import com.jabirdeveloper.ircofhurapollz.activity.SplashActivity;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.util.ModeGelap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingDialog {

    private AlertDialog.Builder dialog;
    private AlertDialog d;
    private Context context;
    private SwitchMaterial swDarkMode;
    private Button btnRestart;
    private Button btnLogout;
    private Spinner spinKategori;
    private ArrayAdapter<CharSequence> adapterKategori;
    private SharedPreferences settings;
    private RequestQueue referenceQueue;

    public SettingDialog(Context context) {
        this.context = context;
        referenceQueue = Volley.newRequestQueue(context);
        init();
    }

    private void init() {
        dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(true);
        dialog.setTitle(context.getString(R.string.menu_setting));
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setting, null);
        settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        btnRestart = view.findViewById(R.id.btn_simpan);
        btnLogout = view.findViewById(R.id.btn_logout);
        spinKategori = view.findViewById(R.id.spinner_kategori);
        swDarkMode = view.findViewById(R.id.sw_dark_mode);

        btnRestart.setOnClickListener(v -> {
            context.startActivity(new Intent(context, MainActivity.class));
            ((Activity) context).finish();
        });

        SharedPreferences sh = ((Activity) context).getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        // Show login or post depends on token condition
        if (sh.getString("token", "").equalsIgnoreCase("")) {
            btnLogout.setVisibility(View.INVISIBLE);
        } else {
            btnLogout.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(v -> {
                // Remove token
                // Get JWT token in shared prefs
                editor.remove("token");
                editor.remove("display_name");
                editor.remove("email");
                editor.remove("nicename");
                editor.apply();

                context.startActivity(new Intent(context, MainActivity.class));
                ((Activity) context).finish();
            });
        }

        adapterKategori = ArrayAdapter.createFromResource(
                context, R.array.list_kategori, android.R.layout.simple_spinner_item
        );
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKategori.setAdapter(adapterKategori);
        spinKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String kat = adapterKategori.getItem(position).toString();
                for (int i = 0; i < AppConfig.KATEGORI_LIST.size(); i++) {
                    if (kat.equals(AppConfig.KATEGORI_LIST.get(i).getKategori())) {
                        String idKat = AppConfig.KATEGORI_LIST.get(i).getId();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("kategori", idKat);
                        editor.apply();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String kategori = settings.getString("kategori", "na");
        if (!kategori.equals("na")) {
            for (int i = 0; i < AppConfig.KATEGORI_LIST.size(); i++) {
                if (kategori.equals(AppConfig.KATEGORI_LIST.get(i).getId())) {
                    String aktif = AppConfig.KATEGORI_LIST.get(i).getKategori();
                    for (int j = 0; j < context.getResources().getStringArray(R.array.list_kategori).length; j++) {
                        if (aktif.equals(context.getResources().getStringArray(R.array.list_kategori)[j])) {
                            spinKategori.setSelection(j);
                        }
                    }
                }
            }
        }

        // Setup dark mode
        if (ModeGelap.isGelap(context)) {
            swDarkMode.setChecked(true);
        } else {
            swDarkMode.setChecked(false);
        }
        swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ModeGelap.setMode(context, isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            context.startActivity(new Intent(context, MainActivity.class));
            ((Activity) context).finish();
        });

        dialog.setView(view);
        d = dialog.create();
        d.show();
    }

}
