package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.adapter.RegistrationPagerAdapter;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.MainContent;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;


import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Arshil Khan.
 */

public class SendOTPActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView headertitel, headersubtitle, codeTextView, otpsendthisno;
    private Button otpSendButton;
    private CircleImageView logoImage;
    private EditText mobileNoedittextView;
    View otpsendLayout;
    private String Role;
    String email;
    String PersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sendotp);
        iniView();

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(), "fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(), "fonts/seguisb.ttf");


        headertitel.setTypeface(tf4);
        headersubtitle.setTypeface(tf3);
        codeTextView.setTypeface(tf4);
        mobileNoedittextView.setTypeface(tf3);
        otpSendButton.setTypeface(tf4);
        otpsendthisno.setTypeface(tf4);
        otpSendButton.setTypeface(tf4);


    }


    public void iniView() {
        Role = getIntent().getStringExtra("Role");
        email = getIntent().getStringExtra("Email");
        PersonName = getIntent().getStringExtra("PersonName");

        this.headertitel = this.findViewById(R.id.headertitel);
        this.headersubtitle = this.findViewById(R.id.headersubtitle);
        this.logoImage = this.findViewById(R.id.logoImage);
        this.codeTextView = this.findViewById(R.id.codeTextView);
        this.mobileNoedittextView = this.findViewById(R.id.mobileNoedittextView);
        this.otpSendButton = this.findViewById(R.id.otpSendButton);
        this.otpsendLayout = this.findViewById(R.id.otpsendLayout);
        this.otpSendButton.setOnClickListener(this);
        this.otpsendthisno = this.findViewById(R.id.otp_send_this_no);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.otpSendButton) {

            String phoneNo = mobileNoedittextView.getText().toString();
            if (phoneNo.length() == 0) {
                Toast.makeText(SendOTPActivity.this, "Please Enter Phone Number", Toast.LENGTH_LONG).show();
            } else if (phoneNo.length() != 10) {
                Toast.makeText(SendOTPActivity.this, "Please Enter Vaild Phone Number", Toast.LENGTH_LONG).show();
            } else {
                userValidate(phoneNo);
            }
        }
    }

    private void sendOtp(final String pNumber) {

        int randomPIN = (int) (Math.random() * 9000) + 1000;
        Log.i("sadafsf",String.valueOf(randomPIN));
        final String PINString = String.valueOf(randomPIN);
        if (Utility.isOnline(this)) {
            String url = Contants.Send_Otp + pNumber + "&" + "message=" + PINString + "%20is%20your%20Jan%20Elaaj%20Registration%20OTP";
            final ProgressDialog progressbar = ProgressDialog.show(SendOTPActivity.this, "", "Please wait..", true);
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
                    Toast.makeText(SendOTPActivity.this, "OTP Send successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SendOTPActivity.this, VarifyOTPActivity.class);
                    intent.putExtra("PhoneNo", pNumber);
                    intent.putExtra("OTP", PINString);
                    intent.putExtra("Role", Role);
                    intent.putExtra("Email", email);
                    intent.putExtra("PersonName", PersonName);
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

    private void userValidate(final String pNumber) {
        if (Utility.isOnline(this)) {
            JSONObject object = new JSONObject();
            try {
                object.put("mobile", pNumber);
                object.put("pldrole", Role);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String url = Contants.SERVICE_BASE_URL + Contants.numberVerify;
        final ProgressDialog progressbar = ProgressDialog.show(SendOTPActivity.this, "", "Please wait..", true);
        progressbar.show();
        ServiceCaller serviceCaller = new ServiceCaller(this);
        serviceCaller.userVerifyservice(url, object, new IAsyncWorkCompletedCallback() {
            @Override
            public void onDone(String result, boolean isComplete) {
                if (isComplete) {
                    MainContent mainContent = new Gson().fromJson(result, MainContent.class);
                    if (mainContent != null) {
                        if (mainContent.getStatus().equals("SUCCESS")) {
                            sendOtp(pNumber);
                        } else {
                            Toast.makeText(SendOTPActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SendOTPActivity.this, "User Already Registered!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Utility.alertForErrorMessage("User Already Registered", SendOTPActivity.this);
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
