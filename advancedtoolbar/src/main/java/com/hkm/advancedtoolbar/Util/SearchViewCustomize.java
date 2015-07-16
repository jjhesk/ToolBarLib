package com.hkm.advancedtoolbar.Util;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by hesk on 8/5/15.
 */
public class SearchViewCustomize<T extends SearchView> {
    public SearchViewCustomize() {

    }
    private void setMargin(LinearLayout tv) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        params.topMargin = -10;
        params.leftMargin = -10;
        params.rightMargin = -10;
        params.bottomMargin = -10;
        // tv.setPadding(0, 0, 0, 0);
        tv.setLayoutParams(params);
    }

    public void fixlayout(T searchview) {
        int searchViewId = searchview.getContext().getResources().getIdentifier("android:id/search_edit_frame", null, null);
        LinearLayout editview = (LinearLayout) searchview.findViewById(searchViewId);
        setMargin(editview);
        editview.requestLayout();
    }
}
