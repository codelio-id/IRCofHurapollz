package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.KategoriPilihanAdapter;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.model.KategoriPilihanModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class KategoriPilihanHolder extends TerbaruBaseHolder {
    private List<KategoriPilihanModel> items = new ArrayList<>();
    private List<WpPostModel> horizontal = new ArrayList<>();
    private LinearLayoutManager manager;
    private KategoriPilihanAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvPilihan;

    public KategoriPilihanHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recyclerView);
        tvPilihan = itemView.findViewById(R.id.tv_pilihan_editor);
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        adapter = new KategoriPilihanAdapter(context, items);
        manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        String id = settings.getString("kategori", "na");
        for (int i = 0; i < AppConfig.KATEGORI_LIST.size(); i++) {
            if (id.equals(AppConfig.KATEGORI_LIST.get(i).getId())) {
                String kategori = AppConfig.KATEGORI_LIST.get(i).getKategori();
                String s = "#IRC_" + kategori;
                tvPilihan.setText(s);
            }
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        if (items.size() == 0) {
            for (int i = 0; i < daftarArtikel.getPostPilihan().size(); i++) {
                if (i < 5){
                    horizontal.add(daftarArtikel.getPostPilihan().get(i));
                }
            }
            items.add(new KategoriPilihanModel(horizontal));
            for (int i = 0; i < daftarArtikel.getPostPilihan().size(); i++) {
                if (i >= 5){
                    items.add(new KategoriPilihanModel(daftarArtikel.getPostPilihan().get(i)));
                }
            }
        }
        adapter.notifyDataSetChanged();

    }
}
