package com.janelaaj.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.activitys.CalanderActivity;
import com.janelaaj.activitys.DashboardActivity;
import com.janelaaj.activitys.DayFeedsActivity;
import com.janelaaj.activitys.ManageLocationActivity;
import com.janelaaj.activitys.OneViewActivity;
import com.janelaaj.activitys.QuickSetsActivity;
import com.janelaaj.activitys.TodaysappointmentsActivity;
import com.janelaaj.activitys.vitalDataEntry;
import com.janelaaj.adapter.HorizontalRecyclerViewAdapter;
import com.janelaaj.model.ImageModel;
import com.janelaaj.utilities.FontManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String loc,doctor_name2;
    String current_location_id;
    String doctor_name,doctor_dob,doctor_gender,doctor_speciality,doctor_introduction,dlmid;
    TextView changename;
    String Role;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            DashboardActivity activity = (DashboardActivity) getActivity();
            loc= activity.getLocationName();
            current_location_id = activity.getCurrentlocid();
            doctor_name = activity.getdoctor_name();
            doctor_name2 = activity.getdoctor_name2();
            doctor_dob = activity.getdoctor_dob();
            doctor_gender = activity.getdoctor_gender();
            doctor_speciality = activity.getdoctor_speciality();
            doctor_introduction = activity.getdoctor_introduction();
            dlmid = activity.getdoctor_dlmid();
            Role = activity.getRole();

        }
    }

    public RecyclerView mHorizontalRecyclerView;
    public HorizontalRecyclerViewAdapter horizontalAdapter;
    public LinearLayoutManager horizontalLayoutManager;
    Button buttonarge;
    LinearLayout linearlayout_invisible;
    CardView card_buttonabove;
    Button lessbutton;
    ScrollView scrollv;
    TextView time_text_view,doctornameTextview;
    LinearLayout c1, c2, c3, quickLayout, calanderView, dailyFeeds, appointmentLayout, oneViewLayout,ManageLocation;

    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Role = getActivity().getIntent().getExtras().getString("Role");
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }

    private void init() {
        buttonarge = view.findViewById(R.id.button_enlarge);
        linearlayout_invisible = view.findViewById(R.id.linearlayout_invisible);
        card_buttonabove = view.findViewById(R.id.card_buttonabove);
        lessbutton = view.findViewById(R.id.lessbutton);
        c1 = view.findViewById(R.id.comingsoon1);
        c2 = view.findViewById(R.id.comingsoon2);
        c3 = view.findViewById(R.id.comingsoon3);
        scrollv = view.findViewById(R.id.scrollv);
        this.doctornameTextview = view.findViewById(R.id.doctornameTextview);
        this.quickLayout = view.findViewById(R.id.quickLayout);
        this.calanderView = view.findViewById(R.id.calanderView);
        this.dailyFeeds = view.findViewById(R.id.dailyFeeds);
        this.ManageLocation = view.findViewById(R.id.manageloc);
        changename = view.findViewById(R.id.calenderordataentry);
        if(Role.equals("VIT"))
        {
            changename.setText("Data Entry");
        }
//        TextView checkarrow = view.findViewById(R.id.checkarrow);
//        TextView exparrow = view.findViewById(R.id.exparrow);
        Typeface materialdesignicons = FontManager.getFontTypefaceMaterialDesignIcons(context, "fonts/materialdesignicons-webfont.otf");
//        checkarrow.setTypeface(materialdesignicons);
//        checkarrow.setText(Html.fromHtml("&#xf054;"));
//        exparrow.setTypeface(materialdesignicons);
//        exparrow.setText(Html.fromHtml("&#xf054;"));


        this.appointmentLayout = view.findViewById(R.id.appointmentLayout);

        this.oneViewLayout = view.findViewById(R.id.oneViewLayout);

        this.time_text_view = view.findViewById(R.id.time_text_view);
        this.appointmentLayout.setOnClickListener(this);
        this.oneViewLayout.setOnClickListener(this);
        quickLayout.setOnClickListener(this);
        this.ManageLocation.setOnClickListener(this);
        this.calanderView.setOnClickListener(this);
        this.dailyFeeds.setOnClickListener(this);


        Toast.makeText(context, doctor_name, Toast.LENGTH_SHORT).show();
        if(Role.equals("VIT"))
        {
            doctornameTextview.setText("Welcome "+doctor_name2);

        }else {
            doctornameTextview.setText(doctor_name);
        }

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
        String date = df.format(Calendar.getInstance().getTime());
        time_text_view.setText(date);




        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "Coming Soon!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        mHorizontalRecyclerView = (RecyclerView) view.findViewById(R.id.horizontalRecyclerView);
        mHorizontalRecyclerView.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.HORIZONTAL));
        mHorizontalRecyclerView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.horizontalmargin));


        buttonarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollv.scrollTo(0, scrollv.getMaxScrollAmount());
                buttonarge.setVisibility(View.GONE);
               // card_buttonabove.setVisibility(View.VISIBLE);
                linearlayout_invisible.setVisibility(View.VISIBLE);
                lessbutton.setVisibility(View.VISIBLE);

                final ScrollView scrollview = ((ScrollView) view.findViewById(R.id.scrollv));
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
               // card_buttonabove.setVisibility(View.GONE);
                linearlayout_invisible.setVisibility(View.GONE);
                lessbutton.setVisibility(View.GONE);
            }
        });

        horizontalAdapter = new HorizontalRecyclerViewAdapter(fillWithData(), context);
        horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);
    }

    public ArrayList<ImageModel> fillWithData() {
        ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
        ImageModel imageModel0 = new ImageModel();
        imageModel0.setId(System.currentTimeMillis());
        imageModel0.setImageName("img1");
        imageModel0.setImagePath(R.drawable.oneheader);
        imageModelArrayList.add(imageModel0);

        ImageModel imageModel1 = new ImageModel();
        imageModel1.setId(System.currentTimeMillis());
        imageModel1.setImageName("img2");
        imageModel1.setImagePath(R.drawable.twoheader);
        imageModelArrayList.add(imageModel1);

        ImageModel imageModel2 = new ImageModel();
        imageModel2.setId(System.currentTimeMillis());
        imageModel2.setImageName("img1");
        imageModel2.setImagePath(R.drawable.thirdheader);
        imageModelArrayList.add(imageModel2);

        ImageModel imageModel3 = new ImageModel();
        imageModel3.setId(System.currentTimeMillis());
        imageModel3.setImageName("img2");
        imageModel3.setImagePath(R.drawable.fourthheader);
        imageModelArrayList.add(imageModel3);

        ImageModel imageModel4 = new ImageModel();
        imageModel4.setId(System.currentTimeMillis());
        imageModel4.setImageName("img1");
        imageModel4.setImagePath(R.drawable.oneheader);
        imageModelArrayList.add(imageModel4);

        ImageModel imageModel5 = new ImageModel();
        imageModel5.setId(System.currentTimeMillis());
        imageModel5.setImageName("img2");
        imageModel5.setImagePath(R.drawable.twoheader);
        imageModelArrayList.add(imageModel5);
        return imageModelArrayList;
    }


    public void onClick(View v) {
        if (v.getId() == R.id.quickLayout) {
            // quickLayout.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(context, QuickSetsActivity.class);
            intent.putExtra("current_location_id",current_location_id);
            intent.putExtra("location_name",loc);
            intent.putExtra("current_location_dlmid",dlmid);
            intent.putExtra("Role",Role);
            startActivity(intent);
        } else if (v.getId() == R.id.calanderView) {
            //    calanderView.setBackgroundResource(R.drawable.login_border);

            if(!Role.equals("VIT")) {
                Intent intent = new Intent(context, CalanderActivity.class);
                intent.putExtra("from_home","1");
                intent.putExtra("locid",current_location_id);
                intent.putExtra("Role",Role);
                intent.putExtra("dlmid",dlmid);
                intent.putExtra("Currentloc",loc);
                startActivity(intent);
            } else
            {
                Intent intent = new Intent(context,vitalDataEntry.class);
                intent.putExtra("from_home", "1");
                intent.putExtra("Role",Role);
                intent.putExtra("current_location_id",current_location_id);
                intent.putExtra("location_name",loc);
                intent.putExtra("current_location_dlmid",dlmid);
                startActivity(intent);
            }
        } else if (v.getId() == R.id.dailyFeeds) {
            // dailyFeeds.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(context, DayFeedsActivity.class);
            intent.putExtra("from_home","1");
            intent.putExtra("locid",current_location_id);
            intent.putExtra("Role",Role);
            startActivity(intent);
        } else if (v.getId() == R.id.appointmentLayout) {
            //   appointmentLayout.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(context, TodaysappointmentsActivity.class);
            intent.putExtra("from_home","1");
            intent.putExtra("locid",current_location_id);
            intent.putExtra("Role",Role);
            startActivity(intent);
        } else if (v.getId() == R.id.oneViewLayout) {
            //   oneViewLayout.setBackgroundResource(R.drawable.login_border);
            Intent intent = new Intent(context, OneViewActivity.class);
            intent.putExtra("from_home","1");
            intent.putExtra("locid",current_location_id);
            intent.putExtra("Role",Role);
            intent.putExtra("dlmid",dlmid);
            intent.putExtra("Currentloc",loc);
            startActivity(intent);
        }
        else if (v.getId() == R.id.manageloc) {
            Intent intent = new Intent(context, ManageLocationActivity.class);
            intent.putExtra("from_home","1");
            intent.putExtra("locid",current_location_id);
            intent.putExtra("Role",Role);
            startActivity(intent);
        }



    }
}
