package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.edu.tarc.self_checkout_admin.Model.Transaction;
import my.edu.tarc.self_checkout_admin.ViewHolder.CustomerTranViewHolder;

public class CustomerTranActivity extends Fragment {

    final DatabaseReference CustTRef = FirebaseDatabase.getInstance().getReference().child("Transaction");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button btnTransactionExcel;
    private List<Transaction> CustomerTransactionList;
    public  FirebaseRecyclerAdapter<Transaction, CustomerTranViewHolder> adapter;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    String date5;
    String InputDate;
    Button btnShow;
    private TextView TextViewDate;
    String TID;

    public CustomerTranActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.activity_customer_tran, container, false);

        CustomerTransactionList = new ArrayList<>();

        recyclerView = root.findViewById(R.id.customerRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        TextViewDate = root.findViewById(R.id.inputReportDate);
        btnShow= root.findViewById(R.id.btnShow);

        TextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), DateSetListener, year, month, day);
                dialog.show();
            }

        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date5 = dayOfMonth + "-" + month + "-" + year;
                TextViewDate.setText(date5);
            }
        };



        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputDate = TextViewDate.getText().toString();


                onStart();
            }
        });
        btnTransactionExcel = (Button)root.findViewById(R.id.btnTransactionExcel);

        btnTransactionExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateTransactionExcel();
            }
        });

       onStart();


        return root;
    }



    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Transaction> options =
                new FirebaseRecyclerOptions.Builder<Transaction>()
                        .setQuery(CustTRef.orderByChild("tscDate").equalTo(date5), Transaction.class)
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

                TID = model.getTscID();
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

//                        Bundle bundle = new Bundle();
//                        bundle.putString("transaction", model.getTscID());
//                        DisplayTranPro de= new DisplayTranPro();
//                        de.setArguments(bundle);
//                        FragmentManager fragmentManager = getFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, de).commit();
                        Intent intent = new Intent(getActivity() ,CustomerPurchaseItem.class );
                        intent.putExtra("tID",model.getTscID());
                        startActivity(intent);



                    }
                });

            }



        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public void onResume(){
        super.onResume();
        adapter.startListening();
    }

    private void GenerateTransactionExcel() {


        CustTRef.orderByChild("tscDate").equalTo(date5).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot TSnapshot : dataSnapshot.getChildren()) {
                    Transaction transaction = TSnapshot.getValue(Transaction.class);
                    CustomerTransactionList.add(transaction);
                }

                StringBuilder data = new StringBuilder();

                data.append("Customer ID,Date,Time,paymentMethod,Subtotal,Discount Value,Total");
                for (int i = 0; i < CustomerTransactionList.size(); i++) {
                    data.append("\n" + CustomerTransactionList.get(i).getCustomerPhone() + "," +
                            CustomerTransactionList.get(i).getTscDate()+ "," +
                            CustomerTransactionList.get(i).getTscTime() + "," +
                            CustomerTransactionList.get(i).getPaymentMethod() + "," +
                            CustomerTransactionList.get(i).getSubtotal() + "," +
                            CustomerTransactionList.get(i).getDiscountValue() + "," +
                            CustomerTransactionList.get(i).getTotal()

                    );
                }

                try {
                    //saving the file into device
                    FileOutputStream out = getActivity().openFileOutput("data.csv", Context.MODE_PRIVATE);
                    out.write((data.toString()).getBytes());
                    out.close();

                    //exporting
                    Context context = getActivity().getApplicationContext();
                    File filelocation = new File( getActivity().getFilesDir(), "data.csv");
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



