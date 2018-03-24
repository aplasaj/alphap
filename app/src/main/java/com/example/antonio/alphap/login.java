package com.example.antonio.alphap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class login extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton();
    }

    public  void LoginButton() {
        username = (EditText)findViewById(R.id.editusername);
        password = (EditText)findViewById(R.id.editpassword);
        login_btn = (Button)findViewById(R.id.button);

        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(username.getText().toString().equals("") &&
                                password.getText().toString().equals("")  ) {
                            Toast.makeText(login.this,"Dobrodošli",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("com.example.antonio.alphap.AfterLogin");
                            startActivity(intent);
                        } else {
                            Toast.makeText(login.this,"Korisnički podatci nisu točni",
                                    Toast.LENGTH_SHORT).show();

                            }
                        }

                    } );
                }
    }



