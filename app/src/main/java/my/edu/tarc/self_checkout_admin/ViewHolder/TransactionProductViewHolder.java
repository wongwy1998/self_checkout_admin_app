package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.self_checkout_admin.Interface.ItemClickListner;
import my.edu.tarc.self_checkout_admin.R;

public class TransactionProductViewHolder extends RecyclerView.ViewHolder{
    public TextView ProductDiscount,ProductName, ProductPrice, ProductQty, ProductWeight;
    public ImageView ProductimageView;
    public TextView ProductLocation;



    public TransactionProductViewHolder(View itemView)
    {
        super(itemView);


        ProductimageView = (ImageView) itemView.findViewById(R.id.imageP);
        ProductName = (TextView) itemView.findViewById(R.id.productName);
        ProductPrice = (TextView) itemView.findViewById(R.id.productPrice);
        ProductQty = (TextView) itemView.findViewById(R.id.productQty);
        ProductWeight = (TextView) itemView.findViewById(R.id.productWeight);
        ProductDiscount = (TextView) itemView.findViewById(R.id.productDiscount);


    }

}
