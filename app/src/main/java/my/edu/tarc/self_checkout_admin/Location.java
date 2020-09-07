package my.edu.tarc.self_checkout_admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Location extends Fragment {


    private ImageView a, b, c, d, e, f, g, h, i, j;
    private Button alcohol, dairy, meat, produce, seafood;
  //  final DatabaseReference ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child("Barcode").child("Location");
    String loc;

    public Location() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_location, container, false);

        a = (ImageView) view.findViewById(R.id.a);
        b = (ImageView) view.findViewById(R.id.b);
        c = (ImageView) view.findViewById(R.id.c);
        d = (ImageView) view.findViewById(R.id.d);
        e = (ImageView) view.findViewById(R.id.e);
        f = (ImageView) view.findViewById(R.id.f);
        g = (ImageView) view.findViewById(R.id.g);
        h = (ImageView) view.findViewById(R.id.h);
        i = (ImageView) view.findViewById(R.id.i);
        j = (ImageView) view.findViewById(R.id.j);
        alcohol = (Button) view.findViewById(R.id.btnalcohol);
        dairy = (Button) view.findViewById(R.id.dairy);
        meat = (Button) view.findViewById(R.id.meat);
        seafood = (Button) view.findViewById(R.id.btnSeafood);
        produce = (Button) view.findViewById(R.id.produce);


        return view;
    }
    public void onStart() {

        super.onStart();
        alcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "Alcohol";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        dairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "Dairy";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "Meat";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        produce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "Produce";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        seafood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "Seafood";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loc = "A";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "B";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "C";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "D";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "E";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "F";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "G";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "H";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "I";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc = "J";

                Intent intent = new Intent(getActivity(), DisplayActivity3.class);
                intent.putExtra("location", loc);
                startActivity(intent);
            }
        });
    }


}



