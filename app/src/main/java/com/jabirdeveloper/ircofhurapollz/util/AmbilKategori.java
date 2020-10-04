package com.jabirdeveloper.ircofhurapollz.util;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.CategoriesModel;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AmbilKategori {

    public static void getData(final Context context, final TextView kategori, String id){
        String url = WPApiUrl.URL_CATEGORIES + "/" + id;
        Call<CategoriesModel> cat = WPConnection.getWpService().getCategories(url);
        cat.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                if (response.body() != null){
                    CategoriesModel cm = response.body();
                    kategori.setText(cm.getName());
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void getData(final Context context, final MaterialToolbar kategori, String id){
        String url = WPApiUrl.URL_CATEGORIES + "/" + id;
        Call<CategoriesModel> cat = WPConnection.getWpService().getCategories(url);
        cat.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                if (response.body() != null){
                    CategoriesModel cm = response.body();
                    kategori.setTitle(cm.getName());
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
