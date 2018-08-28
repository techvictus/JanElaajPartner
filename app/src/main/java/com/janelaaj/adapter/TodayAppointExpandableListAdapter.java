package com.janelaaj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.janelaaj.R;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Arshil Khan.
 */
public class TodayAppointExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles

    private HashMap<String, List<String>> _listDataChild;
    private LinearLayout nextVistLauout;
    TextView textViewFirst, textViewSecond, textViewThird,oneWeek,twoWeek,oneMonths,threeDays;
    CheckBox nextVisitCheckBox;
    Button save;


    public TodayAppointExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
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
            convertView = infalInflater.inflate(R.layout.apointmentlist_item, null);
        }


        this.nextVistLauout = convertView.findViewById(R.id.nextVistLauout);
        this.textViewFirst = convertView.findViewById(R.id.textViewFirst);
        this.textViewSecond = convertView.findViewById(R.id.textViewSecond);
        this.textViewThird = convertView.findViewById(R.id.textViewThird);
        this.nextVisitCheckBox = convertView.findViewById(R.id.nextVisitCheckBox);
        this.save = convertView.findViewById(R.id.save);
        this.oneWeek = convertView.findViewById(R.id.oneWeek);
        this.twoWeek = convertView.findViewById(R.id.twoWeek);
        this.oneMonths = convertView.findViewById(R.id.oneMonths);
        this.threeDays=convertView.findViewById(R.id.threeDays);
        nextVisitCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    nextVistLauout.setVisibility(View.VISIBLE);
                } else {
                    nextVistLauout.setVisibility(View.GONE);
                }

            }
        });
        threeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threeDays.setBackgroundResource(R.drawable.circlebg_textview);
            }
        });
        oneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneWeek.setBackgroundResource(R.drawable.circlebg_textview);
            }
        });
        twoWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoWeek.setBackgroundResource(R.drawable.circlebg_textview);
            }
        });
        oneMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oneMonths.setBackgroundResource(R.drawable.circlebg_textview);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.appointlist_group, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        if (isExpanded) {
            convertView.findViewById(R.id.expand).setVisibility(View.GONE);
            convertView.findViewById(R.id.collapse).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.listGroupLayout).setBackgroundResource(R.drawable.login_border);
            lblListHeader.setTextColor(Color.parseColor("#ffffff"));
            convertView.findViewById(R.id.viewSpace).setVisibility(View.GONE);
        } else {
            convertView.findViewById(R.id.expand).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.collapse).setVisibility(View.GONE);
            convertView.findViewById(R.id.listGroupLayout).setBackgroundColor(Color.parseColor("#a3a3a3"));
            lblListHeader.setTextColor(Color.parseColor("#808080"));
            convertView.findViewById(R.id.viewSpace).setVisibility(View.INVISIBLE);


        }


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
        return true;
    }



}
