package com.janelaaj.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
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
import com.janelaaj.activitys.LoginActivity;
import com.janelaaj.activitys.ManageDiscountEditActivity;
import com.janelaaj.activitys.ManageLocationActivity;
import com.janelaaj.activitys.SelectOptionScreen;
import com.janelaaj.activitys.ServicesRatesEditActivity;
import com.janelaaj.activitys.TimmingEditActivity;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageLocation;
import com.janelaaj.model.OneViewModel;
import com.janelaaj.model.managelocationcheckpoint;
import com.janelaaj.utilities.Contants;
import com.janelaaj.utilities.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 *
 * @author Arshil Khan.
 */
public class OneViewAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    public String dlmid;
    boolean flag=false;
    public Integer gp;
    ArrayList<OneViewModel.timeinfo> TimeInfo;
    ArrayList<OneViewModel.serviceinfo> ServiceInfo;
    ArrayList<ManageLocation.LOC> LocationInfo;

    private HashMap<String, List<String>> _listDataChild;
    public TextView sunday,monday,tuesday,wednesday,thursday,friday,saturday,services1,services2,services3,services4,services5,services6,discounted_rate6,discounted_rate5,discounted_rate4,discounted_rate3,discounted_rate2,discounted_rate1,rate2,rate3,rate4,rate5,rate6,rate1;

    public OneViewAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData,ArrayList<OneViewModel.serviceinfo> ServiceInfo,ArrayList<OneViewModel.timeinfo> TimeInfo,ArrayList<ManageLocation.LOC> LocationInfo)
    {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.LocationInfo = LocationInfo;
        this.TimeInfo = TimeInfo;
        this.ServiceInfo = ServiceInfo;

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
            convertView = infalInflater.inflate(R.layout.list_item2, null);
        }


//        Log.i("dlmid", locationinfo.get(groupPosition).getDlmid());
        gp = groupPosition;

        Log.i("child","child position" + String.valueOf(childPosition));

        Log.i("child","parent position" + String.valueOf(groupPosition));
        Log.i("jaiajai", String.valueOf(groupPosition) + "in 1 pui");
        Log.i("jaiajai", String.valueOf(LocationInfo.get(groupPosition).getDlmid()) + "in 1 pui");


