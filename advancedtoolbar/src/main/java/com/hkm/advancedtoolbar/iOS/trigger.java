package com.hkm.advancedtoolbar.iOS;

import android.support.v7.app.ActionBar;

/**
 * Created by hesk on 26/5/15.
 */
public interface trigger<ACTIONBAR extends ActionBar> {
    /**
     * before showing the tool bar action
     *
     * @param ab The action bar
     */
    void before(ACTIONBAR ab);
}
