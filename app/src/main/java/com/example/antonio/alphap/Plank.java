package com.example.antonio.alphap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedOutputStream;
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

import javax.net.ssl.HttpsURLConnection;

public class Plank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
        Bundle duzine = getIntent().getExtras();
        int duzina1=(int)duzine.get("Duzina1");
        int duzina2=(int)duzine.get("Duzina2");
        int debljina=(int)duzine.get("Debljina");
        String resultid=(String)duzine.get("Result");
        final String klasapalete=(String) duzine.get("Klasa");

//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String resultid= (String) intent.getExtras().get("Resultid");
//                final TextView txtview5 =(TextView)findViewById(R.id.idpreview);
//                txtview5.setText(resultid);
//            }
//        }, new IntentFilter("myAction"));
//

        final TextView txtview1 =(TextView)findViewById(R.id.plankl1);
        txtview1.setText(Integer.toString(duzina1));
        final TextView txtview2 =(TextView)findViewById(R.id.plankl2);
        txtview2.setText(Integer.toString(duzina2));
        final TextView txtview3 =(TextView)findViewById(R.id.klasa2view);
        txtview3.setText(klasapalete);
        final TextView txtview4 =(TextView)findViewById(R.id.debljinapreview);
        txtview4.setText(Integer.toString(debljina));
;




    }
}
