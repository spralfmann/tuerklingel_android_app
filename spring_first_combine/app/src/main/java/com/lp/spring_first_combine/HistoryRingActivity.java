package com.lp.spring_first_combine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryRingActivity extends Activity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    // Activity für die Historie der Klingelanfragen: "activity_history_rings.xml"
    // Daten liegen in der Realtime Database unter: "/rings"
    // Es wird ein ArrayAdapter vom Typ "Ring" angelegt, dieser besteht aus einer ArrayList,
    // einer Layout Datei (event_overview.xml) und einem Layout Element (EventInfo)
    // zunächst werden alle Daten (Chlidren des Pfads "rings") in ein Array aus
    // Ring-Elementen (eigene Klasse "Ring") gelegt, anschließend wird der Adapter bestehend
    // aus Layout-Element in Layout-Datei und der ArrayList als Adapter der Listview gesetzt
    // womit die Elemente in der Listview angezeigt werden. Durch die Verwendung des ValueEventListener
    // werden neue Einträge sofort erkannt und angehängt

    ListView listView;
    ArrayList<Ring> arrayList = new ArrayList<>();
    ArrayAdapter<Ring> arrayAdapter;
    Ring ring;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_rings);

        ring = new Ring();
        listView = (ListView) findViewById(R.id.listviewring);

        // Database
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("rings");

        // ArrayAdapter
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Ring>(this, R.layout.event_overview,R.id.EventInfo, arrayList);

        // Daten auslesen bei Datenänderung
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear(); // Bereinigen des Arrays, dass Elemente nicht doppelt angezeigt werden
                for (DataSnapshot ds: snapshot.getChildren()){
                    ring = ds.getValue(Ring.class);
                    arrayList.add(ring); // Befülle die ArrayList mit den Einträgen
                }
                // setze Adapter in Listview ein
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
