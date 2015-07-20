package com.hkm.advancedtoolbar;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by hesk on 8/5/15.
 */
public class SearchViewFix extends SearchView {
    protected LinearLayout mSearchEditFrame;

    public SearchViewFix(Context context) {
        super(context);
        init();
    }

    public SearchViewFix(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchViewFix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void setMargin(View tv) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tv.getLayoutParams();
        params.topMargin = -10;
        params.leftMargin = -10;
        params.rightMargin = -10;
        params.bottomMargin = -10;
        // tv.setPadding(0, 0, 0, 0);
        tv.setLayoutParams(params);
    }

    private void init() {
       /* mSearchEditFrame = (LinearLayout) findViewById(R.id.search_edit_frame);
        setMargin(mSearchEditFrame);
        mSearchEditFrame.requestLayout();*/
    }
}
