package com.lp.spring_first_combine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Variables for the values of status in the Realtime Database
    public boolean door, alarm, lock;
    public String latestRing, latestAlarm;
    // Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    // Tool for Database
    StatusActivity statusActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set Listener on Values of Status
        setListener();
    }

    // function for open door button
    public void btClickOpenDoor(android.view.View view){
        try {
            if (!lock){
                toggleStatusValue(lock, "/status/lockopen");
            }
        }
        catch (Exception e){
            // Error
        }
    }
    // function for alarm switch
    public void switchToggleAlarm(android.view.View view){
        try {
            toggleStatusValue(alarm, "/status/alarmon");
        }
        catch (Exception e){
            // Error
        }
    }
    // function for setting listener on rt db status
    public void setListener(){
        // and references of status in the rt db
        DatabaseReference doorRef = database.getReference("/status/door");
        DatabaseReference alarmRef = database.getReference("/status/alarmon");
        DatabaseReference lockRef = database.getReference("/status/lockopen");
        DatabaseReference latestRingRef = database.getReference("/status/latestring");
        DatabaseReference latestAlarmRef = database.getReference("/status/latestalarm");
        // DataChange Listener on status values in the rt db
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
                        opener.setText("opening...");
                        opener.setBackgroundColor(0xAA11FF22);
                    }
                    else {
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
        latestRingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    latestRing = dataSnapshot.getValue(String.class);
                    TextView viewLastRing = findViewById(R.id.latestring_textview);
                    viewLastRing.setText(stringInMyDateFormat(latestRing));
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
        latestAlarmRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    latestAlarm = dataSnapshot.getValue(String.class);
                    TextView viewLastRing = findViewById(R.id.latestalarm_textview);
                    viewLastRing.setText(stringInMyDateFormat(latestAlarm));
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
    // function for making an viewable format for a date out of the rt db
    public String stringInMyDateFormat(String rtdbString){
        return rtdbString.substring(0,2)+"."+rtdbString.substring(2,4)+"."+rtdbString.substring(4,8)+" "+rtdbString.substring(8,10)+":"+rtdbString.substring(10,12);
    }

    public void toggleStatusValue(Boolean currentvalue, String path){
        try{
            DatabaseReference toggleRef = database.getReference(path);
            if (currentvalue){
                toggleRef.setValue(false);
            }
            else{
                toggleRef.setValue(true);
            }
        }
        catch (Exception e){
            // Fehler
        }
    }

}