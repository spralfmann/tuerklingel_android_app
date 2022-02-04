package com.LP.kom_rtdb;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Abonniert den Topic, für das Empfangen der Benachrichtigungen der Türklingel
        /*
        // Abonniere die Topics für das Türklingeln und den Sicherheitsalarm
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/alarm");

        // Zeigt den aktuellen Device Token für den Firebase Messaging Service an
        TextView mytf = findViewById(R.id.test_text);
        mytf.setText(FirebaseMessaging.getInstance().getToken().toString());
         */

        // Erstelle einen Scheduled Abruf der Datenbankdaten

        Runnable getStatusDatabase  = new Runnable() {
            @Override
            public void run() {
                // Rufe den Status in der Database ab
                Log.println(Log.INFO, "scheduledexecutor","succeed" );
            }
        };

        // rufe den Runnable alle 3 Sekunden auf
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(/* Funktion */ getStatusDatabase,0,3, TimeUnit.SECONDS);

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

        /*
        // Daten einmal auslesen
        DataSnapshot mDatabase = null;
        String userId=null;
        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        */

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
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