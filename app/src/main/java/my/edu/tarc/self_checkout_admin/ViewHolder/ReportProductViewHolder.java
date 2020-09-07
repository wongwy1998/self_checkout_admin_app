package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.self_checkout_admin.Interface.ItemClickListner;
import my.edu.tarc.self_checkout_admin.R;

public class ReportProductViewHolder extends RecyclerView.ViewHolder{
    public TextView txtReportPName, txtReportSoldQty,txtReportTotalSold;


    public ReportProductViewHolder(View itemView) {
        super(itemView);


        txtReportPName = (TextView) itemView.findViewById(R.id.ReportProName);
        txtReportSoldQty = (TextView) itemView.findViewById(R.id.ReportSoldQty);
        txtReportTotalSold = (TextView) itemView.findViewById(R.id.ReportTotalSold);

    }
}
