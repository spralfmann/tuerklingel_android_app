package com.LP.kom_rtdb;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Button knopf = findViewById(R.id.bt_sende);
        knopf.setOnClickListener((View.OnClickListener) this);

         */

        // Abonniert den Topic, für das Empfangen der Benachrichtigungen der Türklingel
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");

        // Zeigt den aktuellen Device Token für den Firebase Messaging Service an
        TextView mytf = findViewById(R.id.tokendp);
        mytf.setText(FirebaseMessaging.getInstance().getToken().toString());
    }

    public void lick(View v){

        // Schreibt in die Firebase RTDB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test/float");
        int a = LocalTime.now().getSecond();
        String s = java.lang.String.valueOf(a);
        myRef.setValue(s);
    }






}