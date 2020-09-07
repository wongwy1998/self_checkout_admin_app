package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.self_checkout_admin.R;

public class ReceiptProductViewHolder  extends RecyclerView.ViewHolder{

    public TextView ProductDiscount,ProductName, ProductDescription, ProductPrice, ProductQty, ProductWeight, ProductCat;
    public ImageView ProductimageView;
    public TextView ProductLocation;



    public ReceiptProductViewHolder(View itemView)
    {
        super(itemView);


        ProductimageView = (ImageView) itemView.findViewById(R.id.imageP1);
        ProductName = (TextView) itemView.findViewById(R.id.productName1);
        ProductPrice = (TextView) itemView.findViewById(R.id.productPrice1);
        ProductQty = (TextView) itemView.findViewById(R.id.productQty1);
        ProductWeight = (TextView) itemView.findViewById(R.id.productWeight1);



    }

}
