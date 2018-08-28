package com.janelaaj.activitys;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.adapter.MangeDiscountExpandableListAdapter;
import com.janelaaj.adapter.ServicesRateExpandableListAdapter;
import com.janelaaj.adapter.TimeEditExpandableListAdapter2;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageDiscountModel;
import com.janelaaj.model.QuickSets;
import com.janelaaj.model.all_information;
import com.janelaaj.model.getServicesInformationModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author Arshil Khan.
 */

public class ManageDiscountEditActivity extends AppCompatActivity {

    MangeDiscountExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<INFOOO> listDataHeader;
    ArrayList<Boolean> check;
    TextView manage_discount_for,locationname,title_desc,mon,tue,wed,thur,fri,sat,sun;
    Button b1;
    HashMap<INFOOO, List<String>> listDataChild;

    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    public String dlmid;
    String location_name,location_address,location_city;
    ArrayList<ManageDiscountModel.time> ManageDiscountInfo;
    ArrayList<ManageDiscountModel.time> monday;
    ArrayList<ManageDiscountModel.time> tuesday;
    ArrayList<ManageDiscountModel.time> wednesday;
    ArrayList<ManageDiscountModel.time> thursday;
    ArrayList<ManageDiscountModel.time> friday;
    ArrayList<ManageDiscountModel.time> saturday;
    ArrayList<ManageDiscountModel.time> sunday;
    Integer monflag=0,tueflag=0,wedflag=0,thuflag=0,friflag=0,satflag=0,sunflag=0;
    Button apply_my_discount;
    public static int valueChanged =0;
    Integer valueChangedmon=0;
    Integer valueChangedtue=0;
    Integer valueChangedwed=0;
    Integer valueChangedthu=0;
    Integer valueChangedfri=0;
    Integer valueChangedsat=0;
    Integer valueChangedsun=0;

    ArrayList<INFO> updateInfo;
    String from_home,Role;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Role = getIntent().getStringExtra("Role");
        from_home = getIntent().getExtras().getString("from_home");
        dlmid = getIntent().getExtras().getString("dlmid");
        location_name = getIntent().getExtras().getString("location_name");
        location_address = getIntent().getExtras().getString("location_address");
        location_city = getIntent().getExtras().getString("location_city");


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        if(!Role.equals("VIT")) {
            start2(id);
        }


        start(dlmid);

        monday = new ArrayList<>();
        tuesday = new ArrayList<>();
        wednesday = new ArrayList<>();
        thursday = new ArrayList<>();
        friday = new ArrayList<>();
        saturday = new ArrayList<>();
        sunday = new ArrayList<>();
        updateInfo = new ArrayList<>();

        setContentView(R.layout.mnagediscountedit_screen);
        setupToolbar();


        apply_my_discount = (Button) findViewById(R.id.apply_my_discount);

