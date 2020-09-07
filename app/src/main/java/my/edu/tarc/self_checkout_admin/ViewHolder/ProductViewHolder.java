package my.edu.tarc.self_checkout_admin.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.edu.tarc.self_checkout_admin.Interface.ItemClickListner;
import my.edu.tarc.self_checkout_admin.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductDiscount,txtProductName, txtProductDescription, txtProductPrice, txtProductWeight, txtProductCat;
    public ImageView ProductimageView;
    public TextView txtProductBarcode,txtProductZone,txtProductShelf,txtProductSold,txtProductCurrentQty;
    public ItemClickListner listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        ProductimageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description1);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtProductBarcode = (TextView) itemView.findViewById(R.id.product_Barcode);
        txtProductWeight = (TextView) itemView.findViewById(R.id.product_weight);
        txtProductDiscount = (TextView) itemView.findViewById(R.id.proD1);
        txtProductZone = (TextView) itemView.findViewById(R.id.product_zone1);
        txtProductShelf = (TextView) itemView.findViewById(R.id.product_shelf1);
        txtProductSold =(TextView) itemView.findViewById(R.id.product_sold);
        txtProductCurrentQty =(TextView) itemView.findViewById(R.id.product_CurrentQty);
        txtProductCat = (TextView) itemView.findViewById(R.id.product_Category);

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


