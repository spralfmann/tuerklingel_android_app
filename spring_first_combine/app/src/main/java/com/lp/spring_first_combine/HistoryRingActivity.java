package com.lp.spring_first_combine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("rings");

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Ring>(this, R.layout.event_overview,R.id.EventInfo,arrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ring = ds.getValue(Ring.class);
                    arrayList.add(ring);
                }
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // navigate back to main view, activated by back button in top corner
    public void backToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
