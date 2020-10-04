package com.jabirdeveloper.ircofhurapollz.service;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jabirdeveloper.ircofhurapollz.model.NotifikasiModel;
import com.jabirdeveloper.ircofhurapollz.push.KirimNotifikasi;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Notifikasi extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0){
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(remoteMessage.getData());
            NotifikasiModel nm = gson.fromJson(jsonElement, NotifikasiModel.class);
            KirimNotifikasi notifikasi = new KirimNotifikasi(
                    this,
                    nm.getData().getTitle(),
                    nm.getData().getMessage(),
                    nm.getData().getImage());
            notifikasi.kirim();
        }
    }

}
