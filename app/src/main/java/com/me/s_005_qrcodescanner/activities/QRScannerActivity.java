package com.me.s_005_qrcodescanner.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.me.s_005_qrcodescanner.MainActivity;
import com.me.s_005_qrcodescanner.R;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class QRScannerActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    Button btnAction;
    Button btnTest;
    String intentData = "";
    boolean isEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner);

        //initial views
        initView();
    }

    private void initView(){
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        btnAction = findViewById(R.id.btnAction);

        //add test_btn for testing some functions
        btnTestClicked();

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentData.length() > 0) {
                    if (isEmail) {
                    } else {
                        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
                        Intent homeIntent = new Intent(QRScannerActivity.this, ListdataActivity.class);
                        //homeIntent.putExtra("data",intentData);
                        homeIntent.putExtra("QRcode","test");
                        homeIntent.putExtra("status",1);
                        startActivity(homeIntent);
                        finish();
                    }
                } //if intentData
            } //onClick
        }); //btnAction.setOnClickListener
    } // initView

    public void btnTestClicked(){
        btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(QRScannerActivity.this,MainActivity.class);
                homeIntent.putExtra("data","https://tinyurl.com/y5eaeflm");
                homeIntent.putExtra("status",1);
                startActivity(homeIntent);
                finish();
            }
        });
    }  //btnTestClicked()

    private void initDetectorAndSource(){
        Toast.makeText(getApplicationContext(),"Barcode scanner started",Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(1920,1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if(ActivityCompat.checkSelfPermission(QRScannerActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(surfaceView.getHolder());
                    }else {
                        ActivityCompat.requestPermissions(QRScannerActivity.this, new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION);
                    }

                }catch (IOException e){
                    e.printStackTrace();
                    Log.d("testScanner","Error => " + e.toString());
                }
            }  //surfaceCreated

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }  //surfaceChanged

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }  //urfaceDestroyed
        }); //surfaceView.getHolder().addCallback()

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "Stopped barcode scanner", Toast.LENGTH_SHORT).show();
            }  //release

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if(barcodes.size() != 0){
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            if(barcodes.valueAt(0).email != null){
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                btnAction.setText("ADD CONTENT TO THE MAIL");
                            }else {
                                isEmail = false;
                                btnAction.setText("GET DATA");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);
                            }

                        }
                    }); //txtBarcodeValue.post
                } //if barcodes.size
            }
        });  ////barcodeDetector.setProcessor

    } //initDetectorAndSource

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    } // onPause()

    @Override
    protected void onResume() {
        super.onResume();
        initDetectorAndSource();
    } //onResume()
} //Main class
