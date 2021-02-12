package com.example.antonio.alphap;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class ClosingPallet extends AppCompatActivity {

    public static final String TAG = ClosingPallet.class.getSimpleName();
    int duzina1;
    int duzina2;
    int debljina;
    String idsifra;
    String iduneseni;
    String klasapalete;
    EditText odabraniid;
    String odabraniidstring;
    String provjeraZauzetostiIDa="mrkva";
    String loginkey="go";
    String idsifra1;
    String idinkrementalni;
    String odabraniidstringklasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing_pallet);

        Bundle duzine = getIntent().getExtras();
        duzina1=(int)duzine.get("Duzina1");
        duzina2=(int)duzine.get("Duzina2");
        debljina=(int)duzine.get("Debljina");
        idsifra=(String)duzine.get("IDotvorene");
        iduneseni=(String)duzine.get("IDuneseni");
        klasapalete=(String) duzine.get("Klasa");
        idinkrementalni=(String)duzine.get("IDinkrementalni");

        idsifra1=PreferenceManager.getDefaultSharedPreferences(this).getString("resultid", "defaultStringIfNothingFound");


        TextView klasatx=(TextView)findViewById(R.id.klasaview);
        klasatx.setText(klasapalete);

        final TextView txtview6 =(TextView)findViewById(R.id.palletpreview);
        txtview6.setText("Paleta koju želite zatvoriti je klase "+ klasapalete + " ,duljine "+ duzina1+ "-"+  duzina2+ " ,debljine "+ debljina+ ", sifre "+ idinkrementalni);

        Button closepalletbtn=(Button)findViewById(R.id.closepallet);
        closepalletbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                odabraniid=(EditText)findViewById(R.id.idsubmit);
                odabraniidstring=odabraniid.getText().toString();



                new ClosingPallet.idcheck().execute();



            }
        }  );
    }



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
            serverUrl = "http://tehnooz.hr/AlphaPv2/idcheckT.php";
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
                String post_data = URLEncoder.encode("idpalete1","UTF-8")+"="+URLEncoder.encode(String.valueOf(odabraniidstring),"UTF-8")+"&"
                        +URLEncoder.encode("klasapalete1","UTF-8")+"="+URLEncoder.encode(String.valueOf(klasapalete),"UTF-8");;
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

            Toast.makeText(ClosingPallet.this, "Status: " +provjeraZauzetostiIDa,
                    Toast.LENGTH_SHORT).show();


            if (loginkey.equals(result)) {

                AlertDialog.Builder idcheckalert = new AlertDialog.Builder(ClosingPallet.this);
                idcheckalert.setMessage("Odabrani broj palete je slobodan. Molimo još jednom potvrdite da želite zatvoriti paletu")
                        .setCancelable(false)
                        .setPositiveButton("Zatvori paletu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                new ClosingPallet.closepallet().execute();

                            }
                        })
                        .setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alert = idcheckalert.create();
                alert.setTitle("Obavijest:");
                alert.show();


            };


            //   mProgressDialog.dismiss();

        }
    }


    public class closepallet extends AsyncTask<Void,Void,String> {
        String serverUrl;
        String idsifra123="122";
        public closepallet(){
//            mProgressDialog = new ProgressDialog(NewPallet.this);
//            mProgressDialog.setMessage("Molimo sačekajte");
//            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
//            mProgressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/AlphaPv2/zatvaranjepaleteT.php";
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
                String post_data = URLEncoder.encode("idpalete1","UTF-8")+"="+URLEncoder.encode(String.valueOf(odabraniidstring),"UTF-8")+"&"
                        +URLEncoder.encode("idsifra","UTF-8")+"="+URLEncoder.encode(String.valueOf(idinkrementalni),"UTF-8");




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



            Intent intentbacktomainmenu = new Intent("com.example.antonio.alphap.AfterLogin");
            startActivity(intentbacktomainmenu);




            //   mProgressDialog.dismiss();

        }
    }

}