package com.janelaaj.adapter;

import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.janelaaj.R;
import com.janelaaj.activitys.ManageDiscountEditActivity;
import com.janelaaj.activitys.QuickSetsActivity;
import com.janelaaj.activitys.INFO;

import com.janelaaj.activitys.TimmingEditActivity;
import com.janelaaj.activitys.INFOOO;
import com.janelaaj.framework.IAsyncWorkCompletedCallback;
import com.janelaaj.framework.ServiceCaller;
import com.janelaaj.model.ManageDiscountModel;
import com.janelaaj.model.QuickSets;
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

public class MangeDiscountExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<INFOOO> _listDataHeader; // header titles

    private HashMap<INFOOO, List<String>> _listDataChild;
    public ArrayList<ManageDiscountModel.time> ManageDiscountInfo;
    Integer monflag,tueflag,wedflag,thuflag,friflag,satflag,sunflag;
    ArrayList<ManageDiscountModel.time> monday;
    ArrayList<ManageDiscountModel.time> tuesday;
    ArrayList<ManageDiscountModel.time> wednesday;
    ArrayList<ManageDiscountModel.time> thursday;
    ArrayList<ManageDiscountModel.time> friday;
    ArrayList<ManageDiscountModel.time> saturday;
    ArrayList<ManageDiscountModel.time> sunday;

    ArrayList<String> check;
    ArrayList<INFO> updateInfo;
    int valueChanged;
    public MangeDiscountExpandableListAdapter(Context context, List<INFOOO> listDataHeader, HashMap<INFOOO, List<String>> listChildData,ArrayList<ManageDiscountModel.time> ManageDiscountInfo,Integer monflag,Integer tueflag,Integer wedflag,Integer thuflag,Integer friflag,Integer satflag,Integer sunflag,ArrayList<ManageDiscountModel.time> monday,ArrayList<ManageDiscountModel.time> tuesday,ArrayList<ManageDiscountModel.time> wednesday,ArrayList<ManageDiscountModel.time> thursday,ArrayList<ManageDiscountModel.time> friday,ArrayList<ManageDiscountModel.time> saturday, ArrayList<ManageDiscountModel.time> sunday,ArrayList<INFO> updateInfo,Integer valueChanged) {
        this._context = context;

        Log.i("sadafsf","friday flag in adapter"+friflag);
        Log.i("sadafsf","thursday flag in adapter"+thuflag);

        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.ManageDiscountInfo = ManageDiscountInfo;
        this.monflag = monflag;
        this.tueflag = tueflag;
        this.wedflag = wedflag;
        this.thuflag = thuflag;
        this.friflag = friflag;
        this.satflag = satflag;
        this.sunflag = sunflag;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.updateInfo = updateInfo;
        this.valueChanged = valueChanged;

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
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.edittimelist_item, null);
        }


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
        INFOOO headerTitle = (INFOOO) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.mangedisedit_group, null);
        }

        final TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        CheckBox checkdDiscount = convertView.findViewById(R.id.checkdDiscount);




        final LinearLayout listExpandLayout = convertView.findViewById(R.id.listExpandLayout);
        checkdDiscount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()) {
                    listExpandLayout.setBackgroundResource(R.drawable.login_border);
                    lblListHeader.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    listExpandLayout.setBackgroundColor(Color.parseColor("#C2C2C2"));
                    lblListHeader.setTextColor(Color.parseColor("#000000"));
                }

            }
        });
        lblListHeader.setText(headerTitle.getDate());


        if(monflag==1)
        {
            for(int i=0;i<monday.size();i++)
            {

                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }

        else if(tueflag==1)
        {
            for(int i=0;i<tuesday.size();i++)
            {
                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }
        else if(wedflag==1)
        {
            for(int i=0;i<wednesday.size();i++)
            {
                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }
        else if(thuflag==1)
        {
            for(int i=0;i<thursday.size();i++)
            {
                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }
        else if(friflag==1)
        {
            for(int i=0;i<friday.size();i++)
            {
                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }
        else if(satflag==1)
        {
            for(int i=0;i<saturday.size();i++)
            {
                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }
        else if(sunflag==1)
        {
            for(int i=0;i<sunday.size();i++)
            {
                if(headerTitle.getsetcheckedorunchecked().equals("Y"))
                {
                    checkdDiscount.setChecked(true);
                }
                else
                {
                    checkdDiscount.setChecked(false);
                }
            }
        }



        checkdDiscount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //// ifff
                if (((CheckBox) v).isChecked()) {

                    if(monflag==1)
                    {
                        ManageDiscountEditActivity.valueChanged = 1;

                        if(monday.get(groupPosition).getDiscountflag().equals("N")) {


                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(monday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(monday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(tueflag==1)
                    {

                        ManageDiscountEditActivity.valueChanged = 1;

                        if(tuesday.get(groupPosition).getDiscountflag().equals("N")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(tuesday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }


                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(tuesday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(wedflag==1)
                    {

                        ManageDiscountEditActivity.valueChanged = 1;

                        if(wednesday.get(groupPosition).getDiscountflag().equals("N")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(wednesday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }



                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(wednesday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(thuflag==1)
                    {



                        ManageDiscountEditActivity.valueChanged = 1;

                        if(thursday.get(groupPosition).getDiscountflag().equals("N")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(thursday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(thursday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(friflag==1)
                    {

                        Log.i("sadafsf","in friday flag");

                        ManageDiscountEditActivity.valueChanged = 1 ;

                        if(friday.get(groupPosition).getDiscountflag().equals("N")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(friday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(friday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(satflag==1)
                    {


                        ManageDiscountEditActivity.valueChanged = 1;

                        if(saturday.get(groupPosition).getDiscountflag().equals("N")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(saturday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }


                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(saturday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(sunflag==1)
                    {


                        ManageDiscountEditActivity.valueChanged = 1;

                        if(sunday.get(groupPosition).getDiscountflag().equals("N")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(sunday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("Y");
                            info.settimeid(sunday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }

                }
                else
                {




                    /////////////////else

                    if(monflag==1)
                    {

                        ManageDiscountEditActivity.valueChanged = 1;


                        if(monday.get(groupPosition).getDiscountflag().equals("Y")) {




                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(monday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }


                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(monday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(tueflag==1)
                    {

                        ManageDiscountEditActivity.valueChanged = 1;

                        if(tuesday.get(groupPosition).getDiscountflag().equals("Y")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(tuesday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(tuesday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(wedflag==1)
                    {
                        ManageDiscountEditActivity.valueChanged = 1;

                        if(wednesday.get(groupPosition).getDiscountflag().equals("Y")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(wednesday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(wednesday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(thuflag==1)
                    {
                        ManageDiscountEditActivity.valueChanged = 1;

                        if(thursday.get(groupPosition).getDiscountflag().equals("Y")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(thursday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(thursday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(friflag==1)
                    {
                        ManageDiscountEditActivity.valueChanged = 1;


                        if(friday.get(groupPosition).getDiscountflag().equals("Y")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(friday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(friday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(satflag==1)
                    {
                        ManageDiscountEditActivity.valueChanged = 1;


                        if(saturday.get(groupPosition).getDiscountflag().equals("Y")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(saturday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }

                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(saturday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }

                    }
                    else if(sunflag==1)
                    {


                        ManageDiscountEditActivity.valueChanged = 1;

                        if(sunday.get(groupPosition).getDiscountflag().equals("Y")) {

                            for(int i=0;i<updateInfo.size();i++)
                            {
                                if(updateInfo.get(i).gettimeid().equals(sunday.get(groupPosition).getTimeid()))
                                {
                                    updateInfo.remove(i);
                                }
                            }


                            INFO info = new INFO();
                            info.setflag("N");
                            info.settimeid(sunday.get(groupPosition).getTimeid());
                            updateInfo.add(info);
                        }
                    }
                }

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


    public void oprnTimmingEditActivity(Context context) {
        Intent myactivity = new Intent(context.getApplicationContext(), TimmingEditActivity.class);
        myactivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(myactivity);

    }


    private void updateManageDiscount(final String timeid,final String flag) {

        if (Utility.isOnline(_context)) {
            JSONObject object = new JSONObject();
            try {
                object.put("timeid", timeid);
                object.put("flag", flag);

            }

            catch (JSONException e) {
                e.printStackTrace();
            }

            final ProgressDialog progressbar = ProgressDialog.show(_context, "", "Please wait..", true);
            progressbar.show();
            ServiceCaller serviceCaller = new ServiceCaller(_context);
            serviceCaller.updateManageDiscount(object, new IAsyncWorkCompletedCallback() {
                @Override
                public void onDone(String result, boolean isComplete) {
                    if (isComplete) {
                        QuickSets mainContent = new Gson().fromJson(result, QuickSets.class);
                        if (mainContent != null) {
                            if (mainContent.getStatus().equals("SUCCESS")) {

                                Toast.makeText(_context, "SUCCESSFULLY UPDATED", Toast.LENGTH_SHORT).show();

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
