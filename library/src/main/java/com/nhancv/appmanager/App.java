/**
 * Created by nhancao on 10/21/16.
 */

package com.nhancv.appmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatDelegate;

import com.nhancv.appmanager.models.DeviceConfig;
import com.nhancv.appmanager.models.DeviceConstants;

import java.io.File;

import at.amartinz.execution.ShellManager;
import io.paperdb.Paper;
import timber.log.Timber;

public class App extends android.app.Application {
    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static final int APP_VERSION = 1;

    private static App sInstance;
    private static boolean enableDebug = BuildConfig.DEBUG;

    public static App get() {
        return App.sInstance;
    }

    public static App get(Context context) {
        return ((App) context.getApplicationContext());
    }

    public static void setupThemeMode() {
        final DeviceConfig deviceConfig = DeviceConfig.get();
        switch (deviceConfig.themeMode) {
            case DeviceConfig.THEME_AUTO: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            }
            case DeviceConfig.THEME_DAY: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            }
            default:
            case DeviceConfig.THEME_NIGHT: {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (App.sInstance != null) {
            return;
        }
        App.sInstance = this;

        ShellManager.enableDebug(App.enableDebug);

        Paper.init(this);
        setupThemeMode();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                setupEverythingAsync();
            }
        });
    }

    @WorkerThread
    private void setupEverythingAsync() {
        Timber.d("Enable debug: %s", App.enableDebug);

        final String basePath = getFilesDirectory();
        final String[] dirList = new String[]{basePath + DeviceConstants.DC_LOG_DIR};
        for (final String s : dirList) {
            final File dir = new File(s);
            if (!dir.exists()) {
                Timber.v("setupDirectories: creating %s -> %s", s, dir.mkdirs());
            }
        }

        handleAppUpgrades();
    }

    private void handleAppUpgrades() {
        boolean needsUpgrade = false;

        final DeviceConfig deviceConfig = DeviceConfig.get();
        if (deviceConfig.appVersion < 1) {
            needsUpgrade = true;

            final File externalCache = new File(getExternalCacheDir(), "bitmapCache");
            clearDirectory(externalCache);

            final File internalCacheOld = new File(getFilesDir(), "bitmapCache");
            clearDirectory(internalCacheOld);

            final File internalCache = new File(getCacheDir(), "bitmapCache");
            clearDirectory(internalCache);
        }

        if (needsUpgrade) {
            deviceConfig.appVersion = APP_VERSION;
            deviceConfig.save();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void clearDirectory(File directory) {
        if (directory.exists()) {
            final File[] cacheFiles = directory.listFiles();
            if (cacheFiles != null) {
                for (final File cacheFile : cacheFiles) {
                    cacheFile.delete();
                }
            }
            directory.delete();
        }
    }

    @SuppressLint("SdCardPath")
    public String getFilesDirectory() {
        final File tmp = getFilesDir();
        if (tmp != null && tmp.isDirectory()) {
            return tmp.getPath();
        } else {
            return "/data/data/" + getPackageName();
        }
    }

    public String[] getStringArray(final int resId) {
        return getResources().getStringArray(resId);
    }

}
