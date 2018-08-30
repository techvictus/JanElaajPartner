package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.all_information;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author Arshil Khan.
 */

public class Choose_Location extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FloatingActionButton fab;
    private int lastExpandedPosition = -1;
    TextView loc1,loc2,loc3,loc4;
    String locid1,locid2,locid3,locid4,city1,city2,city3,city4,dlmid1,dlmid2,dlmid3,dlmid4;
    ArrayList<String> names;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience,mbbs,md,ms,diploma;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender,degree;
    ArrayList<ManageLocation.LOC> LocationInfo;
    String Role;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Role = getIntent().getStringExtra("Role");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        start(id);
        if(!Role.equals("VIT")) {
            start2(id);
        }
        setContentView(R.layout.activity_choose__location);
        setupToolbar();

        LocationInfo = new ArrayList<ManageLocation.LOC>();




        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(Choose_Location.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });

        this.loc1 = this.findViewById(R.id.location1);
        this.loc2 = this.findViewById(R.id.location2);
        this.loc3 = this.findViewById(R.id.location3);
        this.loc4 = this.findViewById(R.id.location4);

        loc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_Location.this,DashboardActivity.class);
                intent.putExtra("current_location",loc1.getText());
                intent.putExtra("current_location_id",locid1);
                intent.putExtra("current_location_dlmid",dlmid1);
                intent.putExtra("Role",Role);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        loc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_Location.this,DashboardActivity.class);
                intent.putExtra("current_location",loc2.getText());
                intent.putExtra("current_location_id",locid2);
                intent.putExtra("current_location_dlmid",dlmid2);
                intent.putExtra("Role",Role);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        loc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_Location.this,DashboardActivity.class);
                intent.putExtra("current_location",loc3.getText());
                intent.putExtra("current_location_dlmid",dlmid3);
                intent.putExtra("Role",Role);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        loc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choose_Location.this,DashboardActivity.class);
                intent.putExtra("current_location",loc4.getText());
                intent.putExtra("current_location_id",locid4);
                intent.putExtra("current_location_dlmid",dlmid4);
                intent.putExtra("Role",Role);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }


    private void start2(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(Choose_Location.this, "", "Please wait..", true);
        progressbar.show();
        userValidate2(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }
    }

    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(Choose_Location.this, "", "Please wait..", true);
        progressbar.show();
        userValidate(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
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


    private void userValidate(final String Docid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Docid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(Choose_Location.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.chooseLocation(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ManageLocation mainContent = new Gson().fromJson(result, ManageLocation.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                LocationInfo= mainContent.getLocations();
                                for(int i=0;i<LocationInfo.size();i++)
                                {
                                    String name = LocationInfo.get(i).getLname();
                                    String city = LocationInfo.get(i).getLcity();
                                    String locid = LocationInfo.get(i).getLid();
                                    String dlmid = LocationInfo.get(i).getDlmid();
                                    String add1 = LocationInfo.get(i).getLadrline1();

                                    if(i==0)
                                    {
                                        loc1.setText(name + ',' + ' ' + add1 + ',' + ' '+ city);
                                        city1 =city;
                                        locid1 = locid;
                                        dlmid1 =dlmid;
                                    }
                                    else if(i==1)
                                    {
                                        loc2.setText(name + ',' + ' ' + add1 + ',' + ' '+ city);
                                        city2 =city;
                                        locid2= locid;
                                        dlmid2 =dlmid;

                                    }
                                    else if(i==2)
                                    {
                                        loc3.setText(name + ',' + ' ' + add1 + ',' + ' '+ city);
                                        city3 = city;
                                        locid3 = locid;
                                        dlmid3 =dlmid;
                                    }
                                    else if(i==3)
                                    {
                                        loc4.setText(name + ',' + ' ' + add1 + ',' + ' '+ city);
                                        city4 =  city;
                                        locid4 = locid;
                                        dlmid4 =dlmid;
                                    }
                                }

                            } else {
                                Toast.makeText(Choose_Location.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Choose_Location.this, "User Already Registered!", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Utility.alertForErrorMessage("User Already Registered", Choose_Location.this);
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
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(Choose_Location.this, "", "Please wait..", true);
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
                                mbbs = mainContent.getMbbs();
                                md = mainContent.getMd();
                                ms = mainContent.getMs();
                                diploma = mainContent.getDiploma();


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
                                degree = myLayout.findViewById(R.id.degree);


                                ArrayList<String> deg = new ArrayList<>();
                                deg.add(mbbs);
                                deg.add(md);
                                deg.add(ms);
                                deg.add(diploma);

//                                Log.i("sadafsf",mbbs);
//                                Log.i("sadafsf",md);
//                                Log.i("sadafsf",ms);
//                                Log.i("sadafsf",diploma);



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



                                String degree_put = "";

                                for(int i=0;i<deg.size();i++)
                                {
                                    if(i==0 && deg.get(0).equals("Y"))
                                    {
                                        degree_put=degree_put+ "M.B.B.S";
                                    } else if(i==1 && deg.get(1).equals("Y"))
                                    {
                                        degree_put = degree_put + ","+"M.D";
                                    }
                                    else if(i==2 && deg.get(2).equals("Y"))
                                    {
                                        degree_put = degree_put + "," + "M.S";
                                    }
                                    else if(i==3 && deg.get(3).equals("Y"))
                                    {
                                        degree_put = degree_put + ","+ "Diploma,";
                                    }
                                }

                                degree.setText(degree_put);



                            } else {
                                Toast.makeText(Choose_Location.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Choose_Location.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", Choose_Location.this);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            com.janelaaj.utilities.Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
        }
    }


}
