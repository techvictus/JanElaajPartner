package com.janelaaj.fragment;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.activitys.CalanderActivity;
import com.janelaaj.activitys.DashboardActivity;
import com.janelaaj.activitys.DayFeedsActivity;
import com.janelaaj.activitys.ManageLocationActivity;
import com.janelaaj.activitys.OneViewActivity;
import com.janelaaj.activitys.ProfileRegistrationActivity;
import com.janelaaj.activitys.QuickSetsActivity;
import com.janelaaj.activitys.TodaysappointmentsActivity;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.QuickSets;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int OPEN_PDF = 2;
    public ImageView edit_image;
    public Button savebutton, savebutton2;
    public TextView information_text, name, age, speciality, experience;
    public TextInputLayout edit_information;
    private View view;
    Integer userage;
    String current_location_id, docid, dlmid;
    String doctor_name, doctor_dob, doctor_gender, doctor_speciality, doctor_introduction, doctor_experience;
    ImageView doctorImage;
    String imagefetch;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask = "";
    private static final int SELECT_PHOTO = 100;
    private Bitmap bm;
    private String EncodedBase64 = "";
    String image = "";
    File f;
    Integer dataSize;
    String Role, Currentloc;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        DashboardActivity activity = (DashboardActivity) getActivity();
        current_location_id = activity.getCurrentlocid();
        doctor_name = activity.getdoctor_name2();
        doctor_dob = activity.getdoctor_dob();
        doctor_gender = activity.getdoctor_gender();
        doctor_speciality = activity.getdoctor_speciality();
        doctor_introduction = activity.getdoctor_introduction();
        doctor_experience = activity.getExperience();
        imagefetch = activity.getImage();
        dlmid = activity.getdoctor_dlmid();
        Currentloc = activity.getLocationName();
        Role = activity.getRole();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        docid = sp.getString("doctorid", "");


