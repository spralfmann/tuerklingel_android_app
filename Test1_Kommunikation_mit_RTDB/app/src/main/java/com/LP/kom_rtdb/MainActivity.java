package com.LP.kom_rtdb;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Button knopf = findViewById(R.id.bt_sende);
        knopf.setOnClickListener((View.OnClickListener) this);


        // Abonniert den Topic, für das Empfangen der Benachrichtigungen der Türklingel
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");

        // Zeigt den aktuellen Device Token für den Firebase Messaging Service an
        TextView mytf = findViewById(R.id.test_text);
        mytf.setText(FirebaseMessaging.getInstance().getToken().toString());
         */

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        myRef = database.getReference("status/door");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                TextView textView = findViewById(R.id.test_text);
                textView.setText(value.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void lick(View v){

        // Schreibt in die Firebase RTDB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test/float");
        int a = LocalTime.now().getSecond();
        String s = java.lang.String.valueOf(a);
        myRef.setValue(s);
    }

    public void openthedoor (View v){


    }






}