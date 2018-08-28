package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ChangePasswordModel;
import com.janelaaj.model.UploadDocumentFetchModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Arshil Khan.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    Button SignIn;
    String Docid;
    EditText pass,pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_change_password);

        pass = findViewById(R.id.pass);
        pass2 = findViewById(R.id.pass2);

        Docid = getIntent().getStringExtra("Docid");
        Log.i("sadafsf",Docid);

        SignIn = findViewById(R.id.loginButton);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password =  pass.getText().toString().trim();
                String passwordconfirm = pass2.getText().toString().trim();
                String password2 = md5(password);

                if(pass.getText().toString().trim().equals(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(pass2.getText().toString().trim().equals(""))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(passwordconfirm))
                {
                    Toast.makeText(ChangePasswordActivity.this, "Both Passwords Should Match", Toast.LENGTH_SHORT).show();
                }
                else {
                    changePassword(Docid, password2);
                }
            }
        });
    }

    private void changePassword(final String doctorid,final String pass) {

        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("docid", doctorid);
                object.put("pass", pass);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(ChangePasswordActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.changePassword(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ChangePasswordModel mainContent = new Gson().fromJson(result, ChangePasswordModel.class);
                        if (mainContent != null) {
                            Log.i("error",mainContent.getStatus().toString());
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(ChangePasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this,LoginActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ChangePasswordActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

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


}
