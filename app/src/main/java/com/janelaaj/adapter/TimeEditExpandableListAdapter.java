package com.janelaaj.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.activitys.TimmingEditActivity;
import com.janelaaj.fragment.TimePickerFragment;
import com.janelaaj.model.ManageLocation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 *
 * @author Arshil Khan.
 */

public class TimeEditExpandableListAdapter extends BaseExpandableListAdapter{

    private Context _context;
    private List<String> _listDataHeader;
    TextView lblListHeader;// header titles
    ArrayList<String> Mon;
    ArrayList<String> Tue;
    ArrayList<String> Wed;
    ArrayList<String> Thu;
    ArrayList<String> Fri;
    ArrayList<String> Sat;
    ArrayList<String> Sun;



    ArrayList<String> MonF;
    ArrayList<String> TueF;
    ArrayList<String> WedF;
    ArrayList<String> ThuF;
    ArrayList<String> FriF;
    ArrayList<String> SatF;
    ArrayList<String> SunF;


    public  ArrayList<ManageLocation.LOC> locationinfo;
    final Integer m=0;
    Integer t=0;
    Integer w=0;
    Integer th=0;
    Integer f=0;
    Integer sa=0;
    Integer su=0;
    String id;
    String total = "";




    private HashMap<String, List<String>> _listDataChild;

    public TimeEditExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData,ArrayList<String> Mon,ArrayList<String> Tue,ArrayList<String> Wed,ArrayList<String> Thu,ArrayList<String> Fri,ArrayList<String> Sat,ArrayList<String> Sun,String id,ArrayList<String> MonF,ArrayList<String> TueF,ArrayList<String> WedF,ArrayList<String> ThuF,ArrayList<String> FriF,ArrayList<String> SatF,ArrayList<String> SunF) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.Mon = Mon;
        this.Tue = Tue;
        this.Wed = Wed;
        this.Thu = Thu;
        this.Fri = Fri;
        this.Sat = Sat;
        this.Sun = Sun;
        this.MonF = MonF;
        this.TueF = TueF;
        this.WedF = WedF;
        this.ThuF = ThuF;
        this.FriF = FriF;
        this.SatF = SatF;
        this.SunF = SunF;
        this.id = id;
    }

    public TimeEditExpandableListAdapter(Context _context) {
        this._context = _context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.edittimelist_groupfromregistration, null);
        }

        lblListHeader = convertView.findViewById(R.id.lblListHeader);
        ImageView deleteButton = convertView.findViewById(R.id.deleteButton);




        if (isExpanded) {
            convertView.findViewById(R.id.expand).setVisibility(View.GONE);
            convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.listExpandLayout).setBackgroundResource(R.drawable.login_border);
            lblListHeader.setTextColor(Color.parseColor("#ffffff"));
            convertView.findViewById(R.id.timeFillLayout).setVisibility(View.VISIBLE);
            lblListHeader.setVisibility(View.GONE);
        } else {
            convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
            convertView.findViewById(R.id.listExpandLayout).setBackgroundColor(Color.parseColor("#C2C2C2"));
            lblListHeader.setTextColor(Color.parseColor("#808080"));
            convertView.findViewById(R.id.timeFillLayout).setVisibility(View.GONE);
            lblListHeader.setVisibility(View.VISIBLE);

        }


        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }


    @Override
    public View getChildView( int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.edittimelist_item, null);

        }


        final String pos = String.valueOf(groupPosition);

        final TextView monday =  convertView.findViewById(R.id.mon);
        final TextView tuesday = convertView.findViewById(R.id.tue);
        final TextView wednesday =  convertView.findViewById(R.id.wed);
        final TextView thursday = convertView.findViewById(R.id.thu);
        final TextView friday =  convertView.findViewById(R.id.fri);
        final TextView saturday = convertView.findViewById(R.id.sat);
        final TextView sunday = convertView.findViewById(R.id.sun);





//        CheckBox repeatCHeckBox = convertView.findViewById(R.id.repeatCHeckBox);
        final LinearLayout weekNameLayout = convertView.findViewById(R.id.weekNameLayout);
        final Button from = convertView.findViewById(R.id.from);
        final Button to = convertView.findViewById(R.id.to);

