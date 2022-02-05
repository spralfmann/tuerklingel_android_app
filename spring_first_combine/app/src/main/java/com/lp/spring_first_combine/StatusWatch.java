package com.lp.spring_first_combine;

import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatusWatch extends MainActivity{

    // Variables for the values of status in the Realtime Database
    public boolean door, alarm, lock;

    // Database and references of status in the rt db
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    // DataChange Listener on status values in the rt db
    public void setListener(){
        DatabaseReference doorRef = database.getReference("/status/door");
        DatabaseReference alarmRef = database.getReference("/status/alarmon");
        DatabaseReference lockRef = database.getReference("/status/lockopen");

        doorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    door = dataSnapshot.getValue(Boolean.class);
                    TextView doorstatusview = findViewById(R.id.doorstatus);
                    if (door){
                        doorstatusview.setText("open");
                    }
                    else {
                        doorstatusview.setText("closed");
                    }
                }
                catch (Exception e){
                    Log.println(Log.INFO,"EventListener","Falscher Datentyp");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        alarmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    alarm = dataSnapshot.getValue(Boolean.class);
                    //TextView alarmstatusview = findViewById(R.id.alarmstatus);
                    Switch alarmswitch = findViewById(R.id.alarmswitch);
                    if (alarm){
                        //alarmstatusview.setText("Alarm: eingeschaltet");
                        alarmswitch.setChecked(true);
                    }
                    else {
                        //alarmstatusview.setText("Alarm: ausgeschaltet");
                        alarmswitch.setChecked(false);
                    }
                }
                catch (Exception e){
                    Log.println(Log.INFO,"EventListener","Falscher Datentyp");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        lockRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    lock = dataSnapshot.getValue(Boolean.class);
                    //TextView lockstatusview = findViewById(R.id.lockstatus);
                    Button opener = findViewById(R.id.dooropener);
                    if (lock){
                        //lockstatusview.setText("opening...");
                        opener.setText("opening...");
                        opener.setBackgroundColor(0xAAFF1122);
                    }
                    else {
                        //lockstatusview.setText("Lock:   geschlossen");
                        opener.setText("Open door");
                        opener.setBackgroundColor(0xFF4AA7E0);
                    }
                }
                catch (Exception e){
                    Log.println(Log.INFO,"EventListener","Falscher Datentyp");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


    }



}
