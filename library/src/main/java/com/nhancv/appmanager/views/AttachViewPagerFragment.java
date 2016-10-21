/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.nhancv.appmanager.R;

import java.util.ArrayList;

public abstract class AttachViewPagerFragment extends AttachFragment {
    private ViewPager mViewPager;

    @Override
    protected int getFragmentId() {
        return 0;
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        final ViewPagerAdapter adapter = getPagerAdapter();
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);

        final TabLayout tabHost = (TabLayout) view.findViewById(R.id.tabHost);
        tabHost.setupWithViewPager(mViewPager);

        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    public abstract ViewPagerAdapter getPagerAdapter();

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragments;
        private final ArrayList<CharSequence> titles;

        public ViewPagerAdapter(final FragmentManager fm, final ArrayList<Fragment> fragments,
                                final ArrayList<CharSequence> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(final int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

}
