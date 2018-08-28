package com.janelaaj.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.activitys.EditLocationActivity;
import com.janelaaj.activitys.ManageDiscountEditActivity;
import com.janelaaj.activitys.ServicesRatesEditActivity;
import com.janelaaj.activitys.TimmingEditActivity;
import com.janelaaj.activitys.TimmingEditActivity_FromHome;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.managelocationcheckpoint;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 *
 * @author Arshil Khan.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    public String dlmid;
    boolean flag=false;
    public Integer gp;
    public  ArrayList<ManageLocation.LOC> locationinfo;
    String from_home,Role;

    private HashMap<String, List<String>> _listDataChild;
    private LinearLayout timming, services, managediscount, editlocation;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData,ArrayList<ManageLocation.LOC> LocationInfo,String from_home,String Role)
     {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.locationinfo = LocationInfo;
        this.from_home= from_home;
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
    public View getChildView(int groupPosition,int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }


        Log.i("dlmid", locationinfo.get(groupPosition).getDlmid());
        gp = groupPosition;

        Log.i("jaiajai", String.valueOf(groupPosition) + "in 1 pui");
        Log.i("jaiajai", String.valueOf(locationinfo.get(groupPosition).getDlmid()) + "in 1 pui");



        this.timming = convertView.findViewById(R.id.timming);
        this.services = convertView.findViewById(R.id.services);
        this.managediscount = convertView.findViewById(R.id.managediscount);
        this.editlocation = convertView.findViewById(R.id.editlocation);


//        if (from_home.equals("0")){

            if (Utility.isOnline(_context)) {
                JSONObject object = new JSONObject();
                try {
                    object.put("dlmid", locationinfo.get(groupPosition).getDlmid());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                final ProgressDialog progressbar = ProgressDialog.show(_context, "", "Please wait..", true);
                progressbar.show();
                ServiceCaller serviceCaller = new ServiceCaller(_context);
                serviceCaller.manageloctionchild(object, new IAsyncWorkCompletedCallback() {
                    @Override
                    public void onDone(String result, boolean isComplete) {
                        if (isComplete) {
                            managelocationcheckpoint mainContent = new Gson().fromJson(result, managelocationcheckpoint.class);
                            if (mainContent != null) {
                                if (mainContent.getStatus().equals("SUCCESS")) {
                                        services.setEnabled(true);
                                        managediscount.setEnabled(true);
                                        timming.setEnabled(true);
                                        timming.setAlpha(1.0f);
                                        services.setAlpha(1.0f);
                                        editlocation.setEnabled(true);
                                        managediscount.setAlpha(1.0f);
                                        editlocation.setAlpha(1.0f);
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


//        }


        timming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimmingEditActivity(_context);
            }
        });

        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServicesRatesEditActivity(_context);
            }
        });
        managediscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openManageDiscountEditActivity(_context);
            }
        });

        editlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditLocationActivity(_context);
            }
        });



        //  txtListChild.setText(childText);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        if (isExpanded) {
            Log.i("mknjbl","asd "+String.valueOf(groupPosition));
            convertView.findViewById(R.id.expand).setVisibility(View.GONE);
            convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.listGroupLayout).setBackgroundResource(R.drawable.login_border);
            lblListHeader.setTextColor(Color.parseColor("#ffffff"));
            convertView.findViewById(R.id.viewSpace).setVisibility(View.GONE);
        } else {
            Log.i("mknjbl","czx"+String.valueOf(groupPosition));
            convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
            convertView.findViewById(R.id.listGroupLayout).setBackgroundColor(Color.parseColor("#BFBFBF"));
            lblListHeader.setTextColor(Color.parseColor("#757575"));
            convertView.findViewById(R.id.viewSpace).setVisibility(View.INVISIBLE);
        }


//        if(pos != groupPosition){
//            convertView.findViewById(R.id.expand).setVisibility(View.GONE);
//            convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
//            convertView.findViewById(R.id.listGroupLayout).setBackgroundResource(R.drawable.login_border);
//            lblListHeader.setTextColor(Color.parseColor("#ffffff"));
//            convertView.findViewById(R.id.viewSpace).setVisibility(View.GONE);
//        }

//        for(int i=0;i<_listDataHeader.size();i++)
//        {
//            if()
//        }
//
//        Log.i("a","jai ho 1");
//        Log.i("a","grp pos"+groupPosition+" is expanded "+isExpanded);




