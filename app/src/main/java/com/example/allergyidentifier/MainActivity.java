package com.example.allergyidentifier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button loginb, registerb;
    EditText log_email, log_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginb= findViewById(R.id.signin_btn);
        registerb = findViewById(R.id.create);
        log_email = findViewById(R.id.username_et);
        log_pass = findViewById(R.id.password_et);

        registerb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this, MainActivity2.class);
                Bundle b;
                b = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
                startActivity(i,b);
            }
        });



    }
    public void loginUser(View view){

        String emaill=log_email.getText().toString();
        String passl=log_pass.getText().toString();

        DbHelper dbHelper=new DbHelper(this);
        boolean loggin =dbHelper.login(emaill,passl);
        if(loggin){

            Intent intent=new Intent(MainActivity.this,MainActivity3.class);
            intent.putExtra("key_email",emaill);
            Bundle bi;
            bi = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
            startActivity(intent,bi);
        }
        else
        {
            Toast.makeText(this, "login is not  successfully", Toast.LENGTH_SHORT).show();
        }
    }
}