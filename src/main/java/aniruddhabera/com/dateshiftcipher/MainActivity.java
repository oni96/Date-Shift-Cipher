package aniruddhabera.com.dateshiftcipher;


import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{


    int y=0,m=0,d=0;
    String finalDate="";
    EditText result;
    AdView ads;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result= (EditText) findViewById(R.id.outputText);

        ads = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        ads.loadAd(adRequest);


    }

    public void DateChooseButton(View v){
        DatePicker datePicker = new DatePicker();
        datePicker.show(getSupportFragmentManager(),"dateTag");
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth){
        TextView dateView = (TextView) findViewById(R.id.dateview);
        dateView.setText(dayOfMonth+"/"+(month+1)+"/"+year);
        y=year;
        m=month+1;
        d=dayOfMonth;

        if(d<10 && m<10)
            finalDate = "0"+d+"/0"+m+"/"+y;
        else if (m<10)
            finalDate = d+"/0"+m+"/"+y;
        else if(d<10)
            finalDate="0"+d+"/"+m+"/"+y;
        else
            finalDate = d+"/"+m+"/"+y;

        Log.i("FINAL DATE",finalDate);
        dateView.setText(finalDate);
        Toast.makeText(this, finalDate, Toast.LENGTH_SHORT).show();
    }

    public void encodeButton(View v){
        EditText input = (EditText) findViewById(R.id.input);
        String get = input.getText().toString();

        if(get.isEmpty()==true||finalDate.isEmpty()==true)
            Toast.makeText(this, "Please correct your fields!", Toast.LENGTH_SHORT).show();

        else{
            String oP = Encoder(get);

            EditText OutputText = (EditText) findViewById(R.id.outputText);
            OutputText.setText(oP);
        }

        hideKeyboard(v);

    }

    public void decodeButton(View v){
        EditText input = (EditText) findViewById(R.id.input);
        String get = input.getText().toString();

        if(get.isEmpty()==true||finalDate.isEmpty()==true)
            Toast.makeText(this, "Please correct your fields!", Toast.LENGTH_SHORT).show();

        else{
            String oP = Decoder(get);

            EditText OutputText = (EditText) findViewById(R.id.outputText);
            OutputText.setText(oP);
        }

        hideKeyboard(v);

    }

    public String Encoder(String message){

        //message = message.toUpperCase();
        String encodeText="";

        for(int i=0;i<message.length();i++)
        {
            int temp;
            char t;

            if(message.charAt(i) <48 || (message.charAt(i) > 57 && message.charAt(i) < 65) || (message.charAt(i) > 90 && message.charAt(i) < 97) || message.charAt(i) > 122)
                encodeText = encodeText+message.charAt(i);

            else
            {
                temp = ((int)message.charAt(i)) + Character.getNumericValue(finalDate.charAt(i%8));

                if (Character.isUpperCase(message.charAt(i))) {
                    if (temp>90)
                        temp=(temp%90)+64;
                } else {
                    if(temp>122)
                        temp=(temp%122)+96;
                }

                t = (char) temp;
                encodeText = encodeText+t;
            }

        }

        return encodeText;
    }

    public String Decoder(String message){

        String encodeText="";

        for(int i=0;i<message.length();i++)
        {
            int temp;
            char t;


            if(message.charAt(i) <48 || (message.charAt(i) > 57 && message.charAt(i) < 65) || (message.charAt(i) > 90 && message.charAt(i) < 97) || message.charAt(i) > 122)
                encodeText = encodeText+message.charAt(i);
            else
            {
                temp = ((int)message.charAt(i)) - Character.getNumericValue(finalDate.charAt(i%8));

                if (temp<65)
                    temp=90-(65-temp)+1;
                else if (temp>90 && temp<97)
                    temp=122-(97-temp)+1;

                t = (char) temp;
                encodeText = encodeText+t;
            }

        }

        return encodeText;
    }

    private void hideKeyboard(View view){


        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public void OnClear(View v){
        EditText i,o;
        TextView d;
        i = (EditText) findViewById(R.id.input);
        o = (EditText) findViewById(R.id.outputText);
        d= (TextView) findViewById(R.id.dateview);

        d.setText("");
        i.setText("");
        o.setText("");
        finalDate="";
        hideKeyboard(v);

    }

    public void OnCopy(View v){
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text",result.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Message copied!", Toast.LENGTH_SHORT).show();

        hideKeyboard(v);
    }

    public void payLink(View v){
        Uri uri = Uri.parse("https://www.instamojo.com/@oni96/");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
