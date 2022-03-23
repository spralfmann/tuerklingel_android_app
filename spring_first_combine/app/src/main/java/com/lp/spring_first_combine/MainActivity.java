package com.lp.spring_first_combine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    // Variablen für die Werte im Status der Realtime Database

        public boolean door, alarm, lock, takePic, alert;
            // door: Zustand der Tür -> Tür ist offen (true) / Tür ist geschlossen (false)
            // alarm: Zustand der Alarmanlage -> Alarmanlage ist scharf (true) / Alarmanlage ist nicht scharf (false)
            // lock: Zustand des Türschlosses -> Türschloss öffnet (true) / Türschloss öffnet nicht (false)
            // takePic: Anfrage, dass vom ESP32 ein Bild gemacht werden soll -> mache ein Bild, bzw. ein Bild wird gemacht (true) / es wird, bzw. soll aktuell kein extra Bild gemacht werden
            // alert: Zustand des Alarms -> es wurde ein Alarm ausgelöst und noch nicht quittiert (true) / es wurde kein Alarm ausgelöst oder bereits quittiert

        public String latestRing, latestAlarm, latestPicture;
            // latestRing: Zeitstempel der letzten Klingelanfrage
            // latestAlarm: Zeitstempel des letzten Alarms
            // latestPicture: Zeitstempel des letzten Bilds

    // Realtime Database initialisieren
        FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setze einen Listener auf die Werte aus Status (Realtime Database)
            setListener();

        // Lade das neueste Bild aus dem Firebase Storage
            getLatestPicture("gs://tuerklingel-a0ba8.appspot.com/pictures/"+"Testfoto"+".jpg");

        // Abonniere die Topics des Firebase Cloud Messaging Service für den Alarm und das Klingeln
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/alarm");
    }

    // AUSLAGERUNGSFUNKTIONEN

        // Setzt Daten-Listener auf die einzelnen Werte des Status
        public void setListener(){

            // Benötigte Referenzen auf die Werte in der Realtime Database
            DatabaseReference doorRef = database.getReference("/status/door");
            DatabaseReference alarmRef = database.getReference("/status/alarmon");
            DatabaseReference lockRef = database.getReference("/status/lockopen");
            DatabaseReference latestRingRef = database.getReference("/status/latestring");
            DatabaseReference latestAlarmRef = database.getReference("/status/latestalarm");
            DatabaseReference latestPictureRef = database.getReference("/status/latestpic");
            DatabaseReference takePictureRef = database.getReference("/status/takepicture");
            DatabaseReference alertRef = database.getReference("/status/alert");

            // DataChange Listener auf die einzelnen Referenzen
            doorRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        // Schreibt den Status des Reetkontakt in das für den Türstatus vorgesehene Textview
                        door = dataSnapshot.getValue(Boolean.class);
                        TextView doorstatusview = findViewById(R.id.doorstatus);
                        if (door){
                            doorstatusview.setText("Offen");
                        }
                        else {
                            doorstatusview.setText("Geschlossen");
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
                    try {
                        // Zieht aus der Database den hinterlegten Wert über den Status der Alarmanlage
                        // und setzt entsprechend den Switch in die richtige Position
                        alarm = dataSnapshot.getValue(Boolean.class);
                        Switch alarmswitch = findViewById(R.id.alarmswitch);
                        if (alarm){
                            alarmswitch.setChecked(true);
                        }
                        else {
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
                    // Prinzip ähnlich wie bei Alarmanlage, hier wird, wenn sich der Wert in der Database ändert,
                    // der entsprechende Wert ausgelesen und dabei der Knopf zum Tür öffnen geändert
                    // Geändert wird der Wert dabei entweder durch die App, false -> true (Tür soll geöffnet werden)
                    // oder vom ESP32 true -> false (Türöffner öffnet die Tür nicht mehr)
                    try {
                        lock = dataSnapshot.getValue(Boolean.class);
                        Button opener = findViewById(R.id.dooropener);
                        if (lock){
                            opener.setText("öffnet...");
                            opener.setBackgroundColor(0xAA11FF22);
                        }
                        else {
                            opener.setText("Öffne die Tür");
                            opener.setBackgroundColor(0xFF4A61E0);
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
                    // Bildet in das Textview das Datum und die Uhrzeit, wann zuletzt geklingelt wurde
                    // (wird vom ESP32 in die Realtime Database eingetragen)
                    try {
                        latestRing = dataSnapshot.getValue(String.class);
                        TextView viewLastRing = findViewById(R.id.latestring_textview);
                        // Wert aus Database wird in ein anzeigbares Format gewandelt
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
                    // gleiche Funktion wie Value "latestRing", hier jedoch für den letzten Alarm der ausgelöst wurde
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
            latestPictureRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // gleiche Funktion wie Value "latestRing", hier jedoch für das letzte Bild, das aufgenommen wurde
                    // zusätzlich wird, wenn sich der Wert ändert das Bild dazu aus dem Firebase Storage geladen
                    try {
                        latestPicture = dataSnapshot.getValue(String.class);
                        TextView viewLastPic = findViewById(R.id.latestpic_textview);
                        viewLastPic.setText(stringInMyDateFormat(latestPicture));
                        // Laden des Bilds
                        String urlLatestPic = ("gs://tuerklingel-a0ba8.appspot.com/pictures/"+"Testfoto"+".jpg");
                        getLatestPicture(urlLatestPic);
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
            takePictureRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // gleiche Funktionsweise wie "Tür öffnen" , hier um Bild von ESP32 anzufordern
                    try {
                        takePic = dataSnapshot.getValue(Boolean.class);
                        Button requbtn = findViewById(R.id.requestpicture);
                        if (!takePic){
                            requbtn.setText("Mache ein Bild");
                            requbtn.setBackgroundColor(0xFF4A61E0);
                        }
                        else if(takePic){
                            requbtn.setText("Bild wird gemacht...");
                            requbtn.setBackgroundColor(0xAA11DD22);
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
            alertRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // informiert über einen Alarm der vom ESP32 ausgelöst wurde
                    // kann über Button der dann sichtbar ist quittiert werden
                    try {
                        alert = dataSnapshot.getValue(Boolean.class);
                        TextView alertView = findViewById(R.id.alert_textview);
                        Button deactAlert = findViewById(R.id.deactivatealert);
                        // Wird ALarm ausgelöst, werden das Textview und der Button eingeblendet
                        if (alert){
                            alertView.setVisibility(TextView.VISIBLE);
                            deactAlert.setVisibility(TextView.VISIBLE);
                        }
                        // und wieder ausgeblendet, wenn der Alarm wieder ausgeschaltet wurde
                        else {
                            alertView.setVisibility(TextView.INVISIBLE);
                            deactAlert.setVisibility(TextView.INVISIBLE);
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

    // /AUSLAGERUNGSFUNKTIONEN

    // HILFSFUNKTIONEN

        // macht aus dem in der Realtime Database verwendeten Datumsformat ein anzeigbares Format
        public String stringInMyDateFormat(String rtdbString){
            // zieht die einzelnen Daten auseinander
            // Transformation : DDMMYYYYhhmm -> DD.MM.YYYY hh:mm
            return rtdbString.substring(0,2)+"."+rtdbString.substring(2,4)+"."+rtdbString.substring(4,8)+" "+rtdbString.substring(8,10)+":"+rtdbString.substring(10,12);
        }

        // Lädt ein Bild aus dem Firebase Storage, von einem angegebenen Pfad
        public void getLatestPicture(String path){
            try {
                // Picture download from Firebase Storage
                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Url of the newest picture in storage
                StorageReference gsReference = storage.getReferenceFromUrl(path);
                ImageView mIm = (ImageView) findViewById(R.id.imageView);
                final long ONE_MEGABYTE=1024*1024;
                //https://stackoverflow.com/questions/57152775/how-to-set-an-imageview-with-a-picture-from-firebase-storage
                gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        mIm.setImageBitmap(bitmap);
                    }
                });
            }
            catch (Exception e){
                // Error
            }
        }

        // ändert den Wahrheitswert einer Variable in der Realtime Database, unter Angabe
        // des aktuellen Zustands und des jeweiligen Pfads
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

    // /HILFSFUNKTIONEN

    // KNOPFFUNKTIONEN

        // Funktion für den "Tür öffnen" Knopf
        public void btClickOpenDoor(android.view.View view){
            try {
                if (!lock){ // nur Clickbar, wenn ESP gerade nicht am Öffnen ist
                    toggleStatusValue(lock, "/status/lockopen"); // Ändere den Wert in der Realtime Database
                }
            }
            catch (Exception e){
                // Error
            }
        }

        // Knopf zum Aktualisieren des neuesten Bildes in der ImageView
        public void btClickRefresh(android.view.View view){
            // Lädt das neueste Bild aus dem Storage
            String urlLatestPic = ("gs://tuerklingel-a0ba8.appspot.com/pictures/"+latestPicture+".jpg");
            getLatestPicture(urlLatestPic);
        }

        // Fordert über die Realtime Database ein Bild an
        public void btClickRequestPicture(android.view.View view){
            try {
                if (!takePic){ // nur setzbar, wenn nicht bereits ein Bildrequest gesendet wurde und noch nicht vom ESP32 abgearbeitet wurde
                    toggleStatusValue(takePic ,"/status/takepicture");
                }
            }
            catch (Exception error){
                // error
            }
        }

        // Quittiert den Alarm, der vom ESP32 ausgelöst wurde
        public void btClickDeactivateAlarm(android.view.View view){
            toggleStatusValue(alert ,"/status/alert");
        }

        // Öffnet die Historie von Klingelanfragen oder Alarmen
        public void btHistoryChange(android.view.View view){
            String button_text;
            button_text = ((Button) view).getText().toString();
            if (button_text.equals("Rings")) {
                Intent intent2 = new Intent(this, HistoryRingActivity.class);
                startActivity(intent2);
            }
            else if(button_text.equals("Alarms")){
                Intent intent2 = new Intent(this, HistoryAlarmActivity.class);
                startActivity(intent2);
            }
        }

        // Funktion für den Schalter für den Alarm (eingeschaltet -> true)
        public void switchToggleAlarm(android.view.View view){
            // Funktionsprinzip: bei Clicken toggelt hier die Funktion den Wert in der Realtime Database
            //                   anschließend sorgt der DataChange-Listener dafür, dass der Wert richtig
            //                   in der App angezeigt wird
            try {
                toggleStatusValue(alarm, "/status/alarmon"); // Bei Betätigung, wird der Wert in der Realtime Database geändert
            }
            catch (Exception e){
                // Error
            }
        }

    // /KNOPFFUNKTIONEN
}