package my.edu.tarc.self_checkout_admin;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class ProductFragment extends Fragment {

    ImageButton btnaddp, btndisp, btnseaP,btnScanP;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_product, container, false);

        btnaddp = (ImageButton) root.findViewById(R.id.btnaddpro);
        btndisp = (ImageButton) root.findViewById(R.id.btndispro);
        btnScanP = (ImageButton) root.findViewById(R.id.btnScanProduct);
        btnseaP = (ImageButton) root.findViewById(R.id.btnSearch);

        btnaddp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddProductFragment add1 = new AddProductFragment();
                fragmentTransaction.replace(R.id.fragment_container,add1).commit();
            }
        });

        btndisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               UpdateQtyScan uqs = new UpdateQtyScan();
                fragmentTransaction.replace(R.id.fragment_container,uqs).commit();
            }
        });

        btnseaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                search_product search1 = new search_product();
                fragmentTransaction.replace(R.id.fragment_container,search1).commit();
            }
        });

        btnScanP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanCode2Activity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}

