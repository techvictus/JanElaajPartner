package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.util.Calendar;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.all_information;
import com.janelaaj.model.allDetailsFetchModel;
import com.janelaaj.model.dataEntrytwoModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * @author Arshil Khan.
 */

public class vitalDataEntry2 extends AppCompatActivity {

    public String doctor_name2, Role;
    public String location_name, dlmid, locid;
    public ArrayList<allDetailsFetchModel.information> allinfo;
    public String pid, pdmid, pdid, dltmid, dflag;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    String doctor_name, doctor_dob, doctor_gender, doctor_speciality, doctor_introduction, doctor_experience;
    TextView name, qualification, Age_and_gender;
    LinearLayout mLL;
    Spinner servicesNames;
    EditText temphigh;
    EditText templow, phoneNumber;
    Button phoneNumberVerify, Next;
    EditText NameEdittext, DOBEdittext, GenderEdittext, HeightEdittext, WeightEdittext, BMIEdittext, temperatureEdittext, PulseEdittext, RespiratoryRateEdittext, BloodOxygenSaturation, BPHighEdittext, BPLowEdittext, SugarEdittext, HaemoglobinEdittext, BodyFatEdittext;
    TextView BloodGroupTextView, PulseTextView, RespiratoryRateTextView, BloodOxygenSaturationTextView, BPTextView, SugarTextView, HaemoglobinTextView, BodyFatTextView;
    String Name, Dob, Gender;
    int DAY;
    String BPFlag = "N", HaemoglobinFlag = "N", BloodOxygenSaturationFlag = "N", BloodGroupFlag = "N", RespiratoryFlag = "N", SugarFlag = "N", BodyFatFlag = "N", PulseFlag = "N";
    int total_bill = 0;
    Spinner BloodGroupEdittext;
    private TextView welcome_dr_ashish, congratulations, your_profile_registration;
    private Button btn_home;
    float height=0;
    float weight=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Role = "VIT";
        location_name = getIntent().getExtras().getString("location_name");
        locid = getIntent().getExtras().getString("current_location_id");
        dlmid = getIntent().getExtras().getString("current_location_dlmid");
        Name = getIntent().getExtras().getString("NAME");
        Dob = getIntent().getExtras().getString("DOB");
        Gender = getIntent().getExtras().getString("Gender");
        pid = getIntent().getExtras().getString("pid");
        pdid = getIntent().getExtras().getString("pdid");
        pdmid = getIntent().getExtras().getString("pdmid");
        dflag = getIntent().getExtras().getString("dflag");

        Log.i("sadafsf", "pdid " + pdid);
        Log.i("sadafsf", "pid" + pid);


        if (dflag.equals("Y")) {
            Log.i("sadafsf", "in equals");
            pdid = pid;
        }

        Log.i("sadafsf", "pid" + pid);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        if (!Role.equals("VIT")) {
            start(id);
        }


