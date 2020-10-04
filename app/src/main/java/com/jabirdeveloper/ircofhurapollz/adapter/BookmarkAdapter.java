package com.jabirdeveloper.ircofhurapollz.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.app.AppConfig;
import com.jabirdeveloper.ircofhurapollz.util.AmbilPost;
import com.jabirdeveloper.ircofhurapollz.util.UbahKe;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static com.jabirdeveloper.ircofhurapollz.sqlite.BookmarkContract.*;

public class BookmarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public BookmarkAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public class bookmarkHolder extends RecyclerView.ViewHolder {

        private TextView title, publish;
        private ImageView thumbnail;
        public ConstraintLayout viewForeground;

        bookmarkHolder(@NonNull View itemView) {
            super(itemView);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            publish = itemView.findViewById(R.id.tv_publish);
            title = itemView.findViewById(R.id.tv_title);
            thumbnail = itemView.findViewById(R.id.iv_thumbnail);
        }

        void setData(final Cursor cursor, final int position) {
            itemView.setOnClickListener(v -> {
                cursor.moveToPosition(position);
                AmbilPost.singlePost(context, cursor.getString(cursor.getColumnIndex(BookmarkEntry.COLUMN_POST_ID)));
            });

            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(AppConfig.DATE_FORMAT);
            try {
                Date date = df.parse(cursor.getString(cursor.getColumnIndex(BookmarkEntry.COLUMN_POST_DATE)));
                assert date != null;
                publish.setText(UbahKe.getTimeAgo(date));
            } catch (ParseException e) {
                e.printStackTrace();
                publish.setText(cursor.getString(cursor.getColumnIndex(BookmarkEntry.COLUMN_POST_DATE)));
            }
            title.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex(BookmarkEntry.COLUMN_POST_TITLE))));
            Picasso.get().load(cursor.getString(cursor.getColumnIndex(BookmarkEntry.COLUMN_POST_THUMBNAIL)))
                    .placeholder(R.drawable.logo).fit().centerCrop()
                    .into(thumbnail);
            thumbnail.setClipToOutline(true);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bookmark, parent, false);
        return new bookmarkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        long id = cursor.getLong(cursor.getColumnIndex(BookmarkEntry._ID));
        bookmarkHolder vb = (bookmarkHolder) holder;
        vb.setData(cursor,position);
        vb.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor c){
        if (cursor != null){
            cursor.close();
        }
        cursor = c;
        if (c != null){
            notifyDataSetChanged();
        }
    }

}
