package com.lp.spring_first_combine;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// own class for communicating with rt db
public class StatusActivity {

    // Reference the Realtime Database
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Toggle Bool Variables in Realtime Database with Input of the current value
    // an the path, which contain the value to Change


    // Set String Value in Realtime Database
    public void setStringValue(String value, String path){
        try{
            DatabaseReference setStringRef = database.getReference(path);
            setStringRef.setValue(value);
        }
        catch (Exception e){
            // Fehler
        }
    }
}

