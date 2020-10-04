package com.jabirdeveloper.ircofhurapollz.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<WpPostModel> items;

    public EditorAdapter(Context context, List<WpPostModel> items) {
        this.context = context;
        this.items = items;
    }

    class EditorItemHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView judul;
        EditorItemHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_image);
            judul = itemView.findViewById(R.id.tv_judul);
        }
        void setData(WpPostModel data){
            itemView.setOnClickListener(v -> {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("wp_post", data);
                context.startActivity(i);
            });
            judul.setText(Html.fromHtml(data.getTitle().getRendered()));
            thumbnail.setClipToOutline(true);
            Picasso.get()
                    .load(data.getFeaturedMediaUrl())
                    .placeholder(R.drawable.logo)
                    .fit()
                    .centerCrop()
                    .into(thumbnail);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pilihan_editor, parent, false);
        return new EditorItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EditorItemHolder h = (EditorItemHolder) holder;
        h.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
