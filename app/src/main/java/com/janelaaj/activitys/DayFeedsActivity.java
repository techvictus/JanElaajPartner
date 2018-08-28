package com.janelaaj.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.janelaaj.R;
import com.janelaaj.adapter.DayFeedsAdapter;
import com.janelaaj.model.FeedsItem;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Arshil Khan.
 */

public class DayFeedsActivity extends AppCompatActivity implements View.OnClickListener {


    private List<FeedsItem> feedsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DayFeedsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailyfeeds_screen);
        recyclerView = findViewById(R.id.dailyrecycler_view);

        mAdapter = new DayFeedsAdapter(feedsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        FeedsItem feedsItem = new FeedsItem("#EVENTS", "Health Camp", "Greater Kailash, New Delhi", "13th May 2018 AM Onwords", "Volunateer");
        feedsList.add(feedsItem);

        FeedsItem feedsItem1 = new FeedsItem("#Pool", "Health Camp", "Greater Kailash, New Delhi", "13th May 2018 AM Onwords", "Vote");
        feedsList.add(feedsItem1);

        FeedsItem feedsItem2 = new FeedsItem("#Artical", "Health Camp", "Greater Kailash, New Delhi", "13th May 2018 AM Onwords", "Explore");
        feedsList.add(feedsItem2);

        FeedsItem feedsItem3 = new FeedsItem("#EVENTS", "Health Camp", "Greater Kailash, New Delhi", "13th May 2018 AM Onwords", "Vote");
        feedsList.add(feedsItem3);

        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

    }


}
