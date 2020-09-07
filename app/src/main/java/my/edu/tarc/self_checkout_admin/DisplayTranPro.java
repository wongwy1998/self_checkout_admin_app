package my.edu.tarc.self_checkout_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import my.edu.tarc.self_checkout_admin.Model.Product;
import my.edu.tarc.self_checkout_admin.Model.Transaction;
import my.edu.tarc.self_checkout_admin.Model.TransactionProduct;
import my.edu.tarc.self_checkout_admin.ViewHolder.CustomerTranViewHolder;
import my.edu.tarc.self_checkout_admin.ViewHolder.ProductViewHolder;
import my.edu.tarc.self_checkout_admin.ViewHolder.TransactionProductViewHolder;

public class DisplayTranPro extends Fragment {


    DatabaseReference ProductTran = FirebaseDatabase.getInstance().getReference().child("Transaction");
    private RecyclerView recyclerPView;
    RecyclerView.LayoutManager layoutManager;
    String t;

    public DisplayTranPro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.transaction_product_view, container, false);


        Bundle b = getArguments();
        t = b.getString("transaction");
       // t = this.getArguments().getString("transaction");

        recyclerPView = root.findViewById(R.id.productTranRecycler);
        recyclerPView.setLayoutManager(new LinearLayoutManager(getActivity()));

       onStart();

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<TransactionProduct> options =
                new FirebaseRecyclerOptions.Builder<TransactionProduct>()
                        .setQuery(ProductTran.child(t).orderByChild("product"), TransactionProduct.class)
                        .build();


        FirebaseRecyclerAdapter<TransactionProduct,TransactionProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<TransactionProduct, TransactionProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull TransactionProductViewHolder holder, int position, @NonNull TransactionProduct model) {


                        holder.ProductName.setText(model.getPname());
                        holder.ProductWeight.setText(model.getWeight());
                        holder.ProductQty.setText(model.getQuantity());
                        holder.ProductPrice.setText(model.getPrice());
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

