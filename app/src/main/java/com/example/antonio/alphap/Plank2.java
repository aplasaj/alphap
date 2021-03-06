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
import java.util.StringTokenizer;

public class Plank2 extends AppCompatActivity {
    EditText duzinadaskeR;
    EditText sirinadaskeR;
    EditText inputid;
    ProgressDialog mProgressDialog;
    String JSON_RESPONSE;
    TextView naslov;
    String duzinadaskeizradia="0";
    int duzina1;
    int duzina2;
    int debljina;
    String idpalete;
    String klasapalete;
    public static final String TAG = Plank.class.getSimpleName();
    Button insertplank;
    TextView last3planks;
    TextView volumetxt;
    TextView plankcount;

    int duzinadaske;
    int sirinadaske;
    int resultid;
    String iduneseni;
    String idsifra1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank2);
        Bundle duzine = getIntent().getExtras();
        duzina1=(int)duzine.get("Duzina1");
        duzina2=(int)duzine.get("Duzina2");
        debljina=(int)duzine.get("Debljina");
        idpalete=(String)duzine.get("IDotvorene");
        iduneseni=(String)duzine.get("IDuneseni");
        klasapalete=(String) duzine.get("Klasa");
        insertplank=(Button)findViewById(R.id.unosdaskeupaletuGumb);
        last3planks=(TextView)findViewById(R.id.tvlast3planks);
        volumetxt=(TextView)findViewById(R.id.twvolume);
        plankcount=(TextView)findViewById(R.id.plankcounttw);

        //idsifra1=PreferenceManager.getDefaultSharedPreferences(this).getString("resultid", "defaultStringIfNothingFound");

        naslov = (TextView)findViewById(R.id.textView10);
        naslov.setText("PALETA "+ klasapalete+debljina);

        addRadioButtons(duzina1,duzina2);

        final TextView txtview5 =(TextView)findViewById(R.id.idpreview);
        txtview5.setText("Paleta je klase "+ klasapalete + " ,duljine "+ duzina1+ "-"+  duzina2+
                " ,debljine "+ debljina +" ,sifre: "+idpalete);

        new getlast3planks().execute();
        new getvolume().execute();
        new getcount().execute();





    }



    public void Unosdaske(View view){

        EditText sirinadaskeedittext = (EditText) findViewById(R.id.UnosSirineDaskeEditText);
        String testic=sirinadaskeedittext.getText().toString();
        if ((testic.equals(""))||(duzinadaskeizradia.equals("0"))){
            Toast.makeText(Plank2.this, "Niste unijeli sve podatke. Unesite duljinu i širinu daske " +
                            "i pokušajte ponovno.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            //  duzinadaskeR=(EditText)findViewById(R.id.UnosDuzineDaskeEditText);
            sirinadaskeR = (EditText) findViewById(R.id.UnosSirineDaskeEditText);
            String sirinadaskeString = sirinadaskeR.getText().toString();
             duzinadaske = Integer.parseInt(duzinadaskeizradia);
             sirinadaske = Integer.parseInt(sirinadaskeString);



            int granicaduzina = 10;
            int granicasirina = 5;
            if ((duzinadaske < granicaduzina) || (sirinadaske < granicasirina))
            //tu se radi provjera da li je unešena premala vrijednost ili nula
            {
                Toast.makeText(Plank2.this, "Preniske vrijednosti parametara. " +
                                "Unesite pravilne podatke i pokušajte ponovno.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            int type = 9999;
            Toast.makeText(Plank2.this, "Daska unešena",
                    Toast.LENGTH_SHORT).show();
            //  duzinadaskeR.setText("");
            sirinadaskeR.setText("");
            new insertplanks().execute();
           // BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(this); //this je context
          //  backgroundWorker2.execute(type, duzinadaske, sirinadaske, resultid);
            new getlast3planks().execute();
            new getvolume().execute();
            new getcount().execute();
            return;

            //  String duzinadaskeString=duzinadaskeR.getText().toString();
        }

    }
    public void pregleddasaka(View view){
        Intent intent1234567 = new Intent("com.example.antonio.alphap.PlanksFromPallet");
        intent1234567.putExtra("idpalete",idsifra1);
        startActivity(intent1234567);
    }

    public void Zatvaranjepalete(View view){
        AlertDialog.Builder closingpalletdialog = new AlertDialog.Builder(Plank2.this);
        closingpalletdialog.setMessage("Jeste li sigurni da želite zatvoriti paletu?" +
                " Nakon zatvaranja palete unos dasaka u paletu više nije moguć")
                .setCancelable(false)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent33234 = new Intent("com.example.antonio.alphap.ClosingPallet");
                        intent33234.putExtra("Duzina1", duzina1);
                        intent33234.putExtra("Duzina2", duzina2);
                        intent33234.putExtra("Klasa", klasapalete);
                        intent33234.putExtra("Debljina", debljina);
                        intent33234.putExtra("IDinkrementalni", idpalete);

                        startActivity(intent33234);
                      //  new Plank2.getJsonResponse().execute();
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
                rdbtn.setTextAppearance(this, android.R.style.TextAppearance_Large);
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
            mProgressDialog = new ProgressDialog(Plank2.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://bagremozalj.hr/zatvaranjepalete.php";
            mProgressDialog.show();
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
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(String.valueOf(idpalete),"UTF-8");
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
            //  sirinadaskeR.setText("");
            Intent intent123 = new Intent("com.example.antonio.alphap.AfterLogin");
            startActivity(intent123);
        }
    }

    public class getlast3planks extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getlast3planks(){
            mProgressDialog = new ProgressDialog(Plank2.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/AlphaPv2/last3planksT.php";
            mProgressDialog.show();
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
                String post_data = URLEncoder.encode("idpalete","UTF-8")+"="+URLEncoder.encode(String.valueOf(idsifra1),"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_RESPONSE = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_RESPONSE + "\n");

                }

                inputStream.close();
                bufferedReader.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

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
            last3planks.setText(result);

        }
    }

    public class insertplanks extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public insertplanks(){


        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/AlphaPv2/insertplanksT.php";

        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("duzinadaske","UTF-8")+"="+URLEncoder.encode(String.valueOf(duzinadaske),"UTF-8")+"&"
                        +URLEncoder.encode("sirinadaske","UTF-8")+"="+URLEncoder.encode(String.valueOf(sirinadaske),"UTF-8")
                        +"&"
                        +URLEncoder.encode("idpalete","UTF-8")+"="+URLEncoder.encode(String.valueOf(idsifra1),"UTF-8");
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



        }
    }

    public class getvolume extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getvolume(){


        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/AlphaPv2/cubicT.php";
            mProgressDialog.show();
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
                String post_data = URLEncoder.encode("idpalete1","UTF-8")+"="+URLEncoder.encode(String.valueOf(idsifra1),"UTF-8")+"&"
                        +URLEncoder.encode("debljinapalete","UTF-8")+"="+URLEncoder.encode(String.valueOf(debljina),"UTF-8");
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

            volumetxt.setText("Kubikaža: "  +result +"m³");

        }
    }
    public class getcount extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getcount(){


        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/AlphaPv2/getcountT.php";
            mProgressDialog.show();
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
                String post_data = URLEncoder.encode("idpalete1","UTF-8")+"="+URLEncoder.encode(String.valueOf(idsifra1),"UTF-8");
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

            plankcount.setText("Dasaka: " +result );

        }
    }

    public void otherPallets(View view) {
        Intent intent111123 = new Intent("com.example.antonio.alphap.OpenPal");
        startActivity(intent111123);
    }


}






