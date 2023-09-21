package com.example.allergyidentifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {
    TextView P_name,P_email,P_gender,P_allergen;

    BottomNavigationView nav;
    Button logout;
    String emailvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        logout=findViewById(R.id.back);
        P_email =findViewById(R.id.tv_email);
        P_name = findViewById(R.id.tv_profile);
        P_gender = findViewById(R.id.tv_gender);
        P_allergen = findViewById(R.id.tv_allergens);
        nav=findViewById(R.id.nav_bar);

        Intent inte = getIntent();
        emailvalue= inte.getStringExtra("email");
        getUserDetails();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home1:
                        Intent intt = new Intent(MainActivity5.this,MainActivity3.class);
                        Bundle b;
                        b = ActivityOptions.makeSceneTransitionAnimation(MainActivity5.this).toBundle();
                        startActivity(intt,b);
                        break;

                    case R.id.camera:
                        Intent intent = new Intent(MainActivity5.this, MainActivity4.class);
                        Bundle ab;
                        ab = ActivityOptions.makeSceneTransitionAnimation(MainActivity5.this).toBundle();
                        startActivity(intent,ab);
                        break;
              

                        


                }

                return true;
            }
        });



    }

    public void getUserDetails(){
        DbHelper dbHelper=new DbHelper(MainActivity5.this);
        ArrayList<UserModel> al = dbHelper.getLoggedDetails(emailvalue);
        UserModel userModal=al.get(0);

        P_name.setText(userModal.getName());
        P_email.setText(userModal.getEmail());
        P_allergen.setText(userModal.getAllergen());
        P_gender.setText(userModal.getGender());
    }



    public void logout(View view)
    {
        startActivity(new Intent(MainActivity5.this,MainActivity.class));
    }

}