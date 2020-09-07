package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import my.edu.tarc.self_checkout_admin.Model.Product;
import my.edu.tarc.self_checkout_admin.Model.PromoCode;
import my.edu.tarc.self_checkout_admin.ViewHolder.ProductViewHolder;
import my.edu.tarc.self_checkout_admin.ViewHolder.PromoCodeViewHolder;

public class Display_Promo_code extends Fragment {

    final DatabaseReference PromoRef = FirebaseDatabase.getInstance().getReference().child("Discount");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String Pcode;

    public Display_Promo_code() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_display__promo_code, container, false);

        recyclerView = root.findViewById(R.id.promoRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return  root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<PromoCode> options =
                new FirebaseRecyclerOptions.Builder<PromoCode>()
                        .setQuery(PromoRef, PromoCode.class)
                        .build();


        FirebaseRecyclerAdapter<PromoCode, PromoCodeViewHolder> adapter =
                new FirebaseRecyclerAdapter<PromoCode, PromoCodeViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PromoCodeViewHolder holder, final int position, @NonNull final PromoCode model)
                    {

                        holder.pCode1.setText(model.getCode());
                        holder.Desc1.setText(model.getDesc());
                        double maD = (Double.parseDouble (model.getRate())*100);
                        holder.rate1.setText(maD+"%");
                        holder.MinPurchase.setText(String.valueOf(model.getMinPurchase()));


                        holder.discountMax.setText(String.valueOf(model.getMaxDiscount())+ "%");
                         long expDate = model.getExpiryDate();
                        SimpleDateFormat expDate2 = new SimpleDateFormat("dd-MM-yyyy");
                        String expDate1 = expDate2.format(expDate);
                        holder.expdate.setText(expDate1);
                        holder.totalRed.setText(String.valueOf(model.getTotalRedemption()));
                        holder.descHead.setText(model.getHeader());
                        holder.LimitUser.setText(String.valueOf(model.getLimitPerUser()));
                        Pcode = getRef(position).getKey();


                    }


                    @NonNull
                    @Override
                    public PromoCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_code_layout, parent, false);
                        PromoCodeViewHolder holder = new PromoCodeViewHolder(view);
                        return holder;
                    }


                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        helper.attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
           //int position = viewHolder.getAdapterPosition();
           // recyclerView.removeItemDecorationAt(position);
            PromoRef.child(Pcode).removeValue();
           // adapter.notifyDataSetChanged();

        }

    });




}



