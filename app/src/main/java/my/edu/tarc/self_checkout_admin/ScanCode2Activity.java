package my.edu.tarc.self_checkout_admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;




import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode2Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannnerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code2);



        ScannnerView = new ZXingScannerView(this);
        setContentView(ScannnerView);

        if (ContextCompat.checkSelfPermission(ScanCode2Activity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)

            ActivityCompat.requestPermissions(ScanCode2Activity.this, new String[]{Manifest.permission.CAMERA}, 1888);


    }


        @Override
        public void handleResult (Result result){

            String barcode = result.getText();

                    Intent intent = new Intent(ScanCode2Activity.this, DisplayActivity2.class);
                    intent.putExtra("barcodeID", barcode);
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
