package com.jabirdeveloper.ircofhurapollz.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.jabirdeveloper.ircofhurapollz.sqlite.BookmarkContract.BookmarkEntry;

public class BookmarkDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hurapollz.db";
    private static final int version = 1;

    public BookmarkDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, version);
    }

    public void hapusBookmark(String postId){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + BookmarkEntry.TABLE_NAME + " WHERE " + BookmarkEntry.COLUMN_POST_ID + "= '" + postId + "'");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " +
                BookmarkEntry.TABLE_NAME + " (" +
                BookmarkEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BookmarkEntry.COLUMN_POST_ID + " TEXT NOT NULL, " +
                BookmarkEntry.COLUMN_POST_TITLE + " TEXT NOT NULL, " +
                BookmarkEntry.COLUMN_POST_AUTHOR + " TEXT NOT NULL, " +
                BookmarkEntry.COLUMN_POST_THUMBNAIL + " TEXT NOT NULL, " +
                BookmarkEntry.COLUMN_POST_DATE + " TEXT NOT NULL, " +
                BookmarkEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BookmarkEntry.TABLE_NAME);
        onCreate(db);
    }
}
