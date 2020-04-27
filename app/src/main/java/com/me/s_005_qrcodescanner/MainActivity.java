package com.me.s_005_qrcodescanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.s_005_qrcodescanner.activities.ExcelActivity;
import com.me.s_005_qrcodescanner.activities.ListdataActivity;
import com.me.s_005_qrcodescanner.activities.QRScannerActivity;
import com.me.s_005_qrcodescanner.helpers.DBHelper;
import com.me.s_005_qrcodescanner.helpers.adapters.ExcelExporter;
import com.me.s_005_qrcodescanner.model.DataList;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    Button btnScanBarCode,dataSelect_btn;
    TextView txtResult;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show Logo
        showLogo();

        //check get Intent and getExtra
        go2ListDataPageBtn();

    }  // onCreate


    private void go2ListDataPageBtn(){
        dataSelect_btn = findViewById(R.id.scanQRcodeBtn);
        dataSelect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanIntent = new Intent(MainActivity.this, ListdataActivity.class);
                startActivity(scanIntent);
            }
        });
    }  //go2ListDataPageBtn()

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch (id){
            case R.id.excel_import_btn:
                builder.setTitle(getString(R.string.import_title));
                builder.setMessage(getString(R.string.import_msg));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplication(), getString(R.string.txt_importing_data),Toast.LENGTH_SHORT).show();
                                Intent dataImportPage = new Intent(getApplicationContext(), ExcelActivity.class);
                                startActivity(dataImportPage);
                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                            }
                        });
                //Whenever click Cancel button
                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                break;
            case R.id.excel_delete_btn:
                builder.setTitle(getString(R.string.delete_title));
                builder.setMessage(getString(R.string.delete_msg));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper = new DBHelper(getApplicationContext());
                                dbHelper.deleteAll();
                                Toast.makeText(getApplication(),getString(R.string.delete_complete),Toast.LENGTH_SHORT).show();
                                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            }
                        });
                //Whenever click Cancel button
                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                break;
            case R.id.excel_export_btn:
                dbHelper = new DBHelper(getApplicationContext());
                DataList dataList = dbHelper.getDataList();
                List<String> title = dataList.getTitle();
                List<String> data = dataList.getData();
                List<String> scanned = dataList.getScanned();
                List<String> status = dataList.getStatus();
                List<String> timestamp = dataList.getTimestamp();
                askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,200);
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 200);
                ExcelExporter.export(title,data,scanned,status,timestamp);
                Toast.makeText(getApplicationContext(),"Exported at sdcard/iStoreApp/ISTEEL-checked.xls",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected

    //export excel method
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{permission}, requestCode);
            }
        } else {
//            Toast.makeText(this, permission + " is already granted.",
//                    Toast.LENGTH_SHORT).show();
        }
    }//askForPermission

    //
    private void showLogo(){
        ImageView imgLogo = findViewById(R.id.imgLogo);
        Picasso.get().load(getString(R.string.URL_LOGO)).into(imgLogo);
    }

}  //main class
