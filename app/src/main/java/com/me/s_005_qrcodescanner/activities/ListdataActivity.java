package com.me.s_005_qrcodescanner.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.me.s_005_qrcodescanner.R;
import com.me.s_005_qrcodescanner.helpers.DBHelper;
import com.me.s_005_qrcodescanner.helpers.adapters.ExcelImportAdapter;
import com.me.s_005_qrcodescanner.model.DataList;
import com.me.s_005_qrcodescanner.model.DataSource;

import java.util.ArrayList;
import java.util.List;

public class ListdataActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DataList dataList;
    List<String> id,title,data,url,number,status1;
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
            status1 = new ArrayList<>();

            title = dataList.getTitle();
            data = dataList.getData();
            url = dataList.getUrl();
            number = dataList.getNumber();
            status1 = dataList.getStatus();

            dataSource = new DataSource();
            if(url != null) {
                for (int i = 0; i < url.size(); i++) {
                    dataSource.setTitle(title.get(i));
                    dataSource.setData(data.get(i));
                    dataSource.setUrl(url.get(i));
                    dataSource.setNumber(number.get(i));
                    dataSource.setStatus(status1.get(i));
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
            case R.id.show_all_btn:
                Toast.makeText(getApplication(),"Show all items",Toast.LENGTH_SHORT).show();
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
        adapter = new ExcelImportAdapter(this,number,title,url,data,status1);
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
            for(int i=0; i<dataList.getTitle().size();i++){
                if(coilCheck.equals("捆包："+dataList.getTitle().get(i))){
                    String img_checked = "https://banhaanz.github.io/resources/img/GIcoil-1.jpg";
                    //get ID of update data
                    String id = dataList.getId().get(i);

                    List<String> img = dataList.getUrl();
                    img.set(i,img_checked);
                    String img2 = dataList.getUrl().get(i);

                    //get Status of update data
                    List<String> e = dataList.getStatus();
                    //update data
                    e.set(i,statusCheck);
                    //re=assign Status value
                    String b = dataList.getStatus().get(i);


                    //set Status value & update DB
                    dataSource.setUrl(img2);
                    dataSource.setStatus(b);
                    dbHelper.updateDataSource(dataSource,id);

                    Toast.makeText(getApplication(), coilCheck + " : OK / Status=" + id,Toast.LENGTH_SHORT).show();
                    o++; //if 'OK', counter number(o) +1

                }
            }
            if(o==0){
                Toast.makeText(getApplication(), coilCheck + " : NO OK",Toast.LENGTH_SHORT).show();
            }
        }
    } //checkGetIntent()
}
