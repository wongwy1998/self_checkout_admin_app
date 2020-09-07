package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.self_checkout_admin.Interface.ItemClickListner;
import my.edu.tarc.self_checkout_admin.R;


public class CustomerTranViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtTscDate, txtTscTime, txtTscID,txtTscAmt,customerPhone,txtTscDis,txtStatus;
    public ItemClickListner listner;
    public CustomerTranViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTscDate = itemView.findViewById(R.id.tsc_date);
        txtTscTime = itemView.findViewById(R.id.tsc_time);
        txtTscAmt = itemView.findViewById(R.id.tsc_total);
        txtTscID = itemView.findViewById(R.id.tsc_id);
        customerPhone = itemView.findViewById(R.id.Customerid);
        txtTscDis = itemView.findViewById(R.id.tsc_discount);
        txtStatus = itemView.findViewById(R.id.tsc_status);


    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }



    }