//        if(xxx!=null) {
//            Log.i("asdzvcxv","jai ho");
//            TextView ff = xxx.findViewById(R.id.lblListHeader);
//            xxx.findViewById(R.id.expand).setVisibility(View.GONE);
//            xxx.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
//            xxx.findViewById(R.id.listGroupLayout).setBackgroundResource(R.drawable.login_border);
//            ff.setTextColor(Color.parseColor("#ffffff"));
//            xxx.findViewById(R.id.viewSpace).setVisibility(View.GONE);
//        }
//
//
//        xxx=convertView;





        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(locationinfo.get(groupPosition).getLname() + ',' + ' '+ locationinfo.get(groupPosition).getLadrline1() + ',' + ' '+ locationinfo.get(groupPosition).getLcity());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Log.i("zxccvxc",String.valueOf(childPosition));
//        Log.i("zxccvxc",String.valueOf());

        return true;
    }


    public void openTimmingEditActivity(Context context) {
        if(from_home.equals("0"))
        {
            Intent myactivity = new Intent(context.getApplicationContext(), TimmingEditActivity_FromHome.class);
            String dlmid = locationinfo.get(gp).getDlmid();
            myactivity.putExtra("dlmid",dlmid);
            myactivity.putExtra("id",locationinfo.get(gp).getLid());
            myactivity.putExtra("from_home",from_home);
            myactivity.putExtra("location_name",locationinfo.get(gp).getLname());
            myactivity.putExtra("location_address",locationinfo.get(gp).getLadrline1());
            myactivity.putExtra("location_city",locationinfo.get(gp).getLcity());
            myactivity.putExtra("Role",Role);
            myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(myactivity);
        } else
        {
            Intent myactivity = new Intent(context.getApplicationContext(), TimmingEditActivity_FromHome.class);
            String dlmid = locationinfo.get(gp).getDlmid();
            myactivity.putExtra("dlmid",dlmid);
            myactivity.putExtra("id",locationinfo.get(gp).getLid());
            myactivity.putExtra("from_home",from_home);
            myactivity.putExtra("location_name",locationinfo.get(gp).getLname());
            myactivity.putExtra("location_address",locationinfo.get(gp).getLadrline1());
            myactivity.putExtra("location_city",locationinfo.get(gp).getLcity());
            myactivity.putExtra("Role",Role);
            myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(myactivity);
        }


    }
    public void openServicesRatesEditActivity(Context context) {
        Intent myactivity = new Intent(context.getApplicationContext(), ServicesRatesEditActivity.class);
        String dlmid = locationinfo.get(gp).getDlmid();
        myactivity.putExtra("dlmid",dlmid);
        myactivity.putExtra("id",locationinfo.get(gp).getLid());
        myactivity.putExtra("from_home",from_home);
        myactivity.putExtra("location_name",locationinfo.get(gp).getLname());
        myactivity.putExtra("location_address",locationinfo.get(gp).getLadrline1());
        myactivity.putExtra("location_city",locationinfo.get(gp).getLcity());
        myactivity.putExtra("Role",Role);
        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(myactivity);

    }

    public void openManageDiscountEditActivity(Context context) {
        Intent myactivity = new Intent(context.getApplicationContext(), ManageDiscountEditActivity.class);
        String dlmid = locationinfo.get(gp).getDlmid();
        myactivity.putExtra("dlmid",dlmid);
        myactivity.putExtra("id",locationinfo.get(gp).getLid());
        myactivity.putExtra("from_home",from_home);
        myactivity.putExtra("location_name",locationinfo.get(gp).getLname());
        myactivity.putExtra("location_address",locationinfo.get(gp).getLadrline1());
        myactivity.putExtra("location_city",locationinfo.get(gp).getLcity());
        myactivity.putExtra("Role",Role);
        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(myactivity);

    }


    public void openEditLocationActivity(Context context) {
        Intent myactivity = new Intent(context.getApplicationContext(), EditLocationActivity.class);
        String dlmid = locationinfo.get(gp).getDlmid();
        myactivity.putExtra("dlmid",dlmid);
        myactivity.putExtra("id",locationinfo.get(gp).getLid());
        myactivity.putExtra("from_home",from_home);
        myactivity.putExtra("location_name",locationinfo.get(gp).getLname());
        myactivity.putExtra("location_address",locationinfo.get(gp).getLadrline1());
        myactivity.putExtra("location_city",locationinfo.get(gp).getLcity());
        myactivity.putExtra("Role",Role);
        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(myactivity);

    }



    }
