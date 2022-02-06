package com.lp.spring_first_combine;

import android.widget.Button;

public class ColorActivity extends MainActivity{

    public static int atnormal = 0xFF4A61E0;
    public static int toset = 0xFFDB2F07;
    public static int[] idButtons = {
            R.id.dooropener,R.id.refreshpicture,R.id.requestpicture};
    public Boolean alertstatus;
    // constructors
    public ColorActivity(Boolean alertstat){
        alertstatus = alertstat;
    }
    public void setColorButtons(){
        for (int i = 0; i<3; i++) {
            Button btn = findViewById(idButtons[i]);
            if (alertstatus){
                btn.setBackgroundColor(atnormal);
            }
            else{
                btn.setBackgroundColor(toset);
            }
        }
    }
}
