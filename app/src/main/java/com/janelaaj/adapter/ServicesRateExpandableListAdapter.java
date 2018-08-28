package com.janelaaj.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;

import java.util.ArrayList;
import java.util.HashMap;

import com.janelaaj.activitys.AddNewServiceActivity;
import com.janelaaj.activitys.ManageLocationActivity;
import com.janelaaj.activitys.ServicesRatesEditActivity;
import com.janelaaj.activitys.TimmingEditActivity_FromHome;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.AddNewServiceModel;
import com.janelaaj.model.deleteTimmingModel;
import com.janelaaj.model.getServicesDetailsModel;
import com.janelaaj.model.getServicesInformationModel;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 *
 * @author Arshil Khan.
 */
public class ServicesRateExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles

    private HashMap<String, List<String>> _listDataChild;
    private LinearLayout nextVistLauout;
    public ArrayList<getServicesInformationModel.SERVICE> servicesInformation;
    public ArrayList<getServicesDetailsModel.SERVICE> servicesNamesArrayList;


    TextView textViewFirst, textViewSecond, textViewThird,oneWeek,twoWeek,oneMonths,threeDays;
    CheckBox nextVisitCheckBox;
    Button save;
    Spinner servicesNamesSpinner;
    EditText NormalAmount,DiscountAmount;
    CheckBox isDiscountOn;
    ImageView deleteButton;
    Button saveButton;
    TextInputLayout DiscountAmountTextInputLayout;
    private Handler mHandler= new Handler();
    String location_name,location_address,location_city,from_home;
    String dlmid;
    String Role;
