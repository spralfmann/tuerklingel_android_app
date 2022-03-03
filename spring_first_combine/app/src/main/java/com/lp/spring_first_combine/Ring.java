package com.lp.spring_first_combine;

// Klasse für ein Ring/Alarm Ereignis aus der Realtime Database
// Wird benötigt um die Einträge aus der Realtime Database in einem
// Array über einen ArrayAdapter in die ListView zu setzen

public class Ring {
    private String raw; // Rohformat des Datums
    private String picUrl; // zugehörige Bild-Url für eventuellen Aufruf über Listview

    // leerer Konstruktor
    public Ring(){
    }

    // vorgeladener Konstruktor
    public Ring(String rohdate, String bildurl){
        this.raw = rohdate;
        this.picUrl = bildurl;
    }

    // Get-Sets
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    // Funktion für Anzeige des Strings in Textview -> DD.MM.YYYY hh:mm
    public String toString(){
        return stringInDate(this.raw) + " " + stringInTime(this.raw);
    }

    // Zieht Datum aus raw -> DD.MM.YYYY
    private String stringInDate(String rtdbString){
        return rtdbString.substring(0,2)+"."+rtdbString.substring(2,4)+"."+rtdbString.substring(4,8);
    }

    // Zieht Uhrzeit aus raw -> hh:mm
    private String stringInTime(String rtdbString){
        return rtdbString.substring(8,10)+":"+rtdbString.substring(10,12);
    }


}
