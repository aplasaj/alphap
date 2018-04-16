package com.example.antonio.alphap;

import android.app.Activity;
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
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PlanksFromPallet extends AppCompatActivity {
    ArrayList<String> list1 = new ArrayList<String>();
    ProgressDialog mProgressDialog;
    String JSON_RESPONSE;
    public static final String TAG = OpenPal.class.getSimpleName();
    private ListView lview;
    String idpalete;
    TextView twmain;

    String stringdaske1;
    String duljinadaske;
    String stringdaske3;
    String sirinadaske;
    String stringdaske5;
    String vrijemedaske;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planks_from_pallet);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list1);
        lview = (ListView)findViewById(R.id.listid);
        twmain= (TextView)findViewById(R.id.textView14);
        final Bundle duzine = getIntent().getExtras();
        idpalete=(String)duzine.get("idpalete");
        twmain.setText("Popis dasaka u paleti broj:"+ idpalete);
        new getJsonResponse().execute();

        String [] array = list1.toArray(new String [] {});

        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String item = (String) lview.getItemAtPosition(position);

                StringTokenizer podatciDaske = new StringTokenizer(item, "=,");
                stringdaske1 = podatciDaske.nextToken();
                duljinadaske = podatciDaske.nextToken();
                stringdaske3 = podatciDaske.nextToken();
                sirinadaske = podatciDaske.nextToken();
                stringdaske5 = podatciDaske.nextToken();
                vrijemedaske = podatciDaske.nextToken();


                android.support.v7.app.AlertDialog.Builder PlanksFromPalletDialog = new android.support.v7
                        .app.AlertDialog.Builder(PlanksFromPallet.this);
                PlanksFromPalletDialog.setMessage("DULJINA DASKE:"+duljinadaske+" ŠIRINA DASKE:"+sirinadaske)
                        .setCancelable(false)
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                new deletePlank().execute();
                                recreate();
                            }
                        })
                        .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                android.support.v7.app.AlertDialog alert = PlanksFromPalletDialog.create();
                alert.setTitle("JESTE LI SIGURNI DA ŽELITE OBRISATI DASKU?");
                alert.show();
            }
        });
    }
    public void refresh(View view){
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list1);
        lview.setAdapter(adapter);
            return;
        }
    public void nazad(View view){
      //  finish();
        Intent intent12334567 = new Intent("com.example.antonio.alphap.Plank2");
       // intent1234567.putExtra("idpalete",iduneseni);
        startActivity(intent12334567);
        return;
    }

    public class getJsonResponse extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getJsonResponse(){
            mProgressDialog = new ProgressDialog(PlanksFromPallet.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://bagremozalj.hr/popisdasakaupaleti.php";
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
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("idpalete","UTF-8")+"="+URLEncoder.encode(String.valueOf(idpalete),"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_RESPONSE = bufferedReader.readLine()) != null){

                    stringBuilder.append(JSON_RESPONSE + "\n");
                    list1.add(JSON_RESPONSE);
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

    public class deletePlank extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public deletePlank(){


        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://bagremozalj.hr/deleteplank.php";

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
                String post_data = URLEncoder.encode("duljina","UTF-8")+"="+URLEncoder.encode(String.valueOf(duljinadaske),"UTF-8")+"&"
                        +URLEncoder.encode("sirina","UTF-8")+"="+URLEncoder.encode(String.valueOf(sirinadaske),"UTF-8")
                        +"&"
                        +URLEncoder.encode("vrijeme","UTF-8")+"="+URLEncoder.encode(String.valueOf(vrijemedaske),"UTF-8");
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

}
