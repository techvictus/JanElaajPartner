package com.janelaaj.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.janelaaj.model.imageFetchingModel;
import com.janelaaj.model.vitalsignupinfoModel;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @author Arshil Khan.
 */

public class ProfileRegistrationActivity extends AppCompatActivity {

    private TextView headertitel, headersubtitle, codeTextView, doctor, profile_registration;
    private CircleImageView profileimage;
    LinearLayout pagerIndicator;
    Spinner typeSpinneer;


    private ViewPager viewPager;
    private RegistrationPagerAdapter myViewPagerAdapter;
    private ImageView[] dots;
    String image="";
    //   private int[] layouts;

    Spinner s1, s2, s3;
    String nameVieStr, dobViewStr, emailidViewStr, passwordViewStr;
    String gender;
    String MedicalregiNo, ExperinceView, RegistrationYear, RegistrationCouncil, Specialization;
    String address1,address2,city,district,pincode,state,aadhar;
    String phoneNo = "";
    String Role;
    Integer dataSize;
    String email;
    String PersonName;
    TextView header_of_role;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask = "";
    private static final int SELECT_PHOTO = 100;
    private Bitmap bm;
    private String EncodedBase64 = "";
    String namefetch,dobfetch,genderfetch,emailfetch,passwordfetch,flagfetch,imagefetch;
    File f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        imagefetch = getIntent().getStringExtra("imagefetch");
        phoneNo = getIntent().getStringExtra("PhoneNo");
        namefetch = getIntent().getStringExtra("namefetch");
        dobfetch = getIntent().getStringExtra("dobfetch");
        genderfetch = getIntent().getStringExtra("genderfetch");
        emailfetch = getIntent().getStringExtra("emailfetch");
        passwordfetch = getIntent().getStringExtra("passwordfetch");
        flagfetch = getIntent().getStringExtra("flagfetch");

        Log.i("sadafsf","oncreate profileregistration"+ namefetch);
        Log.i("sadafsf","oncreate profileregistration"+ dobfetch);
        Log.i("sadafsf","oncreate profileregistration"+ emailfetch);
        Log.i("sadafsf","oncreate profileregistration"+ passwordfetch);
        Log.i("sadafsf","oncreate profileregistration"+ flagfetch);
        Log.i("sadafsf","oncreate profileregistration"+ genderfetch);


