package com.jabirdeveloper.ircofhurapollz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.dialog.KomentarUserDialog;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;
import com.jabirdeveloper.ircofhurapollz.sqlite.BookmarkDBHelper;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;
import com.jabirdeveloper.ircofhurapollz.util.AmbilKategori;
import com.jabirdeveloper.ircofhurapollz.util.UbahKe;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.jabirdeveloper.ircofhurapollz.sqlite.BookmarkContract.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DetailActivity extends AppCompatActivity implements KomentarUserDialog.KomentarUserListener {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private WpPostModel post;
    private WebView konten, webViewKomentar;
    private int nightMode;
    private BookmarkDBHelper dbHelper;
    private SQLiteDatabase database;
    private ImageButton btnBookmark;
    private TextView judul, nama;
    private boolean isBookmarked;

    private TextInputEditText inputKomen;
    private boolean isLogin = false;
    private KomentarUserDialog komentarUserDialog = new KomentarUserDialog();
    private String komentar;
    private AdLoader adLoader;
    private UnifiedNativeAdView iklanNative;
    private List<String> testDevice = new ArrayList<>();
    private AlertDialog dialogRandomPost;

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dbHelper = new BookmarkDBHelper(this);
        database = dbHelper.getWritableDatabase();
        nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        post = (WpPostModel) getIntent().getSerializableExtra("wp_post");

        testDevice.add(getString(R.string.admob_test_device));
        iklanNative = findViewById(R.id.iklan_native);
        loadIklan();

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        AmbilKategori.getData(this, toolbar, post.getCategories().get(0).toString());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        judul = findViewById(R.id.tv_judul);
        nama = findViewById(R.id.tv_nama_penulis);
        TextView publish = findViewById(R.id.tv_publish);
        judul.setText(post.getTitle().getRendered());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(AppConfig.DATE_FORMAT);
        try {
            Date date = df.parse(post.getDate());
            assert date != null;
            publish.setText(UbahKe.getTimeAgo(date));
        } catch (ParseException e) {
            e.printStackTrace();
            publish.setText(post.getDate());
        }

        AmbilInfo.author(this, nama, post.getLinks().getAuthor().get(0).getHref());

        ImageView thumbnail = findViewById(R.id.iv_image);
        Picasso.get()
                .load(post.getFeaturedMediaUrl())
                .placeholder(R.drawable.logo)
                .fit()
                .centerCrop()
                .into(thumbnail);

        // setting webview konten
        konten = findViewById(R.id.webView);
//        konten.addJavascriptInterface(new WebInterface(), "INTERFACE");
        konten.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                injectCSSKonten();
                view.setVisibility(View.VISIBLE);
                iklanNative.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
//                konten.loadUrl("javascript:window.INTERFACE.cekLogin(document.getElementById('commentform').innerHTML)");
            }

        });
        konten.loadUrl(post.getLink());
        konten.getSettings().setUserAgentString(WebSettings.getDefaultUserAgent(this));
        konten.getSettings().setDomStorageEnabled(true);
        konten.getSettings().setAppCacheEnabled(true);
        konten.getSettings().setLoadsImagesAutomatically(true);
        konten.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        konten.getSettings().setJavaScriptEnabled(true);
        konten.getSettings().setDefaultTextEncodingName("utf-8");
        konten.setOnTouchListener((view, motionEvent) -> (motionEvent.getAction() == MotionEvent.ACTION_MOVE));
        // setting webview komentar
        webViewKomentar = findViewById(R.id.webView_komentar);
        webViewKomentar.addJavascriptInterface(new WebInterface(), "INTERFACE");
        webViewKomentar.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                injectCSSKomentar();
                view.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
                webViewKomentar.loadUrl("javascript:window.INTERFACE.cekLogin(document.getElementById('commentform').innerHTML)");
            }

        });
        webViewKomentar.loadUrl(post.getLink());
        webViewKomentar.getSettings().setUserAgentString(WebSettings.getDefaultUserAgent(this));
        webViewKomentar.getSettings().setDomStorageEnabled(true);
        webViewKomentar.getSettings().setAppCacheEnabled(true);
        webViewKomentar.getSettings().setLoadsImagesAutomatically(true);
        webViewKomentar.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webViewKomentar.getSettings().setJavaScriptEnabled(true);
        webViewKomentar.getSettings().setDefaultTextEncodingName("utf-8");
        webViewKomentar.setOnTouchListener((view, motionEvent) -> (motionEvent.getAction() == MotionEvent.ACTION_MOVE));

        // Bagian bawah
        inputKomen = findViewById(R.id.input_komentar);
        btnBookmark = findViewById(R.id.btn_bookmark);
        ImageButton btnBagikan = findViewById(R.id.btn_bagikan);
        ImageButton btnKomen = findViewById(R.id.btn_komen);

        btnBagikan.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, post.getLink());
            startActivity(share);
        });

        isBookmarked = isBookmark(String.valueOf(post.getId()));
        if (isBookmarked) {
            btnBookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
            btnBookmark.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }
        btnBookmark.setOnClickListener(v -> {
            if (isBookmarked) {
                hapusBookmark();
            } else {
                tambahBookmark();
            }
        });

        inputKomen.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                btnBookmark.setVisibility(View.GONE);
                btnBagikan.setVisibility(View.GONE);
                btnKomen.setVisibility(View.VISIBLE);
            } else {
                btnKomen.setVisibility(View.GONE);
                btnBagikan.setVisibility(View.VISIBLE);
                btnBookmark.setVisibility(View.VISIBLE);
            }
        });

        btnKomen.setOnClickListener(v -> {
            komentar = inputKomen.getText().toString();
            if (!TextUtils.isEmpty(komentar)) {
                if (isLogin){
                    webViewKomentar.loadUrl("javascript:document.getElementById('comment').value = \"" + komentar + "\";document.getElementById('submit').click();");
                    inputKomen.setText("");
                } else {
                    Bundle b = new Bundle();
                    b.putString("komentar", komentar);
                    komentarUserDialog.tampilkan(getSupportFragmentManager(), b);
                }
            } else {
                Toast.makeText(this, "Komentar tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void injectCSSKonten() {
        try {
            InputStream isCss;
            if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                isCss = getAssets().open("css/styles.dark.css");
            } else {
                isCss = getAssets().open("css/styles.css");
            }
            byte[] bCss = new byte[isCss.available()];
            isCss.read(bCss);
            isCss.close();
            String encodeCSS = Base64.encodeToString(bCss, Base64.NO_WRAP);
            String css = "javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + encodeCSS + "');" +
                    "parent.appendChild(style)" +
                    "})()";
            String hapusTagP = "javascript:(function() {" +
                    "var pTags = document.getElementsByTagName('p');" +
                    "for (i=0; i<pTags.length; i++){" +
                    "if(pTags[i].querySelector('img') !== null){" +
                    "pTags[i].outerHTML = pTags[i].innerHTML;}}" +
//                    "var imgTags = document.getElementsByTagName('img');"+
//                    "for(i=0;i<imgTags.length;i++){" +
//                    "var dt = new Date();var url = imgTags[i].src;imgTags[i].src = url;}" +
                    "})()";
            konten.loadUrl(css);
            konten.loadUrl(hapusTagP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void injectCSSKomentar() {
        try {
            InputStream isCss;
            if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                isCss = getAssets().open("css/styles.komentar.dark.css");
            } else {
                isCss = getAssets().open("css/styles.komentar.css");
            }
            byte[] bCss = new byte[isCss.available()];
            isCss.read(bCss);
            isCss.close();
            String encodeCSS = Base64.encodeToString(bCss, Base64.NO_WRAP);
            String css = "javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + encodeCSS + "');" +
                    "parent.appendChild(style)" +
                    "})()";
            webViewKomentar.loadUrl(css);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tambahBookmark() {
        ContentValues cv = new ContentValues();
        cv.put(BookmarkEntry.COLUMN_POST_ID, String.valueOf(post.getId()));
        cv.put(BookmarkEntry.COLUMN_POST_TITLE, post.getTitle().getRendered());
        cv.put(BookmarkEntry.COLUMN_POST_AUTHOR, nama.getText().toString());
        cv.put(BookmarkEntry.COLUMN_POST_THUMBNAIL, post.getFeaturedMediaUrl());
        cv.put(BookmarkEntry.COLUMN_POST_DATE, post.getDate());
        database.insert(BookmarkEntry.TABLE_NAME, null, cv);
        btnBookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        btnBookmark.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        isBookmarked = true;
    }

    private void hapusBookmark() {
        dbHelper.hapusBookmark(String.valueOf(post.getId()));
        btnBookmark.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        btnBookmark.setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        isBookmarked = false;
    }

    private boolean isBookmark(String s) {
        String query = "select * from " + BookmarkEntry.TABLE_NAME + " where " + BookmarkEntry.COLUMN_POST_ID + "=\"" + s + "\"";
        Cursor c = database.rawQuery(query, null);
        return c.getCount() != 0;
    }

    private void loadIklan() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_native));
        adLoader = builder.forUnifiedNativeAd(unifiedNativeAd -> {
            if (!adLoader.isLoading()) {
                if (unifiedNativeAd != null) {
                    iklanNative.setMediaView(iklanNative.findViewById(R.id.ad_media));
                    iklanNative.setHeadlineView(iklanNative.findViewById(R.id.ad_headline));
                    iklanNative.setBodyView(iklanNative.findViewById(R.id.ad_body));
                    iklanNative.setCallToActionView(iklanNative.findViewById(R.id.ad_call_to_action));
                    iklanNative.setIconView(iklanNative.findViewById(R.id.ad_icon));
                    iklanNative.setPriceView(iklanNative.findViewById(R.id.ad_price));
                    iklanNative.setStarRatingView(iklanNative.findViewById(R.id.ad_stars));
                    iklanNative.setStoreView(iklanNative.findViewById(R.id.ad_store));
                    iklanNative.setAdvertiserView(iklanNative.findViewById(R.id.ad_advertiser));

                    // Some assets are guaranteed to be in every UnifiedNativeAd.
                    ((TextView) iklanNative.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
                    ((TextView) iklanNative.getBodyView()).setText(unifiedNativeAd.getBody());
                    ((Button) iklanNative.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());

                    // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
                    // check before trying to display them.
                    NativeAd.Image icon = unifiedNativeAd.getIcon();

                    if (icon == null) {
                        iklanNative.getIconView().setVisibility(View.INVISIBLE);
                    } else {
                        Picasso.get().load(icon.getUri()).fit().centerCrop().into(((ImageView) iklanNative.getIconView()));
                        iklanNative.getIconView().setVisibility(View.VISIBLE);
                    }

                    if (unifiedNativeAd.getPrice() == null) {
                        iklanNative.getPriceView().setVisibility(View.INVISIBLE);
                    } else {
                        iklanNative.getPriceView().setVisibility(View.VISIBLE);
                        ((TextView) iklanNative.getPriceView()).setText(unifiedNativeAd.getPrice());
                    }

                    if (unifiedNativeAd.getStore() == null) {
                        iklanNative.getStoreView().setVisibility(View.INVISIBLE);
                    } else {
                        iklanNative.getStoreView().setVisibility(View.VISIBLE);
                        ((TextView) iklanNative.getStoreView()).setText(unifiedNativeAd.getStore());
                    }

                    if (unifiedNativeAd.getStarRating() == null) {
                        iklanNative.getStarRatingView().setVisibility(View.INVISIBLE);
                    } else {
                        ((RatingBar) iklanNative.getStarRatingView())
                                .setRating(unifiedNativeAd.getStarRating().floatValue());
                        iklanNative.getStarRatingView().setVisibility(View.VISIBLE);
                    }

                    if (unifiedNativeAd.getAdvertiser() == null) {
                        iklanNative.getAdvertiserView().setVisibility(View.INVISIBLE);
                    } else {
                        ((TextView) iklanNative.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
                        iklanNative.getAdvertiserView().setVisibility(View.VISIBLE);
                    }

                    // Assign native ad object to the native view.
                    iklanNative.setNativeAd(unifiedNativeAd);
                }
            } else {
                iklanNative.setVisibility(View.GONE);
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (!adLoader.isLoading()) {
                    iklanNative.setVisibility(View.GONE);
                    Log.e(TAG, "onAdFailedToLoad: gagal mengambil iklan");
                }
            }
        }).build();
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevice)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onSubmitWithUrl(String nama, String email, String url) {
        webViewKomentar.loadUrl("javascript:document.getElementById('comment').value = \"" + komentar + "\";" +
                "document.getElementById('author').value = \"" + nama + "\";" +
                "document.getElementById('email').value = \"" + email + "\";" +
                "document.getElementById('url').value = \"" + url + "\";" +
                "document.getElementById('submit').click();"
        );
        inputKomen.setText("");
    }

    @Override
    public void onSubmit(String nama, String email) {
        webViewKomentar.loadUrl("javascript:document.getElementById('comment').value = \"" + komentar + "\";" +
                "document.getElementById('author').value = \"" + nama + "\";" +
                "document.getElementById('email').value = \"" + email + "\";" +
                "document.getElementById('submit').click();"
        );
        inputKomen.setText("");
    }

    private class WebInterface {
        @JavascriptInterface
        public void cekLogin(String html) {
            isLogin = html.contains("profile.php");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_profile){
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("Mengambil artikel...");
            builder.setCancelable(false);
            dialogRandomPost = builder.create();
            getRandomPost();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        nightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
    }

    private void getRandomPost() {
        dialogRandomPost.show();
        String url = WPApiUrl.URL_POST + WPApiUrl.PER_PAGE + AppConfig.LOAD_PER_PAGE;
        Call<List<WpPostModel>> getPost = WPConnection.getWpService().getPostList(url);
        getPost.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                if (response.body() != null){
                    Random random = new Random();
                    int posisi = random.nextInt(19);
                    Intent i = new Intent(DetailActivity.this, DetailActivity.class);
                    i.putExtra("wp_post", response.body().get(posisi));
                    startActivity(i);
                }
                dialogRandomPost.dismiss();
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialogRandomPost.dismiss();
                Toast.makeText(DetailActivity.this, "Gagal mengambil artikel", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
