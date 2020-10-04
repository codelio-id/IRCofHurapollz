package com.jabirdeveloper.ircofhurapollz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ModeGelap {

    private static boolean isAktif;

    public static void setMode (Context context, boolean isDark){
        SharedPreferences modeGelap = context.getSharedPreferences("modeGelap", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = modeGelap.edit();
        editor.putBoolean("aktif", isDark);
        editor.apply();
    }

    public static void getMode(Context context) {
        SharedPreferences modeGelap = context.getSharedPreferences("modeGelap", Context.MODE_PRIVATE);
        isAktif = modeGelap.getBoolean("aktif", false);
        if (isAktif){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static boolean isGelap(Context context){
        SharedPreferences modeGelap = context.getSharedPreferences("modeGelap", Context.MODE_PRIVATE);
        isAktif = modeGelap.getBoolean("aktif", false);
        return isAktif;
    }

}
