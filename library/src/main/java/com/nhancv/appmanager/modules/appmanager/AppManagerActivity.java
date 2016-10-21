/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.modules.appmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.nhancv.appmanager.R;
import com.nhancv.appmanager.BaseActivity;
import com.nhancv.appmanager.models.AppResources;
import com.nhancv.appmanager.modules.preferences.PreferencesActivity;
import com.nhancv.appmanager.utils.Utils;

import at.amartinz.execution.ShellManager;
import timber.log.Timber;


public class AppManagerActivity extends BaseActivity {

    private static long mBackPressed;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);

        // setup action bar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Custom toolbar with back button
     */
    protected void setupHomeBackToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ViewCompat.setElevation(toolbar, 4.0f);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCustomBackPressed();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PreferencesActivity.REQUEST_PREFERENCES == requestCode) {
            if (resultCode == PreferencesActivity.RESULT_NEEDS_RESTART) {
                // restart the activity and cleanup AppResources to update effects and theme
                AppResources.get().cleanup();
                Utils.restartActivity(AppManagerActivity.this);
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Start activity setting
     */
    public void gotoSettingActivity() {
        final Intent intent = new Intent(AppManagerActivity.this, PreferencesActivity.class);
        startActivityForResult(intent, PreferencesActivity.REQUEST_PREFERENCES);
    }


    /**
     * Custom back pressed
     */
    private void onCustomBackPressed() {
        if (mBackPressed + 2000 > System.currentTimeMillis()) {
            if (mToast != null) {
                mToast.cancel();
            }
            finish();
        } else {
            mToast = Toast.makeText(getBaseContext(),
                    getString(R.string.action_press_again), Toast.LENGTH_SHORT);
            mToast.show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void onBackPressed() {
        onCustomBackPressed();
    }

    @Override
    protected void onDestroy() {
        Timber.d("closing shells");
        ShellManager.get().cleanupShells();

        AppResources.get().cleanup();
        super.onDestroy();
    }

}
