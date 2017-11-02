package com.example.antonio.alphap;

import android.content.Context;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.view.View;

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

/**
 * Created by Antonio on 10/26/17.
 */

public class BackgroundWorker2 extends AsyncTask<Integer, Void, String> {
    Context context2;
    AlertDialog alertDialog2;
    BackgroundWorker2 (Context ctx2) {
        context2 = ctx2;
    }
    @Override
    protected String doInBackground(Integer... params) {
        int type = params[0];
        int duzinadaske = params[1];
        int sirinadaske = params[2];
        int resultid = params[3];

        String login_url = "http://www.tehnooz.hr/insertplanks.php";
      //  if(type==9999) {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("duzinadaske","UTF-8")+"="+URLEncoder.encode(String.valueOf(duzinadaske),"UTF-8")+"&"
                        +URLEncoder.encode("sirinadaske","UTF-8")+"="+URLEncoder.encode(String.valueOf(sirinadaske),"UTF-8")
                        +"&"
                        +URLEncoder.encode("idpalete","UTF-8")+"="+URLEncoder.encode(String.valueOf(resultid),"UTF-8");
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
      //  }
        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
         alertDialog2 = new AlertDialog.Builder(context2).create();
        alertDialog2.setTitle("Unos daske uspješan");
         alertDialog2.show();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


}
