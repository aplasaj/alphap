package com.example.antonio.alphap;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Plank extends AppCompatActivity {
    EditText duzinadaskeR;
    EditText sirinadaskeR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plank);
        Bundle duzine = getIntent().getExtras();
        int duzina1=(int)duzine.get("Duzina1");
        int duzina2=(int)duzine.get("Duzina2");
        int debljina=(int)duzine.get("Debljina");
        final String klasapalete=(String) duzine.get("Klasa");





    String resultid = getPalID();    //dobavljam id palete
     //   OVO ISPISUJE PODATKE O PALETI
//        final TextView txtview1 =(TextView)findViewById(R.id.plankl1);
//        txtview1.setText(Integer.toString(duzina1));
//        final TextView txtview2 =(TextView)findViewById(R.id.plankl2);
//        txtview2.setText(Integer.toString(duzina2));
//        final TextView txtview3 =(TextView)findViewById(R.id.klasa2view);
//        txtview3.setText(klasapalete);
//        final TextView txtview4 =(TextView)findViewById(R.id.debljinapreview);
//        txtview4.setText(Integer.toString(debljina));
        final TextView txtview5 =(TextView)findViewById(R.id.idpreview);
          txtview5.setText("Unos dasaka u paletu broj "+ resultid+ ",klase "+ klasapalete+ " ,duljine "+ duzina1+ "-"+  duzina2+ " i debljine "+ debljina);
    }
public String getPalID() {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);    //dobavljam id palete
    String resultid = prefs.getString("resultid", "no id"); //no id: default value
    return resultid;
}


 public void Unosdaske(View view){
     String resultidstring = getPalID();    //dobavljam id palete


     duzinadaskeR=(EditText)findViewById(R.id.UnosDuzineDaskeEditText);
     sirinadaskeR=(EditText)findViewById(R.id.UnosSirineDaskeEditText);

     String duzinadaskeString=duzinadaskeR.getText().toString();
     String sirinadaskeString=sirinadaskeR.getText().toString();

     int duzinadaske=Integer.parseInt(duzinadaskeString);
     int sirinadaske=Integer.parseInt(sirinadaskeString);
     int  resultid= Integer.parseInt(resultidstring);
     int type=9999;

     BackgroundWorker2 backgroundWorker2 = new BackgroundWorker2(this); //this je context
     backgroundWorker2.execute(type,duzinadaske,sirinadaske,resultid);


 }

}







