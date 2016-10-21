/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.modules.preferences;

import android.os.Bundle;

import com.nhancv.appmanager.R;
import com.nhancv.appmanager.BaseActivity;


public class PreferencesActivity extends BaseActivity {
    public static final int REQUEST_PREFERENCES = 10491;

    public static final int RESULT_NEEDS_RESTART = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        setupToolbar();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new MainPreferencesFragment())
                .commit();
    }

    public void needsRestart() {
        setResult(RESULT_NEEDS_RESTART);
    }
}
