package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.adapter.RegistrationPagerAdapter;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.MainContent;
import com.janelaaj.model.all_information;
import com.janelaaj.model.clinic;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created On 18-05-2018
 *
 * @author Arshil Khan.
 */

public class  AddLocationScreen extends AppCompatActivity{

    private TextView headertitel, headersubtitle, codeTextView;
    private CircleImageView logoImage;
    private EditText clinic_location_name,addLine1_name,addLine2_name,cityy,districtt,pincodee;
    View otpsendLayout;
    Spinner selectOptionSpinneer, statee;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender;
    private Button add_the_above_location,select_location_on_map;
    String Role;
    String from_home;


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    TextView myclinichospital;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Role = getIntent().getStringExtra("Role");
        from_home = getIntent().getExtras().getString("from_home");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");

        if(!Role.equals("VIT"))
        {
        start(id);
        }


        setContentView(R.layout.addlocation_screen);
        setupToolbar();


        clinic_location_name = findViewById(R.id.clinic_location_name);
        addLine1_name = findViewById(R.id.addLine1_name);
        addLine2_name = findViewById(R.id.addLine2_name);
        cityy = findViewById(R.id.city);
        districtt = findViewById(R.id.district);
        statee =  findViewById(R.id.state);
        pincodee =  findViewById(R.id.pincode);
        myclinichospital = findViewById(R.id.myclinichospital);

        if(Role.equals("VIT"))
        {
            myclinichospital.setText("Vital Kendra");
        }
        else
        {
            myclinichospital.setText("My Clinic/Hospital");
        }



        add_the_above_location = findViewById(R.id.add_the_above_location);
        select_location_on_map = findViewById(R.id.select_location_on_map);



        final String[] SpecializationArray = getResources().getStringArray(R.array.States);

        final List<String> plantsList = new ArrayList<>(Arrays.asList(SpecializationArray));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddLocationScreen.this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        statee.setAdapter(spinnerArrayAdapter);

        add_the_above_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = clinic_location_name.getText().toString().trim();
                final String address1 = addLine1_name.getText().toString().trim();
                final String address2 = addLine2_name.getText().toString().trim();
                final String city = cityy.getText().toString().trim();
                final String district = districtt.getText().toString().trim();
                final String state = statee.getSelectedItem().toString();
                final String pincode = pincodee.getText().toString().trim();
                final String Options = "1";
                final String Did = id;

                userValidate(name,address1,address2,city,district,state,pincode,Options,Did);

            }
        });





        select_location_on_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddLocationScreen.this,AddLocationMapActivity.class));

            }
        });




        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(AddLocationScreen.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });



        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");
//
//        selectOptionHeader.setTypeface(tf4);
//        state.setTypeface(tf3);
//        pincode.setTypeface(tf3);
//        clinic_name.setTypeface(tf3);
//        add_line1.setTypeface(tf3);
//        add_line2.setTypeface(tf3);
//        village_name.setTypeface(tf3);
//        dirst_name.setTypeface(tf3);

    }

    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(AddLocationScreen.this, "", "Please wait..", true);
        progressbar.show();
        userValidate(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }
    }


    private void userValidate(final String name,final String address1,final String address2,final String city,final String district,final String state,final String pincode, final String Options,final String Did) {

        Log.i("details",name);

        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("name", name);
                object.put("adrline1", address1);
                object.put("adrline2", address2);
                object.put("city", city);
                object.put("district", district);
                object.put("state", state);
                object.put("pin", pincode);
                object.put("docid", Did);
                object.put("option", Options);

            } catch (JSONException e) {
                e.printStackTrace();
            }



            final ProgressDialog progressbar = ProgressDialog.show(AddLocationScreen.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.clinicaddlocation(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        clinic mainContent = new Gson().fromJson(result, clinic.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                if(from_home.equals("0"))
                                {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putString("togo","2");

                                }


                                Log.i("status",mainContent.getStatus());
                                Toast.makeText(AddLocationScreen.this, "Successfully added the details", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddLocationScreen.this,ManageLocationActivity.class);
                                intent.putExtra("from_home",from_home);
                                intent.putExtra("doctorid",Did);
                                intent.putExtra("Role",Role);
                                startActivity(intent);


                            } else {
                                Toast.makeText(AddLocationScreen.this, "Error!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddLocationScreen.this, "Error!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Utility.alertForErrorMessage("Error", AddLocationScreen.this);
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


    private void userValidate(final String Doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(AddLocationScreen.this, "", "Please wait..", true);
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
                                Toast.makeText(AddLocationScreen.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddLocationScreen.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", AddLocationScreen.this);
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







    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
}
