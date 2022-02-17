package com.lp.spring_first_combine;

public class Ring {
    private String raw;
    private String picUrl;

    public Ring(){

    }
    public Ring(String rohdate, String bildurl){
        this.raw = rohdate;
        this.picUrl = bildurl;
    }

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

    public String toString(){
        return stringInDate(this.raw) + " " + stringInTime(this.raw);
    }

    private String stringInDate(String rtdbString){
        return rtdbString.substring(0,2)+"."+rtdbString.substring(2,4)+"."+rtdbString.substring(4,8);
    }

    private String stringInTime(String rtdbString){
        return rtdbString.substring(8,10)+":"+rtdbString.substring(10,12);
    }


}