        apply_my_discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updateInfo.size()==0)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Apply The Discount First", Toast.LENGTH_SHORT).show();
                }
                else {

                    JSONArray arr = new JSONArray();

                    for (int i = 0; i < updateInfo.size(); i++) {
                        JSONObject obj = new JSONObject();
                        try {
                            Log.i("sadafsf", "flag" + updateInfo.get(i).getflag());
                            Log.i("sadafsf,", "timeid" + updateInfo.get(i).getflag());
                            obj.put("flag", updateInfo.get(i).getflag());
                            obj.put("timeid", updateInfo.get(i).gettimeid());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arr.put(obj);
                    }

                    updateManageDiscount(arr);

                    valueChanged = 0;
                }


            }
        });





        manage_discount_for = findViewById(R.id.manage_discount_for);
        locationname = findViewById(R.id.locationheader);
        title_desc = findViewById(R.id.titledisc);
        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thur = findViewById(R.id.thu);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);

        b1 = findViewById(R.id.btn_home);


        locationname.setText(location_name + ',' + ' ' + location_address  + ',' + ' ' + location_city);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(ManageDiscountEditActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });



        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.i("sadafsf","in act"+valueChanged);

                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {

                    updateInfo.clear();
                    mon.setBackgroundResource(R.drawable.circle_bg2);
                    tue.setBackgroundResource(0);
                    wed.setBackgroundResource(0);
                    thur.setBackgroundResource(0);
                    fri.setBackgroundResource(0);
                    sat.setBackgroundResource(0);
                    sun.setBackgroundResource(0);

                    monflag = 1;
                    tueflag = 0;
                    wedflag = 0;
                    thuflag = 0;
                    friflag = 0;
                    satflag = 0;
                    sunflag = 0;


                    prepareListData(monday);
                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
                    expListView.setAdapter(listAdapter);
                    expListView.setOnGroupClickListener(new OnGroupClickListener() {

                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            Log.i("sadafsf","onGroupClick clicked");
                            return false;

                        }
                    });

                    expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
                        @Override
                        public void onGroupExpand(int groupPosition) {
                            Log.i("sadafsf","setOnGroupExpandListener clicked");

                        }
                    });

                    expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
                        @Override
                        public void onGroupCollapse(int groupPosition) {
                            Log.i("sadafsf","setOnGroupCollapseListener clicked");


                        }
                    });

                    expListView.setOnChildClickListener(new OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            Log.i("sadafsf","setOnChildClickListener clicked");
                            return false;

                        }
                    });
                }
            }
        });


        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.i("sadafsf","in act"+valueChanged);

                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {


                    updateInfo.clear();


                    mon.setBackgroundResource(0);
                    tue.setBackgroundResource(R.drawable.circle_bg2);
                    wed.setBackgroundResource(0);
                    thur.setBackgroundResource(0);
                    fri.setBackgroundResource(0);
                    sat.setBackgroundResource(0);
                    sun.setBackgroundResource(0);

                    monflag = 0;
                    tueflag = 1;
                    wedflag = 0;
                    thuflag = 0;
                    friflag = 0;
                    satflag = 0;
                    sunflag = 0;

                    prepareListData(tuesday);
                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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
                }

            }
        });


        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("sadafsf","in act"+valueChanged);



                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {

                    updateInfo.clear();


                    monflag = 0;
                    tueflag = 0;
                    wedflag = 1;
                    thuflag = 0;
                    friflag = 0;
                    satflag = 0;
                    sunflag = 0;

                    mon.setBackgroundResource(0);
                    tue.setBackgroundResource(0);
                    wed.setBackgroundResource(R.drawable.circle_bg2);
                    thur.setBackgroundResource(0);
                    fri.setBackgroundResource(0);
                    sat.setBackgroundResource(0);
                    sun.setBackgroundResource(0);

                    prepareListData(wednesday);
                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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
                }
            }
        });


        thur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("sadafsf","in act"+valueChanged);


                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {


                    updateInfo.clear();


                    monflag = 0;
                    tueflag = 0;
                    wedflag = 0;
                    thuflag = 1;
                    friflag = 0;
                    satflag = 0;
                    sunflag = 0;


                    mon.setBackgroundResource(0);
                    tue.setBackgroundResource(0);
                    wed.setBackgroundResource(0);
                    thur.setBackgroundResource(R.drawable.circle_bg2);
                    fri.setBackgroundResource(0);
                    sat.setBackgroundResource(0);
                    sun.setBackgroundResource(0);


                    prepareListData(thursday);
                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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
                }
            }
        });


        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("sadafsf","in act"+valueChanged);


                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {


                    updateInfo.clear();


                    monflag = 0;
                    tueflag = 0;
                    wedflag = 0;
                    thuflag = 0;
                    friflag = 1;
                    satflag = 0;
                    sunflag = 0;

                Log.i("sadafsf","fri flag in onclick"+friflag);
                    mon.setBackgroundResource(0);
                    tue.setBackgroundResource(0);
                    wed.setBackgroundResource(0);
                    fri.setBackgroundResource(R.drawable.circle_bg2);
                    thur.setBackgroundResource(0);
                    sat.setBackgroundResource(0);
                    sun.setBackgroundResource(0);


                    prepareListData(friday);

                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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
                }
            }
        });


