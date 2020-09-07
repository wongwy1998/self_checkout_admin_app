package my.edu.tarc.self_checkout_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    DatabaseReference AdminRef1 = FirebaseDatabase.getInstance().getReference().child("Admin");
    DatabaseReference AdminRef2;
    TextView txtusername;
    EditText txtAddress,txtEmail,txtPhone,txtName,txtNewP,txtConP;
    String u;
    Button btnCa,btnCF;
    String uname,uaddress,uphone,uemail,uPersonname,pass;
    String uname1,uaddress1,uphone1,uemail1,uPersonname1,Npass,Cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        u = getIntent().getStringExtra("username");
        AdminRef2 = FirebaseDatabase.getInstance().getReference().child("Admin").child(u);


        txtusername =(TextView) findViewById(R.id.profileusername);
        txtName = (EditText) findViewById(R.id.profileName);
        txtEmail = (EditText) findViewById(R.id.profileEmail);
        txtAddress = (EditText) findViewById(R.id.profileAddress);
        txtPhone =(EditText) findViewById(R.id.profilePhone);
        txtConP = (EditText) findViewById(R.id.profileConfirmP);
        txtNewP = (EditText) findViewById(R.id.profileConfirmN);

        btnCa = (Button) findViewById(R.id.btnD);
        btnCF = (Button) findViewById(R.id.btnc1);
        EditProfile();
        btnCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btnCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               applyChange();
            }
        });

    }

    private void applyChange() {
        uname1= txtusername.getText().toString();
        uPersonname1 = txtName.getText().toString();
        uaddress1 = txtAddress.getText().toString();
        uphone1 = txtPhone.getText().toString();
        uemail1 = txtEmail.getText().toString();
        Npass = txtNewP.getText().toString();
        Cpass = txtConP.getText().toString();

        if(uPersonname1.equals("")){
            Toast.makeText(ProfileActivity.this,"Please fill up the Name",Toast.LENGTH_SHORT).show();
        }else if(uaddress1.equals("")) {
            Toast.makeText(ProfileActivity.this,"Please fill up the Address",Toast.LENGTH_SHORT).show();
        }else if(uphone1.equals("")) {
            Toast.makeText(ProfileActivity.this,"Please fill up the phone number",Toast.LENGTH_SHORT).show();
        }else if(uemail1.equals("")) {
            Toast.makeText(ProfileActivity.this,"Please fill up the Email",Toast.LENGTH_SHORT).show();
        }else if(Npass.equals("") && Cpass.equals("")) {
           ConfirmUpdate();
        }else{
            if(!(Npass.equals("") && Cpass.equals(""))){
                if(Cpass.equals(Npass)){
                    pass = Cpass;
                    ConfirmUpdate();
                }else
                    Toast.makeText(ProfileActivity.this,"Both Password not the same",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void EditProfile() {
        AdminRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    uname = dataSnapshot.child("username").getValue().toString();
                    uaddress= dataSnapshot.child("address").getValue().toString();
                    uphone= dataSnapshot.child("phone").getValue().toString();
                    uPersonname = dataSnapshot.child("name").getValue().toString();
                    uemail= dataSnapshot.child("email").getValue().toString();
                    pass = dataSnapshot.child("password").getValue().toString();

                    txtusername.setText(uname);
                    txtAddress.setText(uaddress);
                    txtEmail.setText(uemail);
                    txtPhone.setText(uphone);
                    txtName.setText(uPersonname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void ConfirmUpdate() {
        final HashMap<String,Object> adminMap = new HashMap<>();
        adminMap.put("username",u);
        adminMap.put("name",uPersonname1);
        adminMap.put("address",uaddress1);
        adminMap.put("email",uemail1);
        adminMap.put("phone",uphone1);
        adminMap.put("password",pass);

       AdminRef1.child(u).updateChildren(adminMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Intent intent = new Intent(ProfileActivity.this , DisplayProductFragment.class );
                    Toast.makeText(ProfileActivity.this,"Product edit successfully",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    String message = task.getException().toString();
                    Toast.makeText(ProfileActivity.this,"Error :" +message,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
