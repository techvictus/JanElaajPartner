package com.janelaaj.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.activitys.ProfileRegistrationActivity;
import com.janelaaj.activitys.TimmingEditActivity_FromHome;
import com.janelaaj.adapter.TimeEditExpandableListAdapter2;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface EditDialogListener {
        void receiveResult(String inputText);
    }

    String from_or_to;
    String pos;
    String id;
    String HOUR;
    String MIN;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
         from_or_to = getArguments().getString("from_or_to");
        pos = getArguments().getString("pos");
        id = getArguments().getString("id");

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



        String HOUR,MIN;
        if(hourOfDay<10)
        {
            HOUR = "0"+String.valueOf(hourOfDay);
        }
        else
        {
            HOUR = String.valueOf(hourOfDay);
        }

        if(minute<10)
        {
            MIN = "0" + String.valueOf(minute);
        }
        else
        {
            MIN = String.valueOf(minute);
        }



        String Time  = HOUR + MIN;
        Log.i("time",Time);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();

        if(from_or_to.equals("From")){
            editor.putString("fromtime"+pos+id,Time);
        }else{
            editor.putString("totime"+pos+id,Time);
        }
        editor.commit();

    }


}