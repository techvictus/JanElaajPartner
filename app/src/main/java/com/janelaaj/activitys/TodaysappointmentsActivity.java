package com.janelaaj.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.janelaaj.R;
import com.janelaaj.adapter.ExpandableListAdapter;
import com.janelaaj.adapter.TodayAppointExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author Arshil Khan.
 */

public class TodaysappointmentsActivity extends AppCompatActivity implements View.OnClickListener{

    TodayAppointExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaysappoint_screen);
        expListView = this.findViewById(R.id.lvExp);
        this.save = this.findViewById(R.id.save);
        save.setOnClickListener(this);
        prepareListData();
        listAdapter = new TodayAppointExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expListView.setDividerHeight(R.dimen._10dp);
                 expListView.setDivider(getResources().getDrawable(android.R.color.transparent));

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
              /*  expListView.setDivider(getResources().getDrawable(R.drawable.expandable_selector));
                expListView.setDividerHeight(10);*/

                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Mr. Amit Anand");
        listDataHeader.add("Ms. Arun Aggarwal");
        listDataHeader.add("Ms. Sunita Williams");
        listDataHeader.add("Dr. Asif Khair");
        listDataHeader.add("Dr. Dyal Singh");
        listDataHeader.add("Dr. Arshi Khurana");
        listDataHeader.add("Dr. Sanjay Rajak");
        listDataHeader.add("Dr. Sunil Sharma");

        // Adding child data
        List<String> allListDocter = new ArrayList<String>();
        allListDocter.add("Ms. Arun Aggarwal");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Mr. Amit Anand");


        listDataChild.put(listDataHeader.get(0), allListDocter); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), nowShowing);
        listDataChild.put(listDataHeader.get(3), nowShowing);
        listDataChild.put(listDataHeader.get(4), nowShowing);
        listDataChild.put(listDataHeader.get(5), nowShowing);
        listDataChild.put(listDataHeader.get(6), nowShowing);
        listDataChild.put(listDataHeader.get(7), nowShowing);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save) {
            Intent intent = new Intent(TodaysappointmentsActivity.this, TimmingEditActivity.class);
            startActivity(intent);
        }


    }

}
