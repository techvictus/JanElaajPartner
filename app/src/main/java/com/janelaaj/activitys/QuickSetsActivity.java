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
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.QuickSets;
import com.janelaaj.model.all_information;
import com.janelaaj.model.quicksethelpModel;
import com.janelaaj.model.checkIfTimingExistModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created On 18-05-2018
 *
 * @author Arshil Khan.
 */

public class QuickSetsActivity extends AppCompatActivity implements View.OnClickListener{
    EditText totime, fromtime, timelate, timelateoff;
    private TextView manageDiscountPage, timmingText;
    private ImageView discountimg, lateimg, timeoffimg;
    private Button sendNotification, sendNotificationtime;
    LinearLayout discountsubScreen, latesubScreen, timeoffsubScreen;
    CardView discountcard_view, lateCardView, timeoffCardView;
    Button discount_button;
    Switch turnOffDicountSwitch, alldisountSwitch, nextLocationSwitch, totalallLocationSwitch, timeoffSwitch;
    String TURNOFFDISCOUNTSWITCH = "N";
    String ALLDISCOUNTSWITCH = "N";
    String NEXTLOCATIONSWITCH = "N";
    String TOTALALLLOCATIONSWITCH = "N";
    String TIMEOFFSWITCH= "N";
    String FROM_HOME_SCREEN_MANAGE_DISCOUNTS ="N";
    String FROM_HOME_SCREEN_MANAGE_TIMMINGS="N";
    String curflag,allflag;
    boolean isClick;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FloatingActionButton fab;
    String docid;
    String locid;
    CircleImageView logoImage;
    TextView name,qualification,Age_and_gender;
    String NAME,QUALIFICATION,AGE_AND_GENDER;
    String location_name,dlmid;
    RadioButton current_location,all_location;
    String one,two,three,Role;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Role = getIntent().getStringExtra("Role");
        location_name = getIntent().getExtras().getString("location_name");
        locid = getIntent().getExtras().getString("current_location_id");
        dlmid = getIntent().getExtras().getString("current_location_dlmid");
        setContentView(R.layout.quicksets_screen);
        iniView();
        setupToolbar();



        current_location = findViewById(R.id.current_location);
        all_location = findViewById(R.id.all_location);


        current_location.setChecked(true);

