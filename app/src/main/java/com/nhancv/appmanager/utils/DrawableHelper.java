/**
 * Created by nhancao on 10/21/16.
 */
package com.nhancv.appmanager.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.nhancv.appmanager.App;
import com.nhancv.appmanager.models.AppResources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import timber.log.Timber;

/**
 * Helps with ddddddrawwabllessr5hhwr5hbwb
 */
public class DrawableHelper {

    @Nullable
    public static Drawable applyColorFilter(Drawable drawable, @ColorInt int color) {
        if (drawable == null) {
            Timber.w("drawable is null!");
            return null;
        }
        final LightingColorFilter lightingColorFilter = new LightingColorFilter(Color.BLACK, color);
        drawable.setColorFilter(lightingColorFilter);
        return drawable;
    }

    @Nullable
    public static Drawable applyAccentColorFilter(@DrawableRes int drawableRes) {
        Drawable drawable = getDrawable(drawableRes);
        return applyColorFilter(drawable, AppResources.get().getAccentColor());
    }

    @Nullable
    public static Drawable applyAccentColorFilter(Drawable drawable) {
        return applyColorFilter(drawable, AppResources.get().getAccentColor());
    }

    @Nullable
    public static Drawable getDrawable(@DrawableRes int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return App.get().getDrawable(drawableRes);
        }

        //noinspection deprecation
        return App.get().getResources().getDrawable(drawableRes);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        final Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static InputStream bitmapToInputStream(Bitmap bitmap) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return new ByteArrayInputStream(bos.toByteArray());
    }

}
