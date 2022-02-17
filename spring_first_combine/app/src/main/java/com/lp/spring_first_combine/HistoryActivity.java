package com.lp.spring_first_combine;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryActivity extends Activity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<Ring> arrayList = new ArrayList<>();
    ArrayAdapter<Ring> arrayAdapter;
    Ring ring;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        /*
        ring = new Ring();
        listView = (ListView) findViewById(R.id.listviewrings);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("rings");

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<Ring>(this, R.layout.ring_info,R.id.ringInfo,arrayList);
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

         */
    }
}
