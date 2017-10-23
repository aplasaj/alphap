package com.example.antonio.alphap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClosedPal extends AppCompatActivity {
private static Button backfromclosepallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed_pal);
        BackFromNewPallet();
    }
    public void BackFromNewPallet() {
        backfromclosepallet=(Button)findViewById(R.id.backfromclopal);
        backfromclosepallet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent("com.example.antonio.alphap.AfterLogin");
                        startActivity(intent3);
                    }
                }
        );

    }
}
