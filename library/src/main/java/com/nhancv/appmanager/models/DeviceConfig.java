/**
 * Created by nhancao on 10/21/16.
 */

package com.nhancv.appmanager.models;

import android.text.TextUtils;

import io.paperdb.Paper;
import io.paperdb.PaperDbException;
import timber.log.Timber;

/**
 * Device configuration which auto serializes itself to a file
 */
public class DeviceConfig {
    public static final int THEME_AUTO = 1;
    public static final int THEME_DAY = 2;
    public static final int THEME_NIGHT = 3;
    private transient static final String NAME = "DeviceConfig";
    private transient static DeviceConfig instance;
    public int themeMode = THEME_NIGHT;
    public String suShellContext = "normal"; //Shell.CONTEXT_NORMAL;
    public int appVersion;

    private DeviceConfig() {
        ensureDefaults();
    }

    public static DeviceConfig get() {
        if (instance == null) {
            final DeviceConfig config = new DeviceConfig();
            try {
                instance = Paper.book().read(NAME, config);
            } catch (PaperDbException pde) {
                instance = config;
                Timber.e(pde, "Could not read %s", NAME);
            }
        }
        return instance;
    }

    public DeviceConfig save() {
        try {
            Paper.book().write(NAME, DeviceConfig.this);
        } catch (PaperDbException pde) {
            Timber.e(pde, "Could not write %s", NAME);
        }
        return this;
    }

    private void ensureDefaults() {
        if (TextUtils.isEmpty(suShellContext)) {
            suShellContext = "normal";//Shell.CONTEXT_NORMAL;
        }
    }

}
