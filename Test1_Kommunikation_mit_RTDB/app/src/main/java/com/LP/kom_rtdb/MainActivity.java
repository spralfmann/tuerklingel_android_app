package com.LP.kom_rtdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Button knopf = findViewById(R.id.bt_sende);
        knopf.setOnClickListener((View.OnClickListener) this);

         */
    }


    public void lick(View v){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test/float");
        int a = LocalTime.now().getSecond();
        String s = java.lang.String.valueOf(a);
        myRef.setValue(s);



    }




}