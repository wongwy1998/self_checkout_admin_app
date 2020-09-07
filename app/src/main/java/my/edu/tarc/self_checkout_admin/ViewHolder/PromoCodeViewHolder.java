package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.self_checkout_admin.R;

public class PromoCodeViewHolder extends RecyclerView.ViewHolder {

   public TextView pCode1,Desc1,rate1,totalRed,expdate,discountMax,MinPurchase,LimitUser,descHead;
    public PromoCodeViewHolder(@NonNull View itemView) {
        super(itemView);

       pCode1 = (TextView)itemView.findViewById(R.id.Code1) ;
       Desc1 = (TextView) itemView.findViewById(R.id.CodeDesc);
       rate1 = (TextView) itemView.findViewById(R.id.CodeRate);
       LimitUser =(TextView)itemView.findViewById(R.id.LimitPerUser2);
       totalRed = (TextView) itemView.findViewById(R.id.TotalRe) ;
       descHead =(TextView) itemView.findViewById(R.id.descHead);
       discountMax =(TextView) itemView.findViewById(R.id.MaximumDisocunt);
       MinPurchase = (TextView) itemView.findViewById(R.id.MinPurchase);
       expdate =(TextView) itemView.findViewById(R.id.ExpiryDate);

    }
}
