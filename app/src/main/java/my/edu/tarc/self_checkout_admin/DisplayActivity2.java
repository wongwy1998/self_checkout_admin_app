package my.edu.tarc.self_checkout_admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DisplayActivity2 extends AppCompatActivity {

    public TextView txtProductDiscount,txtProductName, txtProductDescription, txtProductPrice, txtProductQty, txtProductWeight, txtProductCat;
    public ImageView ProductImageView;
    public TextView txtProductBarcode,txtProductZone,txtProductShelf;
    public Button btnEdit,btnD;
    String barcodeID;
    DatabaseReference ProductRef1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);


        barcodeID = getIntent().getStringExtra("barcodeID");
        ProductRef1 = FirebaseDatabase.getInstance().getReference().child("Products").child(barcodeID);

        txtProductBarcode = (TextView) findViewById(R.id.product_Barcode5);
        ProductImageView = (ImageView) findViewById(R.id.product_image5);
        txtProductName = (TextView) findViewById(R.id.product_name5);
        txtProductDescription = (TextView) findViewById(R.id.product_description5);
        txtProductPrice = (TextView) findViewById(R.id.product_price5);
        txtProductQty = (TextView) findViewById(R.id.product_CurrentQty5);
        txtProductWeight = (TextView) findViewById(R.id.product_weight5);
        txtProductDiscount = (TextView) findViewById(R.id.product_discount5);
        txtProductCat = (TextView) findViewById(R.id.product_Category5);
        txtProductZone =(TextView) findViewById(R.id.product_zone5);
        txtProductShelf =(TextView) findViewById(R.id.product_shelf5);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnD =(Button) findViewById(R.id.btnD) ;
        DisplayProductDetail();


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity2.this , EditProductActivity.class );
                intent.putExtra("barcodeID",barcodeID);
                startActivity(intent);

            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteProduct(barcodeID);
            }
        });




    }

    private void DisplayProductDetail() {
        ProductRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String pname = dataSnapshot.child("Name").getValue().toString();
                    String pimage = dataSnapshot.child("Image").getValue().toString();
                    String pdesc = dataSnapshot.child("Desc").getValue().toString();
                    String pqty = dataSnapshot.child("CurrentStock").getValue().toString();
                    String pprice = dataSnapshot.child("Price").getValue().toString();
                    String pcategory = dataSnapshot.child("Category").getValue().toString();
                    String pweight = dataSnapshot.child("Weight").getValue().toString();
                    String pdiscount = dataSnapshot.child("Discount").getValue().toString();
                    String pzone2 = dataSnapshot.child("Zone").getValue().toString();
                    String pshelf2 = dataSnapshot.child("Shelf").getValue().toString();

                    txtProductBarcode.setText(barcodeID);
                    txtProductName.setText(pname);
                    double pd= Double.parseDouble(pdiscount);
                    txtProductDiscount.setText((pd*100)+"%");
                    txtProductWeight.setText(pweight);
                    txtProductDescription.setText(pdesc);
                    txtProductQty.setText(pqty);
                    txtProductPrice.setText(pprice);
                    txtProductCat.setText(pcategory);
                    txtProductZone.setText(pzone2);
                    txtProductShelf.setText(pshelf2);
                    Picasso.get().load(pimage).into(ProductImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void DeleteProduct(String barcodeID){
        ProductRef1.child(barcodeID).removeValue();

    }
}



