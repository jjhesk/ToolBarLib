package com.hkm.advancedtoolbar.iOS;

import android.support.v7.app.ActionBar;
import android.widget.TextView;

/**
 * Created by hesk on 14/5/15.
 */

@Deprecated public interface ActionBarActionListener<ACTIONBAR extends ActionBar, TV extends TextView> {
    /**
     * a quick callback method for showing up the company logo
     *
     * @param ab the main action bar
     */
    void onShowCompanyLogoCenter(ACTIONBAR ab);

    /**
     * a quick callback method for retoring from the deep action bar
     *
     * @param ab the main action bar
     */
    void onShowRestoreNormalActionBar(ACTIONBAR ab);

    /**
     * the quick action bar callback from revealing the search bar
     *
     * @param ab the main action bar
     */
    @Deprecated
    void onShowSearchActionBarAfter(ACTIONBAR ab);

    void onShowSearchActionBarAfter(ACTIONBAR ab, SearchCustomActionBar bar);

    /**
     * to show up the custom text actionbar on showing up the custom text action bar
     *
     * @param ab    the main action bar
     * @param title the given title on the actionbar
     */
    void onShowCenterTextActionBar(ACTIONBAR ab, String title);

    /**
     * to callback method of closing the search action bar
     *
     * @param ab the main action bar
     */
    void onCloseSearchActionBar(ACTIONBAR ab);


}
