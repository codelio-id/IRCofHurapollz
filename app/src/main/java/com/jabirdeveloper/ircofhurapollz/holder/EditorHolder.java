package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.EditorAdapter;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditorHolder extends TerbaruBaseHolder {
    private List<WpPostModel> items = new ArrayList<>();
    private LinearLayoutManager manager;
    private EditorAdapter adapter;
    private RecyclerView recyclerView;
    public EditorHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recyclerView);
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        adapter = new EditorAdapter(context, items);
        manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setPadding(16, 0, 0, 0);
        if (items.size() == 0){
            items.addAll(daftarArtikel.getPostPilihan());
        }
        adapter.notifyDataSetChanged();

    }
}
