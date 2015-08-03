package com.hkm.advancedtoolbar;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.V3.LayoutAsset;

/**
 * Created by hesk on 17/7/15.
 */
public class ToolbarHelper {
    public static View generateView(final @LayoutRes int layout, Context ctx) {
        return LayoutInflater.from(ctx).inflate(layout, null);
    }

    public static View renewView(Context activity, Toolbar toolbar, LayoutAsset layoutId) {
        toolbar.removeAllViews();
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP;
        final View t = ToolbarHelper.generateView(layoutId.getResourceId(), activity);
        toolbar.addView(t, layoutParams);
        return t;
    }

    public static int getDP(final int f, Resources res) {
        final int h = (int) res.getDisplayMetrics().density * f;
        return h;
    }

    public static RelativeLayout.LayoutParams changeTopMargin(TextView layout, final int margin_offset) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.topMargin = margin_offset;
        layout.setLayoutParams(params);
        return params;
    }

    public static RelativeLayout.LayoutParams changeBottomMargin(TextView layout, final int margin_offset) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.bottomMargin = margin_offset;
        layout.setLayoutParams(params);
        return params;
    }

    public static RelativeLayout.LayoutParams changeLeftMargin(TextView layout, final int margin_offset) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.leftMargin = margin_offset;
        layout.setLayoutParams(params);
        return params;
    }

    public static RelativeLayout.LayoutParams changeRightMargin(TextView layout, final int margin_offset) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        params.rightMargin = margin_offset;
        layout.setLayoutParams(params);
        return params;
    }


}
