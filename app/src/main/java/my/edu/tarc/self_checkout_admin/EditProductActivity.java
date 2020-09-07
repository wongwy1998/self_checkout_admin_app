package my.edu.tarc.self_checkout_admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EditProductActivity extends AppCompatActivity {

    public EditText txtProductDiscount,txtProductName, txtProductDescription, txtProductPrice, txtProductWeight;
    public TextView txtProductBarcode, txtProductOpeningQty;
    public Button btnEdit;
    private Uri ImageUri;
    private ImageView upImage;
    Spinner txtProductCat,zone1,shelf1;
    DatabaseReference ProductRef1;
    DatabaseReference ProductRef2 = FirebaseDatabase.getInstance().getReference().child("Products");
    String barcodeID ;
    int currentStock=0,sold=0;
    private String downloadImageUrl, productRandomKey;
    private StorageReference iStorageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
    private String saveCurrentDate, saveCurrentTime;
    private String pName, pQty,pPrice,pCategory,pweight,pdiscount,pDesc,pZone,pshelf;
    String pBar1,pBar ;
    String pname1 ;
    String pimage1 ;
    String pdesc1 ;
    String pqty1 ;
    Double pprice1 ;
    String pcategory1;
    String pweight1;
    Double pdiscount1;
    String pzone1;
    String pshelf1;
    Double newPrice,d;
    int pSold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        barcodeID = getIntent().getStringExtra("barcodeID");
        ProductRef1 = FirebaseDatabase.getInstance().getReference().child("Products").child(barcodeID);


        txtProductBarcode =(TextView) findViewById(R.id.inputBarcode1);
        upImage = (ImageView)findViewById(R.id.productimage1);
        txtProductName = (EditText) findViewById(R.id.inputpName1);
        txtProductDescription = (EditText) findViewById(R.id.inputDesc1);
        txtProductPrice = (EditText) findViewById(R.id.inputPrice1);
        txtProductOpeningQty = (TextView) findViewById(R.id.inputQty1);
        txtProductWeight = (EditText) findViewById(R.id.inputWeight1);
        txtProductDiscount = (EditText) findViewById(R.id.inputdis1);
        txtProductCat = (Spinner) findViewById(R.id.pcategory1);
        shelf1 =(Spinner) findViewById(R.id.pShelf2);
        zone1 = (Spinner) findViewById(R.id.pZone2);


       btnEdit = (Button) findViewById(R.id.btnEdit);
       EditProductDetail();
       upImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CharSequence options[] = new CharSequence[]
                       {
                               "Take Photo",
                               "Choose from gallery"

                       };
               AlertDialog.Builder builder = new AlertDialog.Builder(EditProductActivity.this);
               builder.setTitle("Choose Your Method");
               builder.setItems(options, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int i) {
                       if (i == 0) {


                           Intent picintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                           if (picintent.resolveActivity(getPackageManager()) != null) {
                               startActivityForResult(picintent, 0);


                           }
                       }else if (i==1) {

                           Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                           startActivityForResult(pickPhoto, 1);
                       }



                   }


               });
               builder.show();
           }
       });



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyEdit();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        ImageUri = data.getData();
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        upImage.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        ImageUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (ImageUri != null) {
                            Cursor cursor = getContentResolver().query(ImageUri,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                upImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }

    }


    private void applyEdit() {
         pBar =txtProductBarcode.getText().toString();
         pName = txtProductName.getText().toString();
         pQty = txtProductOpeningQty.getText().toString();
         pPrice = txtProductPrice.getText().toString();
         pCategory = txtProductCat.getSelectedItem().toString();
         pweight = txtProductWeight.getText().toString();
         pdiscount = txtProductDiscount.getText().toString();
         pDesc = txtProductDescription.getText().toString();
         pZone = zone1.getSelectedItem().toString();
         pshelf = shelf1.getSelectedItem().toString();



        if(pName.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Name",Toast.LENGTH_SHORT).show();
        }else if(pQty.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Quantity",Toast.LENGTH_SHORT).show();
        }else if(pPrice.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Price",Toast.LENGTH_SHORT).show();
        }else if(pCategory.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Category",Toast.LENGTH_SHORT).show();
        }else if(pweight.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Weight",Toast.LENGTH_SHORT).show();
        }else if(pdiscount.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Discount",Toast.LENGTH_SHORT).show();
        }else if(pZone.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Zone",Toast.LENGTH_SHORT).show();
        }else if(pshelf.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Shelf",Toast.LENGTH_SHORT).show();
        }else if(pDesc.equals("")){
            Toast.makeText(EditProductActivity.this,"Please fill up the product Description",Toast.LENGTH_SHORT).show();
        }else if(ImageUri == null) {
            storeBackDatabase1();

        }else{
            confirmUpdate();
        }


    }

    private void confirmUpdate(){

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MM,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;
        final StorageReference filePath = iStorageRef.child(ImageUri.getLastPathSegment()+"  "+ productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(EditProductActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            downloadImageUrl = task.getResult().toString();
                            storeBackDatabase();
                        }
                    }


                });

            }

        });

    }

    private  void storeBackDatabase(){
        currentStock = Integer.parseInt(pQty) - sold;
        int pQ = Integer.parseInt(pQty);
        d =Double.parseDouble(pdiscount);
        newPrice = Double.parseDouble(pPrice);
        String location2 = pZone+"_"+pshelf;
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("Barcode",pBar);
        productMap.put("Name",pName);
        productMap.put("Category",pCategory);
        productMap.put("Desc",pDesc);
        productMap.put("OpeningStock",pQ);
        productMap.put("Sold",pSold);
        productMap.put("Discount",d);
        productMap.put("CurrentStock",currentStock);
        productMap.put("Weight",pweight);
        productMap.put("Price",newPrice);
        productMap.put("Image",downloadImageUrl);
        productMap.put("location",location2);
        productMap.put("Zone",pZone);
        productMap.put("Shelf",pshelf);



        ProductRef2.child(pBar).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Intent intent = new Intent(EditProductActivity.this , HomeActivity.class );
                    Toast.makeText(EditProductActivity.this,"Product edit successfully",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
            }
        });
    }
    private  void storeBackDatabase1(){
        currentStock = Integer.parseInt(pQty) - sold;
        int pQ = Integer.parseInt(pQty);
        d =Double.parseDouble(pdiscount);
        newPrice = Double.parseDouble(pPrice);
        String location2 = pZone+"_"+pshelf;
        final HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("Barcode",pBar);
        productMap.put("Name",pName);
        productMap.put("Category",pCategory);
        productMap.put("Desc",pDesc);
        productMap.put("OpeningStock",pQ);
        productMap.put("Sold",pSold);
        productMap.put("Discount",d);
        productMap.put("CurrentStock",currentStock);
        productMap.put("Weight",pweight);
        productMap.put("Price",newPrice);
        productMap.put("Image",pimage1);
        productMap.put("location",location2);
        productMap.put("Zone",pZone);
        productMap.put("Shelf",pshelf);


        ProductRef2.child(pBar1).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Intent intent = new Intent(EditProductActivity.this , HomeActivity.class );
                    Toast.makeText(EditProductActivity.this,"Product edit successfully",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    String message = task.getException().toString();
                    Toast.makeText(EditProductActivity.this,"Error :" +message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void EditProductDetail() {
        ProductRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                     pBar1 = dataSnapshot.child("Barcode").getValue().toString();
                     pname1 = dataSnapshot.child("Name").getValue().toString();
                     pimage1 = dataSnapshot.child("Image").getValue().toString();
                     pdesc1 = dataSnapshot.child("Desc").getValue().toString();
                     pqty1 = dataSnapshot.child("OpeningStock").getValue().toString();
                     pprice1 = Double.parseDouble(dataSnapshot.child("Price").getValue().toString());
                     pcategory1 = dataSnapshot.child("Category").getValue().toString();
                     pweight1 = dataSnapshot.child("Weight").getValue().toString();
                     pdiscount1 = Double.parseDouble(dataSnapshot.child("Discount").getValue().toString());
                     pSold = Integer.parseInt(dataSnapshot.child("Sold").getValue().toString());
                     pzone1 =dataSnapshot.child("Zone").getValue().toString();
                     pshelf1 = dataSnapshot.child("Shelf").getValue().toString();



                    ArrayAdapter<CharSequence> adapterZone = ArrayAdapter.createFromResource(EditProductActivity.this, R.array.zone, android.R.layout.simple_spinner_item);
                    adapterZone.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    zone1.setAdapter(adapterZone);
                    zone1.setSelection(adapterZone.getPosition(pzone1));

                    ArrayAdapter<CharSequence> adapterShelf = ArrayAdapter.createFromResource(EditProductActivity.this, R.array.Shelf, android.R.layout.simple_spinner_item);
                    adapterShelf.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    shelf1.setAdapter(adapterShelf);
                    shelf1.setSelection(adapterShelf.getPosition(pshelf1));

                    txtProductBarcode.setText(pBar1);
                    txtProductName.setText(pname1);
                    txtProductDiscount.setText(""+pdiscount1);
                    txtProductWeight.setText(pweight1);
                    txtProductDescription.setText(pdesc1);
                    txtProductOpeningQty.setText(pqty1);
                    txtProductPrice.setText(""+pprice1);

                    ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(EditProductActivity.this, R.array.pcategory, android.R.layout.simple_spinner_item);
                    adapterCategory.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    txtProductCat.setAdapter(adapterCategory);
                    txtProductCat.setSelection(adapterCategory.getPosition(pcategory1));

                    Picasso.get().load(pimage1).into(upImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
