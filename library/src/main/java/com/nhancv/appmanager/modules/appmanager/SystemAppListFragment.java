/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.modules.appmanager;

import android.content.pm.ApplicationInfo;

public class SystemAppListFragment extends BaseAppListFragment {

    @Override
    protected boolean isFiltered(ApplicationInfo applicationInfo) {
        return !AppItem.isSystemApp(applicationInfo);
    }

}
