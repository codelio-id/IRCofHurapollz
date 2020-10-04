package com.jabirdeveloper.ircofhurapollz.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.KategoriAdapter;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilPost;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabKategoriFragment extends Fragment {


    public TabKategoriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_kategori, container, false);
        // Inflate the layout for this fragment
        init(view);
        return view;
    }

    private void init(View view) {
        String idKategori = requireArguments().getString("id");
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefresh);
        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorBgRefreshLayout));
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        List<DaftarArtikel> items = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        KategoriAdapter adapter = new KategoriAdapter(getContext(), items);
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);
        rv.getRecycledViewPool().setMaxRecycledViews(0,0);
        AmbilPost post = new AmbilPost(requireContext(), refreshLayout, adapter);
        if (items.size() == 0){
            post.getPostKategori(items, idKategori);
        }
        adapter.notifyDataSetChanged();

        refreshLayout.setOnRefreshListener(() -> {
            post.artikel.clear();
            items.clear();
            post.getPostKategori(items, idKategori);
        });

    }

}
