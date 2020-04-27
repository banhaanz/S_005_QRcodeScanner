package com.me.s_005_qrcodescanner.helpers.publics;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {
    public static String getTimestamp(){
        //get time
        String ts = java.text.DateFormat.getTimeInstance().format(new Date());

        //get date
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c);
        return date +", "+ ts ;
    }
}
