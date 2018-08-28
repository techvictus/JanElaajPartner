package com.janelaaj.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.all_information;
import com.janelaaj.model.checkIfTimingExistModel;
import com.janelaaj.model.featurePhoneUserIDGenerateModel;
import com.janelaaj.model.getDependentDetailsModel;
import com.janelaaj.model.checkifDetailExistForPatientModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Arshil Khan.
 */

public class vitalDataEntry extends AppCompatActivity {

    public String doctor_name2, Role;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    String doctor_name, doctor_dob, doctor_gender, doctor_speciality, doctor_introduction, doctor_experience;
    TextView name, qualification, Age_and_gender;
    LinearLayout mLL;
    Spinner servicesNames;
    Integer dataSize;
    EditText temphigh;
    RadioGroup radiogroupsmartphone;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask = "";
    private static final int SELECT_PHOTO = 100;
    private Bitmap bm;
    private String EncodedBase64 = "";
    EditText templow, phoneNumber;
    Button phoneNumberVerify, Next, userIDVerify;
    String image;
    CircleImageView profileImage;
    RadioButton Dependent, Self, repeatVisit, notRepeatVisit, malefiller, femalefiller, Otherfiller, smartPhone;
    private TextView welcome_dr_ashish, congratulations, your_profile_registration;
    private Button btn_home;
    LinearLayout dependentLinearLayout0, dependentLinearLayout1, dependentLinearLayout2, userIDFetchDetails, phonetypeLinearLayout;
    Spinner dependentSpinner;
    CheckBox featurePhone;
    String presentfetch, unamefetch, dobfetch, genderfetch, mobilefetch, emailfetch, motherfetch, mnamefetch, imagefetch, uidfetch;
    public EditText nameofFiller, mothernameofFiller, doboffiller;
    TextView usernameofFiller;
    ArrayList<getDependentDetailsModel.getDetails> all_info;
    ArrayAdapter<String> spinnerArrayAdapter;
    ArrayList<String> pdmidDependent, patientidDependent, dependentidDependent, nameDependent, dobDependent, genderDependent, photoDependent, emailDependent, mobileDependent;
    EditText dependentUSERNAME, dependentNAME, dependentMOTHERNAME, dependentDOB;
    RadioButton dependentMALE, dependentFEMALE, dependentOTHER;
    String patientid;
    String location_name, dlmid, locid;
    CircleImageView logoImage;
    String flag;
    String PMOBILE;
    String pdmid = "", pdid = "";
    int call = 0;
    public String dflag="N";
    RadioGroup selfdependent;



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
//        location_name = "Asdadada";
//        locid="dadssad";
//        dlmid = "Sadadad";

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        if (!Role.equals("VIT")) {
            start(id);
        }


        setContentView(R.layout.activity_vital_data_entry);
        setupToolbar();

        logoImage = (CircleImageView) findViewById(R.id.logoImage);
        logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vitalDataEntry.this, DashboardActivity.class);
                intent.putExtra("Role", Role);
                intent.putExtra("current_location", location_name);
                intent.putExtra("current_location_id", locid);
                intent.putExtra("current_location_dlmid", dlmid);

                Log.i("sadafsf", "Role " + Role);
                Log.i("sadafsf", "Currentloc " + location_name);
                Log.i("sadafsf", "current_location_id " + locid);
                Log.i("sadafsf", "dlmid " + dlmid);


                startActivity(intent);
            }
        });


        phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumberVerify = findViewById(R.id.phoneNumberVerify);
        Next = findViewById(R.id.NEXT);
        Dependent = findViewById(R.id.Dependent);
        Self = findViewById(R.id.Self);
        dependentLinearLayout0 = findViewById(R.id.dependentLinearLayout0);
        dependentLinearLayout1 = findViewById(R.id.dependentLinearLayout1);
        dependentLinearLayout2 = findViewById(R.id.dependentLinearLayout2);
