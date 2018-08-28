package com.janelaaj.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.janelaaj.fragment.RegistrationFirstpageFragment;
import com.janelaaj.fragment.RegistrationSecondPageFragment;
import com.janelaaj.fragment.RegistrationSecondPageFragmentOfVitals;

/**
 *
 * @author Arshil Khan.
 */

public class RegistrationPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfPage = 2;
    String email;
    String PersonName;
    String Role;
    String namefetch,dobfetch,genderfetch,emailfetch,passwordfetch,flagfetch;

    public RegistrationPagerAdapter(FragmentManager fm, String email,String PersonName,String Role,String flagfetch,String namefetch,String dobfetch,String emailfetch,String passwordfetch,String genderfetch) {
        super(fm);
        this.email=email;
        this.PersonName=PersonName;
        this.Role = Role;
        this.namefetch = namefetch;
        this.dobfetch = dobfetch;
        this.genderfetch = genderfetch;
        this.emailfetch = emailfetch;
        this.passwordfetch = passwordfetch;
        this.flagfetch = flagfetch;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Log.i("sadafsf","getitem  adapter"+ namefetch);
                Log.i("sadafsf","getitem  adapter"+ dobfetch);
                Log.i("sadafsf","getitem  adapter"+ emailfetch);
                Log.i("sadafsf","getitem  adapter"+ passwordfetch);
                Log.i("sadafsf","getitem  adapter"+ flagfetch);
                Log.i("sadafsf","getitem  adapter"+ genderfetch);

                RegistrationFirstpageFragment tab1 = RegistrationFirstpageFragment.newInstance(email, PersonName,namefetch,dobfetch,genderfetch,emailfetch,passwordfetch,flagfetch);
                return tab1;
            case 1:
                RegistrationSecondPageFragmentOfVitals tab2 = RegistrationSecondPageFragmentOfVitals.newInstance("", "");

                RegistrationSecondPageFragment tab3  = RegistrationSecondPageFragment.newInstance("", "");

                if(Role.equals("VIT"))
                {
                    return tab2;
                }
                else
                {
                    return tab3;
                }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfPage;
    }
}
