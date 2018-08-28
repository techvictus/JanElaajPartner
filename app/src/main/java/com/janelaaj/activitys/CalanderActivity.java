package com.janelaaj.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.janelaaj.R;

/**
 * Created On 18-05-2018
 *
 * @author Arshil Khan.
 */

public class CalanderActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);
        iniView();


    }


    public void iniView() {


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.discountcard_view) {

        } else if (v.getId() == R.id.lateCardView) {

        } else if (v.getId() == R.id.timeoffCardView) {

        }
    }


}
