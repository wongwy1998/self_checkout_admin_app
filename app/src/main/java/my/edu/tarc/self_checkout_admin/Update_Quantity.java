package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Update_Quantity extends AppCompatActivity {
    TextView txtName,txtBarcode,txtcurrent;
    ImageView txtPic;
    EditText updateQty;
    DatabaseReference ProductRef2;
    String name3,barcode3,pic3;
    int currentQty3;
    int opening3;
    String updateQ1;
    int Q,Q1;
    int upQ;
    String bar;
    Button btnC1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__quantity);

        bar = getIntent().getStringExtra("barcodeID");

        ProductRef2 = FirebaseDatabase.getInstance().getReference().child("Products").child(bar);


        txtBarcode= (TextView)findViewById(R.id.product_Barcode2);
        updateQty = (EditText) findViewById(R.id.updateQty);
        btnC1 = (Button) findViewById(R.id.btnc1) ;


        ProductRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    barcode3 = dataSnapshot.child("Barcode").getValue().toString();
                    currentQty3 = Integer.parseInt(dataSnapshot.child("CurrentStock").getValue().toString());
                    opening3 = Integer.parseInt(dataSnapshot.child("OpeningStock").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtBarcode.setText(bar);

        btnC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }


    public void update() {

        updateQ1 = updateQty.getText().toString();

        upQ = Integer.parseInt(updateQ1);
        Q = currentQty3 + upQ;
        Q1 = opening3 + upQ;

        if (TextUtils.isEmpty(updateQ1)) {
            Toast.makeText(Update_Quantity.this, "Please fill in the Quantity", Toast.LENGTH_SHORT).show();

        } else {

            ProductRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ProductRef2.child("CurrentStock").setValue(Q);
                    ProductRef2.child("OpeningStock").setValue(Q1);

                    Toast.makeText(Update_Quantity.this, "Update quantity successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Update_Quantity.this, HomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
}
