package com.hkm.advancedtoolbar.iOS;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 14/5/15.
 */
public class ActionBarBuilder<ACTIONBARV7 extends ActionBar, TV extends TextView> {
    protected final ACTIONBARV7 ab;
    protected int searchlayoutRef, titlelayoutRef, companylogoRef;
    protected ActionBarActionListener listener;
    protected String actionbartitle;
    protected SearchCustomActionBar.OnSearchListener searchlistener;

    protected enum exstatus {
        LOGO, SEARCH, TITLECUSTOM, NONE;
    }

    protected exstatus statusnow = exstatus.NONE;
    protected exstatus backup = exstatus.NONE;
    protected exstatus defaultstatus = exstatus.NONE;

    protected Context ctx;

    public ActionBarBuilder(ACTIONBARV7 actionb) {
        ab = actionb;
        ctx = actionb.getThemedContext();
        getThemedSettings();
    }

    protected TypedArray theme_SearchBarStyle;


    /**
     * get hint color
     */
    //int resId = values.getResourceId(R.styleable.SearchBarStyle_search_bar_hint_color, -1);
    private void getThemedSettings() {
        /**
         * theme location
         */
        Resources.Theme theme = ctx.getTheme();
        TypedValue typedValue = new TypedValue();

        theme.resolveAttribute(R.attr.iOSSearchBarStyle, typedValue, true);
        theme_SearchBarStyle = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.SearchBarStyle);

        theme.resolveAttribute(R.attr.iOSActionBarCompanyLogo, typedValue, true);
        if (typedValue.data != 0) {
            companylogoRef = typedValue.data;
        } else companylogoRef = R.drawable.ab_logo;

        theme.resolveAttribute(R.attr.iOSSearchBarLayout, typedValue, true);
        if (typedValue.data != 0) {
            searchlayoutRef = typedValue.data;
        } else searchlayoutRef = R.layout.material_search_ios;

        theme.resolveAttribute(R.attr.iOSCustomBarTitleLayout, typedValue, true);
        if (typedValue.data != 0) {
            titlelayoutRef = typedValue.data;
        } else titlelayoutRef = R.layout.centertextview;
    }

    /**
     * setup the search engine listener this is the big part to be imported
     *
     * @param ls the listener for the search engine
     * @return the build object
     */
    public ActionBarBuilder setSearchEngineListener(SearchCustomActionBar.OnSearchListener ls) {
        searchlistener = ls;
        return this;
    }

    /**
     * this will only allow the arranged layout items to to be customized search action bar
     *
     * @param SearchLayoutResId the arranged layout resource id
     * @return this is the chain setup
     */
    public ActionBarBuilder setSearchLayoutArrangement(int SearchLayoutResId) {
        searchlayoutRef = SearchLayoutResId;
        return this;
    }

    /**
     * this will only allow the arranged layout items to to be customized title action bar
     *
     * @param titleLayoutResId the arranged layout resource id
     * @return this is the chain setup
     */
    public ActionBarBuilder setTitleLayoutArrangement(int titleLayoutResId) {
        titlelayoutRef = titleLayoutResId;
        return this;
    }

    /**
     * install the listener setup for the change actions in the actionbar
     *
     * @param ls the event listener
     * @return this is the chain setup
     */
    public ActionBarBuilder setActionListener(ActionBarActionListener ls) {
        listener = ls;
        return this;
    }

    /**
     * the company logo to be shown on the action bar
     *
     * @param company_logo_ref the resource id of the drawable
     * @return this is the chain setup
     */
    public ActionBarBuilder setCompanyLogo(int company_logo_ref) {
        companylogoRef = company_logo_ref;
        return this;
    }


}
