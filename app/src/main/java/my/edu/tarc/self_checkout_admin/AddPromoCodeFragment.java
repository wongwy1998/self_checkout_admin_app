package my.edu.tarc.self_checkout_admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddPromoCodeFragment extends Fragment {

    private DatabaseReference databaseProduct;
    private EditText rate, discountCode,discountDesc,discountLimitPerUser,discountMinPur,discountMax,discountTotalRedemption,discountDescHeader;
    private Button btnCreate;
    private TextView discountExp;
    private ProgressDialog loadingBar;
    private DatePickerDialog.OnDateSetListener DateSetListener;
    private String disC, disD,disR,limit,minPurchase,MaxDiscount,disExp,date,TotalRem,disDH;
    int minPur,MaxDis;
    int l,totalR;
    long exp;

    public AddPromoCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_discount_code, container, false);


        databaseProduct = FirebaseDatabase.getInstance().getReference("Discount");
        loadingBar = new ProgressDialog(getActivity());
        rate = (EditText) view.findViewById(R.id.disRate);
        discountDesc =(EditText) view.findViewById(R.id.disDesc);
        discountCode = (EditText) view.findViewById(R.id.disCode);
        discountLimitPerUser = (EditText) view.findViewById(R.id.disLimit);
        discountDescHeader =(EditText) view.findViewById(R.id.disDescHead);
        discountTotalRedemption =(EditText) view.findViewById(R.id.disRedeem);
        discountMax = (EditText) view.findViewById(R.id.disMaxDiscount);
        discountMinPur =(EditText)view.findViewById(R.id.disMinPurchase);
        discountExp =(TextView) view.findViewById(R.id.disExpiredDateCode);

        btnCreate = (Button) view.findViewById(R.id.btnCreate);


        discountExp.setOnClickListener(new View.OnClickListener() {
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
                discountExp.setText(date);
            }
        };
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ValidateDiscount();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }

    private void ValidateDiscount() throws ParseException {
        disD = discountDesc.getText().toString();
        disC = discountCode.getText().toString();
        disR = rate.getText().toString();
        limit = discountLimitPerUser.getText().toString();
        minPurchase = discountMinPur.getText().toString();
        MaxDiscount= discountMax.getText().toString();
        disExp = discountExp.getText().toString();
        TotalRem=discountTotalRedemption.getText().toString();
        disDH = discountDescHeader.getText().toString();

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 =(Date)formatter.parse(date);
        long exp =date1.getTime();




        l = Integer.parseInt(limit);
        minPur = Integer.parseInt(minPurchase);
        MaxDis = Integer.parseInt(MaxDiscount);
        totalR = Integer.parseInt(TotalRem);

        if (TextUtils.isEmpty(disC)) {
            Toast.makeText(getActivity(), "Please fill in the Code Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(disD)) {
            Toast.makeText(getActivity(), "Please fill in the Description of discount", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(disR)) {
            Toast.makeText(getActivity(), "Please fill in the Rate of discount", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(limit)) {
            Toast.makeText(getActivity(), "Please fill in the Limit Per User of discount", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(minPurchase)) {
            Toast.makeText(getActivity(), "Please fill in the Minimum purchase amount of discount", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(MaxDiscount)) {
            Toast.makeText(getActivity(), "Please fill in the Maximum of discount", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(disExp)) {
            Toast.makeText(getActivity(), "Please fill in the Expired Date of discount", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(disDH)) {
            Toast.makeText(getActivity(), "Please fill in the Description Header of discount", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(TotalRem)) {
                Toast.makeText(getActivity(), "Please fill in the Total Redemption of discount", Toast.LENGTH_SHORT).show();
        }else
            StoreDisInfo();
    }

    private void StoreDisInfo() {

        loadingBar.setTitle("Adding New Discount Code");
        loadingBar.setMessage("Please Wait, we are adding the new Discount code");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        HashMap<String, Object> CodeMap = new HashMap<>();
        CodeMap.put("Code", disC);
        CodeMap.put("Desc",disD);
        CodeMap.put("rate", disR);
        CodeMap.put("LimitPerUser",l);
        CodeMap.put("maxDiscount",MaxDis);
        CodeMap.put("minPurchase",minPur);
        CodeMap.put("expiryDate",exp);
        CodeMap.put("totalRedemption",totalR);
        CodeMap.put("header",disDH);

        databaseProduct.child(disC).updateChildren(CodeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), "Discount Code added successfully", Toast.LENGTH_SHORT).show();


                } else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(getActivity(), "Error :" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

