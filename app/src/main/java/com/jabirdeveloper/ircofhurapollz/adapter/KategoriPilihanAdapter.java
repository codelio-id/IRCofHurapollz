package com.jabirdeveloper.ircofhurapollz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.KategoriPilihanModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;
import com.jabirdeveloper.ircofhurapollz.util.AmbilKategori;
import com.jabirdeveloper.ircofhurapollz.util.Constants;
import com.jabirdeveloper.ircofhurapollz.util.UbahKe;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class KategoriPilihanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<KategoriPilihanModel> items;

    public KategoriPilihanAdapter(Context context, List<KategoriPilihanModel> items) {
        this.context = context;
        this.items = items;
    }

    class HorizontalPost extends RecyclerView.ViewHolder {
        private List<WpPostModel> items = new ArrayList<>();
        private LinearLayoutManager manager;
        private KategoriHorizontalAdapter adapter;
        private RecyclerView recyclerView;
        private TextView tvPilihan;
        HorizontalPost(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            tvPilihan = itemView.findViewById(R.id.tv_pilihan_editor);
        }
        void setData(List<WpPostModel> data){
            tvPilihan.setVisibility(View.GONE);
            adapter = new KategoriHorizontalAdapter(context, items);
            manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            recyclerView.setPadding(16, 0, 0, 0);
            if (items.size() == 0){
                items.addAll(data);
            }
            adapter.notifyDataSetChanged();
        }
    }

    class VertikalPost extends RecyclerView.ViewHolder {
        private ImageView thumbnail, avatar;
        private TextView kategori, judul, nama, publish;

        VertikalPost(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_image);
            avatar = itemView.findViewById(R.id.iv_avatar);
            kategori = itemView.findViewById(R.id.tv_kategori);
            judul = itemView.findViewById(R.id.tv_judul);
            nama = itemView.findViewById(R.id.tv_nama_penulis);
            publish = itemView.findViewById(R.id.tv_publish);
        }
        void setData(WpPostModel data){
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("wp_post", data);
                context.startActivity(i);
            });
            judul.setText(Html.fromHtml(data.getTitle().getRendered()));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(AppConfig.DATE_FORMAT);
            try {
                Date date = df.parse(data.getDate());
                assert date != null;
                publish.setText(UbahKe.getTimeAgo(date));
            } catch (ParseException e) {
                e.printStackTrace();
                publish.setText(data.getDate());
            }
            avatar.setClipToOutline(true);
            thumbnail.setClipToOutline(true);
            Picasso.get()
                    .load(data.getFeaturedMediaUrl())
                    .placeholder(R.drawable.logo)
                    .fit()
                    .centerCrop()
                    .into(thumbnail);
            if (TextUtils.isEmpty(kategori.getText().toString())){
                AmbilKategori.getData(context,kategori,data.getCategories().get(0).toString());
                AmbilInfo.author(context, avatar, nama, data.getLinks().getAuthor().get(0).getHref());
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Constants.TIPE_KATEGORI) {
            view = LayoutInflater.from(context).inflate(R.layout.item_pilihan_editor_host, parent, false);
            return new HorizontalPost(view);
        }
        view = LayoutInflater.from(context).inflate(R.layout.item_post_1, parent, false);
        return new VertikalPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (items.get(position).getTipe() == Constants.TIPE_KATEGORI){
            HorizontalPost h = (HorizontalPost) holder;
            h.setData(items.get(position).getHorizonal());
        } else {
            VertikalPost h = (VertikalPost) holder;
            h.setData(items.get(position).getVertikal());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTipe();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
