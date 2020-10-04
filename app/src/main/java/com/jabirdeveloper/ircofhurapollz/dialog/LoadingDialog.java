package com.jabirdeveloper.ircofhurapollz.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.MainActivity;
import com.jabirdeveloper.ircofhurapollz.util.ModeGelap;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

public class LoadingDialog {

    private AlertDialog d;
    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loading, null);

        dialog.setView(view);
        d = dialog.create();
        d.show();
    }

    public void tutup(){
        d.dismiss();
    }

}
