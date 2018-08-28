package com.janelaaj.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.vitalsignupinfoModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import static com.janelaaj.utilities.Utility.getTextFromView;
import static com.janelaaj.utilities.Utility.isEmptyStr;
import static com.janelaaj.utilities.Utility.isNonEmptyStr;


/**
 *
 * @author Arshil Khan.
 */

public class VarifyOTPActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView headertitel, headersubtitle, otpsendTextView;
    private Button submitOTP, resendNo, chnageNo;
    private EditText passcodeCharOne, passcodeCharTwo, passcodeCharThree, passcodeCharFour;
    private String one, two, three, four;
    String namefetch,dobfetch,genderfetch,emailfetch,passwordfetch,flagfetch;
    String image;


    View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (Utility.isEmptyView(v)) {
                    View prevFocusView = findViewById(getCurrentFocus().getNextFocusLeftId());
                    if (prevFocusView != null) {
                        prevFocusView.requestFocus();
                    }
                }
            }
            return false;
        }
    };
    TextWatcher passcodeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            submitOTP.setEnabled(isNonEmptyStr(getPassword()));
            if (getCurrentFocus() == null) {
                return;
            }
            View nextFocusView = findViewById(getCurrentFocus().getNextFocusRightId());
            View prevFocusView = findViewById(getCurrentFocus().getNextFocusLeftId());
            if (isNonEmptyStr(s.toString()) && nextFocusView != null) {
                nextFocusView.requestFocus();
            } else if (isEmptyStr(s.toString()) && prevFocusView != null) {
                prevFocusView.requestFocus();
            }
        }
    };
    private CircleImageView logoImage;

    String phoneNo = "";
    private String OTP;
    String Role;
    String email;
    String PersonName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_varifyotp);
        iniView();
        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");


        headertitel.setTypeface(tf4);
        headersubtitle.setTypeface(tf3);
        otpsendTextView.setTypeface(tf4);
        submitOTP.setTypeface(tf4);
        resendNo.setTypeface(tf4);
        chnageNo.setTypeface(tf4);


    }


    public void iniView() {
        phoneNo = getIntent().getStringExtra("PhoneNo");
        OTP = getIntent().getStringExtra("OTP");
        Role = getIntent().getStringExtra("Role");
        //for gamil login
        email = getIntent().getStringExtra("Email");
        PersonName = getIntent().getStringExtra("PersonName");

        this.headertitel = this.findViewById(R.id.headertitel);
        this.headersubtitle = this.findViewById(R.id.headersubtitle);
        this.logoImage = this.findViewById(R.id.logoImage);
        this.otpsendTextView = this.findViewById(R.id.otpsendTextView);
        this.passcodeCharOne = this.findViewById(R.id.passcodeCharOne);
        this.passcodeCharTwo = this.findViewById(R.id.passcodeCharTwo);
        this.passcodeCharThree = this.findViewById(R.id.passcodeCharThree);
        this.passcodeCharFour = this.findViewById(R.id.passcodeCharFour);

        this.submitOTP = this.findViewById(R.id.submitOTP);
        this.resendNo = this.findViewById(R.id.resendNo);
        this.chnageNo = this.findViewById(R.id.chnageNo);


        this.submitOTP.setOnClickListener(this);
        this.resendNo.setOnClickListener(this);
        this.chnageNo.setOnClickListener(this);

        passcodeCharOne.addTextChangedListener(passcodeTextWatcher);
        passcodeCharTwo.addTextChangedListener(passcodeTextWatcher);
        passcodeCharThree.addTextChangedListener(passcodeTextWatcher);
        passcodeCharFour.addTextChangedListener(passcodeTextWatcher);

        passcodeCharOne.setOnKeyListener(onKeyListener);
        passcodeCharTwo.setOnKeyListener(onKeyListener);
        passcodeCharThree.setOnKeyListener(onKeyListener);
        passcodeCharFour.setOnKeyListener(onKeyListener);
        if (phoneNo != null) {
            otpsendTextView.setText("OTP sent to +91" + phoneNo);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitOTP) {
            if (isValidate()) {
                VerifyOtp();
            }
        } else if (v.getId() == R.id.resendNo) {
            sendOtp();
        } else if (v.getId() == R.id.chnageNo) {
            finish();
        }

    }

    // ----validation -----
    private boolean isValidate() {

        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        one = passcodeCharOne.getText().toString();
        two = passcodeCharTwo.getText().toString();
        three = passcodeCharThree.getText().toString();
        four = passcodeCharFour.getText().toString();

        if (one.length() == 0) {
            showToast("Please Enter OTP");
            return false;
        } else if (two.length() == 0) {
            showToast("Please Enter OTP");
            return false;
        } else if (three.length() == 0) {
            showToast("Please Enter OTP");
            return false;
        } else if (four.length() == 0) {
            showToast("Please Enter OTP");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(VarifyOTPActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private String getPassword() {
        return getTextFromView(passcodeCharOne) + getTextFromView(passcodeCharTwo) +
                getTextFromView(passcodeCharThree) + getTextFromView(passcodeCharFour);
    }

    private void sendOtp() {
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        //String PINString = String.valueOf(randomPIN);
        if (Utility.isOnline(this)) {
            String url = Contants.Send_Otp + phoneNo + "&" + "message=" + randomPIN + "%20is%20your%20Jan%20Elaaj%20Registration%20OTP";
            final ProgressDialog progressbar = ProgressDialog.show(VarifyOTPActivity.this, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(this);
            serviceCaller.sendOtpService(url, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                   /* if (isComplete) {
                        Toast.makeText(VarifyOTPActivity.this, "OTP Send successfully", Toast.LENGTH_LONG).show();
                        // Intent intent = new Intent(VarifyOTPActivity.this, VarifyOTPActivity.class);
                        // intent.putExtra("PhoneNo",pNumber);
                        //  startActivity(intent);
                    } else {
                        Utility.alertForErrorMessage("OTP not Send successfully!", VarifyOTPActivity.this);
                    }*/
                    Toast.makeText(VarifyOTPActivity.this, "OTP Send successfully", Toast.LENGTH_LONG).show();
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, this);//off line msg....
        }
    }

    private void VerifyOtp() {
        //String enteroOtp = one + two + three + four;
        String enterOtp=(new StringBuilder()).append(one).append(two).append(three).append(four).toString();
        if (OTP.equals(enterOtp)) {

            vitalssignupinfo(phoneNo);
//
//            Toast.makeText(VarifyOTPActivity.this, "Phone Number verified successfully", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(VarifyOTPActivity.this, ProfileRegistrationActivity.class);
//            Log.i("role",Role);
//            intent.putExtra("PhoneNo", phoneNo);
//            intent.putExtra("Role", Role);
//            intent.putExtra("Email", email);
//            intent.putExtra("PersonName", PersonName);
//            startActivity(intent);
        } else {
            Toast.makeText(VarifyOTPActivity.this, "Phone Number not verified successfully", Toast.LENGTH_LONG).show();
        }
    }


    private void vitalssignupinfo(final String number) {
        if (Utility.isOnline(this)) {
            final ProgressDialog progressbar = ProgressDialog.show(VarifyOTPActivity.this, "", "Please wait..", true);
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
                        vitalsignupinfoModel mainContent = new Gson().fromJson(result, vitalsignupinfoModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

//

                                if(mainContent.getFlag().equals("Y"))
                                {
                                    flagfetch="Y";
                                    namefetch = mainContent.getName();
                                    dobfetch = mainContent.getDob();
                                    genderfetch = mainContent.getGender();
                                    emailfetch = mainContent.getEmail();
                                    passwordfetch  = mainContent.getPassword();
                                    image = mainContent.getImage();
                                }
                                else {
                                    flagfetch="N";
                                    namefetch = "N";
                                    dobfetch = "N";
                                    genderfetch = "N";
                                    emailfetch = "N";
                                    passwordfetch = "N";
                                    image = "N";


                                }

                                Toast.makeText(VarifyOTPActivity.this, "Phone Number verified successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(VarifyOTPActivity.this, ProfileRegistrationActivity.class);
                                Log.i("role",Role);
                                intent.putExtra("PhoneNo", phoneNo);
                                intent.putExtra("Role", Role);
                                intent.putExtra("Email", email);
                                intent.putExtra("PersonName", PersonName);
                                intent.putExtra("namefetch",namefetch);
                                intent.putExtra("dobfetch",dobfetch);
                                intent.putExtra("genderfetch",genderfetch);
                                intent.putExtra("emailfetch",emailfetch);
                                intent.putExtra("passwordfetch",passwordfetch);
                                intent.putExtra("flagfetch",flagfetch);
//                                Log.i("sadafsf",image+"fetched image string");
                                startActivity(intent);



                            } else {
                                Toast.makeText(VarifyOTPActivity.this, mainContent.getStatus(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(VarifyOTPActivity.this, "Registration not successfull!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Utility.alertForErrorMessage("Registration not successfully!", VarifyOTPActivity.this);
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
