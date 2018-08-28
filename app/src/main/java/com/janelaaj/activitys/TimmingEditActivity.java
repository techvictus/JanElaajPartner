package com.janelaaj.activitys;


/**
 *
 * @author Arshil Khan.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.adapter.TimeEditExpandableListAdapter;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.Timing;
import com.janelaaj.model.timeinformation;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class  TimmingEditActivity extends AppCompatActivity {
    TimeEditExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button btn_home;
    TextView manage_location_timings,title_header,mon,tue,wed,thur,fri,sat,sun;


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FloatingActionButton fab;
    public String dlmid;
    public String id;

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
    ArrayList<timeinformation.info> information;
    String location_name,location_address,location_city,from_home;
    TextView location;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String doctorid = sp.getString("doctorid", "");
        check(doctorid);


        dlmid = getIntent().getExtras().getString("dlmid","");
        id = getIntent().getExtras().getString("id","");
//        from_home = getIntent().getExtras().getString("from_home");

        from_home="1";
        location_name = getIntent().getExtras().getString("location_name");
        location_address = getIntent().getExtras().getString("location_address");
        location_city = getIntent().getExtras().getString("location_city");


        setContentView(R.layout.edittimelocation_screen);
        setupToolbar();
        location = findViewById(R.id.titleHeader4);
        location.setText(location_name + ',' + ' ' + location_address  + ',' + ' ' + location_city);





        Mon = new ArrayList<String>();
        Tue = new ArrayList<String>();
        Wed = new ArrayList<String>();
        Thu = new ArrayList<String>();
        Fri = new ArrayList<String>();
        Sat = new ArrayList<String>();
        Sun = new ArrayList<String>();


        MonF = new ArrayList<String>();
        TueF = new ArrayList<String>();
        WedF = new ArrayList<String>();
        ThuF = new ArrayList<String>();
        FriF = new ArrayList<String>();
        SatF = new ArrayList<String>();
        SunF = new ArrayList<String>();



        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

       final TimeEditExpandableListAdapter obj = new TimeEditExpandableListAdapter(TimmingEditActivity.this);
        fab =  findViewById(R.id.addlocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDataHeader.add(" ");
                prepareListData();

            }
        });
        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userValidate(Mon,Tue,Wed,Thu,Fri,Sat,Sun);

            }
        });


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        Intent intent = new Intent(TimmingEditActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });


        expListView = this.findViewById(R.id.lvExp);
        this.manage_location_timings = findViewById(R.id.manage_location_timings);
        this.title_header = findViewById(R.id.titleHeader3);

        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");

        manage_location_timings.setTypeface(tf4);
        btn_home.setTypeface(tf3);



        prepareListData();
        listAdapter = new TimeEditExpandableListAdapter(this, listDataHeader, listDataChild,Mon,Tue,Wed,Thu,Fri,Sat,Sun,id,MonF,TueF,WedF,ThuF,FriF,SatF,SunF);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expListView.setDividerHeight(R.dimen._10dp);
                expListView.setDivider(getResources().getDrawable(android.R.color.transparent));

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  expListView.setDivider(getResources().getDrawable(R.drawable.expandable_selector));
                expListView.setDividerHeight(10);*/
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }





    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }



    private void prepareListData() {


        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");

        for(int i=0;i<listDataHeader.size();i++) {
            listDataChild.put(listDataHeader.get(i), top250);
        }


        Log.i("size",String.valueOf(listDataHeader.size()));




        listAdapter = new TimeEditExpandableListAdapter(this, listDataHeader, listDataChild,Mon,Tue,Wed,Thu,Fri,Sat,Sun,id,MonF,TueF,WedF,ThuF,FriF,SatF,SunF);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expListView.setDividerHeight(R.dimen._10dp);
                expListView.setDivider(getResources().getDrawable(android.R.color.transparent));

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  expListView.setDivider(getResources().getDrawable(R.drawable.expandable_selector));
                expListView.setDividerHeight(10);*/
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }


    private void userValidate(final ArrayList<String> Mon, final ArrayList<String> Tue,final ArrayList<String> Wed,final ArrayList<String> Thu,final ArrayList<String> Fri,final ArrayList<String> Sat,final ArrayList<String> Sun)
    {
        if (Utility.isOnline(this)) {

            JSONArray monday = new JSONArray();

            for(int i=0;i<MonF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",MonF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                monday.put(object);
            }

            JSONArray tuesday = new JSONArray();

            for(int i=0;i<TueF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",TueF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tuesday.put(object);
            }

            JSONArray wednesday = new JSONArray();

            for(int i=0;i<WedF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",WedF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                wednesday.put(object);
            }

            JSONArray thursday = new JSONArray();

            for(int i=0;i<ThuF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",ThuF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                thursday.put(object);
            }

            JSONArray friday = new JSONArray();

            for(int i=0;i<FriF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",FriF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                friday.put(object);
            }

            JSONArray saturday = new JSONArray();

            for(int i=0;i<SatF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",SatF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saturday.put(object);
            }

            JSONArray sunday = new JSONArray();

            for(int i=0;i<SunF.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",SunF.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sunday.put(object);
            }

            JSONObject obj2 = new JSONObject();

            try{
                obj2.put("dlmid",dlmid);
                obj2.put("monday",monday);
                obj2.put("tuesday",tuesday);
                obj2.put("wednesday",wednesday);
                obj2.put("thursday",thursday);
                obj2.put("friday",friday);
                obj2.put("saturday",saturday);
                obj2.put("sunday",sunday);
            }catch (JSONException e){
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.Timing(obj2, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        Timing mainContent = new Gson().fromJson(result, Timing.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {
                                Intent intent = new Intent(TimmingEditActivity.this, ManageLocationActivity.class);
                                Log.i("value",mainContent.getStatus());
                                startActivity(intent);

                            } else {
                                Toast.makeText(TimmingEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TimmingEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", TimmingEditActivity.this);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
        }
    }



    private void check(final String doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.timeinformation(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        timeinformation mainContent = new Gson().fromJson(result, timeinformation.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                information = mainContent.getInfo();

                                for(int i=0;i<information.size();i++){

                                    for(int j=0;j<information.get(i).getMonday().size();j++){
                                        String timeval =  information.get(i).getMonday().get(j).getFrom() + "_" + information.get(i).getMonday().get(j).getTo();
                                        Mon.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getTuesday().size();j++){
                                        String timeval =  information.get(i).getTuesday().get(j).getFrom() + "_" + information.get(i).getTuesday().get(j).getTo();
                                        Tue.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getWednesday().size();j++){
                                        String timeval =  information.get(i).getWednesday().get(j).getFrom() + "_" + information.get(i).getWednesday().get(j).getTo();
                                        Wed.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getThursday().size();j++){
                                        String timeval =  information.get(i).getThursday().get(j).getFrom() + "_" + information.get(i).getThursday().get(j).getTo();
                                        Thu.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getFriday().size();j++){
                                        String timeval =  information.get(i).getFriday().get(j).getFrom() + "_" + information.get(i).getFriday().get(j).getTo();
                                        Fri.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getSaturday().size();j++){
                                        String timeval =  information.get(i).getSaturday().get(j).getFrom() + "_" + information.get(i).getSaturday().get(j).getTo();
                                        Sat.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getSunday().size();j++){
                                        String timeval =  information.get(i).getSunday().get(j).getFrom() + "_" + information.get(i).getSunday().get(j).getTo();
                                        Sun.add(timeval);
                                    }

                                }




                            } else {
                                Toast.makeText(TimmingEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TimmingEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", TimmingEditActivity.this);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
        }
    }

}
