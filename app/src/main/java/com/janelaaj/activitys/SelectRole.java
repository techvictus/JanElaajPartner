package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.Sign_in_Model2;
import com.janelaaj.model.sign_in;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 *
 * @author Arshil Khan.
 */
public class SelectRole extends AppCompatActivity {
    public String email,password;
    public TextView role1,role2;
    public ArrayList<Sign_in_Model2.info> RoleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        setContentView(R.layout.activity_select_role);
        role1 = findViewById(R.id.role1); //doctor
        role2 = findViewById(R.id.role2); //vitals
        role1.setText("DOCTOR");
        role2.setText("VITALS");

        RoleData = new ArrayList<>();
        userValidate(email,password);

        role1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorid="";
                Log.i("sadafsf",String.valueOf(RoleData.size()));

                for (int i=0;i<RoleData.size();i++)
                {

                    Log.i("sadafsf",RoleData.get(i).getPld_role());
                    if(RoleData.get(i).getPld_role().equals("DOC"))
                    {

                        doctorid = RoleData.get(i).getPld_partner_id();
                    }
                    Log.i("sadafsf",doctorid);

                }

                checkPoint(doctorid,"DOC");
            }
        });

        role2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorid="";

                for (int i=0;i<RoleData.size();i++)
                {
                    Log.i("sadafsf",RoleData.get(i).getPld_role());
                    if(RoleData.get(i).getPld_role().equals("VIT"))
                    {
                        doctorid = RoleData.get(i).getPld_partner_id();
                        Log.i("sadafsf",doctorid);
                    }
                }

                checkPoint(doctorid,"VIT");
            }
        });

    }


    private void userValidate(final String email,final String password) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("email", email);
                object.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = Contants.SERVICE_BASE_URL + Contants.login;
            final ProgressDialog progressbar = ProgressDialog.show(SelectRole.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.userLogin(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        Sign_in_Model2 mainContent = new Gson().fromJson(result, Sign_in_Model2.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {
                                RoleData = mainContent.getRole();
                                Log.i("sadafsf","success");

                            } else {
                                Toast.makeText(SelectRole.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SelectRole.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", SelectRole.this);
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

    private void checkPoint(final String docid,final String Role) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", docid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String url = Contants.SERVICE_BASE_URL + Contants.checkpoint2;
            final ProgressDialog progressbar = ProgressDialog.show(SelectRole.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.checkPoint(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        sign_in mainContent = new Gson().fromJson(result, sign_in.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {


                                Integer x= mainContent.getCheckpoint();
                                Log.i("checkpoint",String.valueOf(x));

                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SelectRole.this);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("doctorid",docid);
                                editor.commit();

                                Log.i("sadafsf",docid);

//
//                                SharedPreferences pref = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
//                                SharedPreferences.Editor edit = pref.edit();
//

//                                edit.putString("loginflag", "1");
//                                edit.putString("togo",String.valueOf(mainContent.getCheckpoint()));
//                                edit.commit();

                                Log.i("sadafsf",String.valueOf(mainContent.getCheckpoint()));

                                if(mainContent.getCheckpoint()==1)
                                {

                                    Intent intent = new Intent(SelectRole.this,SelectOptionScreen.class);
                                    intent.putExtra("Role",Role);
                                    startActivity(intent);

                                }
                                else if(mainContent.getCheckpoint()==2)
                                {
                                    Intent intent  = new Intent(SelectRole.this,ManageLocationActivity.class);
                                    intent.putExtra("from_home","0");
                                    intent.putExtra("Role",Role);
                                    startActivity(intent);
                                } else
                                {
                                    Intent intent = new Intent(SelectRole.this,Choose_Location.class);
                                    intent.putExtra("Role",Role);
                                    startActivity(intent);

                                }
////                                Toast.makeText(LoginActivity.this, String.valueOf(mainContent.getCheckpoint()), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SelectRole.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(SelectRole.this, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", SelectRole.this);
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
