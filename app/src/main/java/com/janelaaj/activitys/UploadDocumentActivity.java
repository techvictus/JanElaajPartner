package com.janelaaj.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.QuickSets;
import com.janelaaj.model.UploadDocumentFetchModel;

import com.janelaaj.model.all_information;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Arshil Khan.
 */

public class UploadDocumentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICKFILE_REQUEST_CODE = 21 ;
    CheckBox MBBS,MS,MD,Diploma;
    RadioButton Aadhar,Voterid,Passport;
    String docid;
    String mbbs,ms,md,diploma;
    String aadhar="N",voterid="N",passport="N";
    Integer verifications_details=0;
    EditText number_edittext;
    ImageView attach,attach2;
    int flag;



    private CircleImageView logoImage;
    TextView proceed_further,headertitel,medical_registration,higest_qualification_degree,if_any_queries,well_be_more,thanks_regards;
    private Button edit, exit,submit_otp;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask = "";
    private static final int SELECT_PHOTO = 100;
    private Bitmap bm;
    private String EncodedBase64 = "";
    String  doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,doctor_experience;
    public String doctor_name2;
    TextView name,qualification,Age_and_gender,welcome_dr;
    String aadhaarflag,voteridflag,nosave,passportflag,mbbsflag,mdflag,msflag,diplomaflag;
    String Role;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Role = getIntent().getStringExtra("Role");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = sp.getString("doctorid", "");
        docid = id;

        Log.i("doctor",id);

        if(!Role.equals("VIT")) {
            start(id);
        }
        uploadDocumentFetch(id);


        setContentView(R.layout.activity_upload_document);
        iniView();
        setupToolbar();





        welcome_dr =  findViewById(R.id.welcome_dr);
        welcome_dr.setText(doctor_name);


        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        SharedPreferences settings = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        settings.edit().remove("loginflag").apply();
                        Intent intent = new Intent(UploadDocumentActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });







        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
              selectImage();
            }
        });

        attach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                selectImage();
            }
        });



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MBBS.isChecked())
                {
                    mbbs = "Y";
                }
                else
                {
                    mbbs = "N";
                }

                if(MD.isChecked())
                {
                    md = "Y";
                }
                else
                {
                    md = "N";
                }

                if(MS.isChecked())
                {
                    ms= "Y";
                }
                else
                {
                    ms ="N";
                }

                if(Diploma.isChecked())
                {
                    diploma = "Y";
                }
                else
                {
                    diploma = "N";
                }



                if(Aadhar.isChecked())
                {
                    verifications_details =1;
                }else if(Voterid.isChecked())
                {
                    verifications_details =2;
                } else if(Passport.isChecked())
                {
                    verifications_details = 3;
                }



                String edittext_value = number_edittext.getText().toString();


                Log.i("information","mbbs "+ mbbs);
                Log.i("information","md "+ md);
                Log.i("information","ms "+ ms);
                Log.i("information","diploma "+ diploma);
                Log.i("information","verification no "+ verifications_details);
                Log.i("information","number value "+ edittext_value);

                userValidate(docid,mbbs,md,ms,diploma,verifications_details,edittext_value);

            }
        });



    }


    private void start(final String Docid)
    {
        final String doctorid = Docid;
        final ProgressDialog progressbar = ProgressDialog.show(UploadDocumentActivity.this, "", "Please wait..", true);
        progressbar.show();
        userValidate2(doctorid);
        if (progressbar.isShowing()) {
            progressbar.dismiss();
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
                boolean result = ProfileRegistrationActivity.Utility2.CheckPermission(UploadDocumentActivity.this);
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
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
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
            String x =  ConvertBitmapToBase64String(thumbnail);
            Log.i("sadafsf",x+"this is the encoded string");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(flag==1) {
            attach.setImageBitmap(thumbnail);
        } else
        {
            attach2.setImageBitmap(thumbnail);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                String x = ConvertBitmapToBase64String(bm);
                Log.i("sadafsf",x+"this is the encoded string");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   ImageButton.setText("UPLOAD SUCCESS FROM GALLERY");
            if(flag==1) {
                attach.setImageBitmap(bm);
            } else
            {
                attach2.setImageBitmap(bm);
            }        }

    }


    public String ConvertBitmapToBase64String(Bitmap bitmap) {
        EncodedBase64 = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bao);
        byte[] imgData = bao.toByteArray();
        EncodedBase64 = android.util.Base64.encodeToString(imgData, android.util.Base64.DEFAULT);
        return EncodedBase64;
    }


    private void doExit() {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    UploadDocumentActivity.this);

            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(UploadDocumentActivity.this, LoginActivity.class);
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

    private void userValidate(final String doctorid,final String MBBS,final String MD,final String MS,final String Diploma,final Integer verification_details,final String edittext_value) {

        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", doctorid);
                object.put("MBBS", mbbs);
                object.put("MD", md);
                object.put("MS", ms);
                object.put("Diploma", diploma);
                object.put("verification_no", verifications_details);
                object.put("number", edittext_value);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(UploadDocumentActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.profilingcomplete(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            Log.i("error",mainContent.getStatus().toString());
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();

                                editor.putString("togo","3");

                                Toast.makeText(UploadDocumentActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UploadDocumentActivity.this,WelcomeRegistrationActivity.class);
                                intent.putExtra("Role",Role);
                                startActivity(intent);

                            } else {
                                Toast.makeText(UploadDocumentActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(UploadDocumentActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
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




    public void iniView() {
        this.exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);


        this.welcome_dr = findViewById(R.id.welcome_dr);
        this.proceed_further = findViewById(R.id.welcome_dr);
        this.headertitel = findViewById(R.id.welcome_dr);
        this.medical_registration = findViewById(R.id.welcome_dr);
        this.higest_qualification_degree = findViewById(R.id.welcome_dr);
        this.if_any_queries = findViewById(R.id.welcome_dr);
        this.well_be_more = findViewById(R.id.welcome_dr);
        this.thanks_regards = findViewById(R.id.welcome_dr);

        this.MBBS = findViewById(R.id.MBBS);
        this.MD = findViewById(R.id.MD);
        this.MS = findViewById(R.id.MS);
        this.Diploma = findViewById(R.id.Diploma);

        this.Aadhar =  findViewById(R.id.aadhar_card);
        this.Voterid =  findViewById(R.id.voter_id);
        this.Passport = findViewById(R.id.passport);

        this.number_edittext = findViewById(R.id.number_edittext);
        this.attach = findViewById(R.id.attach);
        this.attach2 = findViewById(R.id.attach2);


        this.exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);


        this.welcome_dr = findViewById(R.id.welcome_dr);
        this.proceed_further = findViewById(R.id.welcome_dr);
        this.headertitel = findViewById(R.id.welcome_dr);
        this.medical_registration = findViewById(R.id.welcome_dr);
        this.higest_qualification_degree = findViewById(R.id.welcome_dr);
        this.if_any_queries = findViewById(R.id.welcome_dr);
        this.well_be_more = findViewById(R.id.welcome_dr);
        this.thanks_regards = findViewById(R.id.welcome_dr);
        this.submit_otp = findViewById(R.id.submitOTP);





    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.exit) {
            Intent intent = new Intent(UploadDocumentActivity.this, WelcomeRegistrationActivity.class);
            startActivity(intent);
        }


    }


    private void userValidate2(final String Doctorid) {
        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", Doctorid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(UploadDocumentActivity.this, "", "Please wait..", true);
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
                                Toast.makeText(UploadDocumentActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(UploadDocumentActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        com.janelaaj.utilities.Utility.alertForErrorMessage("User Already Registered", UploadDocumentActivity.this);
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


    private void uploadDocumentFetch(final String doctorid) {

        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", doctorid);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(UploadDocumentActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.uploadDocumentFetch(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        UploadDocumentFetchModel mainContent = new Gson().fromJson(result, UploadDocumentFetchModel.class);
                        if (mainContent != null) {
                            Log.i("error",mainContent.getStatus().toString());
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                try {


                                    Log.i("sadafsf", "aadharflag" + mainContent.getAadhar_flag());
                                    Log.i("sadafsf", "voteridflag" + mainContent.getVoterid_flag());
                                    Log.i("sadafsf", "passportflag" + mainContent.getPassport_flag());
                                    Log.i("sadafsf", "mdflag" + mainContent.getMd_flag());
                                    Log.i("sadafsf", "mbbsflag" + mainContent.getMbbs_flag());
                                    Log.i("sadafsf", "msflag" + mainContent.getMs_flag());
                                    Log.i("sadafsf", "diplomaflag" + mainContent.getDiploma_flag());

                                    aadhaarflag = mainContent.getAadhar_flag();
                                    voteridflag = mainContent.getVoterid_flag();
                                    passportflag = mainContent.getPassport_flag();
                                    mbbsflag = mainContent.getMbbs_flag();
                                    mdflag = mainContent.getMd_flag();
                                    msflag = mainContent.getMs_flag();
                                    diplomaflag = mainContent.getDiploma_flag();


                                    if (mainContent.getAadhar_flag().equals("Y")) {
                                        nosave = mainContent.getAadhar_number();
                                    } else if (mainContent.getPassport_flag().equals("Y")) {
                                        nosave = mainContent.getPassport_number();
                                    } else if (mainContent.getVoterid_flag().equals("Y")) {
                                        nosave = mainContent.getVoterid_number();
                                    }


                                    if (aadhaarflag.equals("Y")) {
                                        Aadhar.setChecked(true);
                                        number_edittext.setText(nosave);
                                    } else if (passportflag.equals("Y")) {
                                        Passport.setChecked(true);
                                        number_edittext.setText(nosave);
                                    } else if (voteridflag.equals("Y")) {
                                        Voterid.setChecked(true);
                                        number_edittext.setText(nosave);
                                    }


                                    if (mbbsflag.equals("Y")) {
                                        MBBS.setChecked(true);
                                    }

                                    if (mdflag.equals("Y")) {
                                        MD.setChecked(true);
                                    }

                                    if (msflag.equals("Y")) {
                                        MS.setChecked(true);
                                    }

                                    if (diplomaflag.equals("Y")) {
                                        Diploma.setChecked(true);
                                    }


                                } catch (Exception e) {

                                }


//
//                                if(mainContent.getAadhar_flag().equals("Y"))
//                                {
//                                Aadhar.setChecked(true);
//                                number_edittext.setText(mainContent.getAadhar_number());
//                                }else if(mainContent.getPassport_flag().equals("Y"))
//                                {
//                                    Passport.setChecked(true);
//                                    number_edittext.setText(mainContent.getPassport_number());
//                                }
//                                else if(mainContent.getVoterid_flag().equals("Y"))
//                                {
//                                    Voterid.setChecked(true);
//                                    number_edittext.setText(mainContent.getVoterid_number());
//                                }
//
//
//                                if(mainContent.getMbbs_flag().equals("Y"))
//                                {
//                                    MBBS.setChecked(true);
//                                }
//
//                                if(mainContent.getMd_flag().equals("Y"))
//                                {
//                                    MD.setChecked(true);
//                                }
//
//                                if(mainContent.getMs_flag().equals("Y"))
//                                {
//                                    MS.setChecked(true);
//                                }
//
//                                if(mainContent.getDiploma_flag().equals("Y"))
//                                {
//                                    Diploma.setChecked(true);
//                                }


                            } else {
                                Toast.makeText(UploadDocumentActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(UploadDocumentActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
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
