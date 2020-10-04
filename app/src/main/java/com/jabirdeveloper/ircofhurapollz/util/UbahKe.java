package com.jabirdeveloper.ircofhurapollz.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UbahKe {

    private static final String LIST_SEPARATOR = ",";

    public static String listKeString (List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(LIST_SEPARATOR);
        }
        sb.setLength(sb.length() - LIST_SEPARATOR.length());
        return sb.toString();
    }

    public static ArrayList<String> stringKeList(String s){
        return new ArrayList<>(Arrays.asList(s.split(LIST_SEPARATOR)));
    }

    public static String capitalize(String input){
        String[] katas = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<katas.length; i++){
            String kata = katas[i];
            if (i>0 && kata.length()>0){
                builder.append(" ");
            }
            String cap = kata.substring(0,1).toUpperCase() + kata.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getTimeAgo(Date date) {
        long time = date.getTime();
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = currentDate().getTime();
        if (time > now || time <= 0) {
            return "baru saja";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "barusan";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return " 1 menit";
        } else if (diff < 60 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " menit lalu";
        } else if (diff < 2 * HOUR_MILLIS) {
            return "1 jam lalu";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " jam lalu";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "kemarin";
        } else if (diff < 3 * DAY_MILLIS){
            return diff / DAY_MILLIS + " hari lalu";
        } else {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat th = new SimpleDateFormat("yyyy");
            int tahun = Integer.parseInt(th.format(date));
//            if (Calendar.getInstance().get(Calendar.YEAR) == tahun){
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd MMMM, HH:mm");
//                return format.format(date);
//            } else {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
                return df.format(date);
//            }
        }
    }

}
