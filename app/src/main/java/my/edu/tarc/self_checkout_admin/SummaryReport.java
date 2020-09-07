package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import my.edu.tarc.self_checkout_admin.Model.Product;
import my.edu.tarc.self_checkout_admin.Model.ReportProduct;
import my.edu.tarc.self_checkout_admin.Model.Transaction;

public class SummaryReport extends Fragment {

    final DatabaseReference ProductRef5 = FirebaseDatabase.getInstance().getReference().child("Products");
    final DatabaseReference CustomerRef = FirebaseDatabase.getInstance().getReference().child("Transaction");
    private TextView txtInv1, txtInvCost, txtInvSold, txtEarn, txtDiscount, txtNet,txtSoldP,txtOpen,txtCurr;
    double invcurrentCost,invOpeningCost, net, earn, discount,invDiscount,invsoldP, invtotal;
    int invsold,inv1;
    private List<Product> ProductList;
    private List<Transaction> CustomerList;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    TextView TextViewDate;
    Button btnShow,btnExcel;
    String date6;
    int year,month,day;

    public SummaryReport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_summary_report, container, false);

        ProductList = new ArrayList<>();
        CustomerList = new ArrayList<>();

        txtInv1 = (TextView) view.findViewById(R.id.currInv1);
        txtInvSold = (TextView)view.findViewById(R.id.InvSold);
        txtEarn =(TextView) view.findViewById(R.id.EarnProfit);
        txtSoldP =(TextView) view.findViewById(R.id.InvSoldP);

        txtDiscount = (TextView)view.findViewById(R.id.DiscountUsed);
        txtNet = (TextView)view.findViewById(R.id.NetProfit);
        txtCurr = (TextView)view.findViewById(R.id.currInvCost);
        txtOpen = (TextView)view.findViewById(R.id.OpenCost);

        TextViewDate = (TextView)view.findViewById(R.id.inputReportDate);
        btnShow = (Button) view.findViewById(R.id.btnShow);
        btnExcel = (Button) view.findViewById(R.id.btnExcelSummary);

        TextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                 year = cal.get(Calendar.YEAR);
                 month = cal.get(Calendar.MONTH);
                 day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        DateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String dayformat = "" + dayOfMonth;
                String monthformat = "" + month;

                if (dayOfMonth < 10) {
                    dayformat = "0" + dayOfMonth;
                }
                if (month < 10) {
                    monthformat = "0" + month;
                }

                date6 = dayformat + "-" + monthformat + "-" + year;
                TextViewDate.setText(date6);
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
                GenerateExcel();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ProductRef5.orderByChild("date").equalTo(date6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                    Product p = productSnapshot.getValue(Product.class);

                    inv1 = inv1 + ( p.getCurrentStock());

                    invDiscount = (p.getPrice() * (1-p.getDiscount()));

                    invOpeningCost = invOpeningCost+ ((p.getOpeningStock()* p.getPrice()));

                    invcurrentCost = invcurrentCost+ (invDiscount*p.getCurrentStock());
                    // invtotal = invOpeningCost - invcurrentCost;

                    invsold = invsold + (p.getSold());
                    invsoldP = invsoldP +(p.getSold() * invDiscount);


                    txtInv1.setText(String.valueOf(inv1));
                    txtOpen.setText(String.format("%.2f",invOpeningCost));
                    txtCurr.setText(String.format("%.2f",invcurrentCost));
                    txtInvSold.setText(String.valueOf(invsold));
                    txtSoldP.setText(String.format("%.2f",invsoldP));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        CustomerRef.orderByChild("tscDate").equalTo(date6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot salesSnapshot : dataSnapshot.getChildren()) {
                    Transaction t = salesSnapshot.getValue(Transaction.class);

                    earn = earn +(t.getSubtotal());
                    invDiscount = invDiscount +Double.parseDouble(t.getDiscountValue());
                    net = net + (t.getTotal());


                    txtEarn.setText(String.format("%.2f",earn));
                    txtDiscount.setText(String.format("%.2f",invDiscount));
                    txtNet.setText(String.format("%.2f",net));
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void GenerateExcel() {

        StringBuilder data = new StringBuilder();


        data.append("Inv Stock,InvOpeningCost(RM),InvCurrentCost(RM),InvSold,InvSoldCost(RM),Revenue(RM),DiscountUsed(RM),Net Profit(RM)");

            data.append("\n" + inv1 + "," +
                    +invOpeningCost + "," +invcurrentCost+ "," +invsold+ "," +invsoldP+ "," +earn
                    + "," +invDiscount+ "," +net );



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
}