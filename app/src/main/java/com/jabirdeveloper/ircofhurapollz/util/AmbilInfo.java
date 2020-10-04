package com.jabirdeveloper.ircofhurapollz.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.AuthorModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.CategoriesModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.ThumbnailModel;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AmbilInfo {

    private Context context;

    public AmbilInfo(Context context) {
        this.context = context;
    }

    public static void thumbnail(String urlThumbnail, String urlAuthor, String idKategori, InfoListener listener){
        Call<ThumbnailModel> img = WPConnection.getWpService().getThumbnail(urlThumbnail);
        img.enqueue(new Callback<ThumbnailModel>() {
            @Override
            public void onResponse(Call<ThumbnailModel> call, Response<ThumbnailModel> response) {
                if (response.body() != null){
                    ThumbnailModel tm = response.body();
                    String thumbUrl = tm.getGuid().getRendered();
                    author(urlAuthor, idKategori, thumbUrl, listener);
                }
            }

            @Override
            public void onFailure(Call<ThumbnailModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onFailure(t);
            }
        });
    }

    private static void author(String url, String idKategori, String thumbnail, InfoListener listener){
        Call<AuthorModel> au = WPConnection.getWpService().getAuthor(url);
        au.enqueue(new Callback<AuthorModel>() {
            @Override
            public void onResponse(Call<AuthorModel> call, Response<AuthorModel> response) {
                if (response.body() != null){
                    AuthorModel am = response.body();
                    String nama = am.getName();
                    String avatarUrl = am.getAvatarUrl().getUrl();
                    kategori(idKategori, listener, thumbnail, avatarUrl, nama);
                }
            }

            @Override
            public void onFailure(Call<AuthorModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onFailure(t);
            }
        });
    }

    private static void kategori(String id, InfoListener listener, String thumbnail, String avatarUrl, String nama) {
        String url = WPApiUrl.URL_CATEGORIES + "/" + id;
        Call<CategoriesModel> cat = WPConnection.getWpService().getCategories(url);
        cat.enqueue(new Callback<CategoriesModel>() {
            @Override
            public void onResponse(Call<CategoriesModel> call, Response<CategoriesModel> response) {
                if (response.body() != null){
                    CategoriesModel cm = response.body();
                    listener.onComplete(thumbnail, avatarUrl, nama, cm.getName());
                }
            }

            @Override
            public void onFailure(Call<CategoriesModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                listener.onFailure(t);
            }
        });

    }

    public static void thumbnail(final Context context, final ImageView image, String url){
        Call<ThumbnailModel> img = WPConnection.getWpService().getThumbnail(url);
        img.enqueue(new Callback<ThumbnailModel>() {
            @Override
            public void onResponse(Call<ThumbnailModel> call, Response<ThumbnailModel> response) {
                if (response.body() != null){
                    ThumbnailModel tm = response.body();
                    String thumbUrl = tm.getGuid().getRendered();
                    Picasso.get()
                            .load(thumbUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit()
                            .centerCrop()
                            .into(image, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "onSuccess: thumbnail");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e(TAG, "onError: ", e);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<ThumbnailModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void author(final Context context, final ImageView image, TextView nama, String url){
        Call<AuthorModel> au = WPConnection.getWpService().getAuthor(url);
        au.enqueue(new Callback<AuthorModel>() {
            @Override
            public void onResponse(Call<AuthorModel> call, Response<AuthorModel> response) {
                if (response.body() != null){
                    AuthorModel am = response.body();
                    nama.setText(am.getName());
                    String avatarUrl = am.getAvatarUrl().getUrl();
                    Picasso.get()
                            .load(avatarUrl)
                            .placeholder(R.drawable.logo)
                            .fit()
                            .centerCrop()
                            .into(image, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "onSuccess: avatar image");
                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.e(TAG, "onError: avatar image ", e);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<AuthorModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void author(final Context context, TextView nama, String url){
        Call<AuthorModel> au = WPConnection.getWpService().getAuthor(url);
        au.enqueue(new Callback<AuthorModel>() {
            @Override
            public void onResponse(Call<AuthorModel> call, Response<AuthorModel> response) {
                if (response.body() != null){
                    AuthorModel am = response.body();
                    nama.setText(am.getName());
                }
            }

            @Override
            public void onFailure(Call<AuthorModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface InfoListener {
        void onComplete(String thumbnail, String avatar, String nama, String kategori);
        void onFailure(Throwable error);
    }
}
