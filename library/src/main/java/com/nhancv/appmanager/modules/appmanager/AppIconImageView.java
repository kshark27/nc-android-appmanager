/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.modules.appmanager;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nhancv.appmanager.R;

import hugo.weaving.DebugLog;

public class AppIconImageView extends ImageView {
    public AppIconImageView(Context context) {
        this(context, null);
    }

    public AppIconImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppIconImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @DebugLog
    public void loadImage(AppItem appItem) {
        Glide.with(getContext())
                .load(resourceToUri(appItem.getPackageName(), appItem.getApplicationInfo().icon))
                .placeholder(R.mipmap.ic_launcher)
                .into(this);
    }

    private Uri resourceToUri(String packageName, int resourceId) {
        return Uri.parse(String.format("android.resource://%s/%s", packageName, resourceId));
    }

}