//        fri.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.i("sadafsf","in act"+valueChanged);
//
//
//                if(valueChanged==1)
//                {
//                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
//                }
//                else {
//
//                    updateInfo.clear();
//
//                    monflag = 0;
//                    tueflag = 0;
//                    wedflag = 0;
//                    thuflag = 0;
//                    friflag = 1;
//                    satflag = 0;
//                    sunflag = 0;
//
//                    mon.setBackgroundResource(0);
//                    tue.setBackgroundResource(0);
//                    wed.setBackgroundResource(0);
//                    thur.setBackgroundResource(0);
//                    fri.setBackgroundResource(R.drawable.circle_bg2);
//                    sat.setBackgroundResource(0);
//                    sun.setBackgroundResource(0);
//
//                    prepareListData(friday);
//                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
//                    expListView.setAdapter(listAdapter);
//                    expListView.setOnGroupClickListener(new OnGroupClickListener() {
//
//                        @Override
//                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                            return false;
//                        }
//                    });
//
//                    expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//                        @Override
//                        public void onGroupExpand(int groupPosition) {
//
//                        }
//                    });
//
//                    expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//                        @Override
//                        public void onGroupCollapse(int groupPosition) {
//
//
//                        }
//                    });
//
//                    // Listview on child click listener
//                    expListView.setOnChildClickListener(new OnChildClickListener() {
//                        @Override
//                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                            return false;
//
//                        }
//                    });
//                }
//            }
//        });
//

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.i("sadafsf","in act"+valueChanged);


                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {

                    updateInfo.clear();

                    monflag = 0;
                    tueflag = 0;
                    wedflag = 0;
                    thuflag = 0;
                    friflag = 0;
                    satflag = 1;
                    sunflag = 0;

                    mon.setBackgroundResource(0);
                    tue.setBackgroundResource(0);
                    wed.setBackgroundResource(0);
                    thur.setBackgroundResource(0);
                    fri.setBackgroundResource(0);
                    sat.setBackgroundResource(R.drawable.circle_bg2);
                    sun.setBackgroundResource(0);


                    prepareListData(saturday);
                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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
                }
            }
        });


        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sadafsf","in act"+valueChanged);


                if(valueChanged==1)
                {
                    Toast.makeText(ManageDiscountEditActivity.this, "Save the previous value first", Toast.LENGTH_SHORT).show();
                }
                else {

                    updateInfo.clear();

                    monflag = 0;
                    tueflag = 0;
                    wedflag = 0;
                    thuflag = 0;
                    friflag = 0;
                    satflag = 0;
                    sunflag = 1;


                    mon.setBackgroundResource(0);
                    tue.setBackgroundResource(0);
                    wed.setBackgroundResource(0);
                    thur.setBackgroundResource(0);
                    fri.setBackgroundResource(0);
                    sat.setBackgroundResource(0);
                    sun.setBackgroundResource(R.drawable.circle_bg2);


                    prepareListData(sunday);
                    listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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

                            // TODO Auto-generated method stub
                            return false;

                        }
                    });
                }
            }
        });



        prepareListData(monday);
        expListView = this.findViewById(R.id.lvExp);
        listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    public void setvalueChanged(Integer val)
    {
         this.valueChanged = val;
    }

    private void start(final String Dlmid)
    {
        final String dlmid = Dlmid;
        final ProgressDialog progressbar = ProgressDialog.show(ManageDiscountEditActivity.this, "", "Please wait..", true);
        progressbar.show();
        progressbar.setCancelable(true);
        getServiceDetailsInitially(dlmid);
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




    private void prepareListData(ArrayList<ManageDiscountModel.time> day) {
        listDataHeader = new ArrayList<INFOOO>();
        listDataChild = new HashMap<INFOOO, List<String>>();
        check = new ArrayList<Boolean>();


        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemptio");




        for(int i=0;i<day.size();i++) {

            INFOOO info = new INFOOO();
            info.setdate(day.get(i).getFrom()+ ':' + day.get(i).getTo());
            info.setcheckedorunchecked(day.get(i).getDiscountflag());
            listDataHeader.add(info);
            listDataChild.put(listDataHeader.get(i), top250);

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


            final ProgressDialog progressbar = ProgressDialog.show(ManageDiscountEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.ManageDiscount(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ManageDiscountModel mainContent = new Gson().fromJson(result, ManageDiscountModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                monday = mainContent.getMonday();
                                tuesday= mainContent.getTuesday();
                                wednesday = mainContent.getWednesday();
                                thursday = mainContent.getThursday();
                                friday = mainContent.getFriday();
                                saturday = mainContent.getSaturday();
                                sunday = mainContent.getSunday();


                                mon.setBackgroundResource(R.drawable.circle_bg2);
                                tue.setBackgroundResource(0);
                                wed.setBackgroundResource(0);
                                thur.setBackgroundResource(0);
                                fri.setBackgroundResource(0);
                                sat.setBackgroundResource(0);
                                sun.setBackgroundResource(0);



                                monflag=1;
                                tueflag=0;
                                wedflag=0;
                                thuflag=0;
                                friflag=0;
                                satflag=0;
                                sunflag=0;

                                prepareListData(monday);
                                listAdapter = new MangeDiscountExpandableListAdapter(ManageDiscountEditActivity.this, listDataHeader, listDataChild, ManageDiscountInfo, monflag, tueflag, wedflag, thuflag, friflag, satflag, sunflag, monday, tuesday, wednesday, thursday, friday, saturday, sunday, updateInfo,valueChanged);
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
                                        // TODO Auto-generated method stub
                                        return false;

                                    }
                                });



                            } else {
                                Toast.makeText(ManageDiscountEditActivity.this, "error", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ManageDiscountEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ManageDiscountEditActivity.this);
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
        final ProgressDialog progressbar = ProgressDialog.show(ManageDiscountEditActivity.this, "", "Please wait..", true);
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

            final ProgressDialog progressbar = ProgressDialog.show(ManageDiscountEditActivity.this, "", "Please wait..", true);
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
                                Toast.makeText(ManageDiscountEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ManageDiscountEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ManageDiscountEditActivity.this);
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


    @Override
    public void onBackPressed() {
        valueChanged = 0;
        Intent intent = new Intent(ManageDiscountEditActivity.this,ManageLocationActivity.class);
        intent.putExtra("from_home",from_home);
        intent.putExtra("Role",Role);
        startActivity(intent);
    }

    private void updateManageDiscount(JSONArray arr) {

        if (Utility.isOnline(ManageDiscountEditActivity.this)) {
            JSONObject object = new JSONObject();
                try {
                    object.put("values",arr);
                    object.put("locid",dlmid);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }


            final ProgressDialog progressbar = ProgressDialog.show(ManageDiscountEditActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(ManageDiscountEditActivity.this);
            serviceCaller.updateManageDiscount(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(ManageDiscountEditActivity.this, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ManageDiscountEditActivity.this,ManageDiscountEditActivity.class);
//                                intent.putExtra("from_home",from_home);
//                                intent.putExtra("Role",Role);
                                intent.putExtra("dlmid",dlmid);
                                intent.putExtra("from_home",from_home);
                                intent.putExtra("location_name",location_name);
                                intent.putExtra("location_address",location_address);
                                intent.putExtra("location_city",location_city);
                                intent.putExtra("Role",Role);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ManageDiscountEditActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ManageDiscountEditActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", ManageDiscountEditActivity.this);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, ManageDiscountEditActivity.this);//off line msg....
        }
    }

}
