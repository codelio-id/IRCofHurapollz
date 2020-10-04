package com.jabirdeveloper.ircofhurapollz.sqlite;

import android.provider.BaseColumns;

public class BookmarkContract {
    public BookmarkContract() {
    }

    public static class BookmarkEntry implements BaseColumns {
        public static final String TABLE_NAME = "bookmark_list";
        public static final String COLUMN_POST_ID = "post_id";
        public static final String COLUMN_POST_TITLE = "post_title";
        public static final String COLUMN_POST_AUTHOR = "post_author";
        public static final String COLUMN_POST_THUMBNAIL = "post_thumbnail";
        public static final String COLUMN_POST_DATE = "post_date";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
