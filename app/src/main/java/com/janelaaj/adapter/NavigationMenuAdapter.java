package com.janelaaj.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.janelaaj.R;
import com.janelaaj.activitys.DashboardActivity;
import com.janelaaj.utilities.FontManager;

import java.util.HashSet;
import java.util.List;


/**
 * Created by Neeraj on 4/6/2017.
 */
public class NavigationMenuAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<String> menuList = null;
    HashSet<Integer> selectedPosition = new HashSet<>();
    Typeface materialdesignicons_font, roboto_regular, gomedii;

    public NavigationMenuAdapter(Context context,
                                 List<String> menuList) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.menuList = menuList;
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
        this.roboto_regular = FontManager.getFontTypeface(mContext, "fonts/roboto.regular.ttf");
    }

    @Override
    public int getCount() {
        if (menuList != null) {
            return menuList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.menu_item, null);
            holder.menuTitel = (TextView) convertView.findViewById(R.id.menuTitel);
            holder.menuItemLayout = (LinearLayout) convertView.findViewById(R.id.menuItemLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.menuTitel.setTag(position);
        holder.menuTitel.setText(menuList.get(position).toString());

//----------fill selected value------
        if (selectedPosition.contains(position)) {
            holder.menuItemLayout.setBackgroundColor(Color.parseColor("#007FD2"));
            holder.menuTitel.setTextColor(Color.WHITE);
        } else {
            holder.menuItemLayout.setBackgroundColor(Color.WHITE);
            holder.menuTitel.setTextColor(Color.parseColor("#212121"));
        }
      /*  holder.menuTitel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = (int) v.getTag();
                if (selectedPosition.contains(pos)) {
                    //selectedPosition.remove(pos);
                    //selectedPosition.clear();
                    notifyDataSetChanged();
                } else {
                    selectedPosition.clear();
                    selectedPosition.add(pos);
                    notifyDataSetChanged();
                }

                DashboardActivity rootActivity = (DashboardActivity) mContext;
                rootActivity.closeDrawer();
            }
        });
*/
        return convertView;
    }


    public void navigateToFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_right);
        transaction.replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }


    public class ViewHolder {
        public TextView menuTitel;
        public LinearLayout menuItemLayout;
    }

}