        setContentView(R.layout.activity_vital_data_entry2);
        setupToolbar();
        allDetailsFetch(dlmid);
        String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(vitalDataEntry2.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(vitalDataEntry2.this, DashboardActivity.class);
        intent.putExtra("Role", "VIT");
        intent.putExtra("current_location", location_name);
        intent.putExtra("current_location_id", locid);
        intent.putExtra("current_location_dlmid", dlmid);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void start(final String Docid) {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry2.this, "", "Please wait..", true);
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

    private void userValidate(final String dlmid) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", dlmid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry2.this, "", "Please wait..", true);
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


                                if (mainContent.getintroduction() != null) {
                                    Log.i("information", " doctor intro is " + mainContent.getintroduction());
                                    doctor_introduction = mainContent.getintroduction();
                                }


                                View myLayout = findViewById(R.id.navHader);
                                name = myLayout.findViewById(R.id.name_of_doctor);
                                qualification = myLayout.findViewById(R.id.qualification);
                                Age_and_gender = myLayout.findViewById(R.id.age_and_gender);
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


                            } else {
                                Toast.makeText(vitalDataEntry2.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(vitalDataEntry2.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry2.this);
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

    private void allDetailsFetch(final String dlmid) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                Log.i("sadafsf", "in api call" + dlmid);
                object.put("dlmid", dlmid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry2.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.allDetailsFetch(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        final allDetailsFetchModel mainContent = new Gson().fromJson(result, allDetailsFetchModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                allinfo = mainContent.getAllInfo();


                                NameEdittext = findViewById(R.id.NameEdittext);
                                DOBEdittext = findViewById(R.id.DOBEdittext);
                                GenderEdittext = findViewById(R.id.GenderEdittext);
                                NameEdittext = findViewById(R.id.NameEdittext);
                                DOBEdittext = findViewById(R.id.DOBEdittext);
                                GenderEdittext = findViewById(R.id.GenderEdittext);
                                HeightEdittext = findViewById(R.id.HeightEdittext);
                                WeightEdittext = findViewById(R.id.WeightEdittext);
                                BMIEdittext = findViewById(R.id.BMIEdittext);
                                temperatureEdittext = findViewById(R.id.temperatureEdittext);
                                PulseEdittext = findViewById(R.id.PulseEdittext);
                                RespiratoryRateEdittext = findViewById(R.id.RespiratoryRateEdittext);
                                BloodOxygenSaturation = findViewById(R.id.BloodOxygenSaturation);
                                BPHighEdittext = findViewById(R.id.BPHighEdittext);
                                BPLowEdittext = findViewById(R.id.BPLowEdittext);
                                SugarEdittext = findViewById(R.id.SugarEdittext);
                                HaemoglobinEdittext = findViewById(R.id.HaemoglobinEdittext);
                                BodyFatEdittext = findViewById(R.id.BodyFatEdittext);
                                Next = findViewById(R.id.next);
                                BloodGroupEdittext = findViewById(R.id.BloodGroupEdittext);
                                BloodGroupTextView = findViewById(R.id.BloodGroupTextView);
                                PulseTextView = findViewById(R.id.PulseTextView);
                                RespiratoryRateTextView = findViewById(R.id.RespiratoryRateTextView);
                                BloodOxygenSaturationTextView = findViewById(R.id.BloodOxygenSaturationTextView);
                                BPTextView = findViewById(R.id.BPTextView);
                                HaemoglobinTextView = findViewById(R.id.HaemoglobinTextView);
                                BodyFatTextView = findViewById(R.id.BodyFatTextView);
                                SugarTextView = findViewById(R.id.SugarTextView);


                                HeightEdittext.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        if(!s.toString().equals("")){
                                            height = Float.parseFloat(s.toString());
                                            if(height!=0){
                                                float bmi = weight/(height*height);
                                                Log.i("fwbvjvblsbk",String.valueOf(bmi));
                                                BMIEdittext.setText(String.valueOf(bmi*10000));
                                            }else{
                                                float bmi = 0;
                                                Log.i("fwbvjvblsbk",String.valueOf(bmi));
                                                BMIEdittext.setText(String.valueOf(bmi*10000));
                                            }
                                        }else{
                                            height =0;
                                        }

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });

                                WeightEdittext.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        if(!s.toString().equals("")){
                                            weight = Float.parseFloat(s.toString());
                                            if(height!=0){
                                                float bmi = weight/(height*height);
                                                Log.i("fwbvjvblsbk",String.valueOf(bmi));
                                                BMIEdittext.setText(String.valueOf(bmi*10000));
                                            }else{
                                                float bmi = 0;
                                                Log.i("fwbvjvblsbk",String.valueOf(bmi));
                                                BMIEdittext.setText(String.valueOf(bmi*10000));
                                            }
                                        }else{
                                            weight =0;
                                        }


                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });

                                String[] arraySpinner = new String[]{
                                        "Blood Group", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"
                                };
                                Spinner s = (Spinner) findViewById(R.id.BloodGroupEdittext);
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(vitalDataEntry2.this,
                                        R.layout.spinner_item, arraySpinner);
                                adapter.setDropDownViewResource(R.layout.spinner_item);
                                s.setAdapter(adapter);

                                Log.i("sadafsf","all info size"+allinfo.size());

                                for (int i = 0; i < allinfo.size(); i++) {
                                    dltmid = allinfo.get(i).getDltmid();
                                    if (allinfo.get(i).getSid().equals("10012")) {
                                        BloodGroupFlag = "Y";

                                        Log.i("sadafsf","in blood group flag"+BloodGroupFlag);

                                        if (allinfo.get(i).getCurlocdflag() != null) {
                                            if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                    if (allinfo.get(i).getDflag().equals("Y")) {
                                                        BloodGroupTextView.setText(allinfo.get(i).getDamt());
                                                    } else {
                                                        BloodGroupTextView.setText(allinfo.get(i).getNamt());
                                                    }
                                                } else {
                                                    BloodGroupTextView.setText(allinfo.get(i).getNamt());
                                                }
                                            } else {
                                                BloodGroupTextView.setText(allinfo.get(i).getNamt());
                                            }
                                        } else
                                        {
                                            BloodGroupTextView.setText(allinfo.get(i).getNamt());
                                        }


                                        } else if (allinfo.get(i).getSid().equals("10019")) {
                                            PulseFlag = "Y";


                                        if (allinfo.get(i).getCurlocdflag() != null) {
                                                if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                    if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                        if (allinfo.get(i).getDflag().equals("Y")) {
                                                            PulseTextView.setText(allinfo.get(i).getDamt());
                                                        } else {
                                                            PulseTextView.setText(allinfo.get(i).getNamt());
                                                        }
                                                    } else {
                                                        PulseTextView.setText(allinfo.get(i).getNamt());
                                                    }
                                                } else {
                                                    PulseTextView.setText(allinfo.get(i).getNamt());
                                                }
                                            } else
                                            {
                                                PulseTextView.setText(allinfo.get(i).getNamt());
                                            }


                                            } else if (allinfo.get(i).getSid().equals("10016")) {

                                                RespiratoryFlag = "Y";


                                        if (allinfo.get(i).getCurlocdflag() != null) {
                                                    if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                        if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                            if (allinfo.get(i).getDflag().equals("Y")) {
                                                                RespiratoryRateTextView.setText(allinfo.get(i).getDamt());
                                                            } else {
                                                                RespiratoryRateTextView.setText(allinfo.get(i).getNamt());
                                                            }
                                                        } else {
                                                            RespiratoryRateTextView.setText(allinfo.get(i).getNamt());
                                                        }
                                                    } else {
                                                        RespiratoryRateTextView.setText(allinfo.get(i).getNamt());
                                                    }
                                                } else
                                                {
                                                    RespiratoryRateTextView.setText(allinfo.get(i).getNamt());
                                                }

                                                } else if (allinfo.get(i).getSid().equals("10010")) {
                                                    BloodOxygenSaturationFlag = "Y";
                                                    if (allinfo.get(i).getCurlocdflag() != null) {
                                                        if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                            if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                                if (allinfo.get(i).getDflag().equals("Y")) {
                                                                    BloodOxygenSaturationTextView.setText(allinfo.get(i).getDamt());
                                                                } else {
                                                                    BloodOxygenSaturationTextView.setText(allinfo.get(i).getNamt());
                                                                }
                                                            } else {
                                                                BloodOxygenSaturationTextView.setText(allinfo.get(i).getNamt());
                                                            }
                                                        } else {
                                                            BloodOxygenSaturationTextView.setText(allinfo.get(i).getNamt());
                                                        }
                                                    } else
                                                    {
                                                        BloodOxygenSaturationTextView.setText(allinfo.get(i).getNamt());
                                                    }

                                                    } else if (allinfo.get(i).getSid().equals("10008")) {
                                                        BPFlag = "Y";
                                                        Log.i("sadafsf", allinfo.get(i).getCurlocdflag() + "current location flag");
                                                        if (allinfo.get(i).getCurlocdflag() != null) {
                                                            if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                                if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                                    if (allinfo.get(i).getDflag().equals("Y")) {
                                                                        BPTextView.setText(allinfo.get(i).getDamt());
                                                                    } else {
                                                                        BPTextView.setText(allinfo.get(i).getNamt());
                                                                    }
                                                                } else {
                                                                    BPTextView.setText(allinfo.get(i).getNamt());
                                                                }
                                                            } else {
                                                                BPTextView.setText(allinfo.get(i).getNamt());
                                                            }
                                                        } else {
                                                            BPTextView.setText(allinfo.get(i).getNamt());
                                                        }

                                                    } else if (allinfo.get(i).getSid().equals("10017")) {
                                                        SugarFlag = "Y";
                                                        if (allinfo.get(i).getCurlocdflag() != null) {
                                                            if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                                if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                                    if (allinfo.get(i).getDflag().equals("Y")) {
                                                                        SugarTextView.setText(allinfo.get(i).getDamt());
                                                                    } else {
                                                                        SugarTextView.setText(allinfo.get(i).getNamt());
                                                                    }
                                                                } else {
                                                                    SugarTextView.setText(allinfo.get(i).getNamt());
                                                                }
                                                            } else {
                                                                SugarTextView.setText(allinfo.get(i).getNamt());
                                                            }

                                                        } else
                                                        {
                                                            SugarTextView.setText(allinfo.get(i).getNamt());
                                                        }

                                                        } else if (allinfo.get(i).getSid().equals("10009")) {
                                                            HaemoglobinFlag = "Y";
                                                            if (allinfo.get(i).getCurlocdflag() != null) {
                                                                if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                                    if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                                        if (allinfo.get(i).getDflag().equals("Y")) {
                                                                            HaemoglobinTextView.setText(allinfo.get(i).getDamt());
                                                                        } else {
                                                                            HaemoglobinTextView.setText(allinfo.get(i).getNamt());
                                                                        }
                                                                    } else {
                                                                        HaemoglobinTextView.setText(allinfo.get(i).getNamt());
                                                                    }
                                                                } else {
                                                                    HaemoglobinTextView.setText(allinfo.get(i).getNamt());
                                                                }

                                                            } else
                                                            {
                                                                HaemoglobinTextView.setText(allinfo.get(i).getNamt());
                                                            }


                                                            } else if (allinfo.get(i).getSid().equals("10018")) {
                                                                BodyFatFlag = "Y";
                                                                if (allinfo.get(i).getCurlocdflag() != null) {
                                                                    if (allinfo.get(i).getCurlocdflag().equals("Y")) {
                                                                        if (allinfo.get(i).getDofferflag().equals("Y")) {
                                                                            if (allinfo.get(i).getDflag().equals("Y")) {
                                                                                BodyFatTextView.setText(allinfo.get(i).getDamt());
                                                                            } else {
                                                                                BodyFatTextView.setText(allinfo.get(i).getNamt());
                                                                            }
                                                                        } else {
                                                                            BodyFatTextView.setText(allinfo.get(i).getNamt());
                                                                        }
                                                                    } else {
                                                                        BodyFatTextView.setText(allinfo.get(i).getNamt());
                                                                    }

                                                                } else
                                                                {
                                                                    BodyFatTextView.setText(allinfo.get(i).getNamt());
                                                                }

                                                                }
                                                            }

                                                            if (BPFlag.equals("N")) {
                                                                BPTextView.setText("N.A");
                                                                BPHighEdittext.setEnabled(false);
                                                                BPLowEdittext.setEnabled(false);

                                                            }
                                                            if (HaemoglobinFlag.equals("N")) {
                                                                HaemoglobinTextView.setText("N.A");
                                                                HaemoglobinEdittext.setEnabled(false);
                                                            }
                                                            if (BloodOxygenSaturationFlag.equals("N")) {
                                                                BloodOxygenSaturationTextView.setText("N.A");
                                                                BloodOxygenSaturation.setEnabled(false);
                                                            }
                                                            if (BloodGroupFlag.equals("N")) {
                                                                BloodGroupTextView.setText("N.A");
                                                                BloodGroupEdittext.setEnabled(false);
                                                            }
                                                            if (RespiratoryFlag.equals("N")) {
                                                                RespiratoryRateTextView.setText("N.A");
                                                                RespiratoryRateEdittext.setEnabled(false);
                                                            }
                                                            if (SugarFlag.equals("N")) {
                                                                SugarTextView.setText("N.A");
                                                                SugarEdittext.setEnabled(false);
                                                            }
                                                            if (PulseFlag.equals("N")) {
                                                                PulseTextView.setText("N.A");
                                                                PulseEdittext.setEnabled(false);
                                                            }
                                                            if (BodyFatFlag.equals("N")) {
                                                                BodyFatTextView.setText("N.A");
                                                                BodyFatEdittext.setEnabled(false);
                                                            }


                                                            HeightEdittext.setHint(getString(R.string.heightHint));
                                                            WeightEdittext.setHint(getString(R.string.weightHint));
                                                            temperatureEdittext.setHint(getString(R.string.temperatureHint));
                                                            BPHighEdittext.setHint(getString(R.string.bpHighHint));
                                                            BPLowEdittext.setHint(getString(R.string.bpLowHint));
                                                            BMIEdittext.setHint(getString(R.string.bmiHint));


                                                            NameEdittext.setText(Name, TextView.BufferType.EDITABLE);
                                                            DOBEdittext.setText(Dob, TextView.BufferType.EDITABLE);

                                                            if (Gender.equals("M")) {
                                                                GenderEdittext.setText("M", TextView.BufferType.EDITABLE);
                                                            } else if (Gender.equals("F")) {
                                                                GenderEdittext.setText("F", TextView.BufferType.EDITABLE);
                                                            } else {
                                                                GenderEdittext.setText("O", TextView.BufferType.EDITABLE);
                                                            }

                                                            NameEdittext.setEnabled(false);
                                                            DOBEdittext.setEnabled(false);
                                                            GenderEdittext.setEnabled(false);


                                                            Next.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                    dataEntrytwo(dlmid, pid, dltmid, dflag);

                                                                }
                                                            });


                                                        } else {
                                                            Toast.makeText(vitalDataEntry2.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(vitalDataEntry2.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                                                    }
                                                } else {
                                                    com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry2.this);
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


                            private void dataEntrytwo ( final String dlmid, final String pid,
                            final String tid, final String dflag){
                                if (com.janelaaj.utilities.Utility.isOnline(this)) {

                                    JSONArray values = new JSONArray();

                                    for (int i = 0; i < allinfo.size(); i++) {


                                        if (allinfo.get(i).getSid().equals("10008")) {

                                            JSONObject obj = new JSONObject();
                                            try {


                                                if (!BPHighEdittext.getText().toString().trim().equals("") && !BPLowEdittext.getText().toString().trim().equals("")) {
                                                    total_bill += Integer.parseInt(BPTextView.getText().toString().trim());


                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + Integer.parseInt(BPHighEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_bp_lower " + Integer.parseInt(BPLowEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(BPTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);

                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", Integer.parseInt(BPHighEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_bp_lower", Integer.parseInt(BPLowEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(BPTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);

                                                    values.put(obj);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        } else if (allinfo.get(i).getSid().equals("10009")) {


                                            JSONObject obj = new JSONObject();
                                            try {

                                                if (!HaemoglobinEdittext.getText().toString().trim().equals("")) {
                                                    total_bill += Integer.parseInt(HaemoglobinTextView.getText().toString().trim());

                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + Integer.parseInt(HaemoglobinEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(HaemoglobinTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);

                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", Integer.parseInt(HaemoglobinEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(HaemoglobinTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);
                                                    values.put(obj);

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } else if (allinfo.get(i).getSid().equals("10010")) {
                                            JSONObject obj = new JSONObject();
                                            try {

                                                if (!BloodOxygenSaturation.getText().toString().trim().equals("")) {
                                                    total_bill += Integer.parseInt(BloodOxygenSaturationTextView.getText().toString().trim());


                                                    Log.i("sadafsf", " in blood oxygen saturation");
                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + Integer.parseInt(BloodOxygenSaturation.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(BloodOxygenSaturationTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);

                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", Integer.parseInt(BloodOxygenSaturation.getText().toString().trim()));
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(BloodOxygenSaturationTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);

                                                    values.put(obj);
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10011")) {
                                            JSONObject obj = new JSONObject();
                                            try {
                                                if (!BMIEdittext.getText().toString().trim().equals("")) {


                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + 0);
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + Integer.parseInt(BMIEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);


                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", 0);
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", Integer.parseInt(BMIEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);

                                                    values.put(obj);

                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10012")) {
                                            String text = BloodGroupEdittext.getSelectedItem().toString();

                                            if (!text.equals("Blood Group")) {
                                                JSONObject obj = new JSONObject();
                                                try {

                                                    Log.i("sadafsf", "inside !equals of bloodgroup");

                                                    total_bill += Integer.parseInt(BloodGroupTextView.getText().toString().trim());

                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(BloodGroupTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + text);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);

                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(BloodGroupTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", text);
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);
                                                    values.put(obj);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10013")) {
                                            JSONObject obj = new JSONObject();
                                            try {

                                                if (!HeightEdittext.getText().toString().trim().equals("")) {

                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + 0);
                                                    Log.i("sadafsf", "pdlad_height " + Integer.parseInt(HeightEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);


                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", 0);
                                                    obj.put("pdlad_height", Integer.parseInt(HeightEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);
                                                    values.put(obj);

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10016")) {
                                            if (!RespiratoryRateEdittext.getText().toString().trim().equals("")) {

                                                Log.i("sadafsf", "inside !equals respiratory");
                                                JSONObject obj = new JSONObject();
                                                try {
                                                    total_bill += Integer.parseInt(RespiratoryRateTextView.getText().toString().trim());


                                                    Log.i("sadafsf", " in respiratory rate saturation");

                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(RespiratoryRateTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + Integer.parseInt(RespiratoryRateEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);

                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(RespiratoryRateTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", Integer.parseInt(RespiratoryRateEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);
                                                    values.put(obj);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10017")) {
                                            JSONObject obj = new JSONObject();
                                            try {

                                                if (!SugarEdittext.getText().toString().trim().equals("")) {
                                                    total_bill += Integer.parseInt(SugarTextView.getText().toString().trim());


                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + Integer.parseInt(SugarEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(SugarTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);

                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", Integer.parseInt(SugarEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(SugarTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", 0);
                                                    values.put(obj);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10019")) {
                                            if (!PulseEdittext.getText().toString().trim().equals("")) {
                                                JSONObject obj = new JSONObject();
                                                try {
                                                    Log.i("sadafsf", "inside !equals pulse");

                                                    total_bill += Integer.parseInt(PulseTextView.getText().toString().trim());


                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(PulseTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + Integer.parseInt(PulseEdittext.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_bodyfat " + 0);


                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(PulseTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", Integer.parseInt(PulseEdittext.getText().toString().trim()));
                                                    obj.put("pdlad_bodyfat", 0);

                                                    values.put(obj);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } else if (allinfo.get(i).getSid().equals("10018")) {
                                            JSONObject obj = new JSONObject();
                                            try {

                                                if (!BodyFatEdittext.getText().toString().trim().equals("")) {
                                                    total_bill += Integer.parseInt(BodyFatTextView.getText().toString().trim());


                                                    Log.i("sadafsf", "pdlad_dcsm_sm_service_id " + allinfo.get(i).getSid());
                                                    Log.i("sadafsf", "pdlad_dcsm_normat_rate " + Integer.parseInt(allinfo.get(i).getNamt()));
                                                    Log.i("sadafsf", "pdlad_dcsm_discount_flag " + allinfo.get(i).getDflag());
                                                    Log.i("sadafsf", "pdlad_dcsm_discounted_amount " + Integer.parseInt(allinfo.get(i).getDamt()));
                                                    Log.i("sadafsf", "pdlad_bp_upper " + 0);
                                                    Log.i("sadafsf", "pdlad_bp_lower " + 0);
                                                    Log.i("sadafsf", "pdlad_haemoglobin " + 0);
                                                    Log.i("sadafsf", "pdlad_sugar " + 0);
                                                    Log.i("sadafsf", "pdlad_temperature " + 0);
                                                    Log.i("sadafsf", "pdlad_oxygenlevel " + 0);
                                                    Log.i("sadafsf", "pdlad_chargeablerate " + Integer.parseInt(BodyFatTextView.getText().toString().trim()));
                                                    Log.i("sadafsf", "pdlad_height " + 0);
                                                    Log.i("sadafsf", "pdlad_weight " + 0);
                                                    Log.i("sadafsf", "pdlad_bmi " + 0);
                                                    Log.i("sadafsf", "pdlad_respiratorylevel " + 0);
                                                    Log.i("sadafsf", "pdlad_blood_group " + 0);
                                                    Log.i("sadafsf", "pdlad_pulse " + 0);
                                                    Log.i("sadafsf", "pdlad_bodyfat " + Integer.parseInt(BodyFatEdittext.getText().toString().trim()));


                                                    obj.put("pdlad_dcsm_sm_service_id", allinfo.get(i).getSid());
                                                    obj.put("pdlad_dcsm_normat_rate", Integer.parseInt(allinfo.get(i).getNamt()));
                                                    obj.put("pdlad_dcsm_discount_flag", allinfo.get(i).getDflag());
                                                    obj.put("pdlad_dcsm_discounted_amount", Integer.parseInt(allinfo.get(i).getDamt()));
                                                    obj.put("pdlad_bp_upper", 0);
                                                    obj.put("pdlad_bp_lower", 0);
                                                    obj.put("pdlad_haemoglobin", 0);
                                                    obj.put("pdlad_sugar", 0);
                                                    obj.put("pdlad_temperature", 0);
                                                    obj.put("pdlad_oxygenlevel", 0);
                                                    obj.put("pdlad_chargeablerate", Integer.parseInt(BodyFatTextView.getText().toString().trim()));
                                                    obj.put("pdlad_height", 0);
                                                    obj.put("pdlad_weight", 0);
                                                    obj.put("pdlad_bmi", 0);
                                                    obj.put("pdlad_respiratorylevel", 0);
                                                    obj.put("pdlad_blood_group", "");
                                                    obj.put("pdlad_pulse", 0);
                                                    obj.put("pdlad_bodyfat", Integer.parseInt(BodyFatEdittext.getText().toString().trim()));

                                                    values.put(obj);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }


                                    if(total_bill ==0)
                                    {
                                        Toast.makeText(vitalDataEntry2.this, "Please Enter Some Value ", Toast.LENGTH_SHORT).show();
                                    }
                                    else {

                                        JSONObject object = new JSONObject();
                                        try {

                                            Log.i("sadafsf", "dlmid " + dlmid);
                                            Log.i("sadafsf", "pid " + pid);
                                            Log.i("sadafsf", "tid " + tid);
                                            Log.i("sadafsf", "dflag " + dflag);

                                            object.put("dlmid", dlmid);
                                            object.put("pid", pid);
                                            object.put("tid", tid);
                                            object.put("dflag", dflag);
                                            object.put("values", values);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                        final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry2.this, "", "Please wait..", true);
                                        progressbar.show();
                                        ServiceCaller serviceCaller = new ServiceCaller(this);
                                        serviceCaller.dataEntrytwo(object, new IAsyncWorkCompletedCallback() {
                                            @Override
                                            public void onDone(String result, boolean isComplete) {
                                                if (isComplete) {
                                                    dataEntrytwoModel mainContent = new Gson().fromJson(result, dataEntrytwoModel.class);
                                                    if (mainContent != null) {
                                                        if (mainContent.getStatus().equals("SUCCESS")) {






                                                            AlertDialog.Builder builder;
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                                builder = new AlertDialog.Builder(vitalDataEntry2.this, android.R.style.Theme_Material_Dialog_Alert);
                                                            } else {
                                                                builder = new AlertDialog.Builder(vitalDataEntry2.this);
                                                            }
                                                            builder.setTitle("Are You Sure?")
                                                                    .setMessage("Your Bill is " + total_bill)
                                                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            Toast.makeText(vitalDataEntry2.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(vitalDataEntry2.this, DashboardActivity.class);
                                                                            intent.putExtra("Role", "VIT");
                                                                            intent.putExtra("current_location", location_name);
                                                                            intent.putExtra("current_location_id", locid);
                                                                            intent.putExtra("current_location_dlmid", dlmid);
                                                                            startActivity(intent);
                                                                        }
                                                                    })
                                                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            total_bill = 0;
                                                                        }
                                                                    })
                                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                                    .setCancelable(false)
                                                                    .show();


                                                        } else {
                                                            Toast.makeText(vitalDataEntry2.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(vitalDataEntry2.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                                                    }
                                                } else {
                                                    com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry2.this);
                                                }
                                                if (progressbar.isShowing()) {
                                                    progressbar.dismiss();
                                                }
                                            }
                                        });

                                    }

                                } else {
                                    com.janelaaj.utilities.Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
                                }
                            }


                        }
