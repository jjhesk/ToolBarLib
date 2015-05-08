package com.hkm.advancedtoolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * Created by hesk on 8/5/15.
 */
public class advBar extends Toolbar {
    public advBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public advBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public advBar(Context context) {
        super(context);
    }


}
