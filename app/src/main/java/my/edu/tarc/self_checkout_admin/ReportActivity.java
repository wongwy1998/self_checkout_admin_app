package my.edu.tarc.self_checkout_admin;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import my.edu.tarc.self_checkout_admin.Model.Product;
import my.edu.tarc.self_checkout_admin.Model.ReportProduct;
import my.edu.tarc.self_checkout_admin.ViewHolder.ReportProductViewHolder;
import my.edu.tarc.self_checkout_admin.ViewHolder.ReportViewHolder;

public class ReportActivity extends Fragment {

    final DatabaseReference reportPRef = FirebaseDatabase.getInstance().getReference().child("Products");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private List<ReportProduct> ReportProductList;
    public  FirebaseRecyclerAdapter<ReportProduct, ReportProductViewHolder> adapter;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    String date;
    String InputDate;
    Button btnExcel,btnShow;
    private TextView TextViewDate,TextViewDate1;
    double soldTotal;
    public ReportActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.report_product_item_layout, container, false);

        ReportProductList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.reportProductRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnExcel = view.findViewById(R.id.btnExcel);
        TextViewDate = view.findViewById(R.id.inputReportDate);
        btnShow= view.findViewById(R.id.btnShow);

        TextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String dayformat = ""+dayOfMonth;
                String monthformat = ""+month;

                if(dayOfMonth <10) {
                   dayformat = "0"+dayOfMonth;
                }
                if(month<10){
                    monthformat = "0"+month;
                }

                date = dayformat + "-" + monthformat + "-" + year;
                TextViewDate.setText(date);
            }
        };

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onStart();
            }
        });
        btnExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateExcel();
            }
        });


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ReportProduct> options =
                new FirebaseRecyclerOptions.Builder<ReportProduct>()
                        .setQuery(reportPRef.orderByChild("date").endAt(date), ReportProduct.class)
                        .build();


        adapter= new FirebaseRecyclerAdapter<ReportProduct, ReportProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ReportProductViewHolder holder, int position, @NonNull ReportProduct model) {
                holder.txtReportPName.setText(model.getName());
                holder.txtReportSoldQty.setText(String.valueOf(model.getSold()));
                soldTotal = (model.getSold() * model.getPrice());
                holder.txtReportTotalSold.setText(String.format("%.2f",soldTotal));
            }

            @NonNull
            @Override
            public ReportProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_report_product_item, parent, false);
                ReportProductViewHolder holder = new ReportProductViewHolder(view);
                return holder;
            }

        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    private void generateExcel() {

        reportPRef.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot salesSnapshot : dataSnapshot.getChildren()) {
                    ReportProduct pr = salesSnapshot.getValue(ReportProduct.class);
                    ReportProductList.add(pr);
                }


                StringBuilder data = new StringBuilder();

                data.append("Product Name,Sold Quantity, Total Cost");
                for (int i = 0; i < ReportProductList.size(); i++) {
                    data.append("\n" + ReportProductList.get(i).getName() + "," +
                            ReportProductList.get(i).getSold() + "," +
                            (ReportProductList.get(i).getSold()*ReportProductList.get(i).getPrice()));
                }

                try {
                    //saving the file into device
                    FileOutputStream out = getActivity().openFileOutput("data.csv", Context.MODE_PRIVATE);
                    out.write((data.toString()).getBytes());
                    out.close();

                    //exporting
                    Context context =getActivity().getApplicationContext();
                    File filelocation = new File(getActivity().getFilesDir(), "data.csv");
                    Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", filelocation);
                    Intent fileIntent = new Intent(Intent.ACTION_SEND);
                    fileIntent.setType("text/csv");
                    fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                    fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(fileIntent, "Send mail"));
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