package com.example.antonio.alphap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class PlanksFromPallet extends AppCompatActivity {
    ArrayList<String> list1 = new ArrayList<String>();
    ProgressDialog mProgressDialog;
    String JSON_RESPONSE;
    public static final String TAG = OpenPal.class.getSimpleName();
    private ListView lview;
    int idpalete;
    TextView twmain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planks_from_pallet);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list1);
        lview = (ListView)findViewById(R.id.listid);
        twmain= (TextView)findViewById(R.id.textView14);
        Bundle duzine = getIntent().getExtras();
        idpalete=(int)duzine.get("idpalete");
        twmain.setText("Popis dasaka u paleti broj:"+ idpalete);
        new getJsonResponse().execute();

        String [] array = list1.toArray(new String [] {});

        lview.setAdapter(adapter);
    }
    public void refresh(View view){
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list1);
        lview.setAdapter(adapter);
            return;
        }
    public void nazad(View view){
        finish();
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
            serverUrl = "http://tehnooz.hr/popisdasakaupaleti.php";
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
}
