package com.LP.kom_rtdb;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Status {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference().getRoot();

    // Verwalte den Firebase Database /Status
    private final String pathdoor = "/status/door";
    private final String pathtriggeropen = "/status/triggeropen";

    public void setdoortrigger (){
        // setzte Variable in RTDB auf eins
        if (true){
            myRef = database.getReference(pathtriggeropen);
            myRef.setValue(1);
        }
        else{
            //
        }

        // graue den Button x Sekunden aus

    }

    public String getdoorstat (){
        // Lese den Datenbankwert für die

        return "";
    }




    }



}
