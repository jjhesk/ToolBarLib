package com.hkm.advancedtoolbar.Util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.hkm.advancedtoolbar.Util.ToolbarColorizeHelper;

/**
 * Created by hesk on 8/5/15.
 */
public class advBar extends Toolbar {
    private final Context contx;

    public advBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        contx = context;
        init();

    }

    public advBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        contx = context;
        init();
    }

    public advBar(Context context) {
        super(context);
        contx = context;
        init();
    }

    private void init() {

    }

    public void colorize(int colorResId, Activity actio) {
        ToolbarColorizeHelper.colorizeToolbar(this, colorResId, actio);
    }
}
