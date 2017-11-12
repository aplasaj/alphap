package com.example.antonio.alphap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AfterLogin extends AppCompatActivity {
    private static Button logoutstatic;
    private static Button newpalletstatic;
    private static Button closedpalletsstatic;
    private static Button openpalletsstatic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        signout();
        newPallet();
        ClosePal();
        OpenPallets();
    }
    public void signout() {
        logoutstatic=(Button)findViewById(R.id.signoutbutton);
        logoutstatic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = getBaseContext().getPackageManager()
                                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
        );

    }
    public void newPallet() {
        newpalletstatic=(Button)findViewById(R.id.openNewPalletbtn);
        newpalletstatic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            Toast.makeText(AfterLogin.this,"Otvorena nova paleta",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("com.example.antonio.alphap.NewPallet");
                            startActivity(intent);
                    }
                }
        );

    }
    public void ClosePal() {
        closedpalletsstatic=(Button)findViewById(R.id.ClosePalbtn);
        closedpalletsstatic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent10 = new Intent("com.example.antonio.alphap.ClosedPal");
                        startActivity(intent10);
                    }
                }
        );

    }
    public void OpenPallets() {
        openpalletsstatic=(Button)findViewById(R.id.OpenedPalletsbtn);
        openpalletsstatic.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent65 = new Intent("com.example.antonio.alphap.OpenPal");
                        startActivity(intent65);
                    }
                }
        );

    }
}
