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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ForgotPasswordModel;
import com.janelaaj.model.UploadDocumentFetchModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author Arshil Khan.
 */

public class ForgotPasswordActivity extends AppCompatActivity {


    public Button sendOTP;
    public EditText number;
    public String doctorid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgot_password);
        number = findViewById(R.id.mobileNoedittextView);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        doctorid = sp.getString("doctorid", "");

        sendOTP = findViewById(R.id.otpSendButton);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String no  = number.getText().toString().trim();
               forgotPassword(doctorid,no);

            }
        });
    }


    private void forgotPassword(final String doctorid,final String number) {

        if (com.janelaaj.utilities.Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("number",number);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(ForgotPasswordActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.ForgotPassword(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        ForgotPasswordModel mainContent = new Gson().fromJson(result, ForgotPasswordModel.class);
                        if (mainContent != null) {
                            Log.i("error",mainContent.getStatus().toString());
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                if(mainContent.getPresent().equals("N"))
                                {
                                    Toast.makeText(ForgotPasswordActivity.this, "Phone Number Does Not Exist in  the Databse", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    String docid= mainContent.getDocid();
                                    sendOtp(number,docid);
                                }

                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();

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


    public void sendOtp(final String pNumber,final String doctorid) {

        final int randomPIN = (int) (Math.random() * 9000) + 1000;
        Log.i("sadafsf",String.valueOf(randomPIN));
        final String PINString = String.valueOf(randomPIN);
        if (Utility.isOnline(this)) {
            String url = Contants.Send_Otp + pNumber + "&" + "message=" + PINString + "%20is%20your%20Jan%20Elaaj%20Registration%20OTP";
            final ProgressDialog progressbar = ProgressDialog.show(ForgotPasswordActivity.this, "", "Please wait..", true);
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
                    Toast.makeText(ForgotPasswordActivity.this, "OTP Send successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, ForgotPasswordOTPActivity.class);
                    intent.putExtra("Doctorid", doctorid);
                    intent.putExtra("OTP",PINString);
                    startActivity(intent);
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
