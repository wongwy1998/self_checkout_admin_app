package my.edu.tarc.self_checkout_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanReceiptActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    ZXingScannerView ScannnerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_receipt);



        ScannnerView = new ZXingScannerView(this);
        setContentView(ScannnerView);

        if (ContextCompat.checkSelfPermission(ScanReceiptActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)

            ActivityCompat.requestPermissions(ScanReceiptActivity.this, new String[]{Manifest.permission.CAMERA}, 1888);


    }


    @Override
    public void handleResult (Result result){

        String transactionID = result.getText();

        Intent intent = new Intent(ScanReceiptActivity.this, DisplayReceiptActivity.class);
        intent.putExtra("transactionID", transactionID);
        startActivity(intent);



        onBackPressed();
    }

    @Override
    protected void onPause () {
        super.onPause();

        ScannnerView.stopCamera();
    }

    @Override
    protected void onResume () {
        super.onResume();

        ScannnerView.setResultHandler(this);
        ScannnerView.startCamera();
    }




}
