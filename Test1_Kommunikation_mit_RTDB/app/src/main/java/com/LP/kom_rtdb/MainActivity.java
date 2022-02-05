package com.LP.kom_rtdb;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Status Variables
    public boolean door = false;
    private boolean alarm = false;
    private boolean lock = false;
    Activity_Status act = new Activity_Status();

    // Create on Start of the App
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
        //Log.println(Log.INFO, "scheduledexecutor","succeed" );

        // Setzte Referenzen auf den Status von Tür und Alarm

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference doorRef = database.getReference("/status/door");
        DatabaseReference alarmRef = database.getReference("/status/alarmon");
        DatabaseReference lockRef = database.getReference("/status/lockopen");

        // DataChange Listener  auf Status der Realtime Database

        doorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    door = dataSnapshot.getValue(Boolean.class);
                    TextView doorstatusview = findViewById(R.id.doorstatus);
                    if (door){
                        doorstatusview.setText("Tür:   offen");
                    }
                    else {
                        doorstatusview.setText("Tür:   geschlossen");
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
                    TextView alarmstatusview = findViewById(R.id.alarmstatus);
                    Switch alarmswitch = findViewById(R.id.switchalarmon);
                    if (alarm){
                        alarmstatusview.setText("Alarm: eingeschaltet");
                        alarmswitch.setChecked(true);
                    }
                    else {
                        alarmstatusview.setText("Alarm: ausgeschaltet");
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
                    TextView lockstatusview = findViewById(R.id.lockstatus);
                    Button opener = findViewById(R.id.dooropener);
                    if (lock){
                        lockstatusview.setText("Lock:   öffnet...");
                        opener.setText("öffnet...");
                        opener.setBackgroundColor(0xAAFF1122);
                    }
                    else {
                        lockstatusview.setText("Lock:   geschlossen");
                        opener.setText("Tür öffnen");
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







        // Executive Runnable um einen regelmäßigen Aufruf zu realisieren
        /*
        Runnable getStatusDatabase  = new Runnable() {
            @Override
            public void run() {
                // Rufe den Status in der Database ab
                Log.println(Log.INFO, "scheduledexecutor","succeed" );

                FirebaseDatabase database = null;
                DatabaseReference doorRef = database.getReference("/status/door");
                doorRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });

            }
        };


        // rufe den Runnable alle 3 Sekunden auf
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(getStatusDatabase,0,3, TimeUnit.SECONDS);
        */

        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //doorRef.setValue("Hello, World!");

        /*
        doorRef = database.getReference("status/door");
        doorRef.addValueEventListener(new ValueEventListener() {
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

         */


        // Daten einmal auslesen
        /*
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

    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void lick(View v){

        // Schreibt in die Firebase RTDB
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("test/float");
        int a = LocalTime.now().getSecond();
        String s = java.lang.String.valueOf(a);
        myRef.setValue(s);



    }

    public void dooropener (View v){

    }

     */

    // Toggle Alarm Status in Realtime Database via Switch Alarm
    /*
    Ändert den Zustand der Variable Alarm in der Realtim Database, wenn dieser dort geändert wird,
    wird der Zustand des Switches durch den DataChangeListener geändert
     */
    public void toggleAlarm(android.view.View view){
        try {
            act.toggleStatusValue(alarm, "/status/alarmon");
        }
        catch (Exception e){
            // Error
        }
    }
    // Sends open signal to Realtime Database for ESP to open the lock for a time
    public  void openDoor(android.view.View view){
        try {
            act.toggleStatusValue(lock, "/status/lockopen");
        }
        catch (Exception e){
            // Error
        }
    }


}