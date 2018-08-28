package com.janelaaj.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.adapter.HorizontalRecyclerViewAdapter;
import com.janelaaj.model.ImageModel;

import java.util.ArrayList;

/**
 *
 * @author Arshil Khan.
 */
public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener {

    public RecyclerView mHorizontalRecyclerView;
    public HorizontalRecyclerViewAdapter horizontalAdapter;
    public LinearLayoutManager horizontalLayoutManager;
    Button buttonarge;
    LinearLayout linearlayout_invisible;
    CardView card_buttonabove;
    Button lessbutton;
    ScrollView scrollv;
    LinearLayout c1, c2, c3, quickLayout, calanderView, dailyFeeds, appointmentLayout,oneViewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_screen);

        buttonarge = this.findViewById(R.id.button_enlarge);
        linearlayout_invisible = this.findViewById(R.id.linearlayout_invisible);
        card_buttonabove = this.findViewById(R.id.card_buttonabove);
        lessbutton = this.findViewById(R.id.lessbutton);
        c1 = this.findViewById(R.id.comingsoon1);
        c2 = this.findViewById(R.id.comingsoon2);
        c3 = this.findViewById(R.id.comingsoon3);
        scrollv = this.findViewById(R.id.scrollv);
        this.quickLayout = this.findViewById(R.id.quickLayout);
        this.calanderView = this.findViewById(R.id.calanderView);
        this.dailyFeeds = this.findViewById(R.id.dailyFeeds);

        this.appointmentLayout = this.findViewById(R.id.appointmentLayout);

        this.oneViewLayout=this.findViewById(R.id.oneViewLayout);

        this.appointmentLayout.setOnClickListener(this);
        this.oneViewLayout.setOnClickListener(this);
        quickLayout.setOnClickListener(this);
        this.calanderView.setOnClickListener(this);
        this.dailyFeeds.setOnClickListener(this);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Coming Soon!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        mHorizontalRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.HORIZONTAL));
        mHorizontalRecyclerView.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.horizontalmargin));


        buttonarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollv.scrollTo(0, scrollv.getMaxScrollAmount());
                buttonarge.setVisibility(View.GONE);
                card_buttonabove.setVisibility(View.VISIBLE);
                linearlayout_invisible.setVisibility(View.VISIBLE);
                lessbutton.setVisibility(View.VISIBLE);

                final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollv));
                scrollview.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });


            }
        });

        lessbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonarge.setVisibility(View.VISIBLE);
                card_buttonabove.setVisibility(View.GONE);
                linearlayout_invisible.setVisibility(View.GONE);
                lessbutton.setVisibility(View.GONE);
            }
        });

        horizontalAdapter = new HorizontalRecyclerViewAdapter(fillWithData(), getApplication());

        horizontalLayoutManager = new LinearLayoutManager(HomeScreenActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);
    }

    public ArrayList<ImageModel> fillWithData() {
        ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
        ImageModel imageModel0 = new ImageModel();
        imageModel0.setId(System.currentTimeMillis());
        imageModel0.setImageName("img1");
        imageModel0.setImagePath(R.drawable.sliderimage);
        imageModelArrayList.add(imageModel0);

        ImageModel imageModel1 = new ImageModel();
        imageModel1.setId(System.currentTimeMillis());
        imageModel1.setImageName("img2");
        imageModel1.setImagePath(R.drawable.sliderimage);
        imageModelArrayList.add(imageModel1);

        ImageModel imageModel2 = new ImageModel();
        imageModel2.setId(System.currentTimeMillis());
        imageModel2.setImageName("img1");
        imageModel2.setImagePath(R.drawable.pic1);
        imageModelArrayList.add(imageModel2);

        ImageModel imageModel3 = new ImageModel();
        imageModel3.setId(System.currentTimeMillis());
        imageModel3.setImageName("img2");
        imageModel3.setImagePath(R.drawable.sliderimage);
        imageModelArrayList.add(imageModel3);

        ImageModel imageModel4 = new ImageModel();
        imageModel4.setId(System.currentTimeMillis());
        imageModel4.setImageName("img1");
        imageModel4.setImagePath(R.drawable.pic1);
        imageModelArrayList.add(imageModel4);

        ImageModel imageModel5 = new ImageModel();
        imageModel5.setId(System.currentTimeMillis());
        imageModel5.setImageName("img2");
        imageModel5.setImagePath(R.drawable.sliderimage);
        imageModelArrayList.add(imageModel5);
        return imageModelArrayList;
    }


    public void onClick(View v) {
        if (v.getId() == R.id.quickLayout) {
           // quickLayout.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(HomeScreenActivity.this, QuickSetsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.calanderView) {
        //    calanderView.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(HomeScreenActivity.this, CalanderActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.dailyFeeds) {
           // dailyFeeds.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(HomeScreenActivity.this, DayFeedsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.appointmentLayout) {
         //   appointmentLayout.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(HomeScreenActivity.this, TodaysappointmentsActivity.class);
            startActivity(intent);
        }

        else if (v.getId() == R.id.oneViewLayout) {
         //   oneViewLayout.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(HomeScreenActivity.this, OneViewActivity.class);
            startActivity(intent);
        }



    }
}
