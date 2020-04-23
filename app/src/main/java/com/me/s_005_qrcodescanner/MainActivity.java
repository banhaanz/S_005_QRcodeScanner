package com.me.s_005_qrcodescanner;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.s_005_qrcodescanner.activities.ExcelActivity;
import com.me.s_005_qrcodescanner.activities.ListdataActivity;
import com.me.s_005_qrcodescanner.activities.QRScannerActivity;
import com.me.s_005_qrcodescanner.helpers.DBHelper;
import com.me.s_005_qrcodescanner.helpers.publics.AppUtils;
import com.me.s_005_qrcodescanner.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnScanBarCode,dataSelect_btn;
    TextView txtResult;
    List<Product> productList;
    ListView dataList;
    DBHelper dbHelper;
    private int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = findViewById(R.id.dataList);

        //show Logo
        showLogo();

        //check get Intent and getExtra
        checkGetIntent();
        go2ListDataPageBtn();
        go2QRScannerPageBtn();

    }  // onCreate

    private void go2QRScannerPageBtn(){
        btnScanBarCode = findViewById(R.id.testBtn);
        btnScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanIntent = new Intent(MainActivity.this, QRScannerActivity.class);
                startActivity(scanIntent);
            }
        });
    }  //go2QRScannerPageBtn()


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
                Toast.makeText(getApplication(),"Clicked export_btn",Toast.LENGTH_SHORT).show();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected

    @SuppressLint("SetTextI18n")
    public void checkGetIntent(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            txtResult = findViewById(R.id.txtResult);
            String txt = bundle.getString("data");
            int ok = bundle.getInt("status");

            //check text is equal
            for (int i = 0; i < productList.size(); i++) {
                assert txt != null;
                if(txt.equals(productList.get(i).getQrscan())) {
                    productList.get(i).setStatus(ok);
                    txtResult.setText(txt + " : OK \n Status : " + productList.get(i).getStatus() );
                }else {
                    txtResult.setText(txt + " : NO OK\n Status : " + productList.get(i).getStatus());
                } //if else

            }  //for
        }  //if
    }  //checkGetIntent

    //
    private void showLogo(){
        ImageView imgLogo = findViewById(R.id.imgLogo);
        Picasso.get().load(getString(R.string.URL_LOGO)).into(imgLogo);
    }

}  //main class
