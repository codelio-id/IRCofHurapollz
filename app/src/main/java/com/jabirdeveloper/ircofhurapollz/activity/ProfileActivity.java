package com.jabirdeveloper.ircofhurapollz.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.R;

import java.io.InputStream;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileActivity extends AppCompatActivity {

    private WebView konten;
    private SwipeRefreshLayout refreshLayout;
    private int nightMode;
    private ValueCallback<Uri[]> imagePathCallback;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorBgRefreshLayout));
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        // setting webview
        konten = findViewById(R.id.webview_login);
        konten.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                injectCss();
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        konten.loadUrl(getIntent().getStringExtra("url"));
        konten.getSettings().setAllowFileAccess(true);
        konten.getSettings().setLoadsImagesAutomatically(true);
        konten.getSettings().setJavaScriptEnabled(true);
        konten.setHorizontalScrollBarEnabled(false);
        konten.setVerticalScrollBarEnabled(true);
        konten.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (imagePathCallback != null) {
                    imagePathCallback.onReceiveValue(null);
                }
                imagePathCallback = filePathCallback;
                Intent i = fileChooserParams.createIntent();
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                try {
                    startActivityForResult(Intent.createChooser(i, "Pilih Gambar"), 12);
                } catch (Exception e){
                    Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                return true;
            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            String urlSkrng = konten.getUrl();
            konten.loadUrl(urlSkrng);
        });

    }

    private void injectCss() {
        try {
            InputStream inputStream;
            if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                inputStream = getAssets().open("css/styles.dark.css");
            } else {
                inputStream = getAssets().open("css/styles.css");
            }
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encodeCSS = Base64.encodeToString(buffer, Base64.NO_WRAP);
            String css = "javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + encodeCSS + "');" +
                    "parent.appendChild(style)" +
                    "})()";
            konten.loadUrl(css);
            Log.e(TAG, "injectCss: " + css);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 12 && resultCode == RESULT_OK && imagePathCallback != null) {
            Uri[] results = null;
            String dataString = data.getDataString();
            if (dataString != null) {
                results = new Uri[]{Uri.parse(dataString)};
                Toast.makeText(ProfileActivity.this, String.valueOf(data.getData()), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Gambar: " + data.getData());
            }
            imagePathCallback.onReceiveValue(results);
            imagePathCallback = null;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
