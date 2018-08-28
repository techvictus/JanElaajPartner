package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.app.Service;
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
import com.janelaaj.adapter.ServicesRateExpandableListAdapter;
import com.janelaaj.adapter.TimeEditExpandableListAdapter2;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.all_information;
import com.janelaaj.model.getServicesDetailsModel;
import com.janelaaj.model.getServicesInformationModel;

import com.janelaaj.model.TimmingEditActivityFromHomeModel;
import com.janelaaj.model.timeinformation;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicesRatesEditActivity extends AppCompatActivity implements Serializable,View.OnClickListener{

    ServicesRateExpandableListAdapter listAdapter;

    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    TextView titleheader;
    Button b1;
    public Integer gp;
    ArrayList<getServicesDetailsModel.SERVICE> servicesNamesArrayList;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender;


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
    Integer save_value=0;
    ArrayList<getServicesInformationModel.SERVICE> servicesInformationArrayList;


    String doctorid;
    TextView location;
    ArrayList<timeinformation.info> information;
    String totallocation;
    String location_name,location_address,location_city,Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Role = getIntent().getStringExtra("Role");
        from_home = getIntent().getExtras().getString("from_home");


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        doctorid = sp.getString("doctorid", "");
        id = getIntent().getExtras().getString("dlmid");
        if(!Role.equals("VIT")) {
            start2(doctorid);
        }
        servicesNamesArrayList = new ArrayList<getServicesDetailsModel.SERVICE>();

        location_name = getIntent().getExtras().getString("location_name");
        location_address = getIntent().getExtras().getString("location_address");
        location_city = getIntent().getExtras().getString("location_city");
        servicesInformationArrayList = new ArrayList<getServicesInformationModel.SERVICE>();

        if(Role.equals("VIT"))
        {
            getServicesNamesMethod2();
        }
        else {
            getServicesNamesMethod();
        }
        start(id);

        setContentView(R.layout.servicesrateedit_screen);
        setupToolbar();




        location = findViewById(R.id.titleHeader4);
        totallocation = location_name + ',' + ' ' + location_address  + ',' + ' ' + location_city;
        location.setText(location_name + ',' + ' ' + location_address  + ',' + ' ' + location_city);

        fab = findViewById(R.id.addlocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesRatesEditActivity.this,AddNewServiceActivity.class);
                intent.putExtra("totallocation",totallocation);
                intent.putExtra("dlmid",getdlmid());
                intent.putExtra("from_home",from_home);
                intent.putExtra("Role",Role);
                intent.putExtra("location_name",location_name);
                intent.putExtra("location_address",location_address);
                intent.putExtra("location_city",location_city);
                startActivity(intent);
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
                        Intent intent = new Intent(ServicesRatesEditActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return false;

            }
        });
        expListView = this.findViewById(R.id.listExpandble);
        this.titleheader = this.findViewById(R.id.titleHeader);
        prepareListData();

        btn_home = findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ServicesRatesEditActivity.this,ManageLocationActivity.class);
                intent.putExtra("from_home",from_home);
                intent.putExtra("Role",Role);
                startActivity(intent);

            }
        });

    }


    private void start(final String Dlmid)
    {
        final String dlmid = Dlmid;
        final ProgressDialog progressbar = ProgressDialog.show(ServicesRatesEditActivity.this, "", "Please wait..", true);
        progressbar.show();
        progressbar.setCancelable(true);
        getServiceDetailsInitially(dlmid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }

    }


    private void  getServiceDetailsInitially(final String Dlmid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("dlmid", Dlmid);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final ProgressDialog progressbar = ProgressDialog.show(ServicesRatesEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.getServicesInformation(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        getServicesInformationModel mainContent = new Gson().fromJson(result, getServicesInformationModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                servicesInformationArrayList = mainContent.getServicesInformation();

                                prepareListData();

                                listAdapter = new ServicesRateExpandableListAdapter(ServicesRatesEditActivity.this, listDataHeader, listDataChild,servicesInformationArrayList,servicesNamesArrayList,location_name,location_address,location_city,from_home,Role);
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

                                    }
                                });

                                // Listview on child click listener
                                expListView.setOnChildClickListener(new OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                        return false;

                                    }
                                });




                            } else {
                                Toast.makeText(ServicesRatesEditActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ServicesRatesEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ServicesRatesEditActivity.this);
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

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemptio");


        for(int i=0;i<servicesInformationArrayList.size();i++) {
            listDataHeader.add(servicesInformationArrayList.get(i).getServiceName());
            listDataChild.put(listDataHeader.get(i), top250);

        }

    }


    private void start2(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(ServicesRatesEditActivity.this, "", "Please wait..", true);
        progressbar.show();
        userValidate2(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            Intent intent = new Intent(ServicesRatesEditActivity.this, TimmingEditActivity.class);
            intent.putExtra("Role",Role);
            startActivity(intent);
        }

    }

    public String getdlmid()
    {
        return id;
    }

    private void getServicesNamesMethod() {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            final ProgressDialog progressbar = ProgressDialog.show(ServicesRatesEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.getServicesNames(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        getServicesDetailsModel mainContent = new Gson().fromJson(result, getServicesDetailsModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                servicesNamesArrayList = mainContent.getServicesNames();
                                for(int i=0;i<servicesNamesArrayList.size();i++)
                                {

                                    Log.i("sadafsf",servicesNamesArrayList.get(i).getSName());
                                }
                            } else {
                                Toast.makeText(ServicesRatesEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ServicesRatesEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ServicesRatesEditActivity.this);
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


    private void getServicesNamesMethod2() {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            final ProgressDialog progressbar = ProgressDialog.show(ServicesRatesEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.getServicesNames2(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        getServicesDetailsModel mainContent = new Gson().fromJson(result, getServicesDetailsModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                servicesNamesArrayList = mainContent.getServicesNames();
                                Log.i("sadafsf","VIT");
                                for(int i=0;i<servicesNamesArrayList.size();i++)
                                {

                                    Log.i("sadafsf",servicesNamesArrayList.get(i).getSName());
                                }
                            } else {
                                Toast.makeText(ServicesRatesEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ServicesRatesEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ServicesRatesEditActivity.this);
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

    private void userValidate2(final String Doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(ServicesRatesEditActivity.this, "", "Please wait..", true);
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
                                Toast.makeText(ServicesRatesEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ServicesRatesEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ServicesRatesEditActivity.this);
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