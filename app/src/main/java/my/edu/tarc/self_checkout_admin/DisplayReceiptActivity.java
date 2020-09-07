package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import my.edu.tarc.self_checkout_admin.Model.Receipt;
import my.edu.tarc.self_checkout_admin.Model.TransactionProduct;
import my.edu.tarc.self_checkout_admin.ViewHolder.ReceiptProductViewHolder;
import my.edu.tarc.self_checkout_admin.ViewHolder.TransactionProductViewHolder;

public class DisplayReceiptActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public TextView date1,time1,transactionid1,paymentMethod,custPhone,total1,subtotal1,discount1;


    public Button btnVerify;
    String transactionID;
    DatabaseReference ReceiptRef1,receiptProRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_receipt);

        receiptProRef = FirebaseDatabase.getInstance().getReference();
        transactionID = getIntent().getStringExtra("transactionID");
        ReceiptRef1 = FirebaseDatabase.getInstance().getReference().child("Transaction").child(transactionID);

        recyclerView = findViewById(R.id.receiptRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        custPhone = (TextView) findViewById(R.id.receiptcName);
        date1 = (TextView) findViewById(R.id.receiptDate);
        time1 = (TextView) findViewById(R.id.receiptTime);
        paymentMethod = (TextView) findViewById(R.id.receiptPayment);
        transactionid1 = (TextView) findViewById(R.id.receiptid);
        total1 = (TextView) findViewById(R.id.receipttotal);
        subtotal1 =(TextView) findViewById(R.id.receiptsubtotal);
        discount1 = (TextView) findViewById(R.id.receiptdiscount);
        btnVerify =(Button) findViewById(R.id.btnVerify);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ReceiptRef1.child("verifyStatus").equals(1)) {
                    Toast.makeText(DisplayReceiptActivity.this, "Verified Before", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DisplayReceiptActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    ReceiptRef1.child("verifyStatus").setValue(1);
                    Toast.makeText(DisplayReceiptActivity.this, "Verify Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(DisplayReceiptActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
        DisplayReceiptDetail();
        DisplayItem();


    }

    private void DisplayReceiptDetail() {
        ReceiptRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String customer = dataSnapshot.child("customerPhone").getValue().toString();
                    String tdate = dataSnapshot.child("tscDate").getValue().toString();
                    String ttime = dataSnapshot.child("tscTime").getValue().toString();
                    String tid = dataSnapshot.child("tscID").getValue().toString();
                    String paymentmethod1 = dataSnapshot.child("paymentMethod").getValue().toString();
                    String subtotal = dataSnapshot.child("subtotal").getValue().toString();
                    String total = dataSnapshot.child("total").getValue().toString();
                    String disc = dataSnapshot.child("discountValue").getValue().toString();


                    //Double sub =Double.parseDouble(subtotal);
                    discount1.setText("-RM" + disc);
                    total1.setText("RM" + total);
                    subtotal1.setText("RM"+ subtotal);
                    custPhone.setText(customer);
                    date1.setText(tdate);
                    time1.setText(ttime);
                    transactionid1.setText(tid);
                    paymentMethod.setText(paymentmethod1);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void DisplayItem(){
        FirebaseRecyclerOptions<Receipt> options =
                new FirebaseRecyclerOptions.Builder<Receipt>()
                        .setQuery(receiptProRef.child("Transaction").child(transactionID).child("product"), Receipt.class)
                        .build();


        FirebaseRecyclerAdapter<Receipt, ReceiptProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Receipt, ReceiptProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ReceiptProductViewHolder holder, int position, @NonNull Receipt model) {


                        holder.ProductName.setText(model.getPname());
                        holder.ProductWeight.setText(model.getWeight());
                        holder.ProductQty.setText("x"+model.getQuantity());
                        holder.ProductPrice.setText("RM"+model.getPrice());
                        Picasso.get().load(model.getImageRef()).into(holder.ProductimageView);


                    }

                    @NonNull
                    @Override
                    public ReceiptProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_item_layout, parent, false);
                        ReceiptProductViewHolder holder = new ReceiptProductViewHolder(view);
                        return holder;
                    }


                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}