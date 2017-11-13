package com.example.antonio.alphap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.StringTokenizer;

public class OpenPal extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();

    String idotvorene ;
    String klasaotvorene ;
    String debljinaotvorene ;
    String duzina1otvorene ;
    String duzina2otvorene ;
    String isporucenootvorene;
    String datumotvorene;

   // String item;
    TextView tw11;
    private ListView lv;
    AlertDialog alertDialog3;
    Button btnfindplanks;
    TextView txtviewplankinfo;
    EditText enterplankid;
  //  String item;
    ProgressDialog mProgressDialog;
    String JSON_RESPONSE;
    public static final String TAG = OpenPal.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_pal);

        tw11=(TextView)findViewById(R.id.textView11);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        lv = (ListView)findViewById(R.id.lvid);
//
   //     btnfindplanks= (Button)findViewById(R.id.btnidfindplanks);
      //  txtviewplankinfo= (TextView) findViewById(R.id.idtxtviewplankinfo);
//
//        btnfindplanks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //call the getJsonResponse method and fetch the response from the server

                new getJsonResponse().execute();

                String [] array = list.toArray(new String [] {});

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        final String item = (String) lv.getItemAtPosition(position);
                        tw11.setText(item);
                        new getJsonResponse2().execute();

                        android.support.v7.app.AlertDialog.Builder openpalletdialog = new android.support.v7.app.AlertDialog.Builder(OpenPal.this);
                        openpalletdialog.setMessage("Želite li unositi daske u odabranu paletu? Odabrana je paleta broj: "+ item+ "  (ostale informacije o odabranoj paleti su prikazane na vrhu zaslona)" )
                                .setCancelable(false)
                                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        int iduzina1otvorene = Integer.parseInt(duzina1otvorene.toString());
                                        int iduzina2otvorene = Integer.parseInt(duzina2otvorene.toString());
                                        int iitem = Integer.parseInt(item.toString());
                                        int idebljinaotvorene = Integer.parseInt(debljinaotvorene.toString());
                                        String iklasaotvorene = klasaotvorene.toString();

                                        Intent intent333 = new Intent("com.example.antonio.alphap.Plank2");
                                        intent333.putExtra("Duzina1", iduzina1otvorene);
                                        intent333.putExtra("Duzina2", iduzina2otvorene);
                                        intent333.putExtra("Klasa", iklasaotvorene);
                                        intent333.putExtra("Debljina", idebljinaotvorene);
                                        intent333.putExtra("IDotvorene", iitem);

                                        startActivity(intent333);



//                                        Intent intent33 = new Intent("com.example.antonio.alphap.Plank");
//                                        intent33.putExtra("Duzina1", duz1);
//                                        intent33.putExtra("Duzina2", duz2);
//                                        intent33.putExtra("Klasa", selectedClass2);
//                                        intent33.putExtra("Debljina", deb);
//                                        duzina1.setText("");
//
//                                        debljina.setText("");
//                                        startActivity(intent33);

                                    }
                                })
                                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        tw11.setText(item);
                                    }
                                });
                        android.support.v7.app.AlertDialog alert = openpalletdialog.create();
                        alert.setTitle("Obavijest:");
                        alert.show();
                    }
                });

//            }
//        });
    }
    public class getJsonResponse2 extends AsyncTask<String,Void,String> {
        String serverUrl;



        public getJsonResponse2(){
            mProgressDialog = new ProgressDialog(OpenPal.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/openpalletinfo.php";
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... item) {
            try {

                String idotvorene=tw11.getText().toString();


                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
//                        "UTF-8"));
//                String post_data = URLEncoder.encode("viewplankid","UTF-8")+"="+URLEncoder.encode(enterplankidstring,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
                //     httpURLConnection.setRequestMethod("POST");
                //  httpURLConnection.setDoOutput(true);
             //   httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("viewplankid","UTF-8")+"="+URLEncoder.encode(enterplankidstring,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("idotvorene","UTF-8")+"="+URLEncoder.encode(idotvorene,"UTF-8");
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
            //  txtviewplankinfo.setText(result);
            mProgressDialog.dismiss();
            StringTokenizer tokens = new StringTokenizer(result, "!");
             idotvorene = tokens.nextToken();
             klasaotvorene = tokens.nextToken();
             debljinaotvorene = tokens.nextToken();
             duzina1otvorene = tokens.nextToken();
             duzina2otvorene = tokens.nextToken();
             isporucenootvorene = tokens.nextToken();
             datumotvorene = tokens.nextToken();
             tw11.setText("ID: "+ idotvorene + " ,DEBLJINA: " + debljinaotvorene+ " ,DUŽINA: " + duzina1otvorene+ "-" + duzina2otvorene+ "                                                                 DATUM OTVARANJA: " + datumotvorene);
//            ArrayList<String> arr_list= new ArrayList<String>();
//            String str=result.toString();
//
//            String[] str1=str.split(",");
//            for(int i=0;i<str1.length;i++){
//
//                arr_list.add(str1[i]);
//            }
//
//            System.out.println("values=="+str1);
//            System.out.println("arr_list=="+arr_list);
//
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this ,android.R.layout.simple_list_item_1, arr_list);
//
//            // Assign adapter to ListView
//            lv.setAdapter(adapter);
        }
    }
    public void refresh(View view) {
        finish();
        startActivity(getIntent());
     }



    public class getJsonResponse extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getJsonResponse(){
            mProgressDialog = new ProgressDialog(OpenPal.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/sveotvorenepalete.php";
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

//                enterplankid= (EditText) findViewById(R.id.identerplankid);
//                String enterplankidstring=enterplankid.getText().toString();


                URL url = new URL(serverUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
//                        "UTF-8"));
//                String post_data = URLEncoder.encode("viewplankid","UTF-8")+"="+URLEncoder.encode(enterplankidstring,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
                //     httpURLConnection.setRequestMethod("POST");
                //  httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("viewplankid","UTF-8")+"="+URLEncoder.encode(enterplankidstring,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_RESPONSE = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_RESPONSE + "\n");
                    list.add(JSON_RESPONSE);
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
          //  txtviewplankinfo.setText(result);
            mProgressDialog.dismiss();

//            ArrayList<String> arr_list= new ArrayList<String>();
//            String str=result.toString();
//
//            String[] str1=str.split(",");
//            for(int i=0;i<str1.length;i++){
//
//                arr_list.add(str1[i]);
//            }
//
//            System.out.println("values=="+str1);
//            System.out.println("arr_list=="+arr_list);
//
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this ,android.R.layout.simple_list_item_1, arr_list);
//
//            // Assign adapter to ListView
//            lv.setAdapter(adapter);
        }
    }




    }