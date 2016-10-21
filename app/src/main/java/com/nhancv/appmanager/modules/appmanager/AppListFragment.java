/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.modules.appmanager;

import android.support.v4.app.Fragment;

import com.nhancv.appmanager.R;
import com.nhancv.appmanager.views.AttachViewPagerFragment;

import java.util.ArrayList;

public class AppListFragment extends AttachViewPagerFragment {

    @Override
    protected int getFragmentId() {
        return AppListFragment.class.hashCode();
    }

    @Override
    public ViewPagerAdapter getPagerAdapter() {
        final ArrayList<Fragment> fragments = new ArrayList<>(3);
        final ArrayList<CharSequence> titles = new ArrayList<>(3);

        fragments.add(new UserAppListFragment());
        titles.add(getString(R.string.user));

        fragments.add(new SystemAppListFragment());
        titles.add(getString(R.string.system));

        fragments.add(new DisabledAppListFragment());
        titles.add(getString(R.string.disabled));

        return new ViewPagerAdapter(getChildFragmentManager(), fragments, titles);
    }

}