//        phonetypeLinearLayout = findViewById(R.id.phonetypeLinearLayout);
        dependentSpinner = findViewById(R.id.dependentSpinner);
        usernameofFiller = findViewById(R.id.usernameofFiller);
        nameofFiller = findViewById(R.id.nameofFiller);
        mothernameofFiller = findViewById(R.id.mothernameofFiller);
        doboffiller = findViewById(R.id.doboffiller);
        malefiller = findViewById(R.id.malefiller);
        femalefiller = findViewById(R.id.femalefiller);
        Otherfiller = findViewById(R.id.otherfiller);
        profileImage = findViewById(R.id.profileimage);
        featurePhone = findViewById(R.id.featurePhone);
        all_info = new ArrayList<>();
        dependentUSERNAME = findViewById(R.id.dependentUSERNAME);
        dependentNAME = findViewById(R.id.dependentNAME);
        dependentMOTHERNAME = findViewById(R.id.dependentMOTHERNAME);
        dependentDOB = findViewById(R.id.dependentDOB);
        dependentMALE = findViewById(R.id.dependentMALE);
        dependentFEMALE = findViewById(R.id.dependentFEMALE);
        dependentOTHER = findViewById(R.id.dependentOTHER);
        selfdependent = findViewById(R.id.selfdependent);


        malefiller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      if (malefiller.isChecked()) {
                                                          femalefiller.setChecked(false);
                                                          Otherfiller.setChecked(false);
                                                      } else {
                                                          malefiller.setChecked(false);
//                                                         femalefiller.setChecked(false);
//                                                         Otherfiller.setChecked(false);
                                                      }
                                                  }
                                              }
        );


        femalefiller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                    @Override
                                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                        if (femalefiller.isChecked()) {
                                                            malefiller.setChecked(false);
                                                            Otherfiller.setChecked(false);
                                                        } else {
                                                            femalefiller.setChecked(false);
//                                                          malefiller.setChecked(false);
//                                                          Otherfiller.setChecked(false);
                                                        }
                                                    }
                                                }
        );


        Otherfiller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                       if (Otherfiller.isChecked()) {
                                                           malefiller.setChecked(false);
                                                           femalefiller.setChecked(false);
                                                       } else {
                                                           Otherfiller.setChecked(false);
//                                                          femalefiller.setChecked(false);
//                                                          malefiller.setChecked(false);
                                                       }
                                                   }
                                               }
        );


        doboffiller.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                // TODO Auto-generated method stub

                if (text.length() == 2 || text.length() == 5) {
                    text.append('-');
                }


            }
        });

        dependentDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                // TODO Auto-generated method stub

                if (text.length() == 2 || text.length() == 5) {
                    text.append('-');
                }


            }
        });


        final String[] SpecializationArray = getResources().getStringArray(R.array.dependentUser);
        final List<String> plantsList = new ArrayList<>(Arrays.asList(SpecializationArray));
        spinnerArrayAdapter = new ArrayAdapter<String>(vitalDataEntry.this, R.layout.support_simple_spinner_dropdown_item, plantsList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        dependentSpinner.setAdapter(spinnerArrayAdapter);


        Dependent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                 @Override
                                                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                     if (Dependent.isChecked()) {

                                                         if (nameofFiller.isEnabled() && Dependent.isChecked()) {
                                                             flag = "3";
                                                         } else if (!nameofFiller.isEnabled() && Dependent.isChecked()) {
                                                             flag = "2";
                                                         }

                                                          if(!phoneNumber.getText().toString().trim().equals("")) {
                                                              getDependentDetails(uidfetch);
                                                          } else
                                                          {
                                                              Toast.makeText(vitalDataEntry.this, "Enter Phone Number/User id/Email id", Toast.LENGTH_SHORT).show();
                                                          }


                                                     } else {
                                                         dependentSpinner.setVisibility(View.GONE);
                                                         dependentLinearLayout0.setVisibility(View.GONE);
                                                         dependentLinearLayout1.setVisibility(View.GONE);
                                                         dependentLinearLayout2.setVisibility(View.GONE);
                                                     }
                                                 }
                                             }
        );


        Self.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                if (Self.isChecked()) {
                                                    if(nameofFiller.isEnabled() && Self.isChecked())
                                                    {
                                                        flag="1";
                                                    }
                                                    dependentLinearLayout0.setVisibility(View.GONE);
                                                    dependentLinearLayout1.setVisibility(View.GONE);
                                                    dependentLinearLayout2.setVisibility(View.GONE);
                                                    dependentSpinner.setSelection(0);
                                                } else {

                                                }
                                            }
                                        }
        );


        dependentMALE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if (dependentMALE.isChecked()) {
                                                             dependentFEMALE.setChecked(false);
                                                             dependentOTHER.setChecked(false);
                                                         } else {
                                                             dependentMALE.setChecked(false);
//                                                         femalefiller.setChecked(false);
//                                                         Otherfiller.setChecked(false);
                                                         }
                                                     }
                                                 }
        );


        dependentFEMALE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           if (dependentFEMALE.isChecked()) {
                                                               dependentMALE.setChecked(false);
                                                               dependentOTHER.setChecked(false);
                                                           } else {
                                                               dependentFEMALE.setChecked(false);
//                                                          malefiller.setChecked(false);
//                                                          Otherfiller.setChecked(false);
                                                           }
                                                       }
                                                   }
        );


        dependentOTHER.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                      @Override
                                                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                          if (dependentOTHER.isChecked()) {
                                                              dependentMALE.setChecked(false);
                                                              dependentFEMALE.setChecked(false);
                                                          } else {
                                                              dependentOTHER.setChecked(false);
//                                                          femalefiller.setChecked(false);
//                                                          malefiller.setChecked(false);
                                                          }
                                                      }
                                                  }
        );


        dependentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                String selectedItem = adapter.getItemAtPosition(i).toString();
                if (selectedItem.equals("Add New Dependent User")) {
                    Log.i("sadafsf", "in add spinner code");
                    dependentLinearLayout0.setVisibility(View.VISIBLE);
                    dependentLinearLayout1.setVisibility(View.VISIBLE);
                    dependentLinearLayout2.setVisibility(View.VISIBLE);


                    dependentUSERNAME.setEnabled(false);
                    dependentNAME.setEnabled(true);
                    dependentMOTHERNAME.setEnabled(true);
                    dependentDOB.setEnabled(true);
                    dependentMALE.setEnabled(true);
                    dependentFEMALE.setEnabled(true);
                    dependentOTHER.setEnabled(true);


                    dependentUSERNAME.setText("", TextView.BufferType.EDITABLE);
                    dependentNAME.setText("", TextView.BufferType.EDITABLE);
                    dependentMOTHERNAME.setText("", TextView.BufferType.EDITABLE);
                    dependentDOB.setText("", TextView.BufferType.EDITABLE);
                    dependentMALE.setChecked(false);
                    dependentFEMALE.setChecked(false);
                    dependentOTHER.setChecked(false);


                    dependentMALE.setChecked(false);
                    dependentFEMALE.setChecked(false);
                    dependentOTHER.setChecked(false);


                } else {

                    for (int j = 0; j < all_info.size(); j++) {
                        if (all_info.get(j).getName().equals(selectedItem)) {
                            dependentLinearLayout0.setVisibility(View.VISIBLE);
                            dependentLinearLayout1.setVisibility(View.VISIBLE);
                            dependentLinearLayout2.setVisibility(View.VISIBLE);

                            dependentUSERNAME.setText(all_info.get(j).getDependentid(), TextView.BufferType.EDITABLE);
                            dependentNAME.setText(all_info.get(j).getName(), TextView.BufferType.EDITABLE);
                            dependentMOTHERNAME.setText(all_info.get(j).getMobile(), TextView.BufferType.EDITABLE);
                            dependentDOB.setText(all_info.get(j).getDob(), TextView.BufferType.EDITABLE);

                            if (all_info.get(j).getGender().equals("M")) {
                                dependentMALE.setChecked(true);
                                dependentFEMALE.setChecked(false);
                                dependentOTHER.setChecked(false);
                            } else if (all_info.get(j).getGender().equals("F")) {
                                dependentMALE.setChecked(false);
                                dependentFEMALE.setChecked(true);
                                dependentOTHER.setChecked(false);
                            } else if (all_info.get(j).getGender().equals("O")) {
                                dependentMALE.setChecked(false);
                                dependentFEMALE.setChecked(false);
                                dependentOTHER.setChecked(true);
                            }

                            dependentUSERNAME.setEnabled(false);
                            dependentNAME.setEnabled(false);
                            dependentMOTHERNAME.setEnabled(false);
                            dependentDOB.setEnabled(false);
                            dependentMALE.setEnabled(false);
                            dependentFEMALE.setEnabled(false);
                            dependentOTHER.setEnabled(false);

                        }
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!phoneNumber.getText().toString().trim().equals("")) {

                    String PNAME = nameofFiller.getText().toString().trim();
                    String PDOB = doboffiller.getText().toString().trim();
                    String PGENDER;
                    String PMOBILE = mobilefetch;
                    String PEMAIL = emailfetch;
                    String PPHOTO = imagefetch;
                    String MNAME = mothernameofFiller.getText().toString().trim();
                    String DNAME = dependentNAME.getText().toString().trim();
                    String DDOB = dependentDOB.getText().toString().trim();
                    String DGENDER;
                    String DPHOTO = "";
                    String DEMAIL = "";
                    String DMOBILE = dependentMOTHERNAME.getText().toString().trim();
                    String type;

                    int flagg = 0;


                    Log.i("sadafsf","PMOBILE "+ PMOBILE);

                    if(PMOBILE == null)
                    {
                        PMOBILE = phoneNumber.getText().toString().trim();
                    }
                    else if (PMOBILE.equals("")) {
                        PMOBILE = phoneNumber.getText().toString().trim();
                    }
                    if (!PDOB.equals("")) {
                        String[] s = PDOB.split("-");
                        int one = Integer.parseInt(s[0]);
                        int two = Integer.parseInt(s[1]);
                        int three = Integer.parseInt(s[2]);
                        if (one > 31 || two > 12 || three > 1997 || three < 1940) {
                            flagg = 0;
                        } else if (one == 0 || two == 0 || three == 0) {
                            flagg = 0;
                        } else if (one < 0 || two < 0 || three < 0) {
                            flagg = 0;
                        } else {
                            flagg = 1;
                        }


                    }


                    if (malefiller.isChecked()) {
                        PGENDER = "M";
                    } else if (femalefiller.isChecked()) {
                        PGENDER = "F";
                    } else {
                        PGENDER = "O";
                    }

                    if (dependentMALE.isChecked()) {
                        DGENDER = "M";
                    } else if (dependentFEMALE.isChecked()) {
                        DGENDER = "F";
                    } else {
                        DGENDER = "O";
                    }

                    if (featurePhone.isChecked()) {
                        type = "2";
                    } else {
                        type = "1";
                    }

                    if (!dependentNAME.isEnabled() && !usernameofFiller.isEnabled()) {
                        flag = null;
                    } else if (Self.isChecked() && !usernameofFiller.isEnabled()) {
                        flag = null;
                    }

                    if(phoneNumber.equals("0000000000"))
                    {
                        String i = usernameofFiller.getText().toString().trim();
                        dataEntryone(flag, i, PNAME, PDOB, PGENDER, PMOBILE, PEMAIL, PPHOTO, MNAME, DNAME, DDOB, DGENDER, DPHOTO, DEMAIL, DMOBILE, type);

                    }

                    dataEntryone(flag, uidfetch, PNAME, PDOB, PGENDER, PMOBILE, PEMAIL, PPHOTO, MNAME, DNAME, DDOB, DGENDER, DPHOTO, DEMAIL, DMOBILE, type);


                    if(phoneNumber.getText().toString().trim().equals("0000000000") && flag.equals("1") && Dependent.isChecked())
                    {
                        String gender_send = "";
                        if (dependentMALE.isChecked()) {
                            gender_send = "M";
                        } else if (dependentFEMALE.isChecked()) {
                            gender_send = "F";
                        } else {
                            gender_send = "O";
                        }
                        Intent intent = new Intent(vitalDataEntry.this, vitalDataEntry2.class);
                        intent.putExtra("NAME", dependentNAME.getText().toString().trim());
                        intent.putExtra("DOB", dependentDOB.getText().toString().trim());
                        intent.putExtra("Gender", gender_send);
                        intent.putExtra("location_name", location_name);
                        intent.putExtra("current_location_id", locid);
                        intent.putExtra("current_location_dlmid", dlmid);
                        intent.putExtra("pid", uidfetch);
                        intent.putExtra("pdmid", pdmid);
                        intent.putExtra("pdid", pdid);
                        intent.putExtra("dflag",dflag);
                        startActivity(intent);
                    }
                    else if (Self.isChecked() && !nameofFiller.isEnabled()) {
                        String gender_send;
                        if (malefiller.isChecked()) {
                            gender_send = "M";
                        } else if (femalefiller.isChecked()) {
                            gender_send = "F";
                        } else {
                            gender_send = "O";
                        }

                        Intent intent = new Intent(vitalDataEntry.this, vitalDataEntry2.class);
                        intent.putExtra("NAME", nameofFiller.getText().toString().trim());
                        intent.putExtra("DOB", doboffiller.getText().toString().trim());
                        intent.putExtra("Gender", gender_send);
                        intent.putExtra("location_name", location_name);
                        intent.putExtra("current_location_id", locid);
                        intent.putExtra("current_location_dlmid", dlmid);
                        intent.putExtra("pid", uidfetch);
                        intent.putExtra("pdmid", pdmid);
                        intent.putExtra("pdid", pdid);
                        intent.putExtra("dflag",dflag);
                        startActivity(intent);
                    } else if (flag == null) {

                        String gender_send = "";
                        if (dependentMALE.isChecked()) {
                            gender_send = "M";
                        } else if (dependentFEMALE.isChecked()) {
                            gender_send = "F";
                        } else {
                            gender_send = "O";
                        }
                        Intent intent = new Intent(vitalDataEntry.this, vitalDataEntry2.class);
                        intent.putExtra("NAME", dependentNAME.getText().toString().trim());
                        intent.putExtra("DOB", dependentDOB.getText().toString().trim());
                        intent.putExtra("Gender", gender_send);
                        intent.putExtra("location_name", location_name);
                        intent.putExtra("current_location_id", locid);
                        intent.putExtra("current_location_dlmid", dlmid);
                        intent.putExtra("pid", uidfetch);
                        intent.putExtra("pdmid", pdmid);
                        intent.putExtra("pdid", pdid);
                        intent.putExtra("dflag",dflag);
                        startActivity(intent);
                    } else {
                    }

                } else {
                    Toast.makeText(vitalDataEntry.this, "Please Enter Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        phoneNumberVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Self.setChecked(true);

                if (!phoneNumber.getText().toString().trim().equals("")) {
                    String PHONENUMBER = phoneNumber.getText().toString().trim();
                    Log.i("sadafsf", "in OnClickListener");
//                    if (PHONENUMBER.equals("0000000000")) {
////                    phonetypeLinearLayout.setVisibility(View.GONE);
//
//
//                        nameofFiller.setEnabled(true);
//                        doboffiller.setEnabled(true);
//                        malefiller.setEnabled(true);
//                        femalefiller.setEnabled(true);
//                        Otherfiller.setEnabled(true);
//                        usernameofFiller.setEnabled(true);
//                        mothernameofFiller.setEnabled(true);
//                        profileImage.setEnabled(true);
//
//
//                        usernameofFiller.setText("", TextView.BufferType.EDITABLE);
//                        nameofFiller.setText("", TextView.BufferType.EDITABLE);
//                        doboffiller.setText("", TextView.BufferType.EDITABLE);
//                        mothernameofFiller.setText("", TextView.BufferType.EDITABLE);
//                        profileImage.setImageResource(R.drawable.photo);
//
//                        if (malefiller.isChecked()) {
//                            malefiller.setChecked(false);
//                        } else if (femalefiller.isChecked()) {
//                            femalefiller.setChecked(false);
//                        } else if (Otherfiller.isChecked()) {
//                            Otherfiller.setChecked(false);
//                        }
//
//                        getpatientid();
//
//
//                        flag="4";
//
//                    } else {

                    if (PHONENUMBER.equals("0000000000")) {
                        getpatientid();
                    }



                        nameofFiller.setEnabled(true);
                        doboffiller.setEnabled(true);
                        malefiller.setEnabled(true);
                        femalefiller.setEnabled(true);
                        Otherfiller.setEnabled(true);
                        usernameofFiller.setEnabled(true);
                        mothernameofFiller.setEnabled(true);
                        profileImage.setEnabled(true);


                        usernameofFiller.setText("", TextView.BufferType.EDITABLE);
                        nameofFiller.setText("", TextView.BufferType.EDITABLE);
                        doboffiller.setText("", TextView.BufferType.EDITABLE);
                        mothernameofFiller.setText("", TextView.BufferType.EDITABLE);
                        profileImage.setImageResource(R.drawable.photo);


                        if (malefiller.isChecked()) {
                            malefiller.setChecked(false);
                        }

                        if (femalefiller.isChecked()) {

                            femalefiller.setChecked(false);
                        }

                        if (Otherfiller.isChecked()) {
                            Otherfiller.setChecked(false);
                        }

                        checkIfDetailsExist(PHONENUMBER);
                 //   }
                } else {
                    Toast.makeText(vitalDataEntry.this, "Enter Phone Number/Email ID/User ID", Toast.LENGTH_LONG).show();
                }

            }
        });

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(vitalDataEntry.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;


            }
        });


    }


    private void start(final String Docid) {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
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

    private void userValidate(final String Doctorid) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
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
                                Toast.makeText(vitalDataEntry.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(vitalDataEntry.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry.this);
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


    private void checkIfDetailsExist(final String phoneNumber) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("number", phoneNumber);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.checkIfDetailExist(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        checkifDetailExistForPatientModel mainContent = new Gson().fromJson(result, checkifDetailExistForPatientModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                PMOBILE = phoneNumber;
                                presentfetch = mainContent.getPresent();
                                dobfetch = mainContent.getDob();
                                genderfetch = mainContent.getGender();
                                mobilefetch = mainContent.getMobile();
                                emailfetch = mainContent.getEmail();
                                unamefetch = mainContent.getUname();
                                mnamefetch = mainContent.getMname();
                                imagefetch = mainContent.getPhoto();
                                uidfetch = mainContent.getId();

                                Log.i("sadafsf", "uid fetch" + uidfetch);

                                Log.i("sadafsf", genderfetch);


                                if (presentfetch.equals("Y") || phoneNumber.equals("0000000000")) {

                                    if(phoneNumber.equals("0000000000"))
                                    {


                                        nameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        doboffiller.setText("", TextView.BufferType.EDITABLE);
                                        mothernameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        profileImage.setImageResource(R.drawable.photo);

                                        if (malefiller.isChecked()) {
                                            malefiller.setChecked(false);
                                        } else if (femalefiller.isChecked()) {
                                            femalefiller.setChecked(false);
                                        } else if (Otherfiller.isChecked()) {
                                            Otherfiller.setChecked(false);
                                        }
                                     flag="1";

                                    }
                                    else {

                                        usernameofFiller.setText(emailfetch);
                                        nameofFiller.setText(unamefetch, TextView.BufferType.EDITABLE);
                                        doboffiller.setText(dobfetch, TextView.BufferType.EDITABLE);
                                        malefiller.setEnabled(true);
                                        femalefiller.setEnabled(true);
                                        Otherfiller.setEnabled(true);

                                        if (genderfetch.equals("M")) {
//
                                            malefiller.setChecked(true);
                                            femalefiller.setChecked(false);
                                            Otherfiller.setChecked(false);
//
                                        } else if (genderfetch.equals("F")) {

//                                        if(femalefiller.isChecked())
//                                        {
//
//                                        } else {
                                            malefiller.setChecked(false);
                                            femalefiller.setChecked(true);
                                            Otherfiller.setChecked(false);
//                                        malefiller.setEnabled(false);
//                                        femalefiller.setEnabled(false);
//                                        Otherfiller.setEnabled(false);
//                                        }
                                        } else {

//                                        if(Otherfiller.isChecked())
//                                        {
//
//                                        } else {
                                            malefiller.setChecked(false);
                                            femalefiller.setChecked(false);
                                            Otherfiller.setChecked(true);
//                                        }
                                        }

                                        mothernameofFiller.setText(mnamefetch, TextView.BufferType.EDITABLE);

                                        byte[] decodedString = Base64.decode(imagefetch, Base64.DEFAULT);
                                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                        profileImage.setImageBitmap(decodedByte);


                                        nameofFiller.setEnabled(false);
                                        doboffiller.setEnabled(false);
                                        malefiller.setEnabled(false);
                                        femalefiller.setEnabled(false);
                                        Otherfiller.setEnabled(false);
                                        usernameofFiller.setEnabled(false);
                                        mothernameofFiller.setEnabled(false);
                                        profileImage.setEnabled(false);



                                        if(mobilefetch.length() == 10 && !mobilefetch.equals("0000000000")) {
                                            sendOtp(phoneNumber);
                                        }
                                    }

                                } else {

                                    if (!featurePhone.isChecked()) {

                                        Log.i("sadafsf", "in smartPhone checked");

                                        nameofFiller.setEnabled(true);
                                        doboffiller.setEnabled(true);
                                        malefiller.setEnabled(true);
                                        femalefiller.setEnabled(true);
                                        Otherfiller.setEnabled(true);
                                        usernameofFiller.setEnabled(true);
                                        mothernameofFiller.setEnabled(true);
                                        profileImage.setEnabled(true);


                                        usernameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        nameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        doboffiller.setText("", TextView.BufferType.EDITABLE);
                                        mothernameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        profileImage.setImageResource(R.drawable.photo);

                                        if (malefiller.isChecked()) {
                                            malefiller.setChecked(false);
                                        } else if (femalefiller.isChecked()) {
                                            femalefiller.setChecked(false);
                                        } else if (Otherfiller.isChecked()) {
                                            Otherfiller.setChecked(false);
                                        }


                                        Toast.makeText(vitalDataEntry.this, "Please Register Yourself", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Log.i("sadafsf", "in smartPhone notchecked");
                                        flag = "1";


                                        nameofFiller.setEnabled(true);
                                        doboffiller.setEnabled(true);
                                        malefiller.setEnabled(true);
                                        femalefiller.setEnabled(true);
                                        Otherfiller.setEnabled(true);
                                        usernameofFiller.setEnabled(true);
                                        mothernameofFiller.setEnabled(true);
                                        profileImage.setEnabled(true);


                                        usernameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        nameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        doboffiller.setText("", TextView.BufferType.EDITABLE);
                                        mothernameofFiller.setText("", TextView.BufferType.EDITABLE);
                                        profileImage.setImageResource(R.drawable.photo);

                                        if (malefiller.isChecked()) {
                                            malefiller.setChecked(false);
                                        } else if (femalefiller.isChecked()) {
                                            femalefiller.setChecked(false);
                                        } else if (Otherfiller.isChecked()) {
                                            Otherfiller.setChecked(false);
                                        }

                                        usernameofFiller.setText(uidfetch);


                                        if(phoneNumber.length() == 10) {
                                            sendOtp(phoneNumber);
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(vitalDataEntry.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(vitalDataEntry.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry.this);
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


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = ProfileRegistrationActivity.Utility2.CheckPermission(vitalDataEntry.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static class Utility2 {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean CheckPermission(final Context context) {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if (currentAPIVersion >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        android.app.AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ProfileRegistrationActivity.Utility2.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String picturePath = getPath(vitalDataEntry.this.getApplicationContext(), selectedImageUri);
                onSelectFromGalleryResult(data, picturePath);
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            image = ConvertBitmapToBase64String(thumbnail);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        profileImage.setImageBitmap(thumbnail);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data, String picturePath) {

        bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                image = ConvertBitmapToBase64String(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   ImageButton.setText("UPLOAD SUCCESS FROM GALLERY");

            Uri uri = data.getData();
            String scheme = uri.getScheme();
            System.out.println("Scheme type " + scheme);
            if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
                try {
                    InputStream fileInputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                    dataSize = fileInputStream.available();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("sadafsf", "in gallery scheme content" + String.valueOf(dataSize));
//                System.out.println("File size in bytes"+dataSize);

            }
            Bitmap myBitmap = bm;

            try {
                ExifInterface exif = new ExifInterface(picturePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true); // rotating bitmap
            } catch (Exception e) {

            }
            profileImage.setImageBitmap(myBitmap);

        }

    }


    public String ConvertBitmapToBase64String(Bitmap bitmap) {
        EncodedBase64 = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bao);
        byte[] imgData = bao.toByteArray();
        EncodedBase64 = android.util.Base64.encodeToString(imgData, android.util.Base64.DEFAULT);

        return EncodedBase64;
    }

    private void sendOtp(final String pNumber) {

        int randomPIN = (int) (Math.random() * 9000) + 1000;
        Log.i("sadafsf", String.valueOf(randomPIN));
        final String PINString = String.valueOf(randomPIN);
        if (Utility.isOnline(this)) {
            String url = Contants.Send_Otp + pNumber + "&" + "message=" + PINString + "%20is%20your%20Jan%20Elaaj%20Registration%20OTP";
            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.sendOtpService(url, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                  /*  if (isComplete) {
                        Toast.makeText(SendOTPActivity.this, "OTP Send successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SendOTPActivity.this, VarifyOTPActivity.class);
                        intent.putExtra("PhoneNo", pNumber);
                        intent.putExtra("OTP", PINString);
                        startActivity(intent);
                    } else {
                        Utility.alertForErrorMessage("OTP not Send successfully!", SendOTPActivity.this);
                    }*/
//                    Intent intent = new Intent(SendOTPActivity.this, VarifyOTPActivity.class);
//                    intent.putExtra("PhoneNo", pNumber);
//                    intent.putExtra("OTP", PINString);
//                    intent.putExtra("Role", Role);
//                    intent.putExtra("Email", email);
//                    intent.putExtra("PersonName", PersonName);
//                    startActivity(intent);

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(vitalDataEntry.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(vitalDataEntry.this);
                    }
                    builder.setTitle("Confirm OTP")
                            .setMessage("OTP is " + PINString)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(false)
                            .show();


                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }


                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
        }
    }

    private void getpatientid() {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.getpatientid(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        checkifDetailExistForPatientModel mainContent = new Gson().fromJson(result, checkifDetailExistForPatientModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                String id = mainContent.getId();
                                usernameofFiller.setText(id);

                            } else {
                                Toast.makeText(vitalDataEntry.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(vitalDataEntry.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry.this);
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


    private void getDependentDetails(final String pid) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("pid", pid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.getDependentDetails(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        getDependentDetailsModel mainContent = new Gson().fromJson(result, getDependentDetailsModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                spinnerArrayAdapter.clear();
                                spinnerArrayAdapter.add("Select A Dependent User");
                                spinnerArrayAdapter.add("Add New Dependent User");
                                all_info = mainContent.getGetDetails();
                                dependentSpinner.setVisibility(View.VISIBLE);


                                if(!uidfetch.equals("VIU1111111111289")) {

                                    for (int i = 0; i < all_info.size(); i++) {
                                        Log.i("sadafsf", all_info.get(i).getName());
//                                    Log.i("sadafsf",all_info.get(i).getDependentid());
//                                    Log.i("sadafsf",all_info.get(i).getDob());
//                                    Log.i("sadafsf",all_info.get(i).getGender());
//                                    Log.i("sadafsf",all_info.get(i).getPhoto());
//                                    Log.i("sadafsf",all_info.get(i).getEmail());
//                                    Log.i("sadafsf",all_info.get(i).getMobile());

                                    }


                                    for (int i = 0; i < all_info.size(); i++) {

                                        spinnerArrayAdapter.add(all_info.get(i).getName());
//                                    dependentidDependent.add(all_info.get(i).getDependentid());
//                                    nameDependent.add(all_info.get(i).getName());
//                                    dobDependent.add(all_info.get(i).getDob());
//                                    genderDependent.add(all_info.get(i).getGender());
//                                    photoDependent.add(all_info.get(i).getPhoto());
//                                    emailDependent.add(all_info.get(i).getEmail());
//                                    mobileDependent.add(all_info.get(i).getMobile());
                                    }
                                }

                            } else {
                                Toast.makeText(vitalDataEntry.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(vitalDataEntry.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry.this);
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


    private void dataEntryone(final String flag, final String pid, final String pname, final String pdob, final String pgender, final String pmobile, final String pemail, final String pphoto, final String mname, final String dname, final String ddob, final String dgender, final String dphoto, final String demail, final String dmobile, final String type) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {


                String dob_send = "";
                String dob_send2 = "";

                if (!pdob.equals("")) {
                    String[] s = pdob.split("-");
                    dob_send = s[0] + s[1] + s[2];
                }

                if (!ddob.equals("")) {
                    String[] s2 = ddob.split("-");
                    dob_send2 = s2[0] + s2[1] + s2[2];
                }

                if(flag==null)
                {
                    String gender_send = "";
                    if (dependentMALE.isChecked()) {
                        gender_send = "M";
                    } else if (dependentFEMALE.isChecked()) {
                        gender_send = "F";
                    } else {
                        gender_send = "O";
                    }
                    Intent intent = new Intent(vitalDataEntry.this, vitalDataEntry2.class);
                    intent.putExtra("NAME", dependentNAME.getText().toString().trim());
                    intent.putExtra("DOB", dependentDOB.getText().toString().trim());
                    intent.putExtra("Gender", gender_send);
                    intent.putExtra("location_name", location_name);
                    intent.putExtra("current_location_id", locid);
                    intent.putExtra("current_location_dlmid", dlmid);
                    intent.putExtra("pid", uidfetch);
                    intent.putExtra("pdmid", pdmid);
                    intent.putExtra("pdid", pdid);
                    intent.putExtra("dflag",dflag);
                }
                else if(flag.equals("1"))
                {
                    if(pname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(pdob.equals(""))
                    {
                        Toast.makeText(this, "Please Enter D.O.B", Toast.LENGTH_SHORT).show();
                    }
                    else if(mname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Mother's Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(!malefiller.isChecked() && !femalefiller.isChecked() && !Otherfiller.isChecked())
                    {
                        Toast.makeText(this, "Please Select Atleast One Gender", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        call=1;
                    }

                }
                else if(flag.equals("2"))
                {
                    dflag = "Y";
                    if(dname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Dependent Name", Toast.LENGTH_SHORT).show();
                    } else if(ddob.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Dependent D.O.B", Toast.LENGTH_SHORT).show();
                    }
                    else if(!dependentMALE.isChecked() && !dependentFEMALE.isChecked() && !dependentOTHER.isChecked())
                    {
                        Toast.makeText(this, "Please Select Atleast One Gender", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        call =1;
                    }
                }
                else if(flag.equals("3"))
                {
                    dflag ="Y";
                    if(pname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(pdob.equals(""))
                    {
                        Toast.makeText(this, "Please Enter D.O.B", Toast.LENGTH_SHORT).show();
                    }
                    else if(mname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Mother's Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(!malefiller.isChecked() && !femalefiller.isChecked() && !Otherfiller.isChecked())
                    {
                        Toast.makeText(this, "Please Select Atleast One Gender", Toast.LENGTH_SHORT).show();
                    }
                    else if(dname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Dependent Name", Toast.LENGTH_SHORT).show();
                    } else if(ddob.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Dependent D.O.B", Toast.LENGTH_SHORT).show();
                    }
                    else if(!dependentMALE.isChecked() && !dependentFEMALE.isChecked() && !dependentOTHER.isChecked())
                    {
                        Toast.makeText(this, "Please Select Atleast One Gender", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        call = 1;
                    }
                } else if(flag.equals("4"))
                {
                    if(pname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(pdob.equals(""))
                    {
                        Toast.makeText(this, "Please Enter D.O.B", Toast.LENGTH_SHORT).show();
                    }
                    else if(mname.equals(""))
                    {
                        Toast.makeText(this, "Please Enter Mother's Name", Toast.LENGTH_SHORT).show();
                    }
                    else if(!malefiller.isChecked() && !femalefiller.isChecked() && !Otherfiller.isChecked())
                    {
                        Toast.makeText(this, "Please Select Atleast One Gender", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        call=1;
                    }
                }

                if(!nameofFiller.isEnabled() && Dependent.isChecked() && !dependentNAME.isEnabled())
                {
                    dflag = "Y";
                    pdid = dependentUSERNAME.getText().toString().trim();
                }




                Log.i("sadafsf", "flag is " + flag);
                Log.i("sadafsf", "pid is " + pid);
                Log.i("sadafsf", "pname is " + pname);
                Log.i("sadafsf", "pdob is " + dob_send);
                Log.i("sadafsf", "pgender is " + pgender);
                Log.i("sadafsf", "pmobile is " + pmobile);
                Log.i("sadafsf", "pemail is " + pemail);
                Log.i("sadafsf", "pphoto is " + pphoto);
                Log.i("sadafsf", "mname is " + mname);
                Log.i("sadafsf", "dname is " + dname);
                Log.i("sadafsf", "ddob is " + dob_send2);
                Log.i("sadafsf", "dgender is " + dgender);
                Log.i("sadafsf", "dphoto is " + dphoto);
                Log.i("sadafsf", "demail is " + demail);
                Log.i("sadafsf", "dmobile is " + dmobile);
                Log.i("sadafsf", "phonetype is " + type);



                if(phoneNumber.getText().toString().trim().equals("0000000000"))
                {
                    object.put("flag", flag);
                    object.put("pid", usernameofFiller.getText().toString().trim());
                    object.put("pname", pname);
                    object.put("pdob", dob_send);
                    object.put("pgender", pgender);
                    object.put("pmobile", pmobile);
                    object.put("pemail", pemail);
                    object.put("pphoto", pphoto);
                    object.put("mname", mname);
                    object.put("dname", dname);
                    object.put("ddob", dob_send2);
                    object.put("dgender", dgender);
                    object.put("dphoto", dphoto);
                    object.put("demail", demail);
                    object.put("dmobile", dmobile);
                    object.put("phonetype", type);
                } else{
                    object.put("flag", flag);
                    object.put("pid", pid);
                    object.put("pname", pname);
                    object.put("pdob", dob_send);
                    object.put("pgender", pgender);
                    object.put("pmobile", pmobile);
                    object.put("pemail", pemail);
                    object.put("pphoto", pphoto);
                    object.put("mname", mname);
                    object.put("dname", dname);
                    object.put("ddob", dob_send2);
                    object.put("dgender", dgender);
                    object.put("dphoto", dphoto);
                    object.put("demail", demail);
                    object.put("dmobile", dmobile);
                    object.put("phonetype", type);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (call == 1) {
                final ProgressDialog progressbar = ProgressDialog.show(vitalDataEntry.this, "", "Please wait..", true);
                progressbar.show();
                ServiceCaller serviceCaller = new ServiceCaller(this);
                serviceCaller.dataEntryone(object, new IAsyncWorkCompletedCallback() {
                    @Override
                    public void onDone(String result, boolean isComplete) {
                        if (isComplete) {
                            all_information mainContent = new Gson().fromJson(result, all_information.class);
                            if (mainContent != null) {
                                if (mainContent.getStatus().equals("SUCCESS")) {

                                    String Name;
                                    String DOB;
                                    String Gender;

                                    pdmid = mainContent.getPdid();
                                    pdid = mainContent.getPdid();
                                    if (Dependent.isChecked()) {
                                        Name = dname;
                                        DOB = ddob;
                                        Gender = dgender;
                                    } else {
                                        Name = pname;
                                        DOB = pdob;
                                        Gender = pgender;
                                    }

                                    Toast.makeText(vitalDataEntry.this, "SUCCESSFULLY SAVED", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(vitalDataEntry.this, vitalDataEntry2.class);
                                    Log.i("sadafsf", Name + "Name");
                                    Log.i("sadafsf", DOB + "DOB");
                                    Log.i("sadafsf", Gender + "Gender");


                                    Log.i("sadafsf","putextra pid "+ pid);
                                    Log.i("sadafsf","putextra pdid "+ pdid);
                                    Log.i("sadafsf","putextra dflag "+ dflag);


                                    intent.putExtra("NAME", Name);
                                    intent.putExtra("DOB", DOB);
                                    intent.putExtra("Gender", Gender);
                                    intent.putExtra("location_name", location_name);
                                    intent.putExtra("current_location_id", locid);
                                    intent.putExtra("current_location_dlmid", dlmid);
                                    intent.putExtra("pid", pid);
                                    intent.putExtra("pdmid", pdmid);
                                    intent.putExtra("pdid", pdid);
                                    intent.putExtra("dflag",dflag);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(vitalDataEntry.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(vitalDataEntry.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", vitalDataEntry.this);
                        }
                        if (progressbar.isShowing()) {
                            progressbar.dismiss();
                        }

                    }
                });
            } else {
            }
        }


    }
}
