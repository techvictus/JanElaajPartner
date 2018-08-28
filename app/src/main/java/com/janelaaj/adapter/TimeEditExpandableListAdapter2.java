package com.janelaaj.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.activitys.QuickSetsActivity;
import com.janelaaj.activitys.TimmingEditActivity;
import com.janelaaj.activitys.TimmingEditActivity_FromHome;
import com.janelaaj.fragment.TimePickerFragment;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.QuickSets;
import com.janelaaj.model.deleteTimmingModel;
import com.janelaaj.model.TimmingEditActivityFromHomeModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class TimeEditExpandableListAdapter2 extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    TextView lblListHeader;
    String id;
    ImageView delete;
    private HashMap<String, List<String>> _listDataChild;
    ArrayList<TimmingEditActivityFromHomeModel.alltimings> LocationInfo;
    String dlmid;
    TextView timeshow;
    String Role;

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
    String location_name,location_address,location_city;
    Integer save_value;

    String from_home;
    String total = "";



    public TimeEditExpandableListAdapter2(){};


    public TimeEditExpandableListAdapter2(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData,ArrayList<TimmingEditActivityFromHomeModel.alltimings> timmings,String dlmid,ArrayList<String> Mon,ArrayList<String> Tue,ArrayList<String> Wed,ArrayList<String> Thu,ArrayList<String> Fri,ArrayList<String> Sat,ArrayList<String> Sun,ArrayList<String> MonF,ArrayList<String> TueF,ArrayList<String> WedF,ArrayList<String> ThuF,ArrayList<String> FriF,ArrayList<String> SatF,ArrayList<String> SunF,String location_name,String location_address,String location_city,Integer save_value,String from_home,String Role) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.LocationInfo = timmings;
        this.dlmid = dlmid;
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
        this.location_name = location_name;
        this.location_address = location_address;
        this.location_city = location_city;
        this.save_value = save_value;
        this.from_home= from_home;
        this.Role = Role;

    }

    public void setset(String t){
        Log.i("afrssfsseq","vhhaha");
        _listDataHeader.remove("--");
        _listDataHeader.add("pop");
//        listAdapter.notifyDataSetChanged();
    }

    public TimeEditExpandableListAdapter2(Context _context) {
        this._context = _context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        Log.i("info","getchild called");

        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.edittimelist_group, null); }

        Log.i("info","getGroupview called");




        lblListHeader = convertView.findViewById(R.id.lblListHeader);
        ImageView deleteButton = convertView.findViewById(R.id.deleteButton);


        delete = convertView.findViewById(R.id.deleteButton);



        Log.i("dasda",String.valueOf(groupPosition));
        Log.i("dasda",String.valueOf(LocationInfo.size()));

        if( groupPosition >= LocationInfo.size())
        {
            delete.setEnabled(false);

        }




        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(
                        _context);
                alert.setTitle("Confirm Deletion");
                alert.setCancelable(false);
                alert.setIcon(R.drawable.ic_dustbin);
                alert.setMessage("Are you sure you want to delete this timing?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i("asdadasd","clicked");
                        JSONArray arr = new JSONArray();

                        if(LocationInfo.get(groupPosition).getMonid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getMonid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }

                        if(LocationInfo.get(groupPosition).getTueid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getTueid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }if(LocationInfo.get(groupPosition).getWedid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getWedid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }if(LocationInfo.get(groupPosition).getThuid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getThuid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }if(LocationInfo.get(groupPosition).getFriid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getFriid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }if(LocationInfo.get(groupPosition).getSatid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getSatid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }if(LocationInfo.get(groupPosition).getSunid() != 0){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("id",LocationInfo.get(groupPosition).getSunid());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            arr.put(obj);
                        }

                        deleteTimming(arr,groupPosition);
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return;


            }
        });


        int gp = groupPosition;


        if( groupPosition < LocationInfo.size())
        {
            if (isExpanded) {
                Log.i("info","expanded called");
                convertView.setPadding(0, 0, 0, 0);
                convertView.findViewById(R.id.expand).setVisibility(View.GONE);
                convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.listExpandLayout).setBackgroundResource(R.drawable.login_border);
                lblListHeader.setTextColor(Color.parseColor("#ffffff"));
                convertView.findViewById(R.id.timeFillLayout).setVisibility(View.VISIBLE);
                deleteButton.setBackgroundResource(R.drawable.ic_deletewhite);
                //  lblListHeader.setVisibility(View.GONE);
            } else {

                Log.i("info","collapse called");
                convertView.setPadding(0, 0, 0, 20);
                convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
                convertView.findViewById(R.id.listExpandLayout).setBackgroundColor(Color.parseColor("#C2C2C2"));
                lblListHeader.setTextColor(Color.parseColor("#808080"));
                convertView.findViewById(R.id.timeFillLayout).setVisibility(View.GONE);
                deleteButton.setBackgroundResource(R.drawable.ic_dustbin);
                lblListHeader.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            if (isExpanded) {
                Log.i("info","expanded called");
                convertView.setPadding(0, 0, 0, 0);
                convertView.findViewById(R.id.expand).setVisibility(View.GONE);
                convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.listExpandLayout).setBackgroundResource(R.drawable.login_border);
                lblListHeader.setTextColor(Color.parseColor("#ffffff"));
                convertView.findViewById(R.id.timeFillLayout).setVisibility(View.VISIBLE);
                deleteButton.setBackgroundResource(R.drawable.ic_dustbindisabledgradient);
                // lblListHeader.setVisibility(View.GONE);
            } else {

                Log.i("info","collapse called");
                convertView.setPadding(0, 0, 0, 20);
                convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
                convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
                convertView.findViewById(R.id.listExpandLayout).setBackgroundColor(Color.parseColor("#C2C2C2"));
                lblListHeader.setTextColor(Color.parseColor("#808080"));
                convertView.findViewById(R.id.timeFillLayout).setVisibility(View.GONE);
                deleteButton.setBackgroundResource(R.drawable.ic_dustbindisabled);
                lblListHeader.setVisibility(View.VISIBLE);
            }
        }





        lblListHeader.setTypeface(null, Typeface.BOLD);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);

        final String fromtime = preferences.getString("fromtime"+gp+id, "");
        final String totime = preferences.getString("totime"+gp+id, "");


        timeshow = lblListHeader;
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

        if (isLastChild) {
            convertView.setPadding(0, 30, 0, 30);
        }

        Log.i("info","getChildView called");



        final String pos = String.valueOf(groupPosition);


        final TextView monday =  convertView.findViewById(R.id.mon);
        final TextView tuesday = convertView.findViewById(R.id.tue);
        final TextView wednesday =  convertView.findViewById(R.id.wed);
        final TextView thursday = convertView.findViewById(R.id.thu);
        final TextView friday =  convertView.findViewById(R.id.fri);
        final TextView saturday = convertView.findViewById(R.id.sat);
        final TextView sunday = convertView.findViewById(R.id.sun);
        final LinearLayout weekNameLayout = convertView.findViewById(R.id.weekNameLayout);
        final Button from = convertView.findViewById(R.id.from);
        final Button to = convertView.findViewById(R.id.to);
        final TextView header = convertView.findViewById(R.id.lblListHeader);
