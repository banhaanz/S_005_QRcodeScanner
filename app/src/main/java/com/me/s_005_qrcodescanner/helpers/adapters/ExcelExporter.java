package com.me.s_005_qrcodescanner.helpers.adapters;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.me.s_005_qrcodescanner.helpers.DBHelper;
import com.me.s_005_qrcodescanner.model.DataList;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelExporter {
    public static void export(List<String> title, List<String> data, List<String> scanned, List<String> status, List<String> timestamp ){
        File sd = Environment.getExternalStorageDirectory();
        String csvFile = "ISTEEL-checked.xls";
        String foldername = "/iStoreApp";

        File dir = new File(sd.getAbsolutePath()+foldername);

        //create directory if not exists
        if(!dir.isDirectory()){
            dir.mkdirs();
        }
        try{
            File file = new File(dir,csvFile);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale(Locale.ENGLISH.getLanguage(),Locale.ENGLISH.getCountry()));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file,wbSettings);

            //Excel sheetA first sheetA
            WritableSheet sheetA = workbook.createSheet("Steel Checklist",0);

            List<String> col1 = Arrays.asList("NO.","OWNER CODE","COIL NO.","SCANNED","STATUS","LAST SCANNING");
            List<String> row1 = Arrays.asList("row1","row1","row1","row1","row1");

            sheetA.setColumnView(0,6);
            sheetA.setColumnView(1,16);
            sheetA.setColumnView(2,16);
            sheetA.setColumnView(3,12);
            sheetA.setColumnView(4,12);
            sheetA.setColumnView(5,24);


            for(int i=0;i<col1.size();i++){
                sheetA.addCell(new Label(i,0, col1.get(i)));
            }
            for (int i=0;i<title.size();i++){
                sheetA.addCell(new Label(0,i+1,String.valueOf(i+1)));
                sheetA.addCell(new Label(1,i+1,title.get(i)));
                sheetA.addCell(new Label(2,i+1,data.get(i)));
                sheetA.addCell(new Label(3,i+1,scanned.get(i)));
                sheetA.addCell(new Label(4,i+1,status.get(i)));
                sheetA.addCell(new Label(5,i+1,timestamp.get(i)));
            }

            //column and row titles
//            sheetA.addCell(new Label(0,0,"sheet A 1"));
//            sheetA.addCell(new Label(1,0,"sheet A 2"));
//            sheetA.addCell(new Label(0,1,"sheet A 3"));
//            sheetA.addCell(new Label(1,1,"sheet A 4"));

            //Excel sheetB represents second sheet
//            WritableSheet sheetB = workbook.createSheet("sheet B",0);
//
//            //column and row titles
//            sheetB.addCell(new Label(0,0,"sheet B 1"));
//            sheetB.addCell(new Label(1,0,"sheet B 2"));
//            sheetB.addCell(new Label(2,0,"sheet B 3"));
//            sheetB.addCell(new Label(3,0,"sheet B 4"));

            //close workbook
            workbook.write();
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}