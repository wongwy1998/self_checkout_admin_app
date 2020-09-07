package my.edu.tarc.self_checkout_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class HomeActivity extends AppCompatActivity {

    SpaceNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.space);



        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_home_black_24dp));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_report));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_inventory));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.logout));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        getSupportFragmentManager().beginTransaction().addToBackStack(null);

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent i = new Intent(HomeActivity.this, ScanCode2Activity.class);
                startActivity(i);
                navigationView.setCentreButtonSelectable(true);
            }


            @Override
            public void onItemClick(int itemIndex,String ItemName) {
                switch (itemIndex) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportMenuActivity()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new  ProductFragment()).commit();
                        break;
                    case 3:

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class );
                        startActivity(intent);
                        break;


                }
            }

            @Override
            public void onItemReselected(int itemIndex,String ItemName) {
                switch (itemIndex) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportMenuActivity()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProductFragment()).commit();
                        break;
                    case 3:
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class );
                        startActivity(intent);

                        break;


                }
            }
        });

}



}