//        final TextView from_time_display = convertView.findViewById(R.id.from_time_display);
//        final TextView to_time_display = convertView.findViewById(R.id.to_time_display);


        Log.i("saadkjsad",String.valueOf(groupPosition));
        Log.i("saadkjsad",String.valueOf(LocationInfo.size()));
        Log.i("saadkjsad","___________________");


        if( groupPosition<LocationInfo.size())
        {


            from.setEnabled(false);
            to.setEnabled(false);

        }
        else
        {
            from.setEnabled(true);
            to.setEnabled(true);
        }




        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String mon = preferences.getString("m"+pos+id, "0");
        String tue = preferences.getString("t"+pos+id, "0");
        String wed = preferences.getString("w"+pos+id, "0");
        String thu = preferences.getString("th"+pos+id, "0");
        final String fri = preferences.getString("f"+pos+id, "0");
        String sat = preferences.getString("sa"+pos+id, "0");
        String sun = preferences.getString("su"+pos+id, "0");


        if(groupPosition < LocationInfo.size())
        {
            Log.i("jaiho",String.valueOf(groupPosition));
            Log.i("jaiho",String.valueOf(LocationInfo.get(groupPosition).getMon()));
            monday.setEnabled(false);
            tuesday.setEnabled(false);
            wednesday.setEnabled(false);
            thursday.setEnabled(false);
            friday.setEnabled(false);
            saturday.setEnabled(false);
            sunday.setEnabled(false);


            if (LocationInfo.get(groupPosition).getMon().equals("Y")) {
                monday.setBackgroundResource(R.drawable.circle_bg2);

            }else{
                monday.setBackgroundResource(0);
            }
            if (LocationInfo.get(groupPosition).getTue().equals("Y")) {
                tuesday.setBackgroundResource(R.drawable.circle_bg2);
            }else{
                tuesday.setBackgroundResource(0);
            }
            if (LocationInfo.get(groupPosition).getWed().equals("Y")) {
                wednesday.setBackgroundResource(R.drawable.circle_bg2);
            }else{
                wednesday.setBackgroundResource(0);
            }
            if (LocationInfo.get(groupPosition).getThu().equals("Y")) {
                thursday.setBackgroundResource(R.drawable.circle_bg2);
            }else{
                thursday.setBackgroundResource(0);
            }
            if (LocationInfo.get(groupPosition).getFri().equals("Y")) {
                friday.setBackgroundResource(R.drawable.circle_bg2);
            }else{
                friday.setBackgroundResource(0);
            }
            if (LocationInfo.get(groupPosition).getSat().equals("Y")) {
                saturday.setBackgroundResource(R.drawable.circle_bg2);
            }else{
                saturday.setBackgroundResource(0);
            }
            if (LocationInfo.get(groupPosition).getSun().equals("Y")) {
                sunday.setBackgroundResource(R.drawable.circle_bg2);
            }else{
                sunday.setBackgroundResource(0);
            }

        }else
        {
            monday.setEnabled(true);
            tuesday.setEnabled(true);
            wednesday.setEnabled(true);
            thursday.setEnabled(true);
            friday.setEnabled(true);
            saturday.setEnabled(true);
            sunday.setEnabled(true);


            if (mon.equals("1")) {
                monday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                monday.setBackgroundResource(0);
            }

            if (tue.equals("1")) {
                tuesday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                tuesday.setBackgroundResource(0);
            }


            if (wed.equals("1")) {
                wednesday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                wednesday.setBackgroundResource(0);
            }

            if (thu.equals("1")) {
                thursday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                thursday.setBackgroundResource(0);
            }

            if (fri.equals("1")) {
                friday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                friday.setBackgroundResource(0);
            }

            if (sat.equals("1")) {
                saturday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                saturday.setBackgroundResource(0);
            }

            if (sun.equals("1")) {
                sunday.setBackgroundResource(R.drawable.circle_bg2);
            } else {
                sunday.setBackgroundResource(0);
            }


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

//        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(_context);
//        if(preferences2.contains("fromtime"+pos+id))
//        {
//            final String fromtime = preferences.getString("fromtime"+pos+id, "");
//            from_time_display.setText(fromtime);
//        } else if(preferences2.contains("totime"+pos+id))
//        {
//            final String totime = preferences.getString("totime"+pos+id, "");
//            to_time_display.setText(totime);
//        }






//        The following conditions have to be checked for time validations
//                a) To time greator than from time (user input)
//                  Either b or c has to be true
//                  b) To time (user input) < Existing time slot From time
//                  c) From time (user input) > Existing time slot To time
//


        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                String mon = preferences.getString("m"+pos+id, "0");
                final String fromtime = preferences.getString("fromtime"+pos+id, "");
                final String totime = preferences.getString("totime"+pos+id, "");
                total = fromtime + '_' + totime;
                if(preferences.contains("fromtime"+pos+id) && preferences.contains("totime"+pos+id))
                {
                    Integer flag=0;
                    if(mon.equals("0"))
                    {
                        if(Integer.parseInt(fromtime) > Integer.parseInt(totime)) {
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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
                                    Log.i("asddaff","from time that is chosen" + fromtime);
                                    Log.i("asddaff","from that is stored" + from);
                                    Log.i("asddaff","to time that is chosen" + totime);
                                    Log.i("asddaff","to  time that is stored" + to);
                                    Log.i("asddaff","_________________" + to);

                                    flag+=1;
                                }
                                else if(Integer.parseInt(fromtime) > Integer.parseInt(to))
                                {

                                    Log.i("asddaff","from time that is chosen" + fromtime);
                                    Log.i("asddaff","from that is stored" + from);
                                    Log.i("asddaff","to time that is chosen" + totime);
                                    Log.i("asddaff","to  time that is stored" + to);
                                    Log.i("asddaff","___________________");
                                    flag+=1;
                                }

                            }

                            if(flag==Mon.size())
                            {

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
                        SharedPreferences.Editor editor = preferences.edit();
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
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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
                        while (Sat.remove(total))
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
                            Toast.makeText(_context, "from time cannot be greator than to time and the time has to be in the same day", Toast.LENGTH_SHORT).show();
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


        Log.i("asdkjlad",String.valueOf(groupPosition));
        Log.i("asdkjlad",String.valueOf(LocationInfo.size()));

//        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(_context);
//        if(preferences2.contains("fromtime"+pos+id))
//        {
//            final String fromtime = preferences.getString("fromtime"+pos+id, "");
//            from_time_display.setText(fromtime);
//        } else if(preferences2.contains("totime"+pos+id))
//        {
//            final String totime = preferences.getString("totime"+pos+id, "");
//            to_time_display.setText(totime);
//        }


        return convertView;
    }


    private void deleteTimming(JSONArray timeid, final int groupPosition) {


        Log.i("asdadasd","in delete timing");
        if (Utility.isOnline(_context)) {
            JSONObject object = new JSONObject();
            try {
                object.put("idss", timeid);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(_context, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(_context);
            serviceCaller.deleteTimming(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        deleteTimmingModel mainContent = new Gson().fromJson(result, deleteTimmingModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(_context, "SUCCESSFULLY DELETED", Toast.LENGTH_SHORT).show();
                                _listDataHeader.remove(groupPosition);
                                notifyDataSetChanged();
                                Intent intent= new Intent(_context, TimmingEditActivity_FromHome.class);
                                intent.putExtra("dlmid",dlmid);
                                intent.putExtra("from_home",from_home);
                                intent.putExtra("location_name",location_name);
                                intent.putExtra("location_address",location_address);
                                intent.putExtra("location_city",location_city);
                                intent.putExtra("Role",Role);
                                _context.startActivity(intent);

                            } else {
                                Toast.makeText(_context, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(_context, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", _context);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, _context);//off line msg....
        }
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        Log.i("info","getchildrencount called");


        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        Log.i("info","getgroup called");

        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        Log.i("info","getgroupcount called");

        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        Log.i("info","getgroupID called");

        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        Log.i("info","hasStableIds called");

        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Log.i("info","ischildselectable called");

        return true;
    }



}
