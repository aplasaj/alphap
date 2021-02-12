package com.example.antonio.alphap;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    public static final String TAG = NewPallet.class.getSimpleName();
    String selectedClass2="0";
    EditText duzina1;
    EditText duzina2;
    EditText debljina;
    EditText odabraniID;
    String potpuniOdabraniID;
    String provjeraZauzetostiIDa="mrkva";
 //   ProgressDialog mProgressDialog;
    String odabraniIDstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pallet);
      //  BackFromNewPallet();
//        insPlank();

       // odabraniID=(EditText)findViewById(R.id.odabraniIDedittext);
//        odabraniID.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                odabraniIDstring=odabraniID.getText().toString();
//                potpuniOdabraniID=odabraniIDstring+selectedClass2;
//             //   new NewPallet.idcheck().execute();
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });

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
                potpuniOdabraniID=odabraniIDstring+selectedClass2;
              //  new NewPallet.idcheck().execute();
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
public void preUpload(View view ) {

}
    public void Upload(View view){
     //   odabraniID=(EditText)findViewById(R.id.odabraniIDedittext);
      //  odabraniIDstring=odabraniID.getText().toString();
      //  potpuniOdabraniID=odabraniIDstring+selectedClass2;

      //  new NewPallet.idcheck().execute();

        duzina1 = (EditText)findViewById(R.id.plankl1);
        duzina2 = (EditText)findViewById(R.id.duzina2);
        debljina=(EditText)findViewById(R.id.debljinaedittext);


        String duz11=duzina1.getText().toString();
        String duz22=duzina2.getText().toString();
        String deb11=debljina.getText().toString();




        String type="uploadpallet";

        if ((duz11.equals(""))||(duz22.equals(""))||(deb11.equals(""))||(selectedClass2.equals("0"))){
            AlertDialog.Builder falipodataka = new AlertDialog.Builder(NewPallet.this);
            falipodataka.setMessage("Niste unijeli sve podatke palete. Molimo unesite sve tražene podatke i pokušajte ponovno.");
            falipodataka.setTitle("Greška kod izrade nove palete");
            falipodataka.setPositiveButton("OK", null);
            falipodataka.setCancelable(true);
            falipodataka.create().show();
            falipodataka.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
        }
        else {
            if (provjeraZauzetostiIDa.equals("mrkva"))
            {
                BackgroundWorker backgroundWorker = new BackgroundWorker(this); //this je context
                backgroundWorker.execute(type,duz11,duz22,deb11,selectedClass2,potpuniOdabraniID);

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
                                intent33.putExtra("UneseniID",potpuniOdabraniID);
                                //intent33.putExtra("idsifra", thjgfgh);

                                duzina1.setText("");
                                debljina.setText("");
                                provjeraZauzetostiIDa="mrkva";
                                startActivity(intent33);


                            }
                        })
                        .setNegativeButton("Povratak u glavni izbornik", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent3343 = new Intent("com.example.antonio.alphap.AfterLogin");
                                startActivity(intent3343);
                            }
                        });
                AlertDialog alert = newpalletcreated.create();
                alert.setTitle("Obavijest:");
                alert.show();
            }
            else
            {
                provjeraZauzetostiIDa="mrkva";
                AlertDialog.Builder zauzetID = new AlertDialog.Builder(NewPallet.this);
                zauzetID.setMessage("Paleta nije kreirana jer već postoji paleta istog broja i klase ("+potpuniOdabraniID+")." +
                        " Molimo provjerite broj i klasu palete ili provjerite je li paleta koju pokušavate kreirati već kreirana");
                zauzetID.setTitle("Paleta već postoji");
                zauzetID.setPositiveButton("OK", null);
                zauzetID.setCancelable(true);
                zauzetID.create().show();
                zauzetID.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
    }}

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

    public class idcheck extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public idcheck(){
//            mProgressDialog = new ProgressDialog(NewPallet.this);
//            mProgressDialog.setMessage("Molimo sačekajte");
//            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
//            mProgressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://bagremozalj.hr/idcheck.php";
        //    mProgressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                //dobavlja id palete koju zatvaramo

                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("idpalete1","UTF-8")+"="+URLEncoder.encode(String.valueOf(potpuniOdabraniID),"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;


            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException: "+e); //print exception message to log
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e); //print exception message to log
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //set the result which is returned by doInBackground() method to result textView

            provjeraZauzetostiIDa=result;
         //   mProgressDialog.dismiss();

        }
    }
}
