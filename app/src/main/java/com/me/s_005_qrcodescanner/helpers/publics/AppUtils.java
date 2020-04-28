package com.me.s_005_qrcodescanner.helpers.publics;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {
    //public static final String URL_HOST_DATA = "https://banhaanz.github.io/resources";
    public static final String URL_HOST_DATA = "https://siwakornthinyai.github.io/Excel_istock";

    public static final String URL_IMG_CHECKED = URL_HOST_DATA + "/img/GIcoil-1.jpg";
    public static final String URL_IMG_UNCHECKED = URL_HOST_DATA + "/img/GIcoil.jpg";

    public static final String DATA_EXCEL_FILE = URL_HOST_DATA + "/ISTEEL.xls";


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
