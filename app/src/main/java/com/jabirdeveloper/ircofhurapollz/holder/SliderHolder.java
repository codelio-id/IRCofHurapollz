package com.jabirdeveloper.ircofhurapollz.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.SliderAdapter;
import com.jabirdeveloper.ircofhurapollz.model.DaftarArtikel;
import com.jabirdeveloper.ircofhurapollz.model.SliderModel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SliderHolder extends TerbaruBaseHolder {

    private List<SliderModel> itemSlider = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public SliderHolder(@NonNull View itemView) {
        super(itemView);
        viewPager = itemView.findViewById(R.id.view_slider);
        tabLayout = itemView.findViewById(R.id.tab_slider);
    }

    @Override
    public void setData(Context context, DaftarArtikel data, int position) {
        SliderAdapter sliderAdapter = new SliderAdapter(context, itemSlider);
        viewPager.setAdapter(sliderAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (itemSlider.size() == 0){
            for (int i = 0; i < data.getSlider().size(); i++) {
                itemSlider.add(new SliderModel(data.getSlider().get(i),
                        data.getSlider().get(i).getFeaturedMediaUrl()));
                sliderAdapter.notifyDataSetChanged();
                /*if (!isExist(data.getSlider().get(i).getId())){
                    int finalI = i;
                    AmbilInfo.thumbnail(data.getSlider().get(i).getLinks().getFeaturedMedia().get(0).getHref(),
                            data.getSlider().get(i).getLinks().getAuthor().get(0).getHref(),
                            data.getSlider().get(i).getCategories().get(0).toString(),
                            new AmbilInfo.InfoListener() {
                                @Override
                                public void onComplete(String thumbnail, String avatar, String nama, String kategori) {
                                    itemSlider.add(new SliderModel(data.getSlider().get(finalI), thumbnail, avatar, nama));
                                    sliderAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Throwable error) {
                                    Log.e(TAG, "onFailure: ", error);
                                }
                            });
                }*/
            }
        }
        sliderAdapter.notifyDataSetChanged();
    }

    private boolean isExist(int postId){
        for (int i = 0; i < itemSlider.size(); i++) {
            if (postId == itemSlider.get(i).getItems().getId()){
                return true;
            }
        }
        return false;
    }

}
