package com.janelaaj.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.activitys.DashboardActivity;
import com.janelaaj.activitys.ProfileRegistrationActivity;
import com.janelaaj.activitys.VarifyOTPActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFirstpageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFirstpageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    String email;
    String PersonName;
    String namefetch;
    String dobfetch;
    String genderfetch;
    String emailfetch;
    String passwordfetch;
    String flagfetch;
    private static final String NAMEFETCH="param3";
    private static final String DOBFETCH = "param4";
    private static final String GENDERFETCH = "param5";
    private static final String EMAILFETCH = "param6";
    private static final String PASSWORDFETCH = "param7";
    private static final String FLAGFETCH = "param8";


    public RegistrationFirstpageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFirstpageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFirstpageFragment newInstance(String param1, String param2,String namefetch,String dobfetch,String genderfetch,String emailfetch,String passwordfetch,String flagfetch) {
        RegistrationFirstpageFragment fragment = new RegistrationFirstpageFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(NAMEFETCH, namefetch);
        args.putString(DOBFETCH, dobfetch);
        args.putString(FLAGFETCH, flagfetch);
        args.putString(EMAILFETCH, emailfetch);
        args.putString(PASSWORDFETCH, passwordfetch);
        args.putString(GENDERFETCH, genderfetch);

        Log.i("sadafsf","newinstance  firstfragment"+ namefetch);
        Log.i("sadafsf","newinstance  firstfragment"+ dobfetch);
        Log.i("sadafsf","newinstance  firstfragment"+ emailfetch);
        Log.i("sadafsf","newinstance  firstfragment"+ passwordfetch);
        Log.i("sadafsf","newinstance  firstfragment"+ flagfetch);
        Log.i("sadafsf","newinstance  firstfragment"+ genderfetch);


        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_PARAM1);
            PersonName = getArguments().getString(ARG_PARAM2);
            namefetch = getArguments().getString(NAMEFETCH);
            dobfetch = getArguments().getString(DOBFETCH);
            flagfetch = getArguments().getString(FLAGFETCH);
            emailfetch = getArguments().getString(EMAILFETCH);
            passwordfetch = getArguments().getString(PASSWORDFETCH);
            genderfetch = getArguments().getString(GENDERFETCH);

            Log.i("sadafsf","oncreate  firstfragment"+ namefetch);
            Log.i("sadafsf","oncreate  firstfragment"+ dobfetch);
            Log.i("sadafsf","oncreate  firstfragment"+ emailfetch);
            Log.i("sadafsf","oncreate  firstfragment"+ passwordfetch);
            Log.i("sadafsf","oncreate  firstfragment"+ flagfetch);
            Log.i("sadafsf","oncreate  firstfragment"+ genderfetch);
        }
    }

    private View view;
    Context context;
    private LinearLayout nextLayout;
    private Button btnNext;
    ImageView nexticon;
    EditText nameVie, dobView, emailidView, passwordView;
    String nameVieStr, dobViewStr, emailidViewStr, passwordViewStr;
    String gender;
    String check;
    String date_formatted;
    RadioButton radio0;
    RadioButton radio1;
    RadioButton radio2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.registrationfirst_page, container, false);
        init();
        return view;
    }

    private void init() {
        radio0 = (RadioButton) view.findViewById(R.id.radio0);
        radio1 = (RadioButton) view.findViewById(R.id.radio1);
        radio2 = (RadioButton) view.findViewById(R.id.radio2);
        dobView = (EditText) view.findViewById(R.id.dobView);
        nameVie = (EditText) view.findViewById(R.id.nameVie);
        dobView = (EditText) view.findViewById(R.id.dobView);
        emailidView = (EditText) view.findViewById(R.id.emailidView);
        passwordView = (EditText) view.findViewById(R.id.passwordView);
        nextLayout = (LinearLayout) view.findViewById(R.id.nextLayout);
        nexticon = view.findViewById(R.id.nexticon);
        btnNext = view.findViewById(R.id.btn_next);

        try {

            Log.i("sadafsf","try  firstfragment"+ namefetch);
            Log.i("sadafsf","try  firstfragment"+ dobfetch);
            Log.i("sadafsf","try  firstfragment"+ emailfetch);
            Log.i("sadafsf","try  firstfragment"+ passwordfetch);
            Log.i("sadafsf","try  firstfragment"+ flagfetch);
            Log.i("sadafsf","try  firstfragment"+ genderfetch);


            if (flagfetch.equals("Y")) {

                nameVie.setText(namefetch, TextView.BufferType.EDITABLE);
                dobView.setText(dobfetch, TextView.BufferType.EDITABLE);
                emailidView.setText(emailfetch, TextView.BufferType.EDITABLE);
                passwordView.setText(passwordfetch, TextView.BufferType.EDITABLE);

                if (genderfetch.equals("M")) {
                    radio0.setChecked(true);

                } else if (genderfetch.equals("F")) {
                    radio1.setChecked(true);
                } else  if(genderfetch.equals("O"))
                {
                    radio2.setChecked(true);
                }nameVie.setEnabled(false);
                dobView.setEnabled(false);
                emailidView.setEnabled(false);
                passwordView.setEnabled(false);
                radio0.setEnabled(false);
                radio1.setEnabled(false);
                radio2.setEnabled(false);
            }




        } catch (Exception e)
        {

        }


        dobView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable text) {
                // TODO Auto-generated method stub

                if (text.length() == 2 || text.length() == 5) {
                    text.append('-');
                }


            }
        });


        if (email != null) {
            emailidView.setText(email);
        }
        if (PersonName != null) {
            nameVie.setText(PersonName);
        }
