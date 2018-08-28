package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.janelaaj.adapter.ExpandableListAdapter;
import com.janelaaj.adapter.OneViewAdapter;
import com.janelaaj.adapter.RegistrationPagerAdapter;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.MainContent;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.OneViewModel;
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

public class OneViewActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    OneViewAdapter listAdapter;
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
    private int lastExpandedPosition = -1;
    ArrayList<OneViewModel.timeinfo> TimeInfo;
    ArrayList<OneViewModel.serviceinfo> ServiceInfo;
    CircleImageView logoImage;
    TextView name,qualification,Age_and_gender;
    String NAME,QUALIFICATION,AGE_AND_GENDER,Role;
    String current_location_id,dlmid,loc;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        final String id = getIntent().getExtras().getString("doctorid");
        Role = getIntent().getStringExtra("Role");
        current_location_id = getIntent().getStringExtra("locid");
        dlmid = getIntent().getStringExtra("dlmid");
        loc = getIntent().getStringExtra("Currentloc");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        start(id);

        setContentView(R.layout.one_view_rowscreen);
        setupToolbar();




        logoImage = (CircleImageView) findViewById(R.id.logoImage);
        logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneViewActivity.this,DashboardActivity.class);
                intent.putExtra("Role",Role);
                intent.putExtra("current_location",loc);
                intent.putExtra("current_location_id",current_location_id);
                intent.putExtra("current_location_dlmid",dlmid);
                startActivity(intent);
            }
        });


        LocationInfo = new ArrayList<>();
        TimeInfo = new ArrayList<>();
        ServiceInfo = new ArrayList<>();

        navigationView = findViewById(R.id.navigation_viewww);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(OneViewActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });
        expListView = this.findViewById(R.id.lvExp);
        this.btn_home = this.findViewById(R.id.btn_home);
        this.titleheader = this.findViewById(R.id.titleHeader);

        View myLayout = findViewById(R.id.navHader);
        name = myLayout.findViewById(R.id.name_of_doctor);
        qualification = myLayout.findViewById(R.id.qualification);
        Age_and_gender = myLayout.findViewById(R.id.age_and_gender);


        SharedPreferences shared = getSharedPreferences("Doctor_Info", MODE_PRIVATE);
        NAME = (shared.getString("name", ""));
        QUALIFICATION = (shared.getString("qualification", ""));
        AGE_AND_GENDER = (shared.getString("Age_and_gender", ""));


        name.setText(NAME);
        Age_and_gender.setText(AGE_AND_GENDER);
        if(QUALIFICATION.equals("1"))
        {
            qualification.setText("Cardiology");
        } else if(QUALIFICATION.equals("2"))
        {
            qualification.setText("E.N.T");
        }
        else if(QUALIFICATION.equals("3"))
        {
            qualification.setText("Opthalmology");
        }
        else if(QUALIFICATION.equals("4"))
        {
            qualification.setText("Dental");
        }
        else if(QUALIFICATION.equals("5"))
        {
            qualification.setText("General Physician");
        }


        prepareListData();


    }


    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(OneViewActivity.this, "", "Please wait..", true);
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


            final ProgressDialog progressbar = ProgressDialog.show(OneViewActivity.this, "", "Please wait..", true);
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
                                listAdapter = new OneViewAdapter(OneViewActivity.this, listDataHeader, listDataChild, ServiceInfo,TimeInfo,LocationInfo);
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

                                        Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
                                    @Override
                                    public void onGroupCollapse(int groupPosition) {
//                                        Log.i("jaiajai",String.valueOf(groupPosition)+"in 2");

                                        Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                // Listview on child click listener
                                expListView.setOnChildClickListener(new OnChildClickListener() {
                                    @Override
                                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                        Log.i("jaiajai",String.valueOf(groupPosition)+"in 3");
                                        // TODO Auto-generated method stub
                                        Log.i("child",String.valueOf(childPosition));
                                        Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                                        return false;

                                    }
                                });



                            } else {
                                Toast.makeText(OneViewActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(OneViewActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", OneViewActivity.this);
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
            listDataHeader.add(LocationInfo.get(i).getLname() + ","+LocationInfo.get(i).getLadrline1() + ","+LocationInfo.get(i).getLcity());
            listDataChild.put(listDataHeader.get(i), top250);

        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            Intent intent = new Intent(OneViewActivity.this, TimmingEditActivity.class);
            intent.putExtra("Role",Role);
            startActivity(intent);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}