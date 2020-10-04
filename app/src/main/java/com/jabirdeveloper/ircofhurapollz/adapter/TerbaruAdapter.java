package com.jabirdeveloper.ircofhurapollz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.holder.EditorHolder;
import com.jabirdeveloper.ircofhurapollz.holder.HeaderHolder;
import com.jabirdeveloper.ircofhurapollz.holder.HurapollzHolder;
import com.jabirdeveloper.ircofhurapollz.holder.IklanHolder;
import com.jabirdeveloper.ircofhurapollz.holder.KategoriPilihanHolder;
import com.jabirdeveloper.ircofhurapollz.holder.ListHolder;
import com.jabirdeveloper.ircofhurapollz.holder.MotivasiHolder;
import com.jabirdeveloper.ircofhurapollz.holder.SliderHolder;
import com.jabirdeveloper.ircofhurapollz.holder.TerbaruBaseHolder;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.util.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TerbaruAdapter extends RecyclerView.Adapter<TerbaruBaseHolder> {

    private Context context;
    private List<DaftarArtikel> items;

    public TerbaruAdapter(Context context, List<DaftarArtikel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public TerbaruBaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case (Constants.TIPE_SLIDER): {
                view = LayoutInflater.from(context).inflate(R.layout.item_slider_host, parent, false);
                return new SliderHolder(view);
            }
            case (Constants.TIPE_LIST): {
                view = LayoutInflater.from(context).inflate(R.layout.item_post_1, parent, false);
                return new ListHolder(view);
            }
            case (Constants.TIPE_EDITOR): {
                view = LayoutInflater.from(context).inflate(R.layout.item_pilihan_editor_host, parent, false);
                return new EditorHolder(view);
            }
            case (Constants.TIPE_KATEGORI): {
                view = LayoutInflater.from(context).inflate(R.layout.item_pilihan_editor_host, parent, false);
                return new KategoriPilihanHolder(view);
            }
            case (Constants.TIPE_HEADER): {
                view = LayoutInflater.from(context).inflate(R.layout.item_post_2, parent, false);
                return new HeaderHolder(view);
            }
            case (Constants.TIPE_HURAPOLLZ): {
                view = LayoutInflater.from(context).inflate(R.layout.layout_hurapollz, parent, false);
                return new HurapollzHolder(view);
            }
            case (Constants.TIPE_MOTIVASI) : {
                view = LayoutInflater.from(context).inflate(R.layout.layout_motivasi, parent, false);
                return new MotivasiHolder(view);
            }
            default: {
                view = LayoutInflater.from(context).inflate(R.layout.item_iklan_native, parent, false);
                return new IklanHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TerbaruBaseHolder holder, int position) {
        holder.setData(context, items.get(position), position);
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
