package com.jabirdeveloper.ircofhurapollz.network;

import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.model.ArtikelPost;
import com.jabirdeveloper.ircofhurapollz.model.MediaModel;
import com.jabirdeveloper.ircofhurapollz.model.UserModel;
import com.jabirdeveloper.ircofhurapollz.model.authentication.UserAuth;
import com.jabirdeveloper.ircofhurapollz.model.authentication.ValideteCookie;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.AuthorModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.CategoriesModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.CommentsModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.ThumbnailModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public class WPConnection {

    public interface WPService {
        @GET
        Call<WpPostModel> getSinglePost(@Url String url);

        @GET
        Call<List<WpPostModel>> getPostList(@Url String url);

        @GET
        Call<AuthorModel> getAuthor(@Url String url);

        @GET
        Call<ThumbnailModel> getThumbnail(@Url String url);

        @GET
        Call<CategoriesModel> getCategories(@Url String url);

        @GET
        Call<List<CategoriesModel>> getCategoriesList(@Url String url);

        @GET
        Call<List<CommentsModel>> getComments(@Url String url);

        @GET
        Call<ResponseBody> getHTML(@Url String url);

        @GET
        Call<ValideteCookie> getIsValidCookie(@Url String url);

        @Headers("Content-Type: application/json")
        @POST
        Call<UserModel> login(@Url String url, @Body UserAuth userAuth);

        @Multipart
        @POST
        Call<ResponseBody> register(
                @Url String url,
                @Part MultipartBody.Part username,
                @Part MultipartBody.Part fname,
                @Part MultipartBody.Part lname,
                @Part MultipartBody.Part email,
                @Part MultipartBody.Part password,
                @Part MultipartBody.Part password2,
                @Part MultipartBody.Part formId,
                @Part MultipartBody.Part timestamp,
                @Part MultipartBody.Part wpNonce,
                @Part MultipartBody.Part referer
        );

        @Multipart
        @POST
        Call<MediaModel> createMedia(
                @Url String url,
                @Header("Authorization") String authorization,
                @Part MultipartBody.Part file,
                @Part MultipartBody.Part title,
                @Part MultipartBody.Part caption,
                @Part MultipartBody.Part status
        );

        @Headers("Content-Type: application/json")
        @POST
        Call<WpPostModel> contribute(
                @Url String url,
                @Header("Authorization") String authorization,
                @Body ArtikelPost artikelPost
        );
    }

    private static WPService wpService = null;

    public static WPService getWpService() {
        if (wpService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.WEBSITE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            wpService = retrofit.create(WPService.class);
        }
        return wpService;
    }


}
