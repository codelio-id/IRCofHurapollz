package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

public class HeaderHolder extends TerbaruBaseHolder {
    private ImageView thumbnail;
    private TextView judul;
    private String urlThumb;

    public HeaderHolder(@NonNull View itemView) {
        super(itemView);
        thumbnail = itemView.findViewById(R.id.iv_image);
        judul = itemView.findViewById(R.id.tv_judul);
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("wp_post", daftarArtikel.getPost());
            context.startActivity(i);
        });
        if (daftarArtikel.getPost().getTitle().getRendered() != null){
            judul.setText(Html.fromHtml(daftarArtikel.getPost().getTitle().getRendered()));
        }
        Picasso.get()
                .load(daftarArtikel.getPost().getFeaturedMediaUrl())
                .placeholder(R.drawable.logo)
                .fit()
                .centerCrop()
                .into(thumbnail);
    }
}
