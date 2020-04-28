package com.me.s_005_qrcodescanner.activities;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.me.s_005_qrcodescanner.MainActivity;
import com.me.s_005_qrcodescanner.R;
import com.me.s_005_qrcodescanner.helpers.DBHelper;
import com.me.s_005_qrcodescanner.helpers.adapters.ExcelImportAdapter;
import com.me.s_005_qrcodescanner.helpers.publics.AppUtils;
import com.me.s_005_qrcodescanner.model.DataSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelActivity extends AppCompatActivity {
    Workbook workbook;
    AsyncHttpClient asyncHttpClient;
    List<String> timestamp,scanned,status,number,storyTitle,storyContent,thumbImages;
    RecyclerView recyclerView;
    ExcelImportAdapter adapter;
    ProgressBar progressBar;
    Button back2homeBtn;
    TextView wait;
    private DBHelper dbHelper;
    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);

        //map views
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        wait = findViewById(R.id.wait);

        //show scan QRCode/Barcode btn
        back2homeBtn();

        String host = AppUtils.URL_HOST_DATA;
        String URL = AppUtils.DATA_EXCEL_FILE;
        final String image = AppUtils.URL_IMG_UNCHECKED;
        number = new ArrayList<>();
        storyTitle = new ArrayList<>();
        storyContent = new ArrayList<>();
        thumbImages = new ArrayList<>();
        scanned = new ArrayList<>();
        status = new ArrayList<>();
        timestamp = new ArrayList<>();

        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Toast.makeText(ExcelActivity.this, getString(R.string.error_download_excel), Toast.LENGTH_SHORT).show();
                wait.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                WorkbookSettings ws = new WorkbookSettings();
                ws.setGCDisabled(true);
                if(file != null){
                    //text.setText("Success, DO something with the file.");
                    wait.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    try {
                        workbook = Workbook.getWorkbook(file);
                        Sheet sheet = workbook.getSheet(0);

                        for(int i = 1;i< sheet.getRows();i++){
                            Cell[] row = sheet.getRow(i);
                            /* 1
                            storyTitle.add(row[0].getContents());
                            storyContent.add(row[1].getContents());
                            thumbImages.add(row[2].getContents());
                            number.add(row[3].getContents()+i);
                            status.add(row[4].getContents());
                             */
                            if(!row[1].getContents().isEmpty() || !row[2].getContents().isEmpty()) {
                                storyTitle.add(row[1].getContents());
                                storyContent.add(row[2].getContents());
                                thumbImages.add(image);
                                number.add("" + i);
                                scanned.add("0");
                                status.add("0");
                                timestamp.add("0");
                            }
                        }

                        //record to DB
                        dbHelper = new DBHelper(getApplicationContext());
                        DataSource dataSource = new DataSource();
                        for(int i=0;i<storyTitle.size();i++){
                            dataSource.setTitle(storyTitle.get(i));
                            dataSource.setData(storyContent.get(i));
                            dataSource.setUrl(thumbImages.get(i));
                            dataSource.setNumber(number.get(i));
                            dataSource.setScanned(scanned.get(i));
                            dataSource.setStatus(status.get(i));
                            dataSource.setTimestamp(timestamp.get(i));

                            dbHelper.addDataSource(dataSource);
                        }

                        //dbHelper.clearTable(DataSource.TABLE);
                        showData();
                    } catch (IOException | BiffException e) {
                        e.printStackTrace();
                    }
                }
            }  //onSuccess
        }); // asyncHttpClient.get
    } //onCreate

    private void showData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExcelImportAdapter(this,number,storyTitle,thumbImages,storyContent,status,scanned,timestamp);
        recyclerView.setAdapter(adapter);
    } //showData

    private void back2homeBtn(){
        back2homeBtn = findViewById(R.id.back2homeBtn);
        back2homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }  //back2homeBtn()
}  //Main class

