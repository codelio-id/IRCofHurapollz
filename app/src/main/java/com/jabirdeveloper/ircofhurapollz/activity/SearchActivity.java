package com.jabirdeveloper.ircofhurapollz.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.TerbaruAdapter;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilPost;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SearchActivity extends AppCompatActivity {

    private String query;
    private List<DaftarArtikel> items = new ArrayList<>();
    private TerbaruAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query = getIntent().getStringExtra("query");
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(query);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
//            toolbar.setBackgroundColor(getResources().getColor(R.color.colorToolbarDark));
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBarDark));
//        }
        SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new TerbaruAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AmbilPost ap = new AmbilPost(this, refreshLayout, adapter);
        ap.getSearchResult(items, query);
        refreshLayout.setOnRefreshListener(() -> {
            items.clear();
            ap.artikel.clear();
            ap.getSearchResult(items, query);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
