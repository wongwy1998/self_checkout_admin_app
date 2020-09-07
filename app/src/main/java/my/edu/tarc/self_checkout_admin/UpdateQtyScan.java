package my.edu.tarc.self_checkout_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class UpdateQtyScan extends Fragment implements ZXingScannerView.ResultHandler {

    ZXingScannerView ScannnerView;

    public UpdateQtyScan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.activity_update_qty_scan, container, false);
        ScannnerView = new ZXingScannerView(getActivity());
        getActivity().setContentView(ScannnerView);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1888);

        return view;
    }

    @Override
    public void handleResult(Result result) {
        String barcode = result.getText();

        Intent intent = new Intent(getActivity(), Update_Quantity.class);
        intent.putExtra("barcodeID", barcode);
        startActivity(intent);
//        Bundle bundle = new Bundle();
//        bundle.putString("barcode", barcode);
//        Update_Quantity_Stock up= new Update_Quantity_Stock();
//        up.setArguments(bundle);
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.container, up)
//                .commit();


      getActivity().onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();

        ScannnerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();

        ScannnerView.setResultHandler(this);
        ScannnerView.startCamera();
    }
}
