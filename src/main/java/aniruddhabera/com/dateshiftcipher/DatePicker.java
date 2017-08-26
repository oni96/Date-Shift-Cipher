package aniruddhabera.com.dateshiftcipher;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Primus on 23-Jun-17.
 */

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    String finalDate;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(),(MainActivity)getActivity(),year,month,day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        String temp = dayOfMonth+"/"+(month+1)+"/"+year;
        finalDate = temp;
        Toast.makeText(getContext(), finalDate, Toast.LENGTH_SHORT).show();


    }
}
