package com.jabirdeveloper.ircofhurapollz.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.adapter.BookmarkAdapter;
import com.jabirdeveloper.ircofhurapollz.sqlite.BookmarkContract;
import com.jabirdeveloper.ircofhurapollz.sqlite.BookmarkDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class BawahBookmarkFragment extends Fragment {

    private BookmarkAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private SQLiteDatabase database;

    public BawahBookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bawah_bookmark, container, false);
        // Inflate the layout for this fragment
        init(view);
        return view;
    }

    private void init(View view) {
        BookmarkDBHelper dbHelper = new BookmarkDBHelper(getContext());
        database = dbHelper.getWritableDatabase();
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorBgRefreshLayout));
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        RecyclerView rv = view.findViewById(R.id.recyclerView);
        adapter = new BookmarkAdapter(getContext(), getAllBookmark());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null){
                    final View foregroundView = ((BookmarkAdapter.bookmarkHolder) viewHolder).viewForeground;
                    getDefaultUIUtil().onSelected(foregroundView);
                }
            }

            @Override
            public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((BookmarkAdapter.bookmarkHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((BookmarkAdapter.bookmarkHolder) viewHolder).viewForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((BookmarkAdapter.bookmarkHolder) viewHolder).viewForeground;
                getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeBookmark((long)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(rv);

        getBookmark();
        refreshLayout.setOnRefreshListener(this::getBookmark);

    }

    private void getBookmark() {
        adapter.swapCursor(getAllBookmark());
//        if (!getAllBookmark().moveToFirst()){
//            TextView tv = layoutKosong.findViewById(R.id.tv_pesan_error);
//            ImageView img = layoutKosong.findViewById(R.id.image_error);
//            ImageButton ttp = layoutKosong.findViewById(R.id.btn_tutup);
//            Button btn = layoutKosong.findViewById(R.id.btn_refresh);
//            ttp.setVisibility(View.GONE);
//            btn.setVisibility(View.GONE);
//            tv.setText(R.string.no_bookmark);
//            img.setImageResource(R.drawable.pluto_page_not_found);
//            layoutKosong.setVisibility(View.VISIBLE);
//            JDAnimation.overshoot(tv,getResources().getInteger(android.R.integer.config_shortAnimTime));
//            JDAnimation.overshoot(img,getResources().getInteger(android.R.integer.config_shortAnimTime));
//            JDAnimation.overshoot(btn,getResources().getInteger(android.R.integer.config_shortAnimTime));
//            btn.setOnClickListener(v -> {
//                layoutKosong.setVisibility(View.GONE);
//                getBookmark();
//            });
//        }
        refreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
        if (getAllBookmark().getCount() != 0){
//            layoutKosong.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Bookmark loaded", Toast.LENGTH_SHORT).show();
        }
    }

    private Cursor getAllBookmark(){
        return database.query(
                BookmarkContract.BookmarkEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                BookmarkContract.BookmarkEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    private void removeBookmark(long tag) {
        database.delete(BookmarkContract.BookmarkEntry.TABLE_NAME,
                BookmarkContract.BookmarkEntry._ID + "=" + tag, null);
        Toast.makeText(getContext(), R.string.item_dihapus, Toast.LENGTH_SHORT).show();
        getBookmark();
    }

}
