package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.ProfileActivity;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;

import androidx.annotation.NonNull;

public class HurapollzHolder extends TerbaruBaseHolder {

    private Button btnAbout, btnContact, btnPrivacy;

    public HurapollzHolder(@NonNull View itemView) {
        super(itemView);
        btnAbout = itemView.findViewById(R.id.btn_about);
        btnContact = itemView.findViewById(R.id.btn_contact);
        btnPrivacy = itemView.findViewById(R.id.btn_privacy);
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        btnAbout.setOnClickListener(v -> {
            Intent i = new Intent(context, ProfileActivity.class);
            i.putExtra("url", AppConfig.WEBSITE_URL + "about-us");
            context.startActivity(i);
        });
        btnContact.setOnClickListener(v -> {
            Intent i = new Intent(context, ProfileActivity.class);
            i.putExtra("url", AppConfig.WEBSITE_URL + "contact");
            context.startActivity(i);
        });
        btnPrivacy.setOnClickListener(v -> {
            Intent i = new Intent(context, ProfileActivity.class);
            i.putExtra("url", AppConfig.WEBSITE_URL + "privacy-policy");
            context.startActivity(i);
        });
    }
}
