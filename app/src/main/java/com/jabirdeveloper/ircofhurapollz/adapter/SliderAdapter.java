package com.jabirdeveloper.ircofhurapollz.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.activity.DetailActivity;
import com.jabirdeveloper.ircofhurapollz.model.SliderModel;
import com.jabirdeveloper.ircofhurapollz.model.wordpress.WpPostModel;
import com.jabirdeveloper.ircofhurapollz.util.AmbilInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<SliderModel> items;

    public SliderAdapter(Context context, List<SliderModel> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider, null);
        ImageView image = view.findViewById(R.id.iv_image);
        TextView title = view.findViewById(R.id.tv_judul);
        title.setText(items.get(position).getItems().getTitle().getRendered());
        Picasso.get()
                .load(items.get(position).getThumbnail())
                .placeholder(R.drawable.logo)
                .fit()
                .centerCrop()
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Image slider loaded");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
        view.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("wp_post", items.get(position).getItems());
            context.startActivity(i);
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