//        final RadioButton radio0 = (RadioButton) view.findViewById(R.id.radio0);
//        final RadioButton radio1 = (RadioButton) view.findViewById(R.id.radio1);
//        final RadioButton radio2 = (RadioButton) view.findViewById(R.id.radio2);
        radio0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setChecked(true);
                radio1.setChecked(false);
                radio2.setChecked(false);
                gender = "M";
            }
        });
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setChecked(false);
                radio1.setChecked(true);
                radio2.setChecked(false);
                gender = "F";
            }
        });
        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setChecked(false);
                radio1.setChecked(false);
                radio2.setChecked(true);
                gender = "O";
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScreenData(true, false);

              /*  int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                    if (current == myViewPagerAdapter.getCount() - 1) {
                        //btnNext.setText("Register");
                        signupLayout.setVisibility(View.VISIBLE);
                        nextLayout.setVisibility(View.GONE);
                        // nexticon.setVisibility(View.GONE);
                        profileimage.setVisibility(View.GONE);
                    } else {
                        signupLayout.setVisibility(View.GONE);
                        nextLayout.setVisibility(View.VISIBLE);
                        //btnNext.setText("Next");
                        // nexticon.setVisibility(View.VISIBLE);
                        profileimage.setVisibility(View.VISIBLE);
                    }

                } else {

                }
*/
            }
        });
      /*  radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        // do operations specific to this selection
                        gender = "M";
                        break;
                    case R.id.radio1:
                        gender = "F";
                        break;
                    case R.id.radio2:
                        gender = "O";
                        break;
                }
            }
        });
*/
    }

    private void saveScreenData(boolean NextPreviousFlag, boolean DoneFlag) {
        if (isValidate()) {
            Intent intent = new Intent("ViewPageChange");

            Log.i("sadafsf", "NextPreviousFlag"+NextPreviousFlag);
            Log.i("sadafsf", "Done Flag"+DoneFlag);
            Log.i("sadafsf","name"+nameVieStr);
            Log.i("sadafsf","dob"+dobViewStr);
            Log.i("sadafsf","email"+emailidViewStr);
            Log.i("sadafsf","password"+passwordViewStr);
            Log.i("sadafsf","gender"+gender);

            intent.putExtra("NextPreviousFlag", NextPreviousFlag);
            intent.putExtra("DoneFlag", DoneFlag);
            intent.putExtra("Name", nameVieStr);
            intent.putExtra("DOB", dobViewStr);
            intent.putExtra("Email", emailidViewStr);
            intent.putExtra("Password", passwordViewStr);
            intent.putExtra("Gender", gender);
            getActivity().sendBroadcast(intent);
        }
    }

    // ----validation -----
    private boolean isValidate() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        nameVieStr = nameVie.getText().toString();
        dobViewStr = dobView.getText().toString();
        emailidViewStr = emailidView.getText().toString();
        passwordViewStr = passwordView.getText().toString();

        if(radio0.isChecked())
        {
            gender="M";
        }else if(radio1.isChecked())
        {
            gender="F";
        }else
        {
            gender="O";
        }



        if(dobViewStr.length()!=0) {
            String[] s = dobViewStr.split("-");
            check = s[2];
        }

        if (nameVieStr.length() == 0) {
            showToast("Please Enter Name");
            return false;
        } else if (dobViewStr.length() == 0) {
            showToast("Please Enter your DOB");
            return false;
        } else if (Integer.parseInt(check) > 1998) {
            showToast("You must be atleast 20 years of age");
            return false;
        } else if (emailidViewStr.length() == 0) {
            showToast("Please Enter Email ID");
            return false;
        } else if (!emailidViewStr.matches(emailRegex)) {
            showToast("Please Enter valid Email ID");
            return false;
        } else if (passwordViewStr.length() == 0) {
            showToast("Please Enter Password");
            return false;
        } else if (passwordViewStr.length() < 7) {
            showToast("Please Enter atleast 8 characters Password");
            return false;
        }

        Log.i("sadjadkad",dobViewStr);
        return true;
    }

    private boolean AgeValidate(String dateInString) {
        boolean flag = false;
       /* DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println("Current Date : " + dateFormat.format(date));*/


        Date date = null;
        try {

            Log.i("skjdada",dateInString);
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateInString);

            Calendar calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 20);
            if (calendar.getTime().after(date)) {
                flag = true;
            } else {
                flag = false;
            }
            //System.out.printf("Date %s is older than 18? %s", dateInString, calendar.getTime().after(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
