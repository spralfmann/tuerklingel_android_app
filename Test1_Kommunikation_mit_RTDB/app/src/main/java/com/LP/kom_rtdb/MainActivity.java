package com.LP.kom_rtdb;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Abonniert den Topic, für das Empfangen der Benachrichtigungen der Türklingel
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");


    }

    public void click_download(View v) throws IOException {

        ImageView imageView = findViewById(R.id.image);

        StorageReference storageReference = storage.getReferenceFromUrl("gs://tuerklingel-a0ba8.appspot.com/pictures/Testfoto.png");

        //islandRef = storageRef.child("images/island.jpg");

        File localFile = File.createTempFile("images", "jpg");


        Glide.with(this /* context */)
                .load(storageReference)
                .into(imageView);

    }


    // Button zum Schreiben in die Firebase Realtime Database
    public void lick(View v){

        // Schreibt in die Firebase RTDB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test/float");
        int a = LocalTime.now().getSecond();
        String s = java.lang.String.valueOf(a);
        myRef.setValue(s);
    }






}