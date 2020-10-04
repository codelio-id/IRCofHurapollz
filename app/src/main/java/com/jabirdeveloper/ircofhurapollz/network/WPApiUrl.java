package com.jabirdeveloper.ircofhurapollz.network;

import static com.jabirdeveloper.ircofhurapollz.app.AppConfig.WEBSITE_URL;

public class WPApiUrl {
    public static final String BASE_URL = WEBSITE_URL + "wp-json/wp/v2/";
    public static final String BASE_URL_LOGIN = WEBSITE_URL + "wp-json/jwt-auth/v1/token/";
    public static final String WP_TAB_CATEGORIES = WEBSITE_URL + "wp-json/menus/v1/menus/";
    public static final String URL_POST = BASE_URL + "posts";
    public static final String URL_CATEGORIES = BASE_URL + "categories";
    public static final String URL_PAGES = BASE_URL + "pages";
    public static final String URL_TAGS = BASE_URL + "tags";
    public static final String ON_POST = "?post=";
    public static final String SEARCH_IN = "?search=";
    public static final String POST_BY_CATEGORIES = URL_POST + "?categories=";
    public static final String URL_CATEGORIES_OF_POST = URL_CATEGORIES + ON_POST;
    public static final String SEARCH_POST = URL_POST + SEARCH_IN;
    public static final String SEARCH_CATEGORIES = URL_CATEGORIES + SEARCH_IN;
    public static final String SEARCH_PAGES = URL_PAGES + SEARCH_IN;
    public static final String URL_TAGS_OF_POST = URL_TAGS + ON_POST;
    public static final String PAGENATION = "?page=";
    public static final String PER_PAGE = "?per_page=";
    public static final String DAN_PER_PAGE = "&per_page=";
    public static final String URL_POPULAR_POST = WEBSITE_URL + "wp-json/wordpress-popular-posts/v1/popular-posts/";

    // Url Firebase Cloud Messaging
    public static final String FCM_SUBCRIBE = WEBSITE_URL + "wp-json/pd/fcm/subscribe";
    public static final String FCM_UNSUBSCRIBE = WEBSITE_URL + "wp-json/pd/fcm/unsubscribe";

}


