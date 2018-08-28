package com.janelaaj.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;

/**
 *
 * @author Arshil Khan.
 */

public class FBLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView headertitel, headersubtitle, connectTextView1,connectTextView2,connectTextView3;
    private Button fbAccount, complFbRegistration;
    private CircleImageView logoImage;
    View loginfbLayout, complFbRegiLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fblogin);
        iniView();


        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");
        headertitel.setTypeface(tf4);
        headersubtitle.setTypeface(tf3);
        connectTextView1.setTypeface(tf4);
        fbAccount.setTypeface(tf4);
        complFbRegistration.setTypeface(tf4);
        connectTextView2.setTypeface(tf4);
        connectTextView3.setTypeface(tf4);




    }


    public void iniView() {
        this.headertitel = this.findViewById(R.id.headertitel);
        this.headersubtitle = this.findViewById(R.id.headersubtitle);
        this.logoImage = this.findViewById(R.id.logoImage);
        this.loginfbLayout = this.findViewById(R.id.loginfbLayout);
        this.connectTextView1 = this.findViewById(R.id.connectTextView1);
        this.connectTextView2 = this.findViewById(R.id.connectTextView2);
        this.connectTextView3 = this.findViewById(R.id.connectTextView3);
        this.fbAccount = this.findViewById(R.id.fbAccount);
        this.complFbRegistration = this.findViewById(R.id.complFbRegistration);
        this.complFbRegiLayout = this.findViewById(R.id.complFbRegiLayout);


        // this.fbAccount.setOnClickListener(this);
        this.complFbRegistration.setOnClickListener(this);
        //this.complFbRegiLayout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fbAccount) {

        } else if (v.getId() == R.id.complFbRegistration) {
            Intent intent = new Intent(FBLoginActivity.this, SelectTYpeActivity.class);
            //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }
}
