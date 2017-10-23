package com.zxingdemo;

import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by bulefin on 2017/8/31.
 */

public class ScanActivity extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.custom_capture);
        return (DecoratedBarcodeView)findViewById(R.id.zxing_custom_barcode_scanner);
    }
}