Log.i("hello", "getchildview called from reg");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String mon = preferences.getString("m"+pos+id, "0");
        String tue = preferences.getString("t"+pos+id, "0");
        String wed = preferences.getString("w"+pos+id, "0");
        String thu = preferences.getString("th"+pos+id, "0");
        String fri = preferences.getString("f"+pos+id, "0");
        String sat = preferences.getString("sa"+pos+id, "0");
        String sun = preferences.getString("su"+pos+id, "0");


        if(mon.equals("1")){
            monday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            monday.setBackgroundResource(0);
        }

        if(tue.equals("1")){
            tuesday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            tuesday.setBackgroundResource(0);
        }


        if(wed.equals("1")){
            wednesday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            wednesday.setBackgroundResource(0);
        }

        if(thu.equals("1")){
            thursday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            thursday.setBackgroundResource(0);
        }

        if(fri.equals("1")){
            friday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            friday.setBackgroundResource(0);
        }

        if(sat.equals("1")){
            saturday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            saturday.setBackgroundResource(0);
        }

        if(sun.equals("1")){
            sunday.setBackgroundResource(R.drawable.circle_bg2);
        }else{
            sunday.setBackgroundResource(0);
        }


//        final EditText frommin = convertView.findViewById(R.id.frommin);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) _context).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("from_or_to", "From");
                bundle.putString("pos",pos);
                bundle.putString("id",id);
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(manager, "TimePicker");





            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) _context).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("from_or_to", "To");
                bundle.putString("pos",pos);
                bundle.putString("id",id);
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.setArguments(bundle);
                newFragment.show(manager, "TimePicker");


            }
        });

        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String mon = preferences.getString("m"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;

                Log.i("mnblkhj","mon ki value "+mon);
                Log.i("mnblkhj","mon ka size "+String.valueOf(Mon.size()));



                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                    {
                        Integer flag=0;
                        if(mon.equals("0"))
                        {
                            if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                for(int i=0;i<Mon.size();i++)
                                {
                                    String s = Mon.get(i);
                                    String[] separated = s.split("_");
                                    String from = separated[0];
                                    String to = separated[1];

                                    if(Integer.parseInt(totime) < Integer.parseInt(from))
                                    {

                                        flag+=1;
                                    }
                                    else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                    {


                                        flag+=1;
                                    }


                                }

                                if(flag==Mon.size())
                                {
                                    Log.i("time","flag mein");
                                    Mon.add(total);
                                    MonF.add(total);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    monday.setBackgroundResource(R.drawable.circle_bg2);
                                    editor.putString("m" + pos + id, "1");
                                    editor.putString("total" + pos + id, total);
                                    mon = "1";
                                    editor.commit();
                                }
                                else
                                {
                                    Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                                }



                            }

                        }

                        else {

                            monday.setBackgroundResource(0);
                            Log.i("mnblkhj","in else");
                            SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                            editor.putString("total"+pos+id,"");
                            editor.putString("m"+pos+id,"0");
                            editor.commit();
                            while (Mon.remove(total))
                                mon = "0";

                        }
                    }

                    else
                    {
                        Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String tue = preferences.getString("t"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;

                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(tue.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            for(int i=0;i<Tue.size();i++)
                            {
                                Log.i("time",String.valueOf(Tue.size()));

                                Log.i("time","All time " + Tue.get(i));

                                String s = Tue.get(i);
                                String[] separated = s.split("_");
                                String from = separated[0];
                                String to = separated[1];


                                Log.i("time","from time " + fromtime);
                                Log.i("time","to time " + totime);
                                Log.i("time","from " + from);
                                Log.i("time","to "+ to);


                                if(Integer.parseInt(totime) < Integer.parseInt(from))
                                {

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {


                                    flag+=1;
                                }


                            }

                            if(flag==Tue.size())
                            {
                                Log.i("time","flag mein");
                                Tue.add(total);
                                TueF.add(total);
                                SharedPreferences.Editor editor = preferences.edit();
                                tuesday.setBackgroundResource(R.drawable.circle_bg2);
                                editor.putString("t" + pos + id, "1");
                                editor.putString("total" + pos + id, total);
                                tue = "1";
                                editor.commit();
                            }
                            else
                            {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    else {

                        tuesday.setBackgroundResource(0);
                        Log.i("mnblkhj","in else");
                        SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                        editor.putString("total"+pos+id,"");
                        editor.putString("t"+pos+id,"0");
                        editor.commit();
                        while (Tue.remove(total))
                            tue = "0";

                    }
                }

                else
                {
                    Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                }

            }
        });


        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String wed = preferences.getString("w"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;

                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(wed.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            for(int i=0;i<Wed.size();i++)
                            {
                                Log.i("time",String.valueOf(Wed.size()));

                                Log.i("time","All time " + Wed.get(i));

                                String s = Wed.get(i);
                                String[] separated = s.split("_");
                                String from = separated[0];
                                String to = separated[1];


                                Log.i("time","from time " + fromtime);
                                Log.i("time","to time " + totime);
                                Log.i("time","from " + from);
                                Log.i("time","to "+ to);


                                if(Integer.parseInt(totime) < Integer.parseInt(from))
                                {

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {


                                    flag+=1;
                                }


                            }

                            if(flag==Wed.size())
                            {
                                Log.i("time","flag mein");
                                Wed.add(total);
                                WedF.add(total);
                                SharedPreferences.Editor editor = preferences.edit();
                                wednesday.setBackgroundResource(R.drawable.circle_bg2);
                                editor.putString("w" + pos + id, "1");
                                editor.putString("total" + pos + id, total);
                                wed = "1";
                                editor.commit();
                            }
                            else
                            {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    else {

                        wednesday.setBackgroundResource(0);
                        Log.i("mnblkhj","in else");
                        SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                        editor.putString("total"+pos+id,"");
                        editor.putString("w"+pos+id,"0");
                        editor.commit();
                        while (Wed.remove(total))
                            wed = "0";

                    }
                }

                else
                {
                    Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String thu = preferences.getString("th"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;

                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(thu.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            for(int i=0;i<Thu.size();i++)
                            {
                                Log.i("time",String.valueOf(Thu.size()));

                                Log.i("time","All time " + Thu.get(i));

                                String s = Thu.get(i);
                                String[] separated = s.split("_");
                                String from = separated[0];
                                String to = separated[1];


                                Log.i("time","from time " + fromtime);
                                Log.i("time","to time " + totime);
                                Log.i("time","from " + from);
                                Log.i("time","to "+ to);


                                if(Integer.parseInt(totime) < Integer.parseInt(from))
                                {

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {


                                    flag+=1;
                                }


                            }

                            if(flag==Thu.size())
                            {
                                Log.i("time","flag mein");
                                Thu.add(total);
                                ThuF.add(total);
                                SharedPreferences.Editor editor = preferences.edit();
                                thursday.setBackgroundResource(R.drawable.circle_bg2);
                                editor.putString("th" + pos + id, "1");
                                editor.putString("total" + pos + id, total);
                                thu = "1";
                                editor.commit();
                            }
                            else
                            {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    else {

                        thursday.setBackgroundResource(0);
                        Log.i("mnblkhj","in else");
                        SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                        editor.putString("total"+pos+id,"");
                        editor.putString("th"+pos+id,"0");
                        editor.commit();
                        while (Thu.remove(total))
                            thu = "0";

                    }
                }

                else
                {
                    Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                }

            }
        });

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String fri = preferences.getString("f"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;
                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(fri.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            for(int i=0;i<Fri.size();i++)
                            {
                                Log.i("time",String.valueOf(Fri.size()));

                                Log.i("time","All time " + Fri.get(i));

                                String s = Fri.get(i);
                                String[] separated = s.split("_");
                                String from = separated[0];
                                String to = separated[1];


                                Log.i("time","from time " + fromtime);
                                Log.i("time","to time " + totime);
                                Log.i("time","from " + from);
                                Log.i("time","to "+ to);

                                if(Integer.parseInt(totime) < Integer.parseInt(from))
                                {

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {


                                    flag+=1;
                                }


                            }

                            if(flag==Fri.size())
                            {
                                Log.i("time","flag mein");
                                Fri.add(total);
                                FriF.add(total);
                                SharedPreferences.Editor editor = preferences.edit();
                                friday.setBackgroundResource(R.drawable.circle_bg2);
                                editor.putString("f" + pos + id, "1");
                                editor.putString("total" + pos + id, total);
                                fri = "1";
                                editor.commit();
                            }
                            else
                            {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    else {

                        friday.setBackgroundResource(0);
                        Log.i("mnblkhj","in else");
                        SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                        editor.putString("total"+pos+id,"");
                        editor.putString("f"+pos+id,"0");
                        editor.commit();
                        while (Fri.remove(total))
                            fri = "0";

                    }
                }

                else
                {
                    Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                }
            }
        });


        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String sat = preferences.getString("sa"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;

                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(sat.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            for(int i=0;i<Sat.size();i++)
                            {
                                Log.i("time",String.valueOf(Sat.size()));

                                Log.i("time","All time " + Sat.get(i));

                                String s = Sat.get(i);
                                String[] separated = s.split("_");
                                String from = separated[0];
                                String to = separated[1];


                                Log.i("time","from time " + fromtime);
                                Log.i("time","to time " + totime);
                                Log.i("time","from " + from);
                                Log.i("time","to "+ to);


                                if(Integer.parseInt(totime) < Integer.parseInt(from))
                                {

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {


                                    flag+=1;
                                }


                            }

                            if(flag==Sat.size())
                            {
                                Log.i("time","flag mein");
                                Sat.add(total);
                                SatF.add(total);
                                SharedPreferences.Editor editor = preferences.edit();
                                saturday.setBackgroundResource(R.drawable.circle_bg2);
                                editor.putString("sa" + pos + id, "1");
                                editor.putString("total" + pos + id, total);
                                sat = "1";
                                editor.commit();
                            }
                            else
                            {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    else {

                        saturday.setBackgroundResource(0);
                        Log.i("mnblkhj","in else");
                        SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                        editor.putString("total"+pos+id,"");
                        editor.putString("sa"+pos+id,"0");
                        editor.commit();
                        while (Mon.remove(total))
                            sat = "0";

                    }
                }

                else
                {
                    Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                }

            }
        });


        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sunday","sunday clicked");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String sun = preferences.getString("su"+pos+id, "0");

                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;


                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(sun.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            for(int i=0;i<Sun.size();i++)
                            {
                                Log.i("time",String.valueOf(Sun.size()));

                                Log.i("time","All time " + Sun.get(i));

                                String s = Sun.get(i);
                                String[] separated = s.split("_");
                                String from = separated[0];
                                String to = separated[1];


                                Log.i("time","from time " + fromtime);
                                Log.i("time","to time " + totime);
                                Log.i("time","from " + from);
                                Log.i("time","to "+ to);


                                if(Integer.parseInt(totime) < Integer.parseInt(from))
                                {

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {


                                    flag+=1;
                                }


                            }

                            if(flag==Sun.size())
                            {
                                Log.i("time","flag mein");
                                Sun.add(total);
                                SunF.add(total);
                                SharedPreferences.Editor editor = preferences.edit();
                                sunday.setBackgroundResource(R.drawable.circle_bg2);
                                editor.putString("su" + pos + id, "1");
                                editor.putString("total" + pos + id, total);
                                sun = "1";
                                editor.commit();
                            }
                            else
                            {
                                Toast.makeText(_context, "Please choose appropriate timings", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    else {

                        sunday.setBackgroundResource(0);
                        Log.i("mnblkhj","in else");
                        SharedPreferences.Editor editor = preferences.edit();
//                            String removing = preferences.getString("total"+pos, "");
                        editor.putString("total"+pos+id,"");
                        editor.putString("su"+pos+id,"0");
                        editor.commit();
                        while (Sun.remove(total))
                            sun = "0";

                    }
                }

                else
                {
                    Toast.makeText(_context, "Pick To And From Values", Toast.LENGTH_SHORT).show();
                }

            }
        });



//        repeatCHeckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // TODO Auto-generated method stub
//                if (buttonView.isChecked()) {
//                    weekNameLayout.setVisibility(View.VISIBLE);
//                } else {
//                    weekNameLayout.setVisibility(View.GONE);
//                }
//
//            }
//        });


        //  txtListChild.setText(childText);
        return convertView;
    }



    public ArrayList<String> getMon(){


        for(int i=0;i<Mon.size();i++)
        {
            Log.i("save",Mon.get(i));
        }

        return Mon;
    }

    public ArrayList<String> getTue(){
        return Tue;
    }

    public ArrayList<String> getWed(){
        return Wed;
    }

    public ArrayList<String> getThu(){
        return Thu;
    }

    public ArrayList<String> getFri(){
        return Fri;
    }

    public ArrayList<String> getSat(){
        return Sat;
    }

    public ArrayList<String> getSun(){
        return Sun;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        String headerTitle = (String) getGroup(groupPosition);
//        if (convertView == null) {
//            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = infalInflater.inflate(R.layout.edittimelist_group, null);
//        }
//
//        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
//        ImageView deleteButton = convertView.findViewById(R.id.deleteButton);
//
//
//        if (isExpanded) {
//            convertView.findViewById(R.id.expand).setVisibility(View.GONE);
//            convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
//            convertView.findViewById(R.id.listExpandLayout).setBackgroundResource(R.drawable.login_border);
//            lblListHeader.setTextColor(Color.parseColor("#ffffff"));
//            convertView.findViewById(R.id.timeFillLayout).setVisibility(View.VISIBLE);
//            deleteButton.setBackgroundResource(R.drawable.ic_deletewhite);
//            lblListHeader.setVisibility(View.GONE);
//        } else {
//            convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
//            convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
//            convertView.findViewById(R.id.listExpandLayout).setBackgroundColor(Color.parseColor("#C2C2C2"));
//            lblListHeader.setTextColor(Color.parseColor("#808080"));
//            convertView.findViewById(R.id.timeFillLayout).setVisibility(View.GONE);
//            deleteButton.setBackgroundResource(R.drawable.ic_dustbin);
//            lblListHeader.setVisibility(View.VISIBLE);
//
//        }
//
//
//        lblListHeader.setTypeface(null, Typeface.BOLD);
//        lblListHeader.setText(headerTitle);
//
//        return convertView;
//    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
