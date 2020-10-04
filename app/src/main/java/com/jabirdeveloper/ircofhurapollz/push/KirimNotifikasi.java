package com.jabirdeveloper.ircofhurapollz.push;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.app.Aplikasi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class KirimNotifikasi {

    private Context context;
    private String judul, deskripsi, gambar;
    private NotificationManagerCompat notificationManager;
    private boolean isImageReady = false;
    private Bitmap imageBitmap;

    public KirimNotifikasi(Context context, String judul, String deskripsi, String gambar) {
        this.context = context;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        getBitmapImage();
        notificationManager = NotificationManagerCompat.from(context);
    }

    private void getBitmapImage() {
        Picasso.get().load(gambar).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                isImageReady = true;
                imageBitmap = bitmap;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                isImageReady = false;
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                isImageReady = false;
            }
        });
    }

    public void kirim() {
        Notification notification;
        if (isImageReady) {
            notification = new NotificationCompat.Builder(context, Aplikasi.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_irc_notifikasi)
                    .setContentTitle(judul)
                    .setContentText(deskripsi)
                    .setColor(context.getResources().getColor(R.color.colorAccent))
                    .setLargeIcon(imageBitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(imageBitmap)
                            .bigLargeIcon(null))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
        } else {
//            Bitmap gambar = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample1);
            notification = new NotificationCompat.Builder(context, Aplikasi.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_irc_notifikasi)
                    .setContentTitle(judul)
                    .setContentText(deskripsi)
                    .setColor(context.getResources().getColor(R.color.colorAccent))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
        }
        int notifId = (int) System.currentTimeMillis();
        notificationManager.notify(notifId, notification);
    }

}
