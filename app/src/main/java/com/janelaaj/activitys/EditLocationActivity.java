package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.all_information;
import com.janelaaj.model.getLocationDataModel;
import com.janelaaj.model.getServicesDetailsModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 *
 * @author Arshil Khan.
 */

public class EditLocationActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private EditText addLine1_name,addLine2_name,cityy,districtt,pincodee,clinic_location_name;
    Spinner statee;
    String location_name,location_address,location_city,location_id,lflagservice;
    TextView LOC_NAME;
    String[] SpecializationArray;
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender;
    String from_home,Role;
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Role = getIntent().getStringExtra("Role");
        from_home = getIntent().getExtras().getString("from_home");
        location_id = getIntent().getExtras().getString("id");
        getLocationData(location_id);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        if(!Role.equals("VIT")) {
            start(id);
        }


        setContentView(R.layout.activity_edit_location);
        setupToolbar();


        update = findViewById(R.id.Update);
        clinic_location_name = findViewById(R.id.clinic_location_name);
        LOC_NAME = findViewById(R.id.location_name);
        addLine1_name = findViewById(R.id.addLine1_name);
        addLine2_name = findViewById(R.id.addLine2_name);
        cityy = findViewById(R.id.city);
        districtt = findViewById(R.id.district);
        statee =  findViewById(R.id.state);
        pincodee =  findViewById(R.id.pincode);

        location_name = getIntent().getExtras().getString("location_name");
        location_address = getIntent().getExtras().getString("location_address");
        location_city = getIntent().getExtras().getString("location_city");

        LOC_NAME.setText(location_name + ',' + ' ' + location_address  + ',' + ' ' + location_city);



        statee =  findViewById(R.id.state);
        SpecializationArray = getResources().getStringArray(R.array.States);
        final List<String> plantsList = new ArrayList<>(Arrays.asList(SpecializationArray));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditLocationActivity.this,R.layout.spinner_item,plantsList){
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



        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(EditLocationActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = clinic_location_name.getText().toString().trim();
                String addrline1 = addLine1_name.getText().toString().trim();
                String addrline2 = addLine2_name.getText().toString().trim();
                String city = cityy.getText().toString().trim();
                String district = districtt.getText().toString().trim();
                String pin = pincodee.getText().toString().trim();
                String lid = location_id;
                String state = statee.getSelectedItem().toString();

                updateLocationValues(name,addrline1,addrline2,city,district,state,pin,lid);
            }
        });


    }

    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(EditLocationActivity.this, "", "Please wait..", true);
        progressbar.show();
        userValidate(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }
    }


    private void userValidate(final String Doctorid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(EditLocationActivity.this, "", "Please wait..", true);
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
                                Toast.makeText(EditLocationActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(EditLocationActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", EditLocationActivity.this);
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


    private void getLocationData(final String location_id) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("locid", location_id);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }


            final ProgressDialog progressbar = ProgressDialog.show(EditLocationActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.getLocationData(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        getLocationDataModel mainContent = new Gson().fromJson(result, getLocationDataModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                clinic_location_name.setText(mainContent.getLname());
                                addLine1_name.setText(mainContent.getLadrline1());
                                addLine2_name.setText(mainContent.getLadrline2());
                                cityy.setText(mainContent.getCity());
                                districtt.setText(mainContent.getDistrict());
                                pincodee.setText(mainContent.getPincode());
                                Integer pos=1;

                                String state = mainContent.getState();

                                for(int i=0;i<SpecializationArray.length;i++)
                                {
                                    if(SpecializationArray[i].equals(state))
                                    {
                                        Log.i("sdlkadjasjdasl",SpecializationArray[i]+" "+mainContent.getState());
                                        pos = i;
                                    }
                                }

                                statee.setSelection(pos);


                            } else {
                                Toast.makeText(EditLocationActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(EditLocationActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", EditLocationActivity.this);
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


    private void updateLocationValues(final String name,final String addrline1,final String addrline2,final String city,final String district,final String state,final String pin,final String lid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("name", name);
                object.put("adrline1", addrline1);
                object.put("adrline2", addrline2);
                object.put("city", city);
                object.put("district", district);
                object.put("state", state);
                object.put("pin", pin);
                object.put("locid", lid);

            }

            catch (JSONException e) {
                e.printStackTrace();
            }


            final ProgressDialog progressbar = ProgressDialog.show(EditLocationActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.updateLocationData(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        getLocationDataModel mainContent = new Gson().fromJson(result, getLocationDataModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Intent intent = new Intent(EditLocationActivity.this,ManageLocationActivity.class);
                                intent.putExtra("from_home",from_home);
                                intent.putExtra("Role",Role);
                                startActivity(intent);

                            } else {
                                Toast.makeText(EditLocationActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(EditLocationActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", EditLocationActivity.this);
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
