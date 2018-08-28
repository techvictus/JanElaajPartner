package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.adapter.NavigationMenuAdapter;
import com.janelaaj.fragment.HomeFragment;
import com.janelaaj.fragment.ProfileFragment;
import com.janelaaj.fragment.TimePickerFragment;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.sign_in;
import com.janelaaj.utilities.Contants;
import com.janelaaj.model.all_information;
import com.janelaaj.utilities.FontManager;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Arshil Khan.
 */

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    Toolbar t;
    TextView loc;
    String Currentloc;
    Layout layout;
    String current_location_id,dlmid;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience,mbbs,md,ms,diploma;
    public String doctor_name2,Role;
    TextView name,qualification,Age_and_gender,degree;
    String imagefetch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        Role = getIntent().getStringExtra("Role");
        Currentloc = getIntent().getExtras().getString("current_location");
        current_location_id = getIntent().getExtras().getString("current_location_id");
        dlmid = getIntent().getExtras().getString("current_location_dlmid");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
            start(id);


        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("sadafsf","Role "+ Role);
        Log.i("sadafsf","Currentloc "+ Currentloc);
        Log.i("sadafsf","current_location_id "+ current_location_id);
        Log.i("sadafsf","dlmid "+ dlmid);



        loc = findViewById(R.id.title_location);
        loc.setText(Currentloc);

        current_location_id = getIntent().getExtras().getString("current_location_id");

        Log.i("locationid", current_location_id);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        TextView pIcon = (TextView) findViewById(R.id.pIcon);
        Typeface fontawesome = FontManager.getFontTypefaceMaterialDesignIcons(this, "fonts/fontawesome-webfont.ttf");
        pIcon.setTypeface(fontawesome);
        pIcon.setText(Html.fromHtml("&#xf105;"));
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = ProfileFragment.newInstance("", "");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
                closeDrawer();

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



//        name = (TextView)navigationView.getHeaderView(0).findViewById(R.id.name);
//        name.setText(doctor_name2);
//        qualification.setText(doctor_speciality);
//        Age_and_gender.setText(doctor_dob+','+ doctor_gender);


//        setUpDashboardFragment();
//        setMenuLayout();

    }

    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(DashboardActivity.this, "", "Please wait..", true);
        progressbar.show();
        userValidate(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }
    }


    //open default fragment
    private void setUpDashboardFragment() {
        Fragment fragment = HomeFragment.newInstance("", "");
        HomeFragment newFragment = new HomeFragment();
        moveFragment(fragment);
    }

    private void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                //.addToBackStack(null)
                .commit();
    }

    //set slider item value
    public void setMenuLayout() {

        ListView menuList = (ListView) findViewById(R.id.lst_menu_items);
        menuList.setDivider(null);
        List<String> itemList = new ArrayList<String>();
        itemList.add("Extras");
        itemList.add("Upgrade to Premium App");
        itemList.add("24*7 Help");
        itemList.add("Second Opinion");
        itemList.add("Check for Updates");
        itemList.add("Payment Modes");
        itemList.add("Sign Out");


        NavigationMenuAdapter navigationMenuAdapter = new NavigationMenuAdapter(DashboardActivity.this, itemList);
        menuList.setAdapter(navigationMenuAdapter);
    }

    //close drawer after item select
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
        //return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId())

        {

            case R.id.nav_android:
                Toast.makeText(DashboardActivity.this, "Nav Android Selected", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void userValidate(final String Doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(DashboardActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.allinfo(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        all_information mainContent = new Gson().fromJson(result, all_information.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                try {

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
                                    imagefetch = mainContent.getImage();

                                    Log.i("sadafsf",imagefetch+"image fetched code");


                                    if (mainContent.getintroduction() != null) {
                                        Log.i("information", " doctor intro is " + mainContent.getintroduction());
                                        doctor_introduction = mainContent.getintroduction();
                                    }

                                    setUpDashboardFragment();
                                    setMenuLayout();

                                    View myLayout = findViewById(R.id.navHader);
                                    name = myLayout.findViewById(R.id.name_of_doctor);
                                    qualification = myLayout.findViewById(R.id.qualification);
                                    Age_and_gender = myLayout.findViewById(R.id.age_and_gender);
                                    degree = myLayout.findViewById(R.id.degree);
                                    ArrayList<String> deg = new ArrayList<>();
                                    deg.add(mbbs);
                                    deg.add(md);
                                    deg.add(ms);
                                    deg.add(diploma);

                                    name.setText(doctor_name2);
                                    qualification.setText(doctor_speciality);
                                    if (doctor_speciality.equals("1")) {
                                        qualification.setText("Cardiology");
                                    } else if (doctor_speciality.equals("2")) {
                                        qualification.setText("E.N.T");
                                    } else if (doctor_speciality.equals("3")) {
                                        qualification.setText("Opthalmology");
                                    } else if (doctor_speciality.equals("4")) {
                                        qualification.setText("Dental");
                                    } else if (doctor_speciality.equals("5")) {
                                        qualification.setText("General Physician");
                                    }
                                    Age_and_gender.setText(doctor_dob + ',' + doctor_gender);

                                    String degree_put = "";

                                    for (int i = 0; i < deg.size(); i++) {
                                        if (i == 0 && deg.get(0).equals("Y")) {
                                            degree_put = degree_put + "M.B.B.S";
                                        } else if (i == 1 && deg.get(1).equals("Y")) {
                                            degree_put = degree_put + "," + "M.D";
                                        } else if (i == 2 && deg.get(2).equals("Y")) {
                                            degree_put = degree_put + "," + "M.S";
                                        } else if (i == 3 && deg.get(3).equals("Y")) {
                                            degree_put = degree_put + "," + "Diploma,";
                                        }
                                    }

                                    degree.setText(degree_put);


                                    SharedPreferences.Editor editor = getSharedPreferences("Doctor_Info", MODE_PRIVATE).edit();
                                    editor.putString("name", doctor_name2);
                                    editor.putString("Age_and_gender", doctor_dob + ',' + doctor_gender);
                                    editor.putString("qualification", doctor_speciality);
                                    editor.apply();

                                } catch (Exception e)
                                {

                                }


                            } else {
                                Toast.makeText(DashboardActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(DashboardActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", DashboardActivity.this);
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

    public String getCurrentlocid() {
        Log.i("info","location id return hua");
        return current_location_id;
    }
    public String getdoctor_name() {
        Log.i("info","return hua" + doctor_name) ;
        return doctor_name;
    }

    public String getdoctor_name2() {
        return doctor_name2;
    }

    public String getLocationName() {
        return Currentloc;
    }

    public String getdoctor_dob() {
        return doctor_dob;
    }

    public String getdoctor_dlmid() {
        return dlmid;
    }


    public String getRole() {
        return Role;
    }

    public String getdoctor_speciality() {
        return doctor_speciality;
    }
    public String getdoctor_introduction() {
        return doctor_introduction;
    }
    public String getdoctor_gender() {
        return doctor_gender;
    }
    public String getExperience() {
        return doctor_experience;
    }

    public String getImage() {
        return imagefetch;
    }


    public void setdoctor_introduction(String doctor_intro) {
        this.doctor_introduction = doctor_intro;
    }






}
