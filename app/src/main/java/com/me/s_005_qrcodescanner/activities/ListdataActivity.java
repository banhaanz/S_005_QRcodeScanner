package com.me.s_005_qrcodescanner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    List<String> id,title,data,url;
    ExcelImportAdapter adapter;
    ProgressBar progressBar;
    TextView wait;
    Button btnScanBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata);

        //map views
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        wait = findViewById(R.id.wait);

        DBHelper dbHelper = new DBHelper(this);
        dataList = dbHelper.getDataList();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            String QRCode = bundle.getString("QRcode");
            int status = bundle.getInt("status");
            Toast.makeText(getApplication(), status+" / "+QRCode,Toast.LENGTH_SHORT).show();
        }

        if(dataList != null) {
            wait.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            //id = new ArrayList<>();
            title = new ArrayList<>();
            data = new ArrayList<>();
            url = new ArrayList<>();
            title = dataList.getTitle();
            data = dataList.getData();
            url = dataList.getUrl();

            DataSource dataSource = new DataSource();
            if(url != null) {
                for (int i = 0; i < url.size(); i++) {
                    dataSource.setTitle(title.get(i));
                    dataSource.setData(data.get(i));
                    dataSource.setUrl(url.get(i));
                } //for

                showData();
                go2QRScannerPageBtn();
            }else {
                Toast.makeText(getApplication(), getString(R.string.txt_no_data),Toast.LENGTH_SHORT).show();
            }
        }
    }

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
        adapter = new ExcelImportAdapter(this,title,data,url);
        recyclerView.setAdapter(adapter);
    } //showData
}
