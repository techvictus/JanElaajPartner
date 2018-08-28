package com.janelaaj.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.janelaaj.R;
import com.janelaaj.model.FeedsItem;

import java.util.List;


/**
 *
 * @author Arshil Khan.
 */

public class DayFeedsAdapter extends RecyclerView.Adapter<DayFeedsAdapter.MyViewHolder> {

    private List<FeedsItem> daysFeedsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTextView, nameTextView, addressTextView, dateTextView;
        Button eventButton;
        LinearLayout feedsLayout;

        public MyViewHolder(View view) {
            super(view);
            eventTextView = view.findViewById(R.id.eventTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
            addressTextView = view.findViewById(R.id.addressTextView);
            dateTextView = view.findViewById(R.id.dateTextView);
            eventButton = view.findViewById(R.id.eventButton);
            feedsLayout = view.findViewById(R.id.feedsLayout);
        }
    }


    public DayFeedsAdapter(List<FeedsItem> daysFeedsList) {
        this.daysFeedsList = daysFeedsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_feeds_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedsItem feedsItem = daysFeedsList.get(position);
        holder.eventTextView.setText(feedsItem.getEventText());
        holder.nameTextView.setText(feedsItem.getNameText());
        holder.addressTextView.setText(feedsItem.getAddressText());
        holder.dateTextView.setText(feedsItem.getDateText());
        holder.eventButton.setText(feedsItem.getEventButtonText());

        if(feedsItem.getEventButtonText().equalsIgnoreCase("vote")){
            holder.feedsLayout.setBackgroundColor(Color.parseColor("#3b5998"));
        }
    }

    @Override
    public int getItemCount() {
        return daysFeedsList.size();
    }
}