package my.edu.tarc.self_checkout_admin;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import my.edu.tarc.self_checkout_admin.Model.Product;


public class HomeFragment extends Fragment {

    ImageButton pbtn,pmbtn,lbtn,rbtn;

    @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        pbtn = (ImageButton) root.findViewById(R.id.pbtn);
        pmbtn = (ImageButton) root.findViewById(R.id.pmbtn);
        lbtn = (ImageButton) root.findViewById(R.id.lbtn);
        rbtn = (ImageButton) root.findViewById(R.id.rbtn);


        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new ProductFragment()).commit();

            }
        });

        pmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PromoMenu()).commit();
            }
        });

        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new Location()).commit();
            }
        });

        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new ReportMenuActivity()).commit();
            }
        });

        return root;
    }



}
