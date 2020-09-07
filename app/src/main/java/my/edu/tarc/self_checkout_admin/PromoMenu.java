package my.edu.tarc.self_checkout_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PromoMenu extends Fragment {

    ImageButton btnaddPromo, btndisPromo;

    public PromoMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.activity_promo_menu, container, false);
        btnaddPromo = (ImageButton) view.findViewById(R.id.btnaddPromo);
        btndisPromo = (ImageButton) view.findViewById(R.id.btndisPromo);


        btnaddPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddPromoCodeFragment add = new AddPromoCodeFragment();
                fragmentTransaction.replace(R.id.fragment_container,add).commit();
            }
        });

        btndisPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Display_Promo_code dis = new Display_Promo_code();
                fragmentTransaction.replace(R.id.fragment_container,dis).commit();
            }
        });

        return view;
    }
}