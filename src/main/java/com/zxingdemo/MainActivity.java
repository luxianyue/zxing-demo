package com.zxingdemo;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(this);

        mTextView1 = findViewById(R.id.tv1);
    }

    @Override
    public void onClick(View view) {
        //new IntentIntegrator(this).initiateScan();
        IntentIntegrator integrator = new IntentIntegrator(this);
        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCaptureActivity(ScanActivity.class);
        //integrator.setPrompt("请扫描"); //扫码时底部的提示文字，设为""可以置空
        integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK); //前置或者后置摄像头
        //integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
        //integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    //回调获取扫描得到的条码值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_LONG).show();
            } else {
                mTextView1.setText(result.getContents());
                Toast.makeText(this, "扫描成功，条码值: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