//
//        this.timming = convertView.findViewById(R.id.timming);
//        this.services = convertView.findViewById(R.id.services);
//        this.managediscount = convertView.findViewById(R.id.managediscount);
//        this.editlocation = convertView.findViewById(R.id.editlocation);

        this.sunday = convertView.findViewById(R.id.sunday);
        this.monday = convertView.findViewById(R.id.monday);
        this.tuesday = convertView.findViewById(R.id.tuesday);
        this.wednesday = convertView.findViewById(R.id.wednesday);
        this.thursday = convertView.findViewById(R.id.thursday);
        this.friday = convertView.findViewById(R.id.friday);
        this.saturday = convertView.findViewById(R.id.saturday);
        this.services1 = convertView.findViewById(R.id.services1);
        this.services2 = convertView.findViewById(R.id.services2);
        this.services3 = convertView.findViewById(R.id.services3);
        this.services4 = convertView.findViewById(R.id.services4);
        this.services5 = convertView.findViewById(R.id.services5);
        this.services6 = convertView.findViewById(R.id.services6);
        this.rate1 = convertView.findViewById(R.id.rate1);
        this.rate2 = convertView.findViewById(R.id.rate2);
        this.rate3 = convertView.findViewById(R.id.rate3);
        this.rate4 = convertView.findViewById(R.id.rate4);
        this.rate5 = convertView.findViewById(R.id.rate5);
        this.rate6 = convertView.findViewById(R.id.rate6);
        this.discounted_rate1 = convertView.findViewById(R.id.discounted_rate1);
        this.discounted_rate2 = convertView.findViewById(R.id.discounted_rate2);
        this.discounted_rate3 = convertView.findViewById(R.id.discounted_rate3);
        this.discounted_rate4 = convertView.findViewById(R.id.discounted_rate4);
        this.discounted_rate5 = convertView.findViewById(R.id.discounted_rate5);
        this.discounted_rate6 = convertView.findViewById(R.id.discounted_rate6);



        if (Utility.isOnline(_context)) {
            JSONObject object = new JSONObject();
            try {
                object.put("dlmid", LocationInfo.get(groupPosition).getDlmid());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final ProgressDialog progressbar = ProgressDialog.show(_context, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(_context);
            serviceCaller.oneview(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        OneViewModel mainContent = new Gson().fromJson(result, OneViewModel.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                ServiceInfo.clear();
                                TimeInfo.clear();
                                services1.setText("");
                                services2.setText("");
                                services3.setText("");
                                services4.setText("");
                                services5.setText("");
                                services6.setText("");
                                rate1.setText("");
                                rate2.setText("");
                                rate3.setText("");
                                rate4.setText("");
                                rate5.setText("");
                                rate6.setText("");
                                discounted_rate1.setText("");
                                discounted_rate2.setText("");
                                discounted_rate3.setText("");
                                discounted_rate4.setText("");
                                discounted_rate5.setText("");
                                discounted_rate6.setText("");
                                monday.setText("");
                                tuesday.setText("");
                                wednesday.setText("");
                                thursday.setText("");
                                friday.setText("");
                                saturday.setText("");
                                sunday.setText("");


                                TimeInfo = mainContent.getTimeinfo();
                                ServiceInfo = mainContent.getServiceinfo();

                                String mon="";
                                String tue="";
                                String wed="";
                                String thu="";
                                String fri="";
                                String sat="";
                                String sun="";
                                Log.i("vfahbv",String.valueOf(TimeInfo.size()));

                                try {


                                    if (TimeInfo == null) {
                                        Log.i("vfahbv", String.valueOf(TimeInfo.size()));

                                    } else {

                                        Log.i("vfahbv", String.valueOf(TimeInfo.get(0).getMonday().size()));
                                        for (int i = 0; i < TimeInfo.get(0).getMonday().size(); i++) {

                                            String x = TimeInfo.get(0).getMonday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getMonday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

//                                            String m = TimeInfo.get(0).getMonday().get(i).getFrom() + "-" + TimeInfo.get(0).getMonday().get(i).getTo();

                                            String flag = TimeInfo.get(0).getMonday().get(i).getFlag();

                                            if (mon == "" && flag.equals("Y")) {
                                                mon = mon + m + "* ";
                                            } else if (mon == "" && flag.equals("N")) {
                                                mon = mon + m;
                                            } else if (flag.equals("Y")) {
                                                mon = mon + "," + m + "* ";
                                            } else {
                                                mon = mon + "," + m;
                                            }
                                        }
                                        for (int i = 0; i < TimeInfo.get(0).getTuesday().size(); i++) {

                                            String x = TimeInfo.get(0).getTuesday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getTuesday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

                                            String flag = TimeInfo.get(0).getTuesday().get(i).getFlag();

                                            if (tue == "" && flag.equals("Y")) {
                                                tue = tue + m + "* ";
                                            } else if (tue == "" && flag.equals("N")) {
                                                tue = tue + m;
                                            } else if (flag.equals("Y")) {
                                                tue = tue + "," + m + "* ";
                                            } else {
                                                tue = tue + "," + m;
                                            }

                                        }
                                        for (int i = 0; i < TimeInfo.get(0).getWednesday().size(); i++) {

                                            String x = TimeInfo.get(0).getWednesday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getWednesday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

                                            String flag = TimeInfo.get(0).getWednesday().get(i).getFlag();

                                            if (wed == "" && flag.equals("Y")) {
                                                wed = wed + m + "* ";
                                            } else if (wed == "" && flag.equals("N")) {
                                                wed = wed + m;
                                            } else if (flag.equals("Y")) {
                                                wed = wed + "," + m + "* ";
                                            } else {
                                                wed = wed + "," + m;
                                            }
                                        }
                                        for (int i = 0; i < TimeInfo.get(0).getThursday().size(); i++) {

                                            String x = TimeInfo.get(0).getThursday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getThursday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

                                            String flag = TimeInfo.get(0).getThursday().get(i).getFlag();
                                            Log.i("thursday", flag);

                                            if (thu == "" && flag.equals("Y")) {
                                                thu = thu + m + "* ";
                                            } else if (thu == "" && flag.equals("N")) {
                                                thu = thu + m;
                                            } else if (flag.equals("Y")) {
                                                thu = thu + "," + m + "* ";
                                            } else {
                                                thu = thu + "," + m;

                                            }
                                        }
                                        for (int i = 0; i < TimeInfo.get(0).getFriday().size(); i++) {

                                            String x = TimeInfo.get(0).getFriday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getFriday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

                                            String flag = TimeInfo.get(0).getFriday().get(i).getFlag();

                                            if (fri == "" && flag.equals("Y")) {
                                                fri = fri + m + "* ";
                                            } else if (fri == "" && flag.equals("N")) {
                                                fri = fri + m;
                                            } else if (flag.equals("Y")) {
                                                fri = fri + "," + m + "* ";
                                            } else {
                                                fri = fri + "," + m;
                                            }

                                        }
                                        for (int i = 0; i < TimeInfo.get(0).getSaturday().size(); i++) {

                                            String x = TimeInfo.get(0).getSaturday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getSaturday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

                                            String flag = TimeInfo.get(0).getSaturday().get(i).getFlag();

                                            if (sat == "" && flag.equals("Y")) {
                                                sat = sat + m + "* ";
                                            } else if (sat == "" && flag.equals("N")) {
                                                sat = sat + m;
                                            } else if (flag.equals("Y")) {
                                                sat = sat + "," + m + "* ";
                                            } else {
                                                sat = sat + "," + m;
                                            }

                                        }
                                        for (int i = 0; i < TimeInfo.get(0).getSunday().size(); i++) {

                                            String x = TimeInfo.get(0).getSunday().get(i).getFrom();
                                            String y = TimeInfo.get(0).getSunday().get(i).getTo();

                                            String xout = x.replaceAll("..(?!$)", "$0:");
                                            String yout = y.replaceAll("..(?!$)", "$0:");
                                            String m = xout + "-" + yout;

                                            String flag = TimeInfo.get(0).getSunday().get(i).getFlag();

                                            if (sun == "" && flag.equals("Y")) {
                                                sun = sun + m + "* ";
                                            } else if (sun == "" && flag.equals("N")) {
                                                sun = sun + m;
                                            } else if (flag.equals("Y")) {
                                                sun = sun + "," + m + "* ";
                                            } else {
                                                sun = sun + "," + m;
                                            }
                                        }


                                        if (ServiceInfo == null) {
                                            Log.i("size", String.valueOf(ServiceInfo.size()));
                                        } else {

                                            Log.i("jgvcjycv", String.valueOf(ServiceInfo.size()));

                                            for (int i = 0; i < ServiceInfo.size(); i++) {

                                                String s1 = ServiceInfo.get(i).getSname();

                                                String n1 = ServiceInfo.get(i).getNamount();

                                                String d1 = ServiceInfo.get(i).getDamount();

                                                if (i == 0) {
                                                    Log.i("jgvcjycv","in i=0");;
                                                    services1.setText(s1);
                                                } else if (i == 1) {
                                                    Log.i("jgvcjycv","in i=1");;
                                                    services2.setText(s1);
                                                } else if (i == 2) {
                                                    Log.i("jgvcjycv","in i=2");;
                                                    services3.setText(s1);
                                                } else if (i == 3) {
                                                    Log.i("jgvcjycv","in i=3");;
                                                    services4.setText(s1);
                                                } else if (i == 4) {
                                                    Log.i("jgvcjycv","in i=4");;
                                                    services5.setText(s1);
                                                } else if (i == 5) {
                                                    Log.i("jgvcjycv","in i=6");;
                                                    services6.setText(s1);
                                                }


                                                if (i == 0) {
                                                    rate1.setText("\u20B9 " + n1);
                                                } else if (i == 1) {
                                                    rate2.setText("\u20B9 " + n1);
                                                } else if (i == 2) {
                                                    rate3.setText("\u20B9 " + n1);
                                                } else if (i == 3) {
                                                    rate4.setText("\u20B9 " + n1);
                                                } else if (i == 4) {
                                                    rate5.setText("\u20B9 " + n1);
                                                } else if (i == 5) {
                                                    rate6.setText("\u20B9 " + n1);
                                                }


                                                if (i == 0) {
                                                    discounted_rate1.setText("\u20B9 " + d1);
                                                } else if (i == 1) {
                                                    discounted_rate2.setText("\u20B9 " + d1);
                                                } else if (i == 2) {
                                                    discounted_rate3.setText("\u20B9 " + d1);
                                                } else if (i == 3) {
                                                    discounted_rate4.setText("\u20B9 " + d1);
                                                } else if (i == 4) {
                                                    discounted_rate5.setText("\u20B9 " + d1);
                                                } else if (i == 5) {
                                                    discounted_rate6.setText("\u20B9 " + d1);
                                                }


                                            }
                                        }


                                    }
                                } catch (Exception e) {

                                    Toast.makeText(_context, "Please Fill the timings first", Toast.LENGTH_SHORT).show();
                                }




                                monday.setText(mon);
                                tuesday.setText(tue);
                                wednesday.setText(wed);
                                thursday.setText(thu);
                                friday.setText(fri);
                                saturday.setText(sat);
                                sunday.setText(sun);


//                                sunday.setText(String.valueOf(mainContent.getTimeinfo().get(0).get));
//                                sunday = TimeInfo.get(gp).getMonday()



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
            convertView = infalInflater.inflate(R.layout.list_group2, null);
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
            convertView.findViewById(R.id.listGroupLayout).setBackgroundColor(Color.parseColor("#ffffff"));
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
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        Log.i("zxccvxc",String.valueOf(childPosition));
        return true;
    }


//    public void openTimmingEditActivity(Context context) {
//        Intent myactivity = new Intent(context.getApplicationContext(), TimmingEditActivity.class);
//        String dlmid = LocationInfo.get(gp).getDlmid();
//        myactivity.putExtra("dlmid",dlmid);
//        myactivity.putExtra("id",LocationInfo.get(gp).getLid());
//        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
//        context.getApplicationContext().startActivity(myactivity);
//
//    }
//    public void openServicesRatesEditActivity(Context context) {
//        Intent myactivity = new Intent(context.getApplicationContext(), ServicesRatesEditActivity.class);
//        String dlmid = LocationInfo.get(gp).getDlmid();
//        myactivity.putExtra("dlmid",dlmid);
//        myactivity.putExtra("id",LocationInfo.get(gp).getLid());
//        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
//        context.getApplicationContext().startActivity(myactivity);
//
//    }
//
//    public void openManageDiscountEditActivity(Context context) {
//        Intent myactivity = new Intent(context.getApplicationContext(), ManageDiscountEditActivity.class);
//        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
//        context.getApplicationContext().startActivity(myactivity);
//
//    }



}
