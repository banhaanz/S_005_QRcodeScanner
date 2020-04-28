package com.me.s_005_qrcodescanner.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.me.s_005_qrcodescanner.MainActivity;
import com.me.s_005_qrcodescanner.R;
import com.me.s_005_qrcodescanner.helpers.DBHelper;
import com.me.s_005_qrcodescanner.helpers.adapters.ExcelImportAdapter;
import com.me.s_005_qrcodescanner.helpers.publics.AppUtils;
import com.me.s_005_qrcodescanner.model.DataList;
import com.me.s_005_qrcodescanner.model.DataSource;

import java.util.ArrayList;
import java.util.List;

public class ListdataActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DataList dataList;
    List<String> id,title,data,url,number,scanned,status1,timestamp;
    ExcelImportAdapter adapter;
    ProgressBar progressBar;
    DataSource dataSource;
    DBHelper dbHelper;
    TextView wait;
    String QRCode;
    int status;
    Button btnScanBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata);

        //map views
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        wait = findViewById(R.id.wait);

        dbHelper = new DBHelper(this);
        dataList = dbHelper.getDataList();

        //get Extra from other Intents
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            QRCode = bundle.getString("QRcode");
            status = bundle.getInt("status");
            Toast.makeText(getApplication(), status+" / "+QRCode,Toast.LENGTH_SHORT).show();
        }

        //Get data from database to show
        if(dataList != null) {
            wait.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            //id = new ArrayList<>();
            title = new ArrayList<>();
            data = new ArrayList<>();
            url = new ArrayList<>();
            number = new ArrayList<>();
            scanned = new ArrayList<>();
            status1 = new ArrayList<>();
            timestamp = new ArrayList<>();

            title = dataList.getTitle();
            data = dataList.getData();
            url = dataList.getUrl();
            number = dataList.getNumber();
            scanned = dataList.getScanned();
            status1 = dataList.getStatus();
            timestamp = dataList.getTimestamp();

            dataSource = new DataSource();
            if(url != null) {
                for (int i = 0; i < url.size(); i++) {
                    dataSource.setTitle(title.get(i));
                    dataSource.setData(data.get(i));
                    dataSource.setUrl(url.get(i));
                    dataSource.setNumber(number.get(i));
                    dataSource.setScanned(scanned.get(i));
                    dataSource.setStatus(status1.get(i));
                    dataSource.setTimestamp(AppUtils.getTimestamp());
                } //for

                showData(); //show all data list
                checkGetIntent(); //check get value from other Intents
                go2QRScannerPageBtn();
            }else {
                Toast.makeText(getApplication(), getString(R.string.txt_no_data),Toast.LENGTH_SHORT).show();
            }
        }
    } //onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list_data,menu);
        return true;
    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        AlertDialog.Builder builder = new AlertDialog.Builder(ListdataActivity.this);
        switch (id){
            case R.id.show_itemChecked_btn:
                Toast.makeText(getApplication(),"show checked items. ",Toast.LENGTH_SHORT).show();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.show_itemUnchecked_btn:
                Toast.makeText(getApplication(),"Show unchecked items.",Toast.LENGTH_SHORT).show();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
            case R.id.go2home_btn:
                Toast.makeText(getApplication(),"Return to home",Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected


    private void go2QRScannerPageBtn(){
        btnScanBarCode = findViewById(R.id.startScanBtn);
        btnScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanIntent = new Intent(getApplicationContext(), QRScannerActivity.class);
                startActivity(scanIntent);
            }
        });
    }  //go2QRScannerPageBtn()

    private void showData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExcelImportAdapter(this,number,title,url,data,status1,scanned,timestamp);
        recyclerView.setAdapter(adapter);
    } //showData

    @SuppressLint({"SetTextI18n", "ResourceType"})
    public void checkGetIntent(){
        String coilCheck1 = QRCode;
        String statusCheck = String.valueOf(status);
        if(coilCheck1 != null){
            //String coilCheck = coilCheck1.substring(3,14).trim();
            String[] coilCheck2 = coilCheck1.trim().split("\\s+");
            String coilCheck = coilCheck2[0];

            int o = 0; //counter number

            //Loop for checking List which QRCode = title data
            for(int i=0; i<dataList.getData().size();i++){
                if(coilCheck.equals("捆包："+dataList.getData().get(i))){
                    String img_checked = AppUtils.URL_IMG_CHECKED;
                    //get ID of update data
                    String id = dataList.getId().get(i);

                    //set and update URL check status image
                    List<String> img = dataList.getUrl();
                    img.set(i,img_checked);
                    String img2 = dataList.getUrl().get(i);

                    //set and update scanned?
                    List<String> scanned = dataList.getScanned();
                    if(scanned.get(i).equals("0")){
                        scanned.set(i,statusCheck);
                    }else {
                        int a = Integer.parseInt(statusCheck);
                        int b = Integer.parseInt(scanned.get(i));
                        int c = a+b;
                        scanned.set(i,String.valueOf(c));
                    }
                    String scanned2 = dataList.getScanned().get(i);

                    //get Status of update data
                    List<String> e = dataList.getStatus();
                    //update data
                    e.set(i,statusCheck);
                    //re=assign Status value
                    String b = dataList.getStatus().get(i);


                    //set Status value & update DB
                    dataSource.setUrl(img2);
                    dataSource.setStatus(b);
                    dataSource.setScanned(scanned2);
                    dataSource.setTimestamp(AppUtils.getTimestamp());
                    dbHelper.updateDataSource(dataSource,id);

                    Toast.makeText(getApplication(), coilCheck + " : OK / ID=" + id,Toast.LENGTH_LONG).show();
                    o++; //if 'OK', counter number(o) +1

                }
            }
            if(o==0){
                Toast.makeText(getApplication(), coilCheck + " : NO OK",Toast.LENGTH_SHORT).show();
            }
        }
    } //checkGetIntent()
}
