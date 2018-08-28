package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
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
import com.janelaaj.adapter.ExpandableListAdapter;
import com.janelaaj.adapter.RegistrationPagerAdapter;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.MainContent;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.all_information;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author Arshil Khan.
 */

public class ManageLocationActivity extends AppCompatActivity implements View.OnClickListener{

    ExpandableListAdapter listAdapter;
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
    ArrayList<ManageLocation.LOC> LocationInfo;
    FloatingActionButton fab;
    String from_home;
    private int lastExpandedPosition = -1;
    CircleImageView logoImage;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2,Role;
    TextView name,qualification,Age_and_gender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Role = getIntent().getStringExtra("Role");
            from_home = getIntent().getExtras().getString("from_home");

            Log.i("sadafsf","in managelocation" + from_home);
//            Log.i("sadafsf",from_home);
//            Log.i("from_home",from_home);

//        from_home= "1";


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        start(id);
        if(!Role.equals("VIT")) {
            start2(id);
        }

        setContentView(R.layout.mainlocation_screen);
        setupToolbar();

        logoImage = (CircleImageView) findViewById(R.id.logoImage);
        Log.i("sadafsf",from_home+".......");

        try {

            if (from_home.equals("1")) {
                logoImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ManageLocationActivity.this, Choose_Location.class);
                        intent.putExtra("Role", Role);
                        startActivity(intent);
                    }
                });
            }
        }
        catch (Exception e)
        {

        }





        fab = findViewById(R.id.addlocation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageLocationActivity.this,AddLocationScreen.class);
                intent.putExtra("from_home",from_home);
                intent.putExtra("Role",Role);
                startActivity(intent);
            }
        });

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
                        Intent intent = new Intent(ManageLocationActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });
        expListView = this.findViewById(R.id.lvExp);
        this.btn_home = this.findViewById(R.id.btn_home);
        if(from_home.equals("1"))
        {
            btn_home.setText("Home");
        }
        else
        {
            btn_home.setText("Complete Your Registration");
        }
        this.titleheader = this.findViewById(R.id.titleHeader);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.i("sadafsf","click"+from_home);
                if(from_home.equals("1"))
                {
                    Intent intent = new Intent(ManageLocationActivity.this, Choose_Location.class);
                    intent.putExtra("Role",Role);
                    startActivity(intent);
                }
                else
                {

                    if(!Role.equals("VIT")) {
                        Intent intent = new Intent(ManageLocationActivity.this, UploadDocumentActivity.class);
                        intent.putExtra("Role", Role);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(ManageLocationActivity.this, WelcomeRegistrationActivity.class);
                        intent.putExtra("Role",Role);
                        startActivity(intent);
                    }
                }

            }
        });
        prepareListData();


    }



    private void doExit() {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    ManageLocationActivity.this);

            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    settings.edit().remove("loginflag").apply();
                    Intent intent = new Intent(ManageLocationActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
            });

            alertDialog.setNegativeButton("No", null);

            alertDialog.setMessage("Do you want to Logout?");
            alertDialog.setTitle("Logout");
            alertDialog.setCancelable(false);
            alertDialog.show();
    }



    @Override
    public void onBackPressed() {

        doExit();
    }


    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(ManageLocationActivity.this, "", "Please wait..", true);
        progressbar.show();
        userValidate(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
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


            final ProgressDialog progressbar = ProgressDialog.show(ManageLocationActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.ManageLocation(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ManageLocation mainContent = new Gson().fromJson(result, ManageLocation.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                LocationInfo=mainContent.getLocations();



                                Log.i("xxxx","oio "+String.valueOf(mainContent.getLocations().size()));
                                prepareListData();

                                Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
                                Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
                                Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
                                Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");
                                titleheader.setTypeface(tf4);
                                btn_home.setTypeface(tf3);



                                listAdapter = new ExpandableListAdapter(ManageLocationActivity.this, listDataHeader, listDataChild,LocationInfo,from_home,Role);
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
                                Toast.makeText(ManageLocationActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ManageLocationActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ManageLocationActivity.this);
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


    private void start2(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(ManageLocationActivity.this, "", "Please wait..", true);
        progressbar.show();
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

            final ProgressDialog progressbar = ProgressDialog.show(ManageLocationActivity.this, "", "Please wait..", true);
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
                                Toast.makeText(ManageLocationActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ManageLocationActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ManageLocationActivity.this);
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




    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemptio");


        for(int i=0;i<LocationInfo.size();i++) {
            Log.i("xxxx",String.valueOf(LocationInfo.get(i).getLcity()));
            listDataHeader.add(LocationInfo.get(i).getLcity());
            listDataChild.put(listDataHeader.get(i), top250);

        }


//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");

//        List<String> df = new ArrayList<String>();
//        df.add("The sgfdgd");
//
//
//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), nowShowing);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            Intent intent = new Intent(ManageLocationActivity.this, TimmingEditActivity.class);
            startActivity(intent);
        }

    }

}