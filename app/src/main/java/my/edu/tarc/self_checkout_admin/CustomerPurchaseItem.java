package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import my.edu.tarc.self_checkout_admin.Model.TransactionProduct;
import my.edu.tarc.self_checkout_admin.ViewHolder.TransactionProductViewHolder;

public class CustomerPurchaseItem extends AppCompatActivity {


    DatabaseReference ProductTran = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerPView;
    RecyclerView.LayoutManager layoutManager;
    String tID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_purchase_item);

         tID = getIntent().getStringExtra("tID");

        recyclerPView = findViewById(R.id.productTranRecycler);
        recyclerPView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerPView.setLayoutManager(layoutManager);


    FirebaseRecyclerOptions<TransactionProduct> options =
            new FirebaseRecyclerOptions.Builder<TransactionProduct>()
                    .setQuery(ProductTran.child("Transaction").child(tID).child("product"), TransactionProduct.class)
                    .build();


    FirebaseRecyclerAdapter<TransactionProduct, TransactionProductViewHolder> adapter =
            new FirebaseRecyclerAdapter<TransactionProduct, TransactionProductViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull TransactionProductViewHolder holder, int position, @NonNull TransactionProduct model) {


                    holder.ProductName.setText(model.getPname());
                    holder.ProductWeight.setText(model.getWeight());
                    holder.ProductQty.setText("x" +model.getQuantity());
                    holder.ProductPrice.setText("RM"+model.getPrice());
                    holder.ProductDiscount.setText((model.getDiscount()*100)+"%");
                    Picasso.get().load(model.getImageRef()).into(holder.ProductimageView);


                }

                @NonNull
                @Override
                public TransactionProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sub_customer_tran, parent, false);
                    TransactionProductViewHolder holder = new TransactionProductViewHolder(view);
                    return holder;
                }


            };

        recyclerPView.setAdapter(adapter);
        adapter.startListening();
}
}