//        SharedPreferences pref = getActivity().getSharedPreferences("doctorid", MODE_PRIVATE);
//        docid = pref.getString("doctorid", null);

        edit_image = view.findViewById(R.id.edit_image);
        savebutton = view.findViewById(R.id.save_button);
        savebutton2 = view.findViewById(R.id.save_button2);
        edit_information = view.findViewById(R.id.edit_information);
        information_text = view.findViewById(R.id.information_text);
        name = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        speciality = view.findViewById(R.id.speciality);
        experience = view.findViewById(R.id.experience);
        doctorImage = view.findViewById(R.id.doctorImage);


        name.setText(doctor_name);
        age.setText(doctor_dob + ' ' + ',' + ' ' + doctor_gender);

        doctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        if (doctor_speciality.equals("1")) {
            speciality.setText("Cardiology");
        } else if (doctor_speciality.equals("2")) {
            speciality.setText("E.N.T");
        } else if (doctor_speciality.equals("2")) {
            speciality.setText("Opthalmology");
        } else if (doctor_speciality.equals("3")) {
            speciality.setText("Dental");
        } else if (doctor_speciality.equals("4")) {
            speciality.setText("General Physician");
        }


        if (doctor_introduction != null) {
            information_text.setVisibility(View.VISIBLE);
            information_text.setText(doctor_introduction);

        }
        experience.setText("Doctor Experience :" + doctor_experience + " Years");

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_information.setVisibility(View.VISIBLE);
                savebutton.setVisibility(View.VISIBLE);
                edit_information.getEditText().setText(information_text.getText(), TextView.BufferType.EDITABLE);

            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to_send = edit_information.getEditText().getText().toString();
                DashboardActivity activity = (DashboardActivity) getActivity();
                edit_information.getEditText().setText(to_send, TextView.BufferType.EDITABLE);
                activity.setdoctor_introduction(to_send);
                userValidate(docid, to_send);

                Log.i("dsadadsadas", docid + " " + to_send);
                edit_information.setVisibility(View.GONE);
                information_text.setVisibility(View.VISIBLE);
                information_text.setText(to_send);

                Log.i("inform", to_send);


            }
        });


        Log.i("sadafsf", imagefetch + "image fetch val");

        if (!imagefetch.equals("N") && !imagefetch.equals("")) {
            Log.i("sadafsf", "in !imagefetch" + imagefetch);
            byte[] decodedString = Base64.decode(imagefetch, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            doctorImage.setImageBitmap(decodedByte);
        } else {
            Log.i("sadafsf", "in else" + imagefetch);
            doctorImage.setImageResource(R.drawable.photo);
            doctorImage.setEnabled(true);
        }

        savebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValidate2(docid, image);
            }
        });


        return view;
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Choose from PDF",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = ProfileRegistrationActivity.Utility2.CheckPermission(getActivity());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Choose from PDF")) {
                    userChoosenTask = "Choose from PDF";
                    if (result)
                        pdfIntent();
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
            case ProfileRegistrationActivity.Utility2.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                    else if (userChoosenTask.equals("Choose from PDF"))
                        pdfIntent();
                    else {
                        Toast.makeText(getActivity(), "Please Accept Permissions", Toast.LENGTH_SHORT).show();
                    }
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

    private void pdfIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
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
            else if (requestCode == OPEN_PDF) ;


        }
    }

    private void onCaptureImageResult(Intent data) {

        Log.i("sadafsf", "in onCaptureImageResult with Data " + data);
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        ConvertBitmapToBase64String(thumbnail);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            Log.i("sadafsf", "in TRY of ONCAPTURE IMAGE");
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        doctorImage.setImageBitmap(thumbnail);
        savebutton2.setVisibility(View.VISIBLE);
        savebutton.setVisibility(View.INVISIBLE);

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
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
                    InputStream fileInputStream = getActivity().getContentResolver().openInputStream(uri);
                    dataSize = fileInputStream.available();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("sadafsf", "in gallery scheme content" + String.valueOf(dataSize));
//                System.out.println("File size in bytes"+dataSize);

            } else if (scheme.equals(ContentResolver.SCHEME_FILE)) {
                String path = uri.getPath();
                try {
                    f = new File(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("sadafsf", "in gallery scheme file" + String.valueOf(f.length()));
//                System.out.println("File size in bytes"+f.length());
            }

//            if(dataSize>10000000 || f.length()>10000000)
            if (dataSize > 10000000) {
                doctorImage.setImageResource(R.drawable.photo);
                Toast.makeText(getActivity(), "Please Select A Smaller Image", Toast.LENGTH_LONG).show();
            } else {
                doctorImage.setImageBitmap(bm);
                savebutton2.setVisibility(View.VISIBLE);
                savebutton.setVisibility(View.INVISIBLE);

            }
        }

    }


    public String ConvertBitmapToBase64String(Bitmap bitmap) {
        Log.i("sadafsf", "in ConvertBitmapToBase64");
        EncodedBase64 = null;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bao);
        byte[] imgData = bao.toByteArray();
        EncodedBase64 = android.util.Base64.encodeToString(imgData, android.util.Base64.DEFAULT);
        image = EncodedBase64;
        Log.i("sadafsf", "EncodedBase64 of this image" + EncodedBase64);
        return EncodedBase64;
    }


    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }


    private void userValidate(final String Doctorid, String introduction) {

        if (Utility.isOnline(getActivity())) {
            JSONObject object = new JSONObject();
            try {

                object.put("docid", Doctorid);
                object.put("introduction", introduction);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(getActivity(), "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.updateInformation(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(getActivity(), "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", getActivity());
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getActivity());//off line msg....
        }
    }


    private void userValidate2(final String Doctorid, String image) {

        if (Utility.isOnline(getActivity())) {
            JSONObject object = new JSONObject();
            try {
                Log.i("sadafsf", image + "image api called");
                object.put("docid", Doctorid);
                object.put("image", image);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(getActivity(), "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(getActivity());
            serviceCaller.updateInformation2(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(getActivity(), "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getActivity(), "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", getActivity());
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, getActivity());//off line msg....
        }
    }


}