//    String serviceName,sid;


    public ServicesRateExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData,ArrayList<getServicesInformationModel.SERVICE> servicesInformation,ArrayList<getServicesDetailsModel.SERVICE> servicesNamesArrayList,String location_name,String location_address,String location_city,String from_home,String Role) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.servicesInformation = servicesInformation;
        this.servicesNamesArrayList = servicesNamesArrayList;
        this.location_name = location_name;
        this.location_address = location_address;
        this.location_city = location_city;
        this.from_home = from_home;
        this.Role = Role;



    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.servicesedit_item, null);
        }


        servicesNamesSpinner = convertView.findViewById(R.id.servicesNamesSpinner);
        NormalAmount = convertView.findViewById(R.id.NormalAmount);
        DiscountAmount = convertView.findViewById(R.id.DiscountAmount);
        isDiscountOn = convertView.findViewById(R.id.isDiscountOn);
        saveButton = convertView.findViewById(R.id.saveButton);
        DiscountAmountTextInputLayout = convertView.findViewById(R.id.DiscountAmountTextInputLayout);





        for(int i=0;i<servicesInformation.size();i++)
        {
            if(i==groupPosition)
            {
                servicesNamesSpinner.setVisibility(View.GONE);
                NormalAmount.setText(servicesInformation.get(groupPosition).getNormalAmount());
                DiscountAmount.setText(servicesInformation.get(groupPosition).getDiscountAmount());
                if(servicesInformation.get(i).IsDiscountOn().equals("Y"))
                {
                    isDiscountOn.setChecked(true);
                    DiscountAmountTextInputLayout.setVisibility(View.VISIBLE);
                }
                else if(servicesInformation.get(i).IsDiscountOn().equals("N"))
                {
                    isDiscountOn.setChecked(false);
                    DiscountAmountTextInputLayout.setVisibility(View.GONE);
                }
            }

        }

        isDiscountOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDiscountOn.isChecked())
                {
                    DiscountAmountTextInputLayout.setVisibility(View.VISIBLE);
                    DiscountAmount.setText(" ");
                    DiscountAmount.requestFocus();
                }
                else
                {
                    DiscountAmountTextInputLayout.setVisibility(View.GONE);
                }
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String serviceName = servicesInformation.get(groupPosition).getServiceName();
                String dcsmid = servicesInformation.get(groupPosition).getdcsmid();
                String normalAmount = NormalAmount.getText().toString().trim();
                String discountAmount="0";
                String sflag="N";

                if(isDiscountOn.isChecked())
                {
                    sflag = "Y";
                    discountAmount = DiscountAmount.getText().toString().trim();
                }
                else
                {
                    sflag = "N";
                    discountAmount ="0";
                }

                Log.i("jbkjhk",dcsmid);
                Log.i("jbkjhk",normalAmount);
                Log.i("jbkjhk",discountAmount);
                Log.i("jbkjhk",sflag);


                if(_context instanceof ServicesRatesEditActivity){
                  dlmid =  ((ServicesRatesEditActivity)_context).getdlmid();
                }


                if(discountAmount.equals("0"))
                {
                    updateService(dcsmid, normalAmount, discountAmount, sflag);
                } else
                    {
                    if (Integer.parseInt(DiscountAmount.getText().toString().trim()) > Integer.parseInt(NormalAmount.getText().toString().trim()))
                    {
                        Toast.makeText(_context, "Discount Amount Cannot be greator than Normal Amount", Toast.LENGTH_SHORT).show();
                    }
                    else {
                           updateService(dcsmid, normalAmount, discountAmount, sflag);
                         }
                }



            }
        });


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.edittimelist_group, null);
        }

        deleteButton = convertView.findViewById(R.id.deleteButton);


        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        if (isExpanded) {
            convertView.findViewById(R.id.expand).setVisibility(View.GONE);
            convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.listExpandLayout).setBackgroundResource(R.drawable.login_border);
            lblListHeader.setTextColor(Color.parseColor("#ffffff"));
            deleteButton.setBackgroundResource(R.drawable.ic_deletewhite);

        } else {
            convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
            convertView.findViewById(R.id.listExpandLayout).setBackgroundColor(Color.parseColor("#C2C2C2"));
            lblListHeader.setTextColor(Color.parseColor("#808080"));
            deleteButton.setBackgroundResource(R.drawable.ic_dustbin);
        }


        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        final String dcsmid = servicesInformation.get(groupPosition).getdcsmid();

        if(_context instanceof ServicesRatesEditActivity){
            dlmid =  ((ServicesRatesEditActivity)_context).getdlmid();
        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                alert.setTitle("Confirm Deletion");
                alert.setCancelable(false);
                alert.setIcon(R.drawable.ic_dustbin);
                alert.setMessage("Are you sure you want to delete this timing?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteService(dcsmid,groupPosition);
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
                return;


            }
        });


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void updateService(final String dcsmid, final String NormalAmount,final String DiscountAmount,final String sflag) {

        if (Utility.isOnline(_context)) {
            JSONObject object = new JSONObject();
            try {
                object.put("dcsmid",dcsmid);
                object.put("namount", NormalAmount);
                object.put("damount", DiscountAmount);
                object.put("dflag", sflag);
            }

            catch (JSONException e) {
                e.printStackTrace();

            }

            final ProgressDialog progressbar = ProgressDialog.show(_context, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(_context);
            serviceCaller.updateService(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        AddNewServiceModel mainContent = new Gson().fromJson(result, AddNewServiceModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {
                                Toast.makeText(_context, "Information Updated", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(_context, ServicesRatesEditActivity.class);
                                intent.putExtra("dlmid",dlmid);
                                intent.putExtra("location_name",location_name);
                                intent.putExtra("location_address",location_address);
                                intent.putExtra("location_city",location_city);
                                intent.putExtra("Role",Role);
                                _context.startActivity(intent);

                            } else {
                                Toast.makeText(_context, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(_context, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", _context);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, _context);//off line msg....
        }
    }


    private void deleteService(final String dcsmid,final int groupPosition) {

        if (Utility.isOnline(_context)) {
            JSONObject object = new JSONObject();
            try {
                object.put("dcsmid", dcsmid);
            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(_context, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(_context);
            serviceCaller.deleteService(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        deleteTimmingModel mainContent = new Gson().fromJson(result, deleteTimmingModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {



                                Toast.makeText(_context, "SUCCESSFULLY DELETED", Toast.LENGTH_SHORT).show();
                                _listDataHeader.remove(groupPosition);
                                notifyDataSetChanged();
                                Intent intent= new Intent(_context, ServicesRatesEditActivity.class);
                                intent.putExtra("dlmid",dlmid);
                                intent.putExtra("from_home",from_home);
                                intent.putExtra("location_name",location_name);
                                intent.putExtra("location_address",location_address);
                                intent.putExtra("location_city",location_city);
                                intent.putExtra("Role",Role);
                                _context.startActivity(intent);

                            } else {
                                Toast.makeText(_context, mainContent.getStatus().toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(_context, "User Already Registered!", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Utility.alertForErrorMessage("User Already Registered", _context);
                    }
                    if (progressbar.isShowing()) {
                        progressbar.dismiss();
                    }
                }
            });
        } else {
            Utility.alertForErrorMessage(Contants.OFFLINE_MESSAGE, _context);//off line msg....
        }
    }

}
