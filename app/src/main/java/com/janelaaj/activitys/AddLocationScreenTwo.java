package com.janelaaj.activitys;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.janelaaj.R;
import com.janelaaj.component.CircleImageView;

/**
 * Created On 18-05-2018
 *
 * @author Arshil Khan.
 */

public class AddLocationScreenTwo extends AppCompatActivity{

    private TextView headertitel, headersubtitle, codeTextView,selectOptionHeader,i_own,i_provide1,i_provide2,state,pincode;
    private CircleImageView logoImage;
    private EditText clinic_name,add_line1,add_line2,village_name,dirst_name;
    View otpsendLayout;
    Spinner selectOptionSpinneer, stateSpinneer;
    private Button add_the_above_location,select_location_on_map;


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_location_screen_two);
        setupToolbar();



        iniView();

        add_the_above_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddLocationScreenTwo.this,ManageLocationActivity.class));

            }
        });

        select_location_on_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddLocationScreenTwo.this,AddLocationMapActivity.class));

            }
        });




        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_android:
                        Intent intent = new Intent(AddLocationScreenTwo.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;

            }
        });

        Typeface tf1 = Typeface.createFromAsset(getAssets(),"fonts/segoeuil.ttf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUIBold.ttf");
        Typeface tf3 = Typeface.createFromAsset(getAssets(),"fonts/SegoeUI.ttf");
        Typeface tf4 = Typeface.createFromAsset(getAssets(),"fonts/seguisb.ttf");

        selectOptionHeader.setTypeface(tf4);
        i_provide1.setTypeface(tf4);
        i_provide2.setTypeface(tf4);
        state.setTypeface(tf3);
        pincode.setTypeface(tf3);
        clinic_name.setTypeface(tf3);
        add_line1.setTypeface(tf3);
        add_line2.setTypeface(tf3);
        village_name.setTypeface(tf3);
        dirst_name.setTypeface(tf3);

    }


    public void iniView() {
        this.logoImage = this.findViewById(R.id.logoImage2);
        this.selectOptionHeader = this.findViewById(R.id.selectOptionheader2);
        this.clinic_name = this.findViewById(R.id.clihoName_name2);
        this.add_line1 = this.findViewById(R.id.addLine1_name2);
        this.add_line2 = this.findViewById(R.id.addLine2_name2);
        this.village_name = this.findViewById(R.id.village_name2);
        this.dirst_name = this.findViewById(R.id.dirst_name2);
        this.state = this.findViewById(R.id.state2);
        this.pincode = this.findViewById(R.id.pincode_name2);


    }

    private void setupToolbar() {
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
}
