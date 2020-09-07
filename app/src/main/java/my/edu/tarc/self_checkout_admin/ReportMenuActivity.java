package my.edu.tarc.self_checkout_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.core.Repo;

public class ReportMenuActivity extends Fragment {

    ImageButton btnReport,btnCust,btnsummary,btnScanReceipt;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_report_menu, container, false);

        btnReport = (ImageButton)root.findViewById(R.id.btnViewReport);
        btnCust = (ImageButton)root.findViewById(R.id.btnCustomerT);
        btnScanReceipt = (ImageButton) root.findViewById(R.id.btnScanReceipt);
        btnsummary =(ImageButton)root.findViewById(R.id.btnSummaryReport);

        btnCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CustomerTranActivity cta = new CustomerTranActivity();
                fragmentTransaction.replace(R.id.fragment_container,cta).commit();

            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ReportActivity ra = new ReportActivity();
                fragmentTransaction.replace(R.id.fragment_container,ra).commit();
            }
        });

        btnsummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SummaryReport sr = new SummaryReport();
                fragmentTransaction.replace(R.id.fragment_container,sr).commit();
            }
        });

        btnScanReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScanReceiptActivity.class);
                startActivity(i);
            }
        });
        return root;
    }
}
