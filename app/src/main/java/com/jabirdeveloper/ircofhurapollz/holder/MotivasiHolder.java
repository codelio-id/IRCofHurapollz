package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.EditorAdapter;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MotivasiHolder extends TerbaruBaseHolder {

    private TextView motivasi;

    public MotivasiHolder(@NonNull View itemView) {
        super(itemView);
        motivasi = itemView.findViewById(R.id.tv_motivasi);
    }

    @Override
    public void setData(Context context, DaftarArtikel daftarArtikel, int position) {
        Random random = new Random();
        int posisi = random.nextInt(39);
        motivasi.setText(AppConfig.KATA_KATA[posisi]);
    }
}
