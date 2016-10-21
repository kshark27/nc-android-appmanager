/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;

import com.nhancv.appmanager.MainActivity;
import com.nhancv.appmanager.activities.BaseActivity;
import com.nhancv.appmanager.listeners.OnBackPressedListener;
import com.nhancv.appmanager.listeners.OnSectionAttachedListener;
import com.nhancv.appmanager.utils.AppHelper;


public abstract class AttachFragment extends Fragment implements OnBackPressedListener {

    /**
     * @return The fragment id
     */
    protected int getFragmentId() {
        return -1;
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnSectionAttachedListener) {
            ((OnSectionAttachedListener) activity).onSectionAttached(getFragmentId());
        }
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        final Activity activity = getActivity();
        if (!AppHelper.preventOnResume && activity instanceof MainActivity) {
            ((MainActivity) activity).setFragment(this);
        }
        checkMenuItem();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (MainActivity.sDisableFragmentAnimations) {
            final Animation a = new Animation() {
            };
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Nullable
    public final BaseActivity getBaseActivity() {
        final Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        }
        return null;
    }

    private void checkMenuItem() {
        final int menuItemId = getFragmentId();
        if (menuItemId != -1) {
            final BaseActivity activity = getBaseActivity();
            if (activity != null) {
                activity.checkMenuItem(menuItemId);
            }
        }
    }

}
