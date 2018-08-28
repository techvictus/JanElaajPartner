package com.janelaaj.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;
import com.janelaaj.utilities.Utility;

import static com.janelaaj.utilities.Utility.getTextFromView;
import static com.janelaaj.utilities.Utility.isEmptyStr;
import static com.janelaaj.utilities.Utility.isNonEmptyStr;

/**
 *
 * @author Arshil Khan.
 */

public class ForgotPasswordOTPActivity extends AppCompatActivity {


    Button sendOTP;
    String Docid;
    String OTP;
    private TextView headertitel, headersubtitle, otpsendTextView;
    private Button submitOTP, resendNo, chnageNo;
    private EditText passcodeCharOne, passcodeCharTwo, passcodeCharThree, passcodeCharFour;
    private String one, two, three, four;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_forgot_password_otp);
        iniView();

        Docid = getIntent().getStringExtra("Doctorid");
        OTP = getIntent().getStringExtra("OTP");

        Log.i("Sadafsf","OTP"+OTP);





        sendOTP = findViewById(R.id.submitOTP);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {
                    VerifyOtp(); }
            }
        });




    }



    public void iniView() {

        this.headertitel = this.findViewById(R.id.headertitel);
        this.headersubtitle = this.findViewById(R.id.headersubtitle);
        this.otpsendTextView = this.findViewById(R.id.otpsendTextView);
        this.passcodeCharOne = this.findViewById(R.id.passcodeCharOne);
        this.passcodeCharTwo = this.findViewById(R.id.passcodeCharTwo);
        this.passcodeCharThree = this.findViewById(R.id.passcodeCharThree);
        this.passcodeCharFour = this.findViewById(R.id.passcodeCharFour);

        this.submitOTP = this.findViewById(R.id.submitOTP);
        this.resendNo = this.findViewById(R.id.resendNo);
        this.chnageNo = this.findViewById(R.id.chnageNo);



        passcodeCharOne.addTextChangedListener(passcodeTextWatcher);
        passcodeCharTwo.addTextChangedListener(passcodeTextWatcher);
        passcodeCharThree.addTextChangedListener(passcodeTextWatcher);
        passcodeCharFour.addTextChangedListener(passcodeTextWatcher);

        passcodeCharOne.setOnKeyListener(onKeyListener);
        passcodeCharTwo.setOnKeyListener(onKeyListener);
        passcodeCharThree.setOnKeyListener(onKeyListener);
        passcodeCharFour.setOnKeyListener(onKeyListener);



    }





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
        Toast.makeText(ForgotPasswordOTPActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private String getPassword() {
        return getTextFromView(passcodeCharOne) + getTextFromView(passcodeCharTwo) +
                getTextFromView(passcodeCharThree) + getTextFromView(passcodeCharFour);
    }

    private void VerifyOtp() {


        Log.i("sadafsf","OTP"+OTP);
        String enterOtp=(new StringBuilder()).append(one).append(two).append(three).append(four).toString();
        Log.i("sadafsf","enterOtp"+enterOtp);
        if (OTP.equals(enterOtp)) {
            Toast.makeText(ForgotPasswordOTPActivity.this, "Phone Number verified successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ForgotPasswordOTPActivity.this, ChangePasswordActivity.class);
            intent.putExtra("Docid", Docid);
            startActivity(intent);
        } else {
            Toast.makeText(ForgotPasswordOTPActivity.this, "Phone Number not verified successfully", Toast.LENGTH_LONG).show();
        }
    }

}
