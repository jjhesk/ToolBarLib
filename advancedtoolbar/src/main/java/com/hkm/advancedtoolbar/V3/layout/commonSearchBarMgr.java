package com.hkm.advancedtoolbar.V3.layout;

import android.support.v7.app.ActionBar;

/**
 * Created by hesk on 19/8/15.
 */
public interface commonSearchBarMgr {
    void onRestoreToNormal(ActionBar toolbar);

    void onPressSearchButton(ActionBar toolbar);

    void onKeySearchStartConfirm(String text);

    void onKeySearchLetter(String text);
}
