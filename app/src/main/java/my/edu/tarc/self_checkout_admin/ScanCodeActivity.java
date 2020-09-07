package my.edu.tarc.self_checkout_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;


import com.google.zxing.Result;

import java.util.Scanner;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannnerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannnerView = new ZXingScannerView(this);
        setContentView(ScannnerView);

        if (ContextCompat.checkSelfPermission(ScanCodeActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)

            ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[] {Manifest.permission.CAMERA},1888);



    }

    @Override
    public void handleResult(Result result) {
        AddProductFragment.inputBarcode.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        ScannnerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ScannnerView.setResultHandler(this);
        ScannnerView.startCamera();
    }
}

