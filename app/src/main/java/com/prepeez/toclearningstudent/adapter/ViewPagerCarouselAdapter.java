package com.prepeez.toclearningstudent.adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prepeez.toclearningstudent.fragment.ViewPagerCarouselFragment;

/**
 * Created by Nana on 11/11/2017.
 */

public class ViewPagerCarouselAdapter extends FragmentStatePagerAdapter {
    private int[] imageResourceIds;

    public ViewPagerCarouselAdapter(android.support.v4.app.FragmentManager fm, int[] imageResourceIds) {
        super(fm);
        this.imageResourceIds = imageResourceIds;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerCarouselFragment.IMAGE_RESOURCE_ID, imageResourceIds[position]);
        ViewPagerCarouselFragment frag = new ViewPagerCarouselFragment();
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public int getCount() {
        return (imageResourceIds == null) ? 0: imageResourceIds.length;
    }

}