        logoImage = (CircleImageView) findViewById(R.id.logoImage);
        logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuickSetsActivity.this,DashboardActivity.class);
                intent.putExtra("Role",Role);
                intent.putExtra("current_location",location_name);
                intent.putExtra("current_location_id",locid);
                intent.putExtra("current_location_dlmid",dlmid);
                startActivity(intent);
            }
        });



        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        docid= sp.getString("doctorid", "");

        Log.i("sadafsf",docid);

        String[] separated = location_name.split(",");
         one = separated[0];
         two = separated[1];
         three = separated[2];

         Log.i("sadafsf",one);
         Log.i("sadafsf",two);
         Log.i("sadafsf",three);

        quicksethelp(locid);


        Log.i("locationid", locid);

        turnOffDicountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    TURNOFFDISCOUNTSWITCH ="Y";
                }
                else
                {
                    TURNOFFDISCOUNTSWITCH = "N";
                }
            }
        });

        alldisountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    ALLDISCOUNTSWITCH = "N";
                }
                else
                {
                    ALLDISCOUNTSWITCH = "Y";
                }
            }
        });

        Log.i("info",ALLDISCOUNTSWITCH);

        nextLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    NEXTLOCATIONSWITCH = "Y";
                }
                else
                {
                    NEXTLOCATIONSWITCH = "N";
                }
            }
        });


        totalallLocationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    TOTALALLLOCATIONSWITCH = "Y";
                }
                else
                {
                    TOTALALLLOCATIONSWITCH = "N";
                }
            }
        });

        timeoffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    TIMEOFFSWITCH = "Y";
                }
                else
                {
                    TIMEOFFSWITCH = "N";
                }
            }
        });

        timmingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FROM_HOME_SCREEN_MANAGE_DISCOUNTS = "Y";
                Intent intent = new Intent(QuickSetsActivity.this,TimmingEditActivity.class);
                intent.putExtra("FROM_HOME_SCREEN",FROM_HOME_SCREEN_MANAGE_DISCOUNTS);
                startActivity(intent);


            }
        });

        manageDiscountPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuickSetsActivity.this,ManageDiscountEditActivity.class);
                Log.i("sadafsf","dlmid "+dlmid);
                Log.i("sadafsf","one "+one);
                Log.i("sadafsf","two "+two);
                Log.i("sadafsf","three "+three);
                Log.i("sadafsf","Role in ClickListener "+Role);

                intent.putExtra("dlmid",dlmid);
                intent.putExtra("from_home","1");
                intent.putExtra("location_name",one);
                intent.putExtra("location_address",two);
                intent.putExtra("location_city",three);
                intent.putExtra("Role",Role);
                startActivity(intent);

            }
        });

        discount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfTimingExist(locid);

            }
        });


        navigationView = findViewById(R.id.navigation_vieww);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(QuickSetsActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });

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


    }

    private void userValidate(final String doctorid,final String ALLDISCOUNTSWITCH,final String locid,final String TURNOFFDISCOUNTSWITCH) {

        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                Log.i("adaslkdjasljda","docid"+doctorid);
                Log.i("adaslkdjasljda","all discount switch"+ALLDISCOUNTSWITCH);
                object.put("docid", doctorid);
                object.put("resp", ALLDISCOUNTSWITCH);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(QuickSetsActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.quicksets(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(QuickSetsActivity.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(QuickSetsActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(QuickSetsActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", QuickSetsActivity.this);
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



    private void userValidate2(final String doctorid,final String ALLDISCOUNTSWITCH,final String locid,final String TURNOFFDISCOUNTSWITCH) {

        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                Log.i("adaslkdjasljda","Locid"+locid);
                Log.i("adaslkdjasljda","all discount switch"+TURNOFFDISCOUNTSWITCH);
                object.put("locid", locid);
                object.put("resp",TURNOFFDISCOUNTSWITCH);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(QuickSetsActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.currentlocationdiscount(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(QuickSetsActivity.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(QuickSetsActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(QuickSetsActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", QuickSetsActivity.this);
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


    public void iniView() {
        this.discountcard_view = this.findViewById(R.id.discountcard_view);
        this.lateCardView = this.findViewById(R.id.lateCardView);
        this.timeoffCardView = this.findViewById(R.id.timeoffCardView);
        this.totime = this.findViewById(R.id.totime);
        this.fromtime = this.findViewById(R.id.fromtime);
        //this.timelate = this.findViewById(R.id.timelate);
        this.timelateoff = this.findViewById(R.id.timelateoff);
        this.manageDiscountPage = this.findViewById(R.id.manageDiscountPage);
        this.timmingText = this.findViewById(R.id.timmingText);

        this.sendNotification = this.findViewById(R.id.sendNotification);
        this.sendNotificationtime = this.findViewById(R.id.sendNotificationtime);

        this.discountsubScreen = this.findViewById(R.id.discountsubScreen);
        this.latesubScreen = this.findViewById(R.id.latesubScreen);
        this.timeoffsubScreen = this.findViewById(R.id.timeoffsubScreen);

        this.turnOffDicountSwitch = this.findViewById(R.id.turnOffDicountSwitch);
        this.alldisountSwitch = this.findViewById(R.id.alldisountSwitch);
        this.nextLocationSwitch = this.findViewById(R.id.nextLocationSwitch);
        this.totalallLocationSwitch = this.findViewById(R.id.totalallLocationSwitch);
        this.timeoffSwitch = this.findViewById(R.id.timeoffSwitch);

        this.discountsubScreen.setOnClickListener(this);
        this.latesubScreen.setOnClickListener(this);
        this.timeoffsubScreen.setOnClickListener(this);
        this.discountcard_view.setOnClickListener(this);
        this.lateCardView.setOnClickListener(this);
        this.timeoffCardView.setOnClickListener(this);

        this.discount_button = this.findViewById(R.id.button_discount);

    }


    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.discountcard_view) {
            if (discountsubScreen.getVisibility() == View.GONE) {
                discountsubScreen.setVisibility(View.VISIBLE);
            } else {
                discountsubScreen.setVisibility(View.GONE);
            }


        } else if (v.getId() == R.id.lateCardView) {
            if (latesubScreen.getVisibility() == View.GONE) {
                latesubScreen.setVisibility(View.VISIBLE);
            } else {
                latesubScreen.setVisibility(View.GONE);
            }

        } else if (v.getId() == R.id.timeoffCardView) {
            if (timeoffsubScreen.getVisibility() == View.GONE) {
                timeoffsubScreen.setVisibility(View.VISIBLE);
            } else {
                timeoffsubScreen.setVisibility(View.GONE);
            }

        }
    }



    public void checkIfTimingExist(final String dlmid)
    {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("locid", dlmid);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(QuickSetsActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.checkIfTimingExist(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        checkIfTimingExistModel mainContent = new Gson().fromJson(result, checkIfTimingExistModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Log.i("sadafsf",mainContent.getCheck());


                                if(mainContent.getCheck().equals("Y")) {

                                    Log.i("sadafsf","all loc checked"+String.valueOf(all_location.isChecked()));
                                    Log.i("sadafsf",String.valueOf(current_location.isChecked()));


                                    if(all_location.isChecked()) {

                                        userValidate(docid, ALLDISCOUNTSWITCH, locid, TURNOFFDISCOUNTSWITCH);
                                    }
                                    else if(current_location.isChecked())
                                    {
                                        userValidate2(docid, ALLDISCOUNTSWITCH, locid, TURNOFFDISCOUNTSWITCH);
                                    }



                                } else
                                {
                                    Toast.makeText(QuickSetsActivity.this, "Please Enter Timings First", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Toast.makeText(QuickSetsActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(QuickSetsActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", QuickSetsActivity.this);
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


    private void quicksethelp(final String locid) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("locid", locid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(QuickSetsActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.quicksethelp(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        quicksethelpModel mainContent = new Gson().fromJson(result, quicksethelpModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                allflag = mainContent.getAllflag();

                                Log.i("sadafsf",allflag+"all flag");

                                if(allflag != null) {
                                    if (allflag.equals("Y")) {
                                        turnOffDicountSwitch.setChecked(true);
                                    } else {
                                        turnOffDicountSwitch.setChecked(false);
                                    }
                                } else
                                {
                                    turnOffDicountSwitch.setChecked(false);
                                }



                            } else {
                                Toast.makeText(QuickSetsActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(QuickSetsActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", QuickSetsActivity.this);
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
