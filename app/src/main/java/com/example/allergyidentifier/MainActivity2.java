package com.example.allergyidentifier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText reg_name ,email, password,gender, allergens,repass;
    Button register,exist;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        reg_name=(EditText) findViewById(R.id.editTextTextPersonName);
        email=(EditText) findViewById(R.id.editTextTextEmailAddress);
        password=(EditText) findViewById(R.id.editTextTextPassword);
        exist=(Button) findViewById(R.id.back);
        repass=findViewById(R.id.confirmation);
        gender=(EditText) findViewById(R.id.gender);
        allergens=(EditText) findViewById(R.id.allergen);
        register =(Button) findViewById(R.id.signUP);

        //Database

        dbHelper=new DbHelper(getApplicationContext());

        exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity2.this,MainActivity.class) ;
                Bundle b;
                b = ActivityOptions.makeSceneTransitionAnimation(MainActivity2.this).toBundle();
                startActivity(i,b);

            }
        });

    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name1=  reg_name.getText().toString();
            String email1=  email.getText().toString();
            String password1=  password.getText().toString();
            String gender1=  gender.getText().toString();
            String repass1=repass.getText().toString();
            String allergen1=  allergens.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (name1.equals("")) {
                reg_name.setError("Username not valid");
                Toast.makeText(MainActivity2.this, "Please complete whole form or put right email", Toast.LENGTH_SHORT).show();
            }else if (!email.equals(emailPattern)&&!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                email.setError("Not valid E-mail");
                Toast.makeText(MainActivity2.this, "Email in valid", Toast.LENGTH_SHORT).show();
            }
            else if (password1.equals("") || password1.equals(" ") || password1.length() <= 7) {
                password.setError("Password more than seven");
                Toast.makeText(MainActivity2.this, "Please enter the password", Toast.LENGTH_SHORT).show();
            } else if (repass1.equals("")) {
                repass.setError("Not Valid");
                Toast.makeText(MainActivity2.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            }else {
                if (password1.equals(repass1)){

                    boolean b = dbHelper.registerUserHelper(name1,email1,password1,gender1,allergen1);
                        if(b==true) {
                            Toast.makeText(MainActivity2.this, "user register successfully", Toast.LENGTH_SHORT).show();

                            reg_name.setText("");
                            email.setText("");
                            password.setText("");
                            gender.setText("");
                            allergens.setText("");
                        }
                        else{
                            Toast.makeText(MainActivity2.this,"Error .....!",Toast.LENGTH_SHORT).show();
                    }}

                    else {
                        repass.setError("Not Match");
                        Toast.makeText(MainActivity2.this, "Password not matching", Toast.LENGTH_SHORT).show();
                    }
        }}
    });
    }

}