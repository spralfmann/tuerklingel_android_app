package com.LP.kom_rtdb;

import static android.content.ContentValues.TAG;

import android.nfc.Tag;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Status {

    // Reference the Realtime Database
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Toggle Bool Variables in Realtime Database with Input of the current value
    // an the path, which contain the value to Change
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

    public String getStatus (){
        // Lese den Datenbankwert für die
        return "";
    }
}




