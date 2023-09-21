package com.example.allergyidentifier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    BottomNavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        nav=findViewById(R.id.nav_bar);


        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent inten=getIntent();
            String key_value= inten.getStringExtra("key_email");

                switch (item.getItemId()) {

                    case R.id.person:
                        Intent intt = new Intent(MainActivity3.this,MainActivity5.class);
                        intt.putExtra("email",key_value);
                        Bundle b;
                        b = ActivityOptions.makeSceneTransitionAnimation(MainActivity3.this).toBundle();
                        startActivity(intt,b);
                        break;

                    case R.id.camera:
                        Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                        Bundle ab;
                        ab = ActivityOptions.makeSceneTransitionAnimation(MainActivity3.this).toBundle();
                        startActivity(intent,ab);
                        break;
                    default:
                        

                }






                return true;
            }
        });
    }
   
}