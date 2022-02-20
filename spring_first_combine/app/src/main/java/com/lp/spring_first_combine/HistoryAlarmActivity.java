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

public class HistoryAlarmActivity extends Activity {
    FirebaseDatabase database1;
    DatabaseReference databaseReference1;
    ListView listView1;
    ArrayList<Ring> arrayList1 = new ArrayList<>();
    ArrayAdapter<Ring> arrayAdapter1;
    Ring ring1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_alarms);

        ring1 = new Ring();
        listView1 = (ListView) findViewById(R.id.listviewalarm);

        database1 = FirebaseDatabase.getInstance();
        databaseReference1 = database1.getReference("alarm");

        arrayList1 = new ArrayList<>();
        arrayAdapter1 = new ArrayAdapter<Ring>(this, R.layout.ring_info,R.id.ringInfo,arrayList1);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList1.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ring1 = ds.getValue(Ring.class);
                    arrayList1.add(ring1);
                }
                listView1.setAdapter(arrayAdapter1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
