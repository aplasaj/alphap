package com.example.antonio.alphap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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


public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String duzina1 = params[1];
        String duzina2 = params[2];
        String debljina = params[3];
        String selectedClass2 = params[4];
        String odabraniID = params[5];
        String login_url = "http://tehnooz.hr/AlphaPv2/insertpalletT.php";
        if(type.equals("uploadpallet")) {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("duzina1","UTF-8")+"="+URLEncoder.encode(duzina1,"UTF-8")+"&"
                        +URLEncoder.encode("duzina2","UTF-8")+"="+URLEncoder.encode(duzina2,"UTF-8")
                        +"&"
                        +URLEncoder.encode("debljina","UTF-8")+"="+URLEncoder.encode(debljina,"UTF-8")
                        +"&"
                        +URLEncoder.encode("odabraniID","UTF-8")+"="+URLEncoder.encode(odabraniID,"UTF-8")
                        +"&"
                        +URLEncoder.encode("selectedClass2","UTF-8")+"="+URLEncoder.encode(selectedClass2,"UTF-8");
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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
//        alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        // alertDialog = new AlertDialog.Builder(context).create();
        // alertDialog.setTitle("Login Status");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("resultid", result); //InputString: from the EditText
        editor.commit();


    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }



}

