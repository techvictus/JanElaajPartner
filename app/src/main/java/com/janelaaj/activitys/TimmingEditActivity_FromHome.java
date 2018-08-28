package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.View.OnClickListener;
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
import com.janelaaj.adapter.TimeEditExpandableListAdapter2;
import com.janelaaj.fragment.TimePickerFragment;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.Timing;
import com.janelaaj.model.TimmingEditActivityFromHomeModel;
import com.janelaaj.model.all_information;
import com.janelaaj.model.timeinformation;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author Arshil Khan.
 */

public class TimmingEditActivity_FromHome extends AppCompatActivity implements View.OnClickListener{

    TimeEditExpandableListAdapter2 listAdapter;
//    TimeEditExpandableListAdapter2second listAdapter2;

    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    TextView titleheader;
    Button b1;
    public Integer gp;

    private Button btn_home;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ArrayList<TimmingEditActivityFromHomeModel.alltimings> LocationInfo;
    FloatingActionButton fab;
    String from_home;
    private int lastExpandedPosition = -1;
    String id;
    Button save;
    public String dlmid;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender;

    Integer save_value=0;

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

    ArrayList<String> MonInitial;
    ArrayList<String> TueInitial;
    ArrayList<String> WedInitial;
    ArrayList<String> ThuInitial;
    ArrayList<String> FriInitial;
    ArrayList<String> SatInitial;
    ArrayList<String> SunInitial;

    String doctorid;
    TextView location;
    ArrayList<timeinformation.info> information;
    String location_name,location_address,location_city,Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Role = getIntent().getStringExtra("Role");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        doctorid = sp.getString("doctorid", "");
        if(!Role.equals("VIT")) {
            start2(doctorid);
        }

        id = getIntent().getExtras().getString("dlmid");

        from_home = getIntent().getExtras().getString("from_home");
//        from_home="1";
        location_name = getIntent().getExtras().getString("location_name");
        location_address = getIntent().getExtras().getString("location_address");
        location_city = getIntent().getExtras().getString("location_city");

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



        MonInitial = new ArrayList<String>();
        TueInitial = new ArrayList<String>();
        WedInitial = new ArrayList<String>();
        ThuInitial = new ArrayList<String>();
        FriInitial = new ArrayList<String>();
        SatInitial = new ArrayList<String>();
        SunInitial = new ArrayList<String>();



        check(doctorid);
        start(id);

        setContentView(R.layout.activity_timming_edit__from_home);
        setupToolbar();
        location = findViewById(R.id.titleHeader4);
        location.setText(location_name + ',' + ' ' + location_address  + ',' + ' ' + location_city);

        dlmid = getIntent().getExtras().getString("dlmid","");
        SharedPreferences sp4 = PreferenceManager.getDefaultSharedPreferences(TimmingEditActivity_FromHome.this);
        sp4.edit().clear().commit();


        SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(TimmingEditActivity_FromHome.this);
        SharedPreferences.Editor editor = sp2.edit();
        editor.putString("doctorid",doctorid);
        editor.commit();

