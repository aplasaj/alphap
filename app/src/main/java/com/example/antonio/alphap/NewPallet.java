package com.example.antonio.alphap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class NewPallet extends AppCompatActivity {
    private static Button backfromnewpallet;
    private static Button gotoplankinsert;
    String selectedClass2="0";
    EditText duzina1;
    EditText duzina2;
    EditText debljina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pallet);
        BackFromNewPallet();
//        insPlank();

        RadioGroup  group= (RadioGroup)findViewById(R.id.palletclasses);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
              //  final TextView radioid =(TextView)findViewById(R.id.radioid);
                if (index==0){
                //    radioid.setText("AB");
                    selectedClass2="AB";
                }
                if (index==1){
                 //   radioid.setText("CD");
                    selectedClass2="CD";
                }
                if (index==2){
                  //  radioid.setText("ABC");
                    selectedClass2="ABC";
                }
                if (index==3){
                   // radioid.setText("ABCD");
                    selectedClass2="ABCD";
                }
            }
        });


    }

    public void BackFromNewPallet() {
        backfromnewpallet=(Button)findViewById(R.id.Back1);
        backfromnewpallet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent("com.example.antonio.alphap.AfterLogin");
                        startActivity(intent3);
                    }
                }
        );

    }

    public void Upload(View view){
        duzina1 = (EditText)findViewById(R.id.plankl1);
        duzina2 = (EditText)findViewById(R.id.duzina2);
        debljina=(EditText)findViewById(R.id.debljinaedittext);

        String duz11=duzina1.getText().toString();
        String duz22=duzina2.getText().toString();
        String deb11=debljina.getText().toString();
        String type="uploadpallet";


        if ((duz11.equals(""))||(duz22.equals(""))||(deb11.equals(""))||(selectedClass2.equals("0"))){
            Toast.makeText(NewPallet.this, "Niste unijeli sve podatke. Unesite sve tražene podatke palete " +
                            "i pokušajte ponovno.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else {
        BackgroundWorker backgroundWorker = new BackgroundWorker(this); //this je context
        backgroundWorker.execute(type,duz11,duz22,deb11,selectedClass2);

        AlertDialog.Builder newpalletcreated = new AlertDialog.Builder(NewPallet.this);
        newpalletcreated.setMessage("Nova paleta kreirana. Zapoceti unos dasaka u paletu ?")
                .setCancelable(false)
                .setPositiveButton("Nastavi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        duzina1 = (EditText)findViewById(R.id.plankl1);
                        duzina2 = (EditText)findViewById(R.id.duzina2);
                        debljina=(EditText)findViewById(R.id.debljinaedittext);
                        int duz1=Integer.parseInt(duzina1.getText().toString());
                        int duz2=Integer.parseInt(duzina2.getText().toString());
                        int deb=Integer.parseInt(debljina.getText().toString());



                        Intent intent33 = new Intent("com.example.antonio.alphap.Plank");
                        intent33.putExtra("Duzina1", duz1);
                        intent33.putExtra("Duzina2", duz2);
                        intent33.putExtra("Klasa", selectedClass2);
                        intent33.putExtra("Debljina", deb);
                        duzina1.setText("");

                        debljina.setText("");
                        startActivity(intent33);

                    }
                })
                .setNegativeButton("Povratak u glavni izbornik", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = newpalletcreated.create();
        alert.setTitle("Obavijest:");
        alert.show();
    }
    }

//    public void insPlank() {
//        gotoplankinsert=(Button)findViewById(R.id.gotoinsertpallet);
//        gotoplankinsert.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        duzina1 = (EditText)findViewById(R.id.plankl1);
//                        duzina2 = (EditText)findViewById(R.id.duzina2);
//                        debljina=(EditText)findViewById(R.id.debljinaedittext);
//                        int duz1=Integer.parseInt(duzina1.getText().toString());
//                        int duz2=Integer.parseInt(duzina2.getText().toString());
//                        int deb=Integer.parseInt(debljina.getText().toString());
//
//                        Intent intent33 = new Intent("com.example.antonio.alphap.Plank");
//                        intent33.putExtra("Duzina1", duz1);
//                        intent33.putExtra("Duzina2", duz2);
//                        intent33.putExtra("Klasa", selectedClass2);
//                        intent33.putExtra("Debljina", deb);
//                        startActivity(intent33);

//
//
//                    }
//
//                }
//        );
//
//    }


}
