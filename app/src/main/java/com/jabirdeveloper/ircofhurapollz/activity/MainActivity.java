package com.jabirdeveloper.ircofhurapollz.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.dialog.SettingDialog;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public NavigationView navigationView;
    private EditText inputQuery;
    private ImageButton btnClear;
    public DrawerLayout drawer;
    private Dialog dialogRandomPost;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputQuery = findViewById(R.id.input_query);
        btnClear = findViewById(R.id.btn_clear_text);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        BottomNavigationView navView = findViewById(R.id.nav_view_bawah);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_home, R.id.menu_bookmark, R.id.menu_kontribusi, R.id.menu_setting)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        inputQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String q = s.toString();
                if (!TextUtils.isEmpty(q)) {
                    btnClear.setVisibility(View.VISIBLE);
                    btnClear.getDrawable().setTint(getResources().getColor(R.color.colorPrimary));
                    btnClear.setOnClickListener(v -> inputQuery.setText(""));
                } else {
                    btnClear.setVisibility(View.GONE);
                }
            }
        });
        inputQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                i.putExtra("query", inputQuery.getText().toString());
                startActivity(i);
                return true;
            }
            return false;
        });

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.menu_drawer_buka, R.string.meun_drawer_tutup);
        navigationView.setCheckedItem(R.id.nav_home);
        toolbar.setNavigationOnClickListener(v -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (navView.getSelectedItemId() == R.id.menu_home) {
                Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

        navView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_setting){
                new SettingDialog(MainActivity.this);
                return false;
            }
            navController.navigate(item.getItemId());
            return true;
        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Mengambil artikel...");
            builder.setCancelable(false);
            dialogRandomPost = builder.create();
            getRandomPost();
        }
        return super.onOptionsItemSelected(item);
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
                    Intent i = new Intent(MainActivity.this, DetailActivity.class);
                    i.putExtra("wp_post", response.body().get(posisi));
                    startActivity(i);
                }
                dialogRandomPost.dismiss();
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialogRandomPost.dismiss();
                Toast.makeText(MainActivity.this, "Gagal mengambil artikel", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
