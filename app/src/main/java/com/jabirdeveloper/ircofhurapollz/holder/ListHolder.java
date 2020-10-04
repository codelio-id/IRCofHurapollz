package com.jabirdeveloper.ircofhurapollz.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;
import com.jabirdeveloper.ircofhurapollz.util.AmbilKategori;
import com.jabirdeveloper.ircofhurapollz.util.UbahKe;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;

public class ListHolder extends TerbaruBaseHolder {

    private ImageView thumbnail, avatar;
    private TextView kategori, judul, nama, publish;

    public ListHolder(@NonNull View itemView) {
        super(itemView);
        thumbnail = itemView.findViewById(R.id.iv_image);
        avatar = itemView.findViewById(R.id.iv_avatar);
        kategori = itemView.findViewById(R.id.tv_kategori);
        judul = itemView.findViewById(R.id.tv_judul);
        nama = itemView.findViewById(R.id.tv_nama_penulis);
        publish = itemView.findViewById(R.id.tv_publish);
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("wp_post", daftarArtikel.getPost());
            context.startActivity(i);
        });
        judul.setText(Html.fromHtml(daftarArtikel.getPost().getTitle().getRendered()));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(AppConfig.DATE_FORMAT);
        try {
            Date date = df.parse(daftarArtikel.getPost().getDate());
            assert date != null;
            publish.setText(UbahKe.getTimeAgo(date));
        } catch (ParseException e) {
            e.printStackTrace();
            publish.setText(daftarArtikel.getPost().getDate());
        }
        avatar.setClipToOutline(true);
        thumbnail.setClipToOutline(true);
        Picasso.get()
                .load(daftarArtikel.getPost().getFeaturedMediaUrl())
                .placeholder(R.drawable.logo)
                .fit()
                .centerCrop()
                .into(thumbnail);
        if (TextUtils.isEmpty(kategori.getText().toString())){
            AmbilKategori.getData(context,kategori,daftarArtikel.getPost().getCategories().get(0).toString());
            AmbilInfo.author(context, avatar, nama, daftarArtikel.getPost().getLinks().getAuthor().get(0).getHref());
        }
    }
}