        fab = findViewById(R.id.addlocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save_value==1)
                {
                    Toast.makeText(TimmingEditActivity_FromHome.this, "Save the previous timings first", Toast.LENGTH_SHORT).show();
                }
                else {
                    listDataHeader.add(" ");
                    prepareListData2();
                    save_value = 1;
                }
            }
        });

        LocationInfo = new ArrayList<TimmingEditActivityFromHomeModel.alltimings>();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(TimmingEditActivity_FromHome.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;

            }
        });
        expListView = this.findViewById(R.id.lvExp);
        this.titleheader = this.findViewById(R.id.titleHeader);
        prepareListData();

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(save_value==1)
                {

                    Integer mondayoriginialsize =0;
                    Integer tuesdayoriginalsize =0;
                    Integer wednesdayoriginalsize =0;
                    Integer thursdayoriginalsize =0;
                    Integer fridayoriginalsize =0;
                    Integer saturdayoriginalsize =0;
                    Integer sundayoriginalsize =0;


                        Log.i("sadafsf", "mon" + Mon.size());
                        Log.i("sadafsf", "tue"+ Tue.size());
                        Log.i("sadafsf", "wed"+Wed.size());
                        Log.i("sadafsf", "thu"+Thu.size());
                        Log.i("sadafsf", "fri" + Fri.size());
                        Log.i("sadafsf", "sat"+Sat.size());
                        Log.i("sadafsf", "sun"+Sun.size());



                        Log.i("sadafsf", "mon final"+ String.valueOf(MonF.size()));
                        Log.i("sadafsf","tue final"+  String.valueOf(TueF.size()));
                        Log.i("sadafsf", "wed final" + String.valueOf(WedF.size()));
                        Log.i("tsadafsf", "thu final" + String.valueOf(ThuF.size()));
                        Log.i("sadafsf", "fri final" +  String.valueOf(FriF.size()));
                        Log.i("sadafsf", "sat final" + String.valueOf(SatF.size()));
                        Log.i("sadafsf", "sun final" + String.valueOf(SunF.size()));



                    Log.i("sadafsf", "mon in"+ String.valueOf(MonInitial.size()));
                    Log.i("sadafsf","tue in"+  String.valueOf(TueInitial.size()));
                    Log.i("sadafsf", "wed in" + String.valueOf(WedInitial.size()));
                    Log.i("tsadafsf", "thu in" + String.valueOf(ThuInitial.size()));
                    Log.i("sadafsf", "fri in" +  String.valueOf(FriInitial.size()));
                    Log.i("sadafsf", "sat in" + String.valueOf(SatInitial.size()));
                    Log.i("sadafsf", "sun in" + String.valueOf(SunInitial.size()));





                    if(Mon.size()==0 && Tue.size() ==0 && Wed.size()==0 && Thu.size()==0 && Fri.size()==0 && Sat.size()==0 && Sun.size()==0)
                    {
                        Toast.makeText(TimmingEditActivity_FromHome.this, "Select A Day First", Toast.LENGTH_SHORT).show();
                    } else if(MonInitial.size() == Mon.size() && TueInitial.size() ==Tue.size() && WedInitial.size() ==Wed.size() && ThuInitial.size() == Thu.size() && FriInitial.size() == Fri.size() && SatInitial.size() == Sat.size() && Sun.size() == SunInitial.size()) {
                        Toast.makeText(TimmingEditActivity_FromHome.this, "Select a Day First", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        userValidate2(Mon, Tue, Wed, Thu, Fri, Sat, Sun);
                    }

                }else
                {
                    Toast.makeText(TimmingEditActivity_FromHome.this, "Enter Timings first", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity_FromHome.this, "", "Please wait..", true);
        progressbar.show();
        progressbar.setCancelable(true);
        userValidate(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }

    }


    private void start2(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity_FromHome.this, "", "Please wait..", true);
        progressbar.show();
        progressbar.setCancelable(true);
        userValidate2(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }

    }


    private void userValidate2(final String Doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity_FromHome.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.allinfo(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        all_information mainContent = new Gson().fromJson(result, all_information.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                doctor_name = "Welcome Dr. " + mainContent.getdocname();
                                doctor_name2 = mainContent.getdocname();
                                doctor_dob = mainContent.getdocdob();
                                doctor_gender = mainContent.getdocgender();
                                doctor_speciality = mainContent.getspeciality();
                                doctor_experience = mainContent.getExperience();


                                if(mainContent.getintroduction() !=  null)
                                {
                                    Log.i("information", " doctor intro is " + mainContent.getintroduction());
                                    doctor_introduction = mainContent.getintroduction();
                                }


                                View myLayout = findViewById(R.id.navHader);
                                name = myLayout.findViewById(R.id.name_of_doctor);
                                qualification = myLayout.findViewById(R.id.qualification);
                                Age_and_gender = myLayout.findViewById(R.id.age_and_gender);
                                name.setText(doctor_name2);
                                qualification.setText(doctor_speciality);
                                if(doctor_speciality.equals("1"))
                                {
                                    qualification.setText("Cardiology");
                                } else if(doctor_speciality.equals("2"))
                                {
                                    qualification.setText("E.N.T");
                                }
                                else if(doctor_speciality.equals("3"))
                                {
                                    qualification.setText("Opthalmology");
                                }
                                else if(doctor_speciality.equals("4"))
                                {
                                    qualification.setText("Dental");
                                }
                                else if(doctor_speciality.equals("5"))
                                {
                                    qualification.setText("General Physician");
                                }
                                Age_and_gender.setText(doctor_dob+','+ doctor_gender);


                            } else {
                                Toast.makeText(TimmingEditActivity_FromHome.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TimmingEditActivity_FromHome.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", TimmingEditActivity_FromHome.this);
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



    private void userValidate(final String Docid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Docid);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity_FromHome.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.TimmingEditActivityFromHome(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        TimmingEditActivityFromHomeModel mainContent = new Gson().fromJson(result, TimmingEditActivityFromHomeModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                LocationInfo=mainContent.getAlltimings();
                                prepareListData();

                                Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
                                Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
                                Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
                                Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");



                                listAdapter = new TimeEditExpandableListAdapter2(TimmingEditActivity_FromHome.this, listDataHeader, listDataChild,LocationInfo,id,Mon,Tue,Wed,Thu,Fri,Sat,Sun,MonF,TueF,WedF,ThuF,FriF,SatF,SunF,location_name,location_address,location_city,save_value,from_home,Role);
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
                                        Log.i("jaiajai",String.valueOf(groupPosition)+"in 1");
                                        gp = groupPosition;
                                        if (lastExpandedPosition != -1
                                                && groupPosition != lastExpandedPosition) {
                                            expListView.collapseGroup(lastExpandedPosition);
                                        }
                                        lastExpandedPosition = groupPosition;
                                        expListView.setDividerHeight(R.dimen._10dp);
                                        expListView.setDivider(getResources().getDrawable(android.R.color.transparent));

                                    }
                                });

                                expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
                                    @Override
                                    public void onGroupCollapse(int groupPosition) {
//                                        Log.i("jaiajai",String.valueOf(groupPosition)+"in 2");


                                    }
                                });

                                // Listview on child click listener
                                expListView.setOnChildClickListener(new OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                        Log.i("jaiajai",String.valueOf(groupPosition)+"in 3");
                                        // TODO Auto-generated method stub
                                        Log.i("child",String.valueOf(childPosition));
                                        return false;

                                    }
                                });




                            } else {
                                Toast.makeText(TimmingEditActivity_FromHome.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TimmingEditActivity_FromHome.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", TimmingEditActivity_FromHome.this);
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



    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void check(final String doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity_FromHome.this, "", "Please wait..", true);
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
                                        MonInitial.add(timeval);

                                    }
                                    for(int j=0;j<information.get(i).getTuesday().size();j++){
                                        String timeval =  information.get(i).getTuesday().get(j).getFrom() + "_" + information.get(i).getTuesday().get(j).getTo();
                                        Tue.add(timeval);
                                        TueInitial.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getWednesday().size();j++){
                                        String timeval =  information.get(i).getWednesday().get(j).getFrom() + "_" + information.get(i).getWednesday().get(j).getTo();
                                        Wed.add(timeval);
                                        WedInitial.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getThursday().size();j++){
                                        String timeval =  information.get(i).getThursday().get(j).getFrom() + "_" + information.get(i).getThursday().get(j).getTo();
                                        Thu.add(timeval);
                                        ThuInitial.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getFriday().size();j++){
                                        String timeval =  information.get(i).getFriday().get(j).getFrom() + "_" + information.get(i).getFriday().get(j).getTo();
                                        Fri.add(timeval);
                                        FriInitial.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getSaturday().size();j++){
                                        String timeval =  information.get(i).getSaturday().get(j).getFrom() + "_" + information.get(i).getSaturday().get(j).getTo();
                                        Sat.add(timeval);
                                        SatInitial.add(timeval);
                                    }
                                    for(int j=0;j<information.get(i).getSunday().size();j++){
                                        String timeval =  information.get(i).getSunday().get(j).getFrom() + "_" + information.get(i).getSunday().get(j).getTo();
                                        Sun.add(timeval);
                                        SunInitial.add(timeval);
                                    }

                                }




                            } else {
                                Toast.makeText(TimmingEditActivity_FromHome.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TimmingEditActivity_FromHome.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", TimmingEditActivity_FromHome.this);
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


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemptio");


        for(int i=0;i<LocationInfo.size();i++) {

            String x = LocationInfo.get(i).getFrom();
            String y = LocationInfo.get(i).getTo();

            String xout = x.replaceAll("..(?!$)", "$0:");
            String yout = y.replaceAll("..(?!$)", "$0:");
            String m = xout + "-" + yout;
            listDataHeader.add(m);
            listDataChild.put(listDataHeader.get(i), top250);

        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            Intent intent = new Intent(TimmingEditActivity_FromHome.this, TimmingEditActivity.class);
            startActivity(intent);
        }

    }

//    public void setset(String t){
//        Log.i("afrssfsseq","vhhaha");
//        listDataHeader.remove("--");
//        listDataHeader.add("pop");
//        listAdapter.notifyDataSetChanged();
//    }

    private void prepareListData2() {


        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");

        for(int i=0;i<listDataHeader.size();i++) {
            listDataChild.put(listDataHeader.get(i), top250);
        }

        Log.i("size",String.valueOf(listDataHeader.size()));



        listAdapter = new TimeEditExpandableListAdapter2(this, listDataHeader, listDataChild,LocationInfo,id,Mon,Tue,Wed,Thu,Fri,Sat,Sun,MonF,TueF,WedF,ThuF,FriF,SatF,SunF,location_name,location_address,location_city,save_value,from_home,Role);
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
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
                expListView.setDividerHeight(R.dimen._10dp);
                expListView.setDivider(getResources().getDrawable(android.R.color.transparent));

            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

    }


    private void userValidate2(final ArrayList<String> Mon, final ArrayList<String> Tue,final ArrayList<String> Wed,final ArrayList<String> Thu,final ArrayList<String> Fri,final ArrayList<String> Sat,final ArrayList<String> Sun)
    {
        if (Utility.isOnline(this)) {

            JSONArray monday = new JSONArray();

            Set<String> MonUnique = new HashSet<String>(MonF);
            ArrayList<String> MonUniqueArrayList = new ArrayList<String>(MonUnique);


            for(int i=0;i<MonUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",MonUniqueArrayList.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                monday.put(object);
            }

            JSONArray tuesday = new JSONArray();

            Set<String> TueUnique = new HashSet<String>(TueF);
            ArrayList<String> TueUniqueArrayList = new ArrayList<String>(TueUnique);


            for(int i=0;i<TueUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",TueUniqueArrayList.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tuesday.put(object);
            }

            JSONArray wednesday = new JSONArray();

            Set<String> WedUnique = new HashSet<String>(WedF);
            ArrayList<String> WedUniqueArrayList = new ArrayList<String>(WedUnique);


            for(int i=0;i<WedUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",WedUniqueArrayList.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                wednesday.put(object);
            }

            JSONArray thursday = new JSONArray();

            Set<String> ThuUnique = new HashSet<String>(ThuF);
            ArrayList<String> ThuUniqueArrayList = new ArrayList<String>(ThuUnique);

            for(int i=0;i<ThuUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",ThuUniqueArrayList.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                thursday.put(object);
            }

            JSONArray friday = new JSONArray();

            Set<String> FriUnique = new HashSet<String>(FriF);
            ArrayList<String> FriUniqueArrayList = new ArrayList<String>(FriUnique);

            for(int i=0;i<FriUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",FriUniqueArrayList.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                friday.put(object);
            }

            JSONArray saturday = new JSONArray();

            Set<String> SatUnique = new HashSet<String>(SatF);
            ArrayList<String> SatUniqueArrayList = new ArrayList<String>(SatUnique);

            for(int i=0;i<SatUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",SatUniqueArrayList.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saturday.put(object);
            }

            JSONArray sunday = new JSONArray();

            Set<String> SunUnique = new HashSet<String>(SunF);
            ArrayList<String> SunUniqueArrayList = new ArrayList<String>(SunUnique);

            for(int i=0;i<SunUniqueArrayList.size();i++){
                JSONObject object = new JSONObject();
                try {
                    object.put("time",SunUniqueArrayList.get(i));
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

            final ProgressDialog progressbar = ProgressDialog.show(TimmingEditActivity_FromHome.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.Timing(obj2, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        Timing mainContent = new Gson().fromJson(result, Timing.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(TimmingEditActivity_FromHome.this);
                                sp.edit().clear().commit();

                                SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(TimmingEditActivity_FromHome.this);
                                SharedPreferences.Editor editor = sp2.edit();
                                editor.putString("doctorid",doctorid);
                                editor.commit();

                                Toast.makeText(TimmingEditActivity_FromHome.this, "Timings Saved", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TimmingEditActivity_FromHome.this,ManageLocationActivity.class);
                                intent.putExtra("Role",Role);
                                intent.putExtra("from_home",from_home);
                                startActivity(intent);

                            } else {
                                Toast.makeText(TimmingEditActivity_FromHome.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(TimmingEditActivity_FromHome.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", TimmingEditActivity_FromHome.this);
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