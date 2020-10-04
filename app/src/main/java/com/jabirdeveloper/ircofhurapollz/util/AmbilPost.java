package com.jabirdeveloper.ircofhurapollz.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;
import com.jabirdeveloper.ircofhurapollz.adapter.SliderAdapter;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.dialog.LoadingDialog;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AmbilPost {

    private Context context;
    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout refreshLayout;
    public List<WpPostModel> artikel = new ArrayList<>();
    public List<WpPostModel> sliderItem = new ArrayList<>();
    private boolean isKategori = false;
    private List<WpPostModel> itemsPopular = new ArrayList<>();
    private List<WpPostModel> itemsPilihan = new ArrayList<>();
    private SharedPreferences settings;

    public AmbilPost(Context context, SwipeRefreshLayout refreshLayout, RecyclerView.Adapter adapter) {
        this.context = context;
        this.refreshLayout = refreshLayout;
        this.adapter = adapter;
        settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static void singlePost(Context context, String postId) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        String url = WPApiUrl.URL_POST + "/" + postId;
        Log.d(TAG, "singlePost: "+url);
        Call<WpPostModel> data = WPConnection.getWpService().getSinglePost(url);
        data.enqueue(new Callback<WpPostModel>() {
            @Override
            public void onResponse(Call<WpPostModel> call, Response<WpPostModel> response) {
                if (response.body() != null) {
                    loadingDialog.tutup();
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("wp_post", response.body());
                    context.startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<WpPostModel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, "Tidak dapat mengambil data", Toast.LENGTH_SHORT).show();
                loadingDialog.tutup();
            }
        });
    }

    public void getPostKategori(List<DaftarArtikel> items, String idKategori) {
        isKategori = true;
        refreshLayout.setRefreshing(true);
        String url = WPApiUrl.POST_BY_CATEGORIES + idKategori + WPApiUrl.DAN_PER_PAGE + AppConfig.LOAD_PER_PAGE;
        Log.d(TAG, "getPostKategori: " + url);
        Call<List<WpPostModel>> getData = WPConnection.getWpService().getPostList(url);
        getData.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    artikel.addAll(response.body());
                    tambahKeAdapter(items);
                } else {
                    Toast.makeText(context, "Tidak ada data", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void getListTerbaru(List<DaftarArtikel> items, int halaman) {
        refreshLayout.setRefreshing(true);
        String url;
        if (halaman == 1) {
            url = WPApiUrl.URL_POST + WPApiUrl.PER_PAGE + AppConfig.LOAD_PER_PAGE;
        } else {
            url = WPApiUrl.URL_POST + WPApiUrl.PAGENATION + halaman + WPApiUrl.PER_PAGE + AppConfig.LOAD_PER_PAGE;
        }
        Log.d(TAG, "singlePost: "+url);
        Call<List<WpPostModel>> getPost = WPConnection.getWpService().getPostList(url);
        getPost.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    artikel.addAll(response.body());
                    tambahKeAdapter(items);
                } else {
                    Toast.makeText(context, "Tidak ada data", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    public void getSearchResult(List<DaftarArtikel> items, String query) {
        String url = WPApiUrl.SEARCH_POST + query;
        Call<List<WpPostModel>> data = WPConnection.getWpService().getPostList(url);
        data.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    artikel.addAll(response.body());
                    tambahKeAdapterSearch(items);
                } else {
                    Toast.makeText(context, "Tidak ada data", Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void tambahKeAdapterSearch(List<DaftarArtikel> items) {
        for (int i = 0; i < artikel.size(); i++) {
            items.add(new DaftarArtikel(artikel.get(i), Constants.TIPE_LIST));
            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }

    private void tambahKeAdapter(List<DaftarArtikel> items) {
        for (int i = 0; i < artikel.size(); i++) {
            if (i == 0){
                items.add(new DaftarArtikel(artikel.get(i), Constants.TIPE_HEADER));
            } else {
                items.add(new DaftarArtikel(artikel.get(i), Constants.TIPE_LIST));
            }
//            adapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }

    private boolean isPilihan(int id) {
        for (int i = 0; i < itemsPilihan.size(); i++) {
            if (id == itemsPilihan.get(i).getId()) {
                return true;
            }
        }
        return false;
    }

    private boolean isMotivasi(List<DaftarArtikel> items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getTipe() == Constants.TIPE_MOTIVASI) {
                return true;
            }
        }
        return false;
    }

    public static void getSlider(final Context context, final SliderAdapter adapter, final List<WpPostModel> items, String idKategori) {
        String url = WPApiUrl.POST_BY_CATEGORIES + idKategori;
        Call<List<WpPostModel>> getData = WPConnection.getWpService().getPostList(url);
        getData.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if (i < 3) {
                            items.add(response.body().get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(context, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPopularPost(List<DaftarArtikel> items) {
        Call<List<WpPostModel>> data = WPConnection.getWpService().getPostList(WPApiUrl.URL_POPULAR_POST);
        data.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                assert response.body() != null;
                if (response.body().size() != 0) {
                    itemsPopular.addAll(response.body());
                    String kat = settings.getString("kategori", "na");
                    if (kat.equals("na")) {
                        tambahKeAdapter(items);
                    } else {
//                        getKategoriPilihan(kat, items);
                    }
                } else {
                    Log.e(TAG, "Tidak ada post populer");
                }
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "Failed to get popular post: ", t);
            }
        });
    }

    public void getKategoriPilihan(List<DaftarArtikel> items) {
        refreshLayout.setRefreshing(true);
        String id = settings.getString("kategori", "na");
        String url = WPApiUrl.POST_BY_CATEGORIES + id + WPApiUrl.DAN_PER_PAGE + AppConfig.LOAD_PER_PAGE;
        Call<List<WpPostModel>> getData = WPConnection.getWpService().getPostList(url);
        getData.enqueue(new Callback<List<WpPostModel>>() {
            @Override
            public void onResponse(Call<List<WpPostModel>> call, Response<List<WpPostModel>> response) {
                if (response.body() != null && response.body().size() != 0) {
                    itemsPilihan.addAll(response.body());
                    items.add(new DaftarArtikel(Constants.TIPE_MOTIVASI));
                    items.add(new DaftarArtikel(itemsPilihan, Constants.TIPE_KATEGORI));
                    items.add(new DaftarArtikel());
                    adapter.notifyDataSetChanged();
//                    tambahKeAdapter(items);
                } else {
                    Toast.makeText(context, "Tidak ada data", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<WpPostModel>> call, Throwable t) {
                Log.e(TAG, "Failed to get kategori: ", t);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private boolean isExist(List<WpPostModel> items, String postId) {
        for (int i = 0; i < items.size(); i++) {
            if (postId.equals(String.valueOf(items.get(i).getId()))) {
                return false;
            }
        }
        return true;
    }

}
