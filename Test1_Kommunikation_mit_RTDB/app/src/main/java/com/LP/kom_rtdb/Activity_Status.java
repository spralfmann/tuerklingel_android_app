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

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Verwalte den Firebase Database /Status
    private final String pathdoor = "/status/door";

    public void setdoortrigger (){
        if (true){
            // setzte Variable in RTDB auf eins
            String pathtriggeropen = "/status/triggeropen";
            DatabaseReference myRef = database.getReference(pathtriggeropen);
            myRef.setValue(1);

            // graue den Button x Sekunden aus
        }
        else{
            //
        }
    }

    public String getStatus (){
        // Lese den Datenbankwert für die
        return "";
    }

    public void toggleAlarm(){
        // Lese Alarm aus
        String pathalarmon = "/status/alarmon";
        DatabaseReference myRef = database.getReference(pathalarmon);
        boolean alarmstat = true;
        if(alarmstat){
                        
        }
    }

}




