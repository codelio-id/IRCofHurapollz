package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.view.View;

import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class TerbaruBaseHolder extends RecyclerView.ViewHolder {
    public abstract void setData(Context context, DaftarArtikel daftarArtikel, int position);
    public TerbaruBaseHolder(@NonNull View itemView) {
        super(itemView);
    }
}
