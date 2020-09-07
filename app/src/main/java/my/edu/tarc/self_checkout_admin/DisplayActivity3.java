package my.edu.tarc.self_checkout_admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import my.edu.tarc.self_checkout_admin.Model.Product;
import my.edu.tarc.self_checkout_admin.ViewHolder.ProductViewHolder;

public class DisplayActivity3 extends AppCompatActivity {
    DatabaseReference ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
    String zone;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public  FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;
    String shelf,zoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display3);


        zone= getIntent().getStringExtra("location");


        recyclerView = findViewById(R.id.display3Recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(ProductsRef.orderByChild("Zone").equalTo(zone), Product.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, final int position, @NonNull final Product model)
            {
                holder.txtProductName.setText( model.getName());
                holder.txtProductDescription.setText(model.getDesc());
                holder.txtProductCat.setText( model.getCategory());
                holder.txtProductBarcode.setText(model.getBarcode());

//                ProductsRef.child(model.getBarcode()).child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        zoneno = dataSnapshot.child("Zone").getValue().toString();
//                        shelf= dataSnapshot.child("Shelf").getValue().toString();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
                holder.txtProductZone.setText(model.getZone());
                holder.txtProductShelf.setText(model.getShelf());
                holder.txtProductDiscount.setText((model.getDiscount()*100)+"%");
                holder.txtProductWeight.setText(model.getWeight());
                holder.txtProductCurrentQty.setText(String.valueOf(model.getCurrentStock()));
                holder.txtProductSold.setText(String.valueOf(model.getSold()));
                holder.txtProductPrice.setText( "RM"+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.ProductimageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]= new CharSequence[]
                                {
                                        "Edit",
                                        "Delete",
                                        "Update Quantity"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity3.this);
                        builder.setTitle("Choose your Method");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){


                                    Intent intent = new Intent(DisplayActivity3.this , EditProductActivity.class );
                                    intent.putExtra("barcodeID",model.getBarcode());
                                    startActivity(intent);


                                }
                                else if(i==1)
                                {
                                    CharSequence options1[]= new CharSequence[]
                                            {
                                                    "Yes",
                                                    "No"
                                            };
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DisplayActivity3.this);
                                    builder1.setTitle("Have you need to delete this product?");
                                    builder1.setItems(options1, new DialogInterface.OnClickListener() {


                                        @Override
                                        public void onClick(DialogInterface dialog, int j) {
                                            if(j==0){
                                                String  barcode = getRef(position).getKey();

                                                RemoverProduct(barcode);
                                            }
                                            else{

                                                Intent intent = new Intent(DisplayActivity3.this , HomeActivity.class );
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                    builder1.show();

                                }else if(i==2)
                                {

                                }

                                else{

                                    Intent intent = new Intent(DisplayActivity3.this , MainActivity.class );
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    private void RemoverProduct(String barcode) {
        ProductsRef.child(barcode).removeValue();
    }
}

