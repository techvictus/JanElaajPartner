package com.janelaaj.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;


/**
 *
 * @author Arshil Khan.
 */

public class SelectTYpeActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private TextView headertitel, headersubtitle, codeTextView, doctor, vitals, different, pathlab, iam;
    private CircleImageView logoImage;
    View otpsendLayout;
    Spinner typeSpinneer;
    String email;
    String PersonName;
    boolean Navigation = false;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_selecttype);
        iniView();


        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");

        headertitel.setTypeface(tf4);
        headersubtitle.setTypeface(tf3);
        iam.setTypeface(tf3);
        doctor.setTypeface(tf4);
        different.setTypeface(tf4);
        pathlab.setTypeface(tf4);


        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            email = intent.getString("Email");
            PersonName = intent.getString("PersonName");
            Navigation = intent.getBoolean("Navigation");
        }

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent;
                if (Navigation) {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "DOC");
                    intent.putExtra("Email", email);
                    intent.putExtra("PersonName", PersonName);
                } else {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "DOC");
                }
                startActivity(intent);
            }
        });

        vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent;
                if (Navigation) {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "VIT");
                    intent.putExtra("Email", email);
                    intent.putExtra("PersonName", PersonName);
                } else {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "VIT");
                }
                startActivity(intent);
            }
        });

        different.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent;
                if (Navigation) {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "DOC");
                    intent.putExtra("Email", email);
                    intent.putExtra("PersonName", PersonName);
                } else {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "DOC");
                }
                startActivity(intent);
            }
        });

        pathlab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                Intent intent;
                if (Navigation) {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "DOC");
                    intent.putExtra("Email", email);
                    intent.putExtra("PersonName", PersonName);
                } else {
                    intent = new Intent(SelectTYpeActivity.this, SendOTPActivity.class);
                    intent.putExtra("Role", "DOC");
                }
                startActivity(intent);
            }
        });


        buildGoogleApiClient();
    }

    public void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    private void Logout() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

    }

    public void iniView() {
        this.headertitel = this.findViewById(R.id.headertitel);
        this.headersubtitle = this.findViewById(R.id.headersubtitle);
        this.logoImage = this.findViewById(R.id.logoImage);
        this.doctor = this.findViewById(R.id.i_am_a_doctor);
        this.vitals = this.findViewById(R.id.were_a_vitals);
        this.different = this.findViewById(R.id.were_different);
        this.pathlab = this.findViewById(R.id.were_a_pathlab);
        this.iam = this.findViewById(R.id.i_am);


        //        this.typeSpinneer = this.findViewById(R.id.typeSpinneer);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.otpsendLayout) {

        }


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

