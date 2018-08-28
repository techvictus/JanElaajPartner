package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.adapter.ServicesRateExpandableListAdapter;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.AddNewServiceModel;
import com.janelaaj.model.deleteTimmingModel;
import com.janelaaj.model.getServicesDetailsModel;
import com.janelaaj.model.getServicesInformationModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class AddNewServiceActivity extends AppCompatActivity implements Serializable {

    String dlmid;
    String totallocation;
    ArrayList<getServicesDetailsModel.SERVICE> servicesNamesArrayList;
    Spinner servicesNamesSpinner;
    CheckBox discounton;
    TextInputLayout DiscountAmountTextInputLayout;
    TextView locationname;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button saveButton;
    ArrayList<getServicesInformationModel.SERVICE> servicesInformationArrayList;
    EditText NormalAmount,DiscountAmount;
    String from_home,Role;
    String location_name,location_address,location_city;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Role = getIntent().getStringExtra("Role");
        if(Role.equals("VIT")) {
            getServicesNamesMethod2();
        } else
        {
            getServicesNamesMethod();
        }

        from_home = getIntent().getExtras().getString("from_home");

        Log.i("sadafsf","in addnewservices" + from_home);

        dlmid = getIntent().getExtras().getString("dlmid");
        totallocation = getIntent().getExtras().getString("totallocation");
        location_name = getIntent().getExtras().getString("location_name");
        location_address = getIntent().getExtras().getString("location_address");
        location_city = getIntent().getExtras().getString("location_city");

        servicesNamesArrayList = new ArrayList<getServicesDetailsModel.SERVICE>();
        servicesInformationArrayList = new ArrayList<getServicesInformationModel.SERVICE>();


//        start(dlmid);

        setContentView(R.layout.activity_add_new_service);
        setupToolbar();


        discounton = findViewById(R.id.isDiscountOn);
        locationname = findViewById(R.id.locationname);
        DiscountAmountTextInputLayout = findViewById(R.id.DiscountAmountTextInputLayout);
        saveButton = findViewById(R.id.saveButton);
        NormalAmount = findViewById(R.id.NormalAmount);
        DiscountAmount = findViewById(R.id.DiscountAmount);
        servicesNamesSpinner = findViewById(R.id.servicesNamesSpinner);




        locationname.setText(totallocation);
        discounton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(discounton.isChecked())
                {
                    DiscountAmountTextInputLayout.setVisibility(View.VISIBLE);
                    DiscountAmount.requestFocus();
                }
                else
                {
                    DiscountAmountTextInputLayout.setVisibility(View.GONE);
                }

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String normalAmount = NormalAmount.getText().toString().trim();
                String discountAmount="0";
                String sflag="N";
                String ServiceName = servicesNamesSpinner.getSelectedItem().toString();
                String sid="0";


                for(int i=0;i<servicesNamesArrayList.size();i++)
                {
                    if(ServiceName.equals(servicesNamesArrayList.get(i).getSName()))
                    {
                        sid = servicesNamesArrayList.get(i).getSid();
                    }
                }

                if(discounton.isChecked())
                {
                    sflag = "Y";
                    discountAmount = DiscountAmount.getText().toString().trim();
                }
                else
                {
                    sflag = "N";
                    discountAmount ="0";
                }

                JSONArray arr = new JSONArray();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("sid",sid);
                    obj.put("namt",normalAmount);
                    obj.put("damt",discountAmount);
                    obj.put("sflag",sflag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arr.put(obj);
                if(discountAmount.equals("0"))
                {
                    addNewService(arr, dlmid);
                } else {
                    if (Integer.parseInt(DiscountAmount.getText().toString().trim()) > Integer.parseInt(NormalAmount.getText().toString().trim())) {
                        Toast.makeText(AddNewServiceActivity.this, "Discount Amount Cannot be greator than Normal Amount", Toast.LENGTH_SHORT).show();
                    } else {
                        addNewService(arr, dlmid);
                    }
                }


            }
        });


    }

    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void getServicesNamesMethod() {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            final ProgressDialog progressbar = ProgressDialog.show(AddNewServiceActivity.this, "", "Please wait..", true);
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

                                start(dlmid);


                            } else {
                                Toast.makeText(AddNewServiceActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddNewServiceActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", AddNewServiceActivity.this);
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
            final ProgressDialog progressbar = ProgressDialog.show(AddNewServiceActivity.this, "", "Please wait..", true);
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

                                start(dlmid);


                            } else {
                                Toast.makeText(AddNewServiceActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddNewServiceActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", AddNewServiceActivity.this);
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



    private void start(final String Dlmid)
    {
        final String dlmid = Dlmid;
        final ProgressDialog progressbar = ProgressDialog.show(AddNewServiceActivity.this, "", "Please wait..", true);
        progressbar.show();
        progressbar.setCancelable(true);
        getServiceDetailsInitially(dlmid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
        }

    }

    private void getServiceDetailsInitially(final String Dlmid) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("dlmid", Dlmid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(AddNewServiceActivity.this, "", "Please wait..", true);
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
                                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(AddNewServiceActivity.this, android.R.layout.simple_spinner_item, android.R.id.text1);
                                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                servicesNamesSpinner.setAdapter(spinnerAdapter);
                                spinnerAdapter.add("Service Name");


                                for(int i=0;i<servicesNamesArrayList.size();i++) {

                                    int flag=1;

                                    for(int j=0;j<servicesInformationArrayList.size();j++)
                                    {
                                        if(servicesNamesArrayList.get(i).getSName().equals(servicesInformationArrayList.get(j).getServiceName()))
                                        {
                                            flag=0;
                                        }
                                    }
                                    if(flag==1)
                                    {
                                        spinnerAdapter.add(servicesNamesArrayList.get(i).getSName());
                                    }

                                }
                                spinnerAdapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(AddNewServiceActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddNewServiceActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", AddNewServiceActivity.this);
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


    private void addNewService(JSONArray values, final String Dlmid) {

        if (Utility.isOnline(AddNewServiceActivity.this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("values",values);
                object.put("dlmid", Dlmid);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(AddNewServiceActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(AddNewServiceActivity.this);
            serviceCaller.addNewService(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        AddNewServiceModel mainContent = new Gson().fromJson(result, AddNewServiceModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Intent intent = new Intent(AddNewServiceActivity.this,ServicesRatesEditActivity.class);
//                              intent.putExtra("from_home",from_home);
//                              intent.putExtra("Role",Role);
                                intent.putExtra("totallocation",totallocation);
                                intent.putExtra("dlmid",dlmid);
                                intent.putExtra("from_home",from_home);
                                intent.putExtra("Role",Role);
                                intent.putExtra("location_name",location_name);
                                intent.putExtra("location_address",location_address);
                                intent.putExtra("location_city",location_city);
                                startActivity(intent);

                            } else {
                                Toast.makeText(AddNewServiceActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AddNewServiceActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", AddNewServiceActivity.this);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, AddNewServiceActivity.this);//off line msg....
        }
    }



}
