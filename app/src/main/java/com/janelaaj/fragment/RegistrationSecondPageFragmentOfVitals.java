package com.janelaaj.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.activitys.AddLocationScreen;
import com.janelaaj.activitys.ProfileRegistrationActivity;
import com.janelaaj.activitys.SelectOptionScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationSecondPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationSecondPageFragmentOfVitals extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RegistrationSecondPageFragmentOfVitals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationSecondPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationSecondPageFragmentOfVitals newInstance(String param1, String param2) {
        RegistrationSecondPageFragmentOfVitals fragment = new RegistrationSecondPageFragmentOfVitals();
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
        }
    }

    private View view;
    Context context;
    String Specialization;
    String RegistrationCouncil;
    String RegistrationYear;
    Button btn_signup;
    private LinearLayout signupLayout;
    EditText medicalregiNo, experinceView;
    Spinner statee;
    String medicalregiNoStr, experinceViewStr;
    EditText Address1,Address2,City,District,Pincode,Aadhar;
    String address1="",address2="",city="",district="",pincode="",state="",aadhar="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();
        view = inflater.inflate(R.layout.fragment_registration_second_page_fragment_of_vitals, container, false);
        init();
        return view;



    }

    private void init() {

        statee =  view.findViewById(R.id.state);
        btn_signup = view.findViewById(R.id.btn_signup);
        Address1 = view.findViewById(R.id.Address1);
        Address2 = view.findViewById(R.id.Address2);
        City = view.findViewById(R.id.City);
        District = view.findViewById(R.id.District);
        Pincode = view.findViewById(R.id.Pincode);
        Aadhar = view.findViewById(R.id.Aadhar_No);





        final String[] SpecializationArray = getResources().getStringArray(R.array.States);
        final List<String> plantsList = new ArrayList<>(Arrays.asList(SpecializationArray));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        statee.setAdapter(spinnerArrayAdapter);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidate()) {

                     address1 = Address1.getText().toString().trim();
                     address2 = Address2.getText().toString().trim();
                     city = City.getText().toString().trim();
                     district = District.getText().toString().trim();
                     pincode = Pincode.getText().toString().trim();
                     state =  statee.getSelectedItem().toString();
                     aadhar = Aadhar.getText().toString().trim();


                    saveScreenData(false, true);
                }
            }
        });

    }

    // ----validation -----
    private boolean isValidate() {
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
//
//        if (medicalregiNoStr.length() == 0) {
//            showToast("Please Enter Medical Registration Number");
//            return false;
//        } else if (RegistrationYear == null) {
//            showToast("Select Registration Year First");
//            return false;
//        } else if (Specialization == null || Specialization.equals("")) {
//            showToast("Select Specialization");
//            return false;
//        } else if (RegistrationCouncil == null || RegistrationCouncil.equals("")) {
//            showToast("Select Registration Council");
//            return false;
//        } else if (RegistrationYear == null || RegistrationYear.equals("")) {
//            showToast("Select Registration Year");
//            return false;
//        } else if (RegistrationYear.equals("Registration Year")) {
//            showToast("Select Registration Year");
//            return false;
//        } else if (experinceViewStr.length() == 0) {
//            showToast("Please Enter Career Experience in Years");
//            return false;
//        }

        return true;
    }

    private void saveScreenData(boolean NextPreviousFlag, boolean DoneFlag) {
        Intent intent = new Intent("ViewPageChange");
        intent.putExtra("NextPreviousFlag", NextPreviousFlag);
        intent.putExtra("DoneFlag", DoneFlag);
        intent.putExtra("Address1", address1);
        intent.putExtra("Address2", address2);
        intent.putExtra("City", city);
        intent.putExtra("District", district);
        intent.putExtra("State", state);
        intent.putExtra("Pincode", pincode);
        intent.putExtra("Aadhar", aadhar);

        getActivity().sendBroadcast(intent);
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
