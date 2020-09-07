package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import my.edu.tarc.self_checkout_admin.Interface.ItemClickListner;
import my.edu.tarc.self_checkout_admin.R;

public class ReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtReportPName,  txtReportPrice, txtReportQty,txtReporttCat,txtDate1;
    public ItemClickListner listner;

    public ReportViewHolder(View itemView) {
        super(itemView);

//        txtDate1 = (TextView)itemView.findViewById(R.id.report_Date) ;
//        txtReportPName = (TextView) itemView.findViewById(R.id.report_Name);
//        txtReportPrice = (TextView) itemView.findViewById(R.id.report_Price);
//        txtReportQty = (TextView) itemView.findViewById(R.id.report_quantity);
//        txtReporttCat = (TextView) itemView.findViewById(R.id.report_Category);
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
