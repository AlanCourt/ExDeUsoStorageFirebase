package com.example.iossenac.exdeusostoragefirebase;

import android.app.Application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by iossenac on 24/06/17.
 */

public class ControlApp extends Application {

    public static StorageReference storageRef;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://exdeusostoragefirebase-e46df.appspot.com");

    }
}
