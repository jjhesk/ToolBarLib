package com.hkm.advancedtoolbar.V3;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by hesk on 17/7/15.
 */
public class ToolbarHelper {

    public static View generateView(final @LayoutRes int layout, Context ctx) {
        return LayoutInflater.from(ctx).inflate(layout, null);
    }
}
