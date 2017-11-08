package com.example.antonio.alphap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
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

public class ViewPlanksByPalletID extends AppCompatActivity {
    AlertDialog alertDialog3;
    Button btnfindplanks;
    TextView txtviewplankinfo;
    EditText enterplankid;
    ProgressDialog mProgressDialog;
    String JSON_RESPONSE;
    public static final String TAG = ViewPlanksByPalletID.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_planks_by_pallet_id);

        btnfindplanks= (Button)findViewById(R.id.btnidfindplanks);
        txtviewplankinfo= (TextView) findViewById(R.id.idtxtviewplankinfo);

        btnfindplanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the getJsonResponse method and fetch the response from the server

                new getJsonResponse().execute();
            }
        });
    }

    public class getJsonResponse extends AsyncTask<Void,Void,String> {
        String serverUrl;
        public getJsonResponse(){
            mProgressDialog = new ProgressDialog(ViewPlanksByPalletID.this);
            mProgressDialog.setMessage("Molimo sačekajte");
            mProgressDialog.setTitle("Obrada vašeg zahtjeva");
            mProgressDialog.setCancelable(false);

        }

        @Override
        protected void onPreExecute() {
            //set the url from we have to fetch the json response
            serverUrl = "http://tehnooz.hr/jsonplankfrompalletid.php";
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                enterplankid= (EditText) findViewById(R.id.identerplankid);
                String enterplankidstring=enterplankid.getText().toString();


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
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("viewplankid","UTF-8")+"="+URLEncoder.encode(enterplankidstring,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
//                OutputStream outputStream= httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
//                bufferedWriter.write(enterplankidstring);
//                outputStream.close();
//                bufferedWriter.flush();
//                bufferedWriter.close();


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
            txtviewplankinfo.setText(result);
            mProgressDialog.dismiss();
        }
    }
}
