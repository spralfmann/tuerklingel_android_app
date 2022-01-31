package com.LP.kom_rtdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        // subscribe the topic of ring
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");

        TextView mytf = findViewById(R.id.tokendp);
        mytf.setText(FirebaseMessaging.getInstance().getToken().toString());



    }

    public void lick(View v){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test/float");
        int a = LocalTime.now().getSecond();
        String s = java.lang.String.valueOf(a);
        myRef.setValue(s);
        
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");

    }






}