        vitalssignupinfo(phoneNo);

//        setContentView(R.layout.registration_viewpager);
//
//
//
//        header_of_role = findViewById(R.id.header_of_Role);
//
////        phoneNo = getIntent().getStringExtra("PhoneNo");
//
//        Role = getIntent().getStringExtra("Role");
////        Role="VIT";
//
//        email = getIntent().getStringExtra("Email");
//        PersonName = getIntent().getStringExtra("PersonName");
//
//        viewPager = this.findViewById(R.id.view_pager);
//        pagerIndicator = this.findViewById(R.id.viewPagerCountDots);
//        profileimage = this.findViewById(R.id.profileimage);
//
//        profile_registration = this.findViewById(R.id.profile_registration);
//        s1 = this.findViewById(R.id.specilization_nameLayout);
//        s2 = this.findViewById(R.id.medicalNo_nameLayout);
//        s3 = this.findViewById(R.id.regicouncle_nameLayout);
//
//
//        if(Role.equals("VIT"))
//        {
//         header_of_role.setText("Vitals Partner");
//        } else
//        {
//            header_of_role.setText("Doctor");
//        }
//
//      /*  layouts = new int[]{
//                R.layout.registrationfirst_page,
//                R.layout.registration_second_page,
//        };*/
//
//        changeStatusBarColor();
//
//
//
//        //myViewPagerAdapter = new MyViewPagerAdapter();
//        myViewPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager(),email,PersonName,Role,flagfetch,namefetch,dobfetch,emailfetch,passwordfetch,genderfetch);
//        viewPager.setAdapter(myViewPagerAdapter);
//        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        setUiPageViewController();
//
//
//
//
//        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
//        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
//        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
//        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");
//        profile_registration.setTypeface(tf3);
//
//        profileimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage();
//            }
//        });
//
//
//        Log.i("sadafsf",imagefetch+"fetched image string");
//
//        if(!imagefetch.equals("N")) {
//            byte[] decodedString = Base64.decode(imagefetch, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            profileimage.setImageBitmap(decodedByte);
//            profileimage.setEnabled(false);
//        }



    }


    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (dotsCount <= 1 || position > dots.length) {
                return;
            }
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageResource(R.drawable.nonselected_dot);
            }
            dots[position].setImageResource(R.drawable.selected_dot);
            if (position == myViewPagerAdapter.getCount() - 1) {
                //btnNext.setText("Register");
                //nexticon.setVisibility(View.GONE);
                profileimage.setVisibility(View.GONE);
            } else {
                //  btnNext.setText("Next");
                //  nexticon.setVisibility(View.VISIBLE);
                profileimage.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private int dotsCount;

    private void setUiPageViewController() {
        pagerIndicator.removeAllViews();
        dotsCount = myViewPagerAdapter.getCount();
        if (dotsCount <= 1) {
            pagerIndicator.setVisibility(View.INVISIBLE);
            return;
        }

        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.nonselected_dot);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(6, 0, 6, 0);
            pagerIndicator.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.selected_dot);
    }


    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //for geting next previous click action
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("ViewPageChange")) {
                boolean DoneFlag = intent.getBooleanExtra("DoneFlag", false);
                boolean NextPreviousFlag = intent.getBooleanExtra("NextPreviousFlag", false);

                if (NextPreviousFlag) {
                    nameVieStr = intent.getStringExtra("Name");
                    dobViewStr = intent.getStringExtra("DOB");
                    emailidViewStr = intent.getStringExtra("Email");
                    passwordViewStr = intent.getStringExtra("Password");
                    gender = intent.getStringExtra("Gender");
                    int currentPage = viewPager.getCurrentItem();
                    viewPager.setCurrentItem(currentPage + 1, true);
                } else if (DoneFlag) {
                    //second page data

                    if(Role.equals("VIT"))
                    {
                        address1 = intent.getStringExtra("Address1");
                        address2 = intent.getStringExtra("Address2");
                        city = intent.getStringExtra("City");
                        district = intent.getStringExtra("District");
                        state = intent.getStringExtra("State");
                        pincode = intent.getStringExtra("Pincode");
                        aadhar = intent.getStringExtra("Aadhar");
                        sendData2();

                    } else {
                        MedicalregiNo = intent.getStringExtra("MedicalregiNo");
                        ExperinceView = intent.getStringExtra("ExperinceView");
                        RegistrationYear = intent.getStringExtra("RegistrationYear");
                        RegistrationCouncil = intent.getStringExtra("RegistrationCouncil");
                        Specialization = intent.getStringExtra("Specialization");
                        sendData();

                    }

                } else {
                    int currentPagepre = viewPager.getCurrentItem();
                    if (currentPagepre > 0) {
                        viewPager.setCurrentItem(currentPagepre - 1, true);
                    }
                }

            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("ViewPageChange"));
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    //for hid keyboard when tab outside edittext box
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * View pager adapter
     */
   /* public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;


        public MyViewPagerAdapter() {
        }

        *//*  R.layout.registrationfirst_page,
          R.layout.registration_second_page,
  *//*
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          *//*  if (R.layout.registrationfirst_page == layouts[position]) {

            }else{

            }*//*
            View view = layoutInflater.inflate(layouts[position], container, false);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }*/

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility2.CheckPermission(ProfileRegistrationActivity.this);
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
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
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
            case Utility2.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
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
            image =  ConvertBitmapToBase64String(thumbnail);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


            profileimage.setImageBitmap(thumbnail);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                image = ConvertBitmapToBase64String(bm);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   ImageButton.setText("UPLOAD SUCCESS FROM GALLERY");

            Uri uri  = data.getData();
            String scheme = uri.getScheme();
            System.out.println("Scheme type " + scheme);
            if(scheme.equals(ContentResolver.SCHEME_CONTENT))
            {
                try {
                    InputStream fileInputStream=getApplicationContext().getContentResolver().openInputStream(uri);
                    dataSize = fileInputStream.available();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("sadafsf","in gallery scheme content"+ String.valueOf(dataSize));
//                System.out.println("File size in bytes"+dataSize);

            }
            else if(scheme.equals(ContentResolver.SCHEME_FILE))
            {
                String path = uri.getPath();
                try {
                    f = new File(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("sadafsf","in gallery scheme file" + String.valueOf(f.length()));
//                System.out.println("File size in bytes"+f.length());
            }

//            if(dataSize>10000000 || f.length()>10000000)
                if(dataSize>500000)
                {
                    Toast.makeText(this, "Please Select A Smaller Image", Toast.LENGTH_LONG).show();
                    profileimage.setImageResource(R.drawable.photo);
                }
                else {
                    profileimage.setImageBitmap(bm);
                }
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

    private void sendData() {
        if (Utility.isOnline(this)) {
            final ProgressDialog progressbar = ProgressDialog.show(ProfileRegistrationActivity.this, "", "Please wait..", true);
            progressbar.show();
            JSONObject object = new JSONObject();
            try {


                String[] s=dobViewStr.split("-");
                String dob_send = s[0]+s[1]+s[2];

                String password2 = md5(passwordViewStr);

                Log.i("sadafsf","sending dob"+dob_send);

                Log.i("sdajhka",dob_send);
                object.put("mobile",phoneNo);
                object.put("pldrole",Role);
                object.put("name", nameVieStr);
                object.put("gender", gender);
                object.put("dob", dob_send);
                object.put("email", emailidViewStr);
                object.put("password", password2);
                object.put("specialityid", Specialization);
                object.put("registernumber", MedicalregiNo);
                object.put("registercouncil", RegistrationCouncil);
                object.put("registeryear", RegistrationYear);
                object.put("experience", ExperinceView);
                object.put("image",image);

                Log.i("sadafsf",image+"fetched image string in API call");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.SignUpService(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        MainContent mainContent = new Gson().fromJson(result, MainContent.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {
                                Toast.makeText(ProfileRegistrationActivity.this, "Registration successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ProfileRegistrationActivity.this, SelectOptionScreen.class);
                                intent.putExtra("Role",Role);
//                                intent.putExtra("doctorid",mainContent.getId());

                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProfileRegistrationActivity.this);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("doctorid",mainContent.getId());
                                editor.commit();
                                Log.i("doctorid",mainContent.getId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ProfileRegistrationActivity.this, mainContent.getStatus(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ProfileRegistrationActivity.this, "Registration not successfull!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Utility.alertForErrorMessage("Registration not successfully!", ProfileRegistrationActivity.this);
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

    private void sendData2() {
        if (Utility.isOnline(this)) {
            final ProgressDialog progressbar = ProgressDialog.show(ProfileRegistrationActivity.this, "", "Please wait..", true);
            progressbar.show();
            JSONObject object = new JSONObject();
            try {

                Log.i("sadafsf","dob before send"+dobViewStr);

                String[] s=dobViewStr.split("-");
                String dob_send = s[0]+s[1]+s[2];
                Log.i("sadafsf","dob after send"+dob_send);

                String password2 = md5(passwordViewStr);

                Log.i("sadafsf",dob_send);
                Log.i("sadafsf",phoneNo);
                Log.i("sadafsf",Role);
                Log.i("sadafsf",nameVieStr);
                Log.i("sadafsf",gender);
                Log.i("sadafsf",emailidViewStr);
                Log.i("sadafsf",password2);
                Log.i("sadafsf",address1);
                Log.i("sadafsf",address2);
                Log.i("sadafsf",city);
                Log.i("sadafsf",state);
                Log.i("sadafsf",pincode);
                Log.i("sadafsf",district);
                Log.i("sadafsf",aadhar);
                Log.i("sdajhka",dob_send);

                object.put("mobile",phoneNo);
                object.put("pldrole",Role);
                object.put("name", nameVieStr);
                object.put("gender", gender);
                object.put("dob", dob_send);
                object.put("email", emailidViewStr);
                object.put("password", password2);
                object.put("address1", address1);
                object.put("address2", address2);
                object.put("city", city);
                object.put("state", state);
                object.put("pincode", pincode);
                object.put("district", district);
                object.put("adnumber", aadhar);
                object.put("image",image);
                Log.i("sadafsf",image+"fetched image string in API call");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.SignUpService2(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        MainContent mainContent = new Gson().fromJson(result, MainContent.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {
                                Toast.makeText(ProfileRegistrationActivity.this, "Registration successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ProfileRegistrationActivity.this, SelectOptionScreen.class);
                                intent.putExtra("Role",Role);

                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ProfileRegistrationActivity.this);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("doctorid",mainContent.getId());
                                editor.commit();

                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ProfileRegistrationActivity.this, mainContent.getStatus(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ProfileRegistrationActivity.this, "Registration not successfull!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Utility.alertForErrorMessage("Registration not successfully!", ProfileRegistrationActivity.this);
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

    private void vitalssignupinfo(final String number) {
        if (Utility.isOnline(this)) {
            final ProgressDialog progressbar = ProgressDialog.show(ProfileRegistrationActivity.this, "", "Please wait..", true);
            progressbar.show();
            JSONObject object = new JSONObject();
            try {
                object.put("number",number);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.vitalssignupinfo(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                       vitalsignupinfoModel  mainContent = new Gson().fromJson(result, vitalsignupinfoModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                imagefetch = mainContent.getImage();


                                setContentView(R.layout.registration_viewpager);

                                header_of_role = findViewById(R.id.header_of_Role);
                                Role = getIntent().getStringExtra("Role");
                                email = getIntent().getStringExtra("Email");
                                PersonName = getIntent().getStringExtra("PersonName");

                                viewPager = findViewById(R.id.view_pager);
                                pagerIndicator = findViewById(R.id.viewPagerCountDots);
                                profileimage = findViewById(R.id.profileimage);

                                profile_registration = findViewById(R.id.profile_registration);
                                s1 = findViewById(R.id.specilization_nameLayout);
                                s2 = findViewById(R.id.medicalNo_nameLayout);
                                s3 = findViewById(R.id.regicouncle_nameLayout);


                                if(Role.equals("VIT"))
                                {
                                    header_of_role.setText("Vitals Partner");
                                } else
                                {
                                    header_of_role.setText("Doctor");
                                }


                                changeStatusBarColor();

                                myViewPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager(),email,PersonName,Role,flagfetch,namefetch,dobfetch,emailfetch,passwordfetch,genderfetch);
                                viewPager.setAdapter(myViewPagerAdapter);
                                viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
                                setUiPageViewController();

                                Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
                                Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
                                Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
                                Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");
                                profile_registration.setTypeface(tf3);

                                profileimage.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        selectImage();
                                    }
                                });


                                Log.i("sadafsf",imagefetch+"fetched image string");

                                if(!imagefetch.equals("N")) {
                                    byte[] decodedString = Base64.decode(imagefetch, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    profileimage.setImageBitmap(decodedByte);
                                    profileimage.setEnabled(false);
                                }





                            } else {
                                Toast.makeText(ProfileRegistrationActivity.this, mainContent.getStatus(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ProfileRegistrationActivity.this, "Registration not successfull!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Utility.alertForErrorMessage("Registration not successfully!", ProfileRegistrationActivity.this);
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




    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String getisDetail() {
        return flagfetch;
    }

    public String getname() {
        return namefetch;
    }

    public String getdob() {
        return dobfetch;
    }

    public String getgender() {
        return genderfetch;
    }

    public String getemail() {
        return emailfetch;
    }

    public String getpassword() {
        return passwordfetch;
    }

}
