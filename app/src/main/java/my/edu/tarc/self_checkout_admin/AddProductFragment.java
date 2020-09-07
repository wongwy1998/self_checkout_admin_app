package my.edu.tarc.self_checkout_admin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {

    public static EditText inputBarcode;
    private String proName, proBarcode, proDesc, proweight, prodis, spinnerCat, spinnerLoc,spinnerS;
    private String proPrice;
    private String proQty;
    private EditText inputName, inputDesc, inputPrice, inputQty, inputWeight, inputDis;
    private Button btnConfirm, btnCancel, btnScan;
    private Spinner spinnerCategory, spinnerLocation,spinnerShelf;
    private ImageView upImage;
    private StorageReference iStorageRef;
    private Uri ImageUri;
    private String saveCurrentDate, saveCurrentTime;
    private String downloadImageUrl, productRandomKey;
    private DatabaseReference databaseProduct;
    private ProgressDialog loadingBar;
    private int currentStock = 0;
    private int sold = 0;
    double price,discount;
    int quantity;
    double discountedPrice;
    String location2;


    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_product, container, false);

        iStorageRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        databaseProduct = FirebaseDatabase.getInstance().getReference("Products");


        inputBarcode = (EditText) view.findViewById(R.id.inputBarcode);
        inputName = (EditText) view.findViewById(R.id.inputpName);
        inputDesc = (EditText) view.findViewById(R.id.inputDesc);
        inputPrice = (EditText) view.findViewById(R.id.inputPrice);
        inputQty = (EditText) view.findViewById(R.id.inputQty);
        inputWeight = (EditText) view.findViewById(R.id.inputWeight);
        inputDis = (EditText) view.findViewById(R.id.inputdis);

        btnConfirm = (Button) view.findViewById(R.id.btnConfirm);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnScan = (Button) view.findViewById(R.id.btnScanCode);

        upImage = (ImageView) view.findViewById(R.id.productimage);
        spinnerCategory = (Spinner) view.findViewById(R.id.pcategory);
        spinnerLocation = (Spinner) view.findViewById(R.id.plocation);
        spinnerShelf = (Spinner) view.findViewById(R.id.pShelf);

        loadingBar = new ProgressDialog(getActivity());

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();

            }
        });

        upImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence options[] = new CharSequence[]
                        {
                                "Take Photo",
                                "Choose from gallery"

                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Your Method");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {


                            Intent picintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            if (picintent.resolveActivity(getActivity().getPackageManager()) != null) {
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

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), ScanCodeActivity.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });


        return view;
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
                            Cursor cursor = getActivity().getContentResolver().query(ImageUri,
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

    private void ValidateProduct() {



        proName = inputName.getText().toString();
        proBarcode = inputBarcode.getText().toString();
        proPrice = inputPrice.getText().toString();
        proDesc = inputDesc.getText().toString();
        proweight = inputWeight.getText().toString();
        prodis = inputDis.getText().toString();
        proQty = inputQty.getText().toString();

        price = Double.parseDouble(proPrice);
        quantity = Integer.parseInt(proQty);
        discount= Double.parseDouble(prodis);

        spinnerCat = spinnerCategory.getSelectedItem().toString();
        spinnerLoc = spinnerLocation.getSelectedItem().toString();
        spinnerS = spinnerShelf.getSelectedItem().toString();

        if (TextUtils.isEmpty(proName)) {
            Toast.makeText(getActivity(), "Please fill in the Product Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proBarcode)) {
            Toast.makeText(getActivity(), "Please scan in the Product Barcode", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proDesc)) {
            Toast.makeText(getActivity(), "Please fill in the Product Description", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proPrice)) {
            Toast.makeText(getActivity(), "Please fill in the Product Price", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proQty)) {
            Toast.makeText(getActivity(), "Please fill in the Product Quantity", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proweight)) {
            Toast.makeText(getActivity(), "Please fill in the Product Weight", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(prodis)) {
            Toast.makeText(getActivity(), "Please fill in the Product discount", Toast.LENGTH_SHORT).show();

        }  else if (TextUtils.isEmpty(spinnerS)) {
            Toast.makeText(getActivity(), "Please select the Product Shelf", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(spinnerCat)) {
            Toast.makeText(getActivity(), "Please select the Product Category", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(spinnerLoc)) {
            Toast.makeText(getActivity(), "Please select the Product Location", Toast.LENGTH_SHORT).show();

        }
        else if (ImageUri == null) {
            Toast.makeText(getActivity(), "Please select the Product Image", Toast.LENGTH_SHORT).show();

        }else {
            StoreProductInfo();
        }

    }

    private void StoreProductInfo() {

        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Please Wait, we are adding the new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        currentStock = Integer.parseInt(proQty) - sold;


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = iStorageRef.child(ImageUri.getLastPathSegment() +"  "+ productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getActivity(), "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
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

                            saveProductInfoToDatabase();

                        }
                    }


                });

            }

        });
    }
    private void saveProductInfoToDatabase(){
        location2 = spinnerLoc+"_"+spinnerS;

        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("Barcode",proBarcode);
        productMap.put("date", saveCurrentDate);
        productMap.put("Time",saveCurrentTime);
        productMap.put("Name",proName);
        productMap.put("Category",spinnerCat);
        productMap.put("Desc",proDesc);
        productMap.put("OpeningStock",quantity);
        productMap.put("Sold",sold);
        productMap.put("Discount",discount);
        productMap.put("CurrentStock",currentStock);
        productMap.put("Weight",proweight);
        productMap.put("Price",price);
        productMap.put("Image",downloadImageUrl);
        productMap.put("location",location2);
        productMap.put("Zone",spinnerLoc);
        productMap.put("Shelf",spinnerS);


        databaseProduct.child(proBarcode).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Intent intent = new Intent(getActivity() , HomeActivity.class );
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(getActivity(),"Product added successfully",Toast.LENGTH_SHORT).show();


                }
                else{
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(getActivity(),"Error :" +message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

