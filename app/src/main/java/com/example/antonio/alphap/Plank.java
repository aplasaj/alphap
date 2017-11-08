package com.example.antonio.alphap;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Plank extends AppCompatActivity {
    EditText duzinadaskeR;
    EditText sirinadaskeR;
    ProgressDialog mProgressDialog;
    String JSON_RESPONSE;
    TextView naslov;
    String duzinadaskeizradia;
    public static final String TAG = Plank.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
        Bundle duzine = getIntent().getExtras();
        int duzina1=(int)duzine.get("Duzina1");
        int duzina2=(int)duzine.get("Duzina2");
        int debljina=(int)duzine.get("Debljina");
        final String klasapalete=(String) duzine.get("Klasa");
        String resultidstring = getPalID();

        naslov = (TextView)findViewById(R.id.textView10);
        naslov.setText("UNOS DASAKA U PALETU BROJ  "+ resultidstring);

        addRadioButtons(duzina1,duzina2);

        String resultid = getPalID();    //dobavljam id palete

        final TextView txtview5 =(TextView)findViewById(R.id.idpreview);
          txtview5.setText("Paleta je klase "+ klasapalete + "duljine "+ duzina1+ "-"+  duzina2+ "debljine "+ debljina);
    }
public String getPalID() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);    //dobavljam id palete
    String resultid = prefs.getString("resultid", "no id"); //no id: default value
    return resultid;
}


 public void Unosdaske(View view){
     String resultidstring = getPalID();    //dobavljam id palete


   //  duzinadaskeR=(EditText)findViewById(R.id.UnosDuzineDaskeEditText);
     sirinadaskeR=(EditText)findViewById(R.id.UnosSirineDaskeEditText);

   //  String duzinadaskeString=duzinadaskeR.getText().toString();
     String sirinadaskeString=sirinadaskeR.getText().toString();

     int duzinadaske=Integer.parseInt(duzinadaskeizradia);
     int sirinadaske=Integer.parseInt(sirinadaskeString);
     int  resultid= Integer.parseInt(resultidstring);
     int type=9999;
     Toast.makeText(Plank.this,"Daska unešena",
             Toast.LENGTH_SHORT).show();
   //  duzinadaskeR.setText("");
     sirinadaskeR.setText("");

     BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(this); //this je context
     backgroundWorker2.execute(type,duzinadaske,sirinadaske,resultid);



 }

    public void Zatvaranjepalete(View view){
        AlertDialog.Builder closingpalletdialog = new AlertDialog.Builder(Plank.this);
        closingpalletdialog.setMessage("Jeste li sigurni da želite zatvoriti paletu? Nakon zatvaranja palete unos dasaka u paletu više nije moguć")
                .setCancelable(false)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        new Plank.getJsonResponse().execute();
                    }
                })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = closingpalletdialog.create();
        alert.setTitle("Obavijest:");
        alert.show();
    }


    public void addRadioButtons(int duzina1, int duzina2) {

        for (int row = 0; row < 1; row++) {
            final RadioGroup duzineradio = new RadioGroup(this);
            duzineradio.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = duzina1; i <= duzina2; i = i + 10) {
                RadioButton rdbtn = new RadioButton(this);
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("" + rdbtn.getId());
                duzineradio.addView(rdbtn);

            }
            ((ViewGroup) findViewById(R.id.radiogroup)).addView(duzineradio);
            duzineradio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int oznaceno=duzineradio.getCheckedRadioButtonId();
                     duzinadaskeizradia=Integer.toString(oznaceno);

                }
            });
        }
    }


    public class getJsonResponse extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getJsonResponse(){
            mProgressDialog = new ProgressDialog(Plank.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/zatvaranjepalete.php";
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                 String resultidstring = getPalID();
                //dobavlja id palete koju zatvaramo

                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(resultidstring,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_RESPONSE = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_RESPONSE + "\n");
                }

                inputStream.close();
                bufferedReader.close();
                httpURLConnection.disconnect();

                return null;

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
            mProgressDialog.dismiss();
         //   duzinadaskeR.setText("");
            sirinadaskeR.setText("");
        }
    }
}







