package com.lp.spring_first_combine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Variables for the values of status in the Realtime Database
    public boolean door, alarm, lock, takePic, alert;
    public String latestRing, latestAlarm, latestPicture;
    // Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    // all elements to set red in alert mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Set Listener on Values of Status
        setListener();
        getLatestPicture("gs://tuerklingel-a0ba8.appspot.com/pictures/"+"Testfoto"+".jpg");

        // subscribe the topics of ring and alert
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/ring");
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/alarm");


    }

    // function for open door button
    public void btClickOpenDoor(android.view.View view){
        try {
            if (!lock){
                toggleStatusValue(lock, "/status/lockopen");
            }
        }
        catch (Exception e){
            // Error
        }
    }
    // function for alarm switch
    public void switchToggleAlarm(android.view.View view){
        try {
            toggleStatusValue(alarm, "/status/alarmon");
        }
        catch (Exception e){
            // Error
        }
    }
    // function for setting listener on rt db status
    public void setListener(){
        // and references of status in the rt db
        DatabaseReference doorRef = database.getReference("/status/door");
        DatabaseReference alarmRef = database.getReference("/status/alarmon");
        DatabaseReference lockRef = database.getReference("/status/lockopen");
        DatabaseReference latestRingRef = database.getReference("/status/latestring");
        DatabaseReference latestAlarmRef = database.getReference("/status/latestalarm");
        DatabaseReference latestPictureRef = database.getReference("/status/latestpic");
        DatabaseReference takePictureRef = database.getReference("/status/takepicture");
        DatabaseReference alertRef = database.getReference("/status/alert");
        // DataChange Listener on status values in the rt db
        doorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    door = dataSnapshot.getValue(Boolean.class);
                    TextView doorstatusview = findViewById(R.id.doorstatus);
                    if (door){
                        doorstatusview.setText("open");
                    }
                    else {
                        doorstatusview.setText("closed");
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
                    //TextView alarmstatusview = findViewById(R.id.alarmstatus);
                    Switch alarmswitch = findViewById(R.id.alarmswitch);
                    if (alarm){
                        //alarmstatusview.setText("Alarm: eingeschaltet");
                        alarmswitch.setChecked(true);
                    }
                    else {
                        //alarmstatusview.setText("Alarm: ausgeschaltet");
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
                    //TextView lockstatusview = findViewById(R.id.lockstatus);
                    Button opener = findViewById(R.id.dooropener);
                    if (lock){
                        opener.setText("opening...");
                        opener.setBackgroundColor(0xAA11FF22);
                    }
                    else {
                        opener.setText("Open door");
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    latestRing = dataSnapshot.getValue(String.class);
                    TextView viewLastRing = findViewById(R.id.latestring_textview);
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    latestPicture = dataSnapshot.getValue(String.class);
                    TextView viewLastPic = findViewById(R.id.latestpic_textview);
                    viewLastPic.setText(stringInMyDateFormat(latestPicture));
                    String urlLatestPic = ("gs://tuerklingel-a0ba8.appspot.com/pictures/"+latestPicture+".jpg");
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    takePic = dataSnapshot.getValue(Boolean.class);
                    Button requbtn = findViewById(R.id.requestpicture);
                    if (!takePic){
                        requbtn.setText("Request picture");
                        requbtn.setBackgroundColor(0xFF4A61E0);
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
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    alert = dataSnapshot.getValue(Boolean.class);
                    TextView alertView = findViewById(R.id.alert_textview);
                    Button deactAlert = findViewById(R.id.deactivatealert);
                    ColorActivity ca = new ColorActivity(alert);
                    //ca.setColorButtons();
                    if (alert){
                        alertView.setVisibility(TextView.VISIBLE);
                        deactAlert.setVisibility(TextView.VISIBLE);
                    }
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
    // function for making an viewable format for a date out of the rt db
    public String stringInMyDateFormat(String rtdbString){
        return rtdbString.substring(0,2)+"."+rtdbString.substring(2,4)+"."+rtdbString.substring(4,8)+" "+rtdbString.substring(8,10)+":"+rtdbString.substring(10,12);
    }
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
    // Refresh Button Click to get the newest picture from storage
    public void btClickRefresh(android.view.View view){
        String urlLatestPic = ("gs://tuerklingel-a0ba8.appspot.com/pictures/"+latestPicture+".jpg");
        getLatestPicture(urlLatestPic);
    }
    // set the take picture value to true
    public void btClickRequestPicture(android.view.View view){
        try {
            if (!takePic){
                toggleStatusValue(takePic ,"/status/takepicture");
                Button requbtn = findViewById(R.id.requestpicture);
                requbtn.setText("Taking picture...");
                requbtn.setBackgroundColor(0xAA11DD22);
            }
        }
        catch (Exception error){
            // error
        }
    }
    // deactivate the alarm, set values alarmon and alert to false
    public void btClickDeactivateAlarm(android.view.View view){

    }
    // set picture in imageView in main from path
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

    /*
    public void setColorButtons(){
        for (int i = 0; i<3; i++) {
            Button btn = findViewById(idButtons[i]);
            if (!alert){
                btn.setBackgroundColor(0xFF4A61E0);
            }
            else{
                btn.setBackgroundColor(0xFFDB2F07);
            }
        }
    }
     */
}