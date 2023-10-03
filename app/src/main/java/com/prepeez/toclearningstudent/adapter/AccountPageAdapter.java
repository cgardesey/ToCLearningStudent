package com.prepeez.toclearningstudent.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.prepeez.toclearningstudent.fragment.TabFragment1;
import com.prepeez.toclearningstudent.fragment.TabFragment2;
import com.prepeez.toclearningstudent.fragment.TabFragment3;
import com.prepeez.toclearningstudent.fragment.TabFragment4;
import com.prepeez.toclearningstudent.fragment.TabFragment5;
import com.prepeez.toclearningstudent.pojo.User;

public class AccountPageAdapter extends FragmentPagerAdapter {
    User users;

    public AccountPageAdapter(FragmentManager fm, User user) {
        super(fm);
        //this.mNumOfTabs = NumOfTabs;
        this.users = user;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            case 3:
                TabFragment4 tab4 = new TabFragment4();
                return tab4;
            case 4:
                TabFragment5 tab5 = new TabFragment5();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}