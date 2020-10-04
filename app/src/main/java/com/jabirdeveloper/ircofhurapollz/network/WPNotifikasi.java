package com.jabirdeveloper.ircofhurapollz.network;

import com.jabirdeveloper.ircofhurapollz.app.AppConfig;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class WPNotifikasi {

    public interface WPFirebase {
        @GET()
        Call<String> getData(@Url String url);
    }

    private static WPFirebase wpFirebase = null;
    public static WPFirebase getWpFirebase(){
        if (wpFirebase == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.WEBSITE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            wpFirebase = retrofit.create(WPFirebase.class);
        }
        return wpFirebase;
    }

}
