package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import my.edu.tarc.self_checkout_admin.Model.Transaction;
import my.edu.tarc.self_checkout_admin.ViewHolder.CustomerTranViewHolder;

public class CustomerTransactionActivity extends AppCompatActivity {

    final DatabaseReference CustTRef = FirebaseDatabase.getInstance().getReference().child("Transaction");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button btnTransactionExcel;
    private List<Transaction> CustomerTransactionList;
    public FirebaseRecyclerAdapter<Transaction, CustomerTranViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_transaction);


        CustomerTransactionList = new ArrayList<>();

        recyclerView = findViewById(R.id.customerRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(CustomerTransactionActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        btnTransactionExcel = (Button)findViewById(R.id.btnTransactionExcel);

        btnTransactionExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateTransactionExcel();
            }
        });

        FirebaseRecyclerOptions<Transaction> options =
                new FirebaseRecyclerOptions.Builder<Transaction>()
                        .setQuery(CustTRef, Transaction.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Transaction, CustomerTranViewHolder>(options) {
            @NonNull
            @Override
            public CustomerTranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_layout, parent, false);
                CustomerTranViewHolder holder = new CustomerTranViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull CustomerTranViewHolder holder, final int position, @NonNull final Transaction model) {
                holder.txtTscID.setText(model.getTscID());
                holder.txtTscDate.setText(model.getTscDate());
                holder.txtTscTime.setText(model.getTscTime());
                holder.txtTscAmt.setText("RM" + model.getTotal());
                holder.customerPhone.setText(model.getCustomerPhone());
                holder.txtTscDis.setText(model.getDiscountValue());

                if (model.getVerifyStatus() == 0) {
                    holder.txtStatus.setText("Unpaid");
                }
                else if(model.getVerifyStatus() == 1) {
                    holder.txtStatus.setText("Paid");
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        bundle.putString("transaction", model.getTscID());
                        DisplayTranPro de= new DisplayTranPro();
                        de.setArguments(bundle);

                    }
                });

            }



        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    public void onResume(){
        super.onResume();
        adapter.startListening();
    }

    private void GenerateTransactionExcel() {


        CustTRef.child("Transaction").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot salesSnapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = salesSnapshot.getValue(Transaction.class);
                    CustomerTransactionList.add(transaction);
                }

                //currentDate.format(CustomerTransactionList.getTscDate());

                StringBuilder data = new StringBuilder();

                data.append("Customer ID,Date,Time,paymentMethod,Subtotal,Discount Value,Total");
                for (int i = 0; i < CustomerTransactionList.size(); i++) {
                    data.append("\n" + CustomerTransactionList.get(i).getCustomerPhone() + "," +
                            (CustomerTransactionList.get(i).getTscDate())+ "," +
                            (CustomerTransactionList.get(i).getTscTime()) + "," +
                            CustomerTransactionList.get(i).getPaymentMethod() + "," +
                            CustomerTransactionList.get(i).getSubtotal() + "," +
                            CustomerTransactionList.get(i).getDiscountValue() + "," +
                            CustomerTransactionList.get(i).getTotal()

                    );
                }

                try {
                    //saving the file into device
                    FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                    out.write((data.toString()).getBytes());
                    out.close();

                    //exporting
                    Context context =getApplicationContext();
                    File filelocation = new File( getFilesDir(), "data.csv");
                    Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", filelocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/csv");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "Customer Transaction"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
