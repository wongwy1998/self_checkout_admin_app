package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import my.edu.tarc.self_checkout_admin.Model.Product;
import my.edu.tarc.self_checkout_admin.ViewHolder.ProductViewHolder;

public class search_product extends Fragment {


    private Button SearchBtn;
    private EditText SinputText;
    private RecyclerView searchList;
    private String SearchInput,zoneno,shelf;
    DatabaseReference ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
    public search_product() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root  = inflater.inflate(R.layout.activity_search_product, container, false);


        SinputText = root.findViewById(R.id.inputSearch1);
        SearchBtn = root.findViewById(R.id.btnSearch1);
        searchList = root.findViewById(R.id.searchproductRecycler);
        searchList.setLayoutManager(new LinearLayoutManager(getActivity()));

        onStart();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SearchInput = SinputText.getText().toString();

                onStart();
            }
        });

       return  root;
    }

    @Override
    public void onStart() {
        super.onStart();



        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(ProductsRef.orderByChild("Name").equalTo(SearchInput), Product.class)
                        .build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, final int position, @NonNull final Product model)
                    {
                        holder.txtProductName.setText( model.getName());
                        holder.txtProductDescription.setText(model.getDesc());
                        holder.txtProductCat.setText( model.getCategory());
                        holder.txtProductBarcode.setText(model.getBarcode());
                        holder.txtProductZone.setText(model.getZone());
                        holder.txtProductShelf.setText(model.getShelf());
                        holder.txtProductDiscount.setText(model.getDiscount()+"%");
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
                                                "Delete"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Choose your Method");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(i==0){


                                            Intent intent = new Intent(getActivity() , EditProductActivity.class );
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
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                            builder1.setTitle("Have you need to delete this product?");
                                            builder1.setItems(options1, new DialogInterface.OnClickListener() {


                                                @Override
                                                public void onClick(DialogInterface dialog, int j) {
                                                    if(j==0){
                                                        String  barcode = getRef(position).getKey();

                                                        RemoverProduct(barcode);
                                                    }
                                                    else{

                                                        Intent intent = new Intent(getActivity() , DisplayProductFragment.class );
                                                        startActivity(intent);
                                                    }

                                                }
                                            });
                                            builder1.show();

                                        }
                                        else{

                                            Intent intent = new Intent(getActivity() , MainActivity.class );
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
        searchList.setAdapter(adapter);
        adapter.startListening();

    }

    private void RemoverProduct(String barcode) {
        ProductsRef.child(barcode).removeValue();
    }
}


