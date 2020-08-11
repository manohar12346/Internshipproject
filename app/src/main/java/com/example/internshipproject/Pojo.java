package com.example.internshipproject;

import android.widget.TextView;

public class Pojo  {

    String myday,mymonth,myname,myphone,myuri;





    public String getMyday() {
        return myday;
    }

    public String getMymonth() {
        return mymonth;
    }

    public String getMyname() {
        return myname;
    }


    public String getMyphone() {
        return myphone;
    }

    public String getMyuri() {
        return myuri;
    }

    public Pojo(String mname, String mday, String mmonth, String ph, String imageuri){
        myday=mday;
        mymonth=mmonth;
        myname=mname;
        myphone=ph;
        myuri=imageuri;

    }
    public Pojo(){


    }

}
