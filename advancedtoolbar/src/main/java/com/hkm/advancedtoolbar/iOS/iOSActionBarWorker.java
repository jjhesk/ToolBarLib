package com.hkm.advancedtoolbar.iOS;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;

/**
 * hkm invention 2015
 * Created by hesk on 14/5/15.
 */
public class iOSActionBarWorker<ABv7 extends ActionBar, TV extends TextView> extends ActionBarBuilder {

    /**
     * the start of the iOS action bar is in here
     *
     * @param actionb the support action bar
     */
    public iOSActionBarWorker(ABv7 actionb) {
        super(actionb);
    }


    /**
     * install the listener setup for the change actions in the actionbar
     *
     * @param actionb the support action bar
     * @param ls      the event listener
     */
    public iOSActionBarWorker(ABv7 actionb, ActionBarActionListener ls) {
        super(actionb);
        listener = ls;
    }

    /**
     * show the centerized or customized title action bar
     *
     * @param title the title in string
     */
    @SuppressLint("WrongViewCast")
    public void showCenterizedTitleActionBar(String title) {
        statusnow = exstatus.TITLECUSTOM;
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(titlelayoutRef);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayUseLogoEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        final TV text = (TV) ab.getCustomView().findViewById(R.id.title_display);
        text.setText(title);
        actionbartitle = title;
        if (listener != null) {
            listener.onShowCenterTextActionBar(ab, title);
        }
    }

    /**
     * show and display the company logo only
     */
    public void showCompanyLogo() {
        statusnow = exstatus.LOGO;
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        ab.setDisplayShowTitleEnabled(false);
        ab.setLogo(companylogoRef);
        ab.setDisplayUseLogoEnabled(true);
        if (listener != null) {
            listener.onShowCompanyLogoCenter(ab);
        }
    }

    /**
     * show and display the search action bar only
     */
    public void showiosSearchActionBar() {
        backup = statusnow;
        statusnow = exstatus.SEARCH;
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);

        if (searchlayoutRef > 0)
            ab.setCustomView(searchlayoutRef);

        SearchCustomActionBar control = new SearchCustomActionBar(this, 1000);
        control.setOnSearchListener(searchlistener);
        if (listener != null) {
            listener.onShowSearchActionBarAfter(ab, control);
        }
    }

    /**
     * this is triggered by the internal action;
     */
    public void afterSearchBarShown(SearchCustomActionBar com) {
        com.showkeyboard();
    }

    public void showSearchBar(trigger done) {
        done.before(ab);
        showiosSearchActionBar();

    }

    public void showCompanyLogo(trigger done) {
        done.before(ab);
        showCompanyLogo();
    }

    /**
     * show and display to the normal bar only
     */
    public void restoreToNormal() {
        showCenterizedTitleActionBar(actionbartitle);
        if (listener != null) {
            listener.onShowRestoreNormalActionBar(ab);
        }
    }

    /**
     * close the display from the search bar
     */
    public void closeSearchBar() {
        routeTo(backup);
        if (listener != null) {
            listener.onCloseSearchActionBar(ab);
        }
    }

    private void routeTo(exstatus symbol) {
        switch (symbol) {
            case SEARCH:
                showiosSearchActionBar();
                break;
            case TITLECUSTOM:
                showCenterizedTitleActionBar(actionbartitle);
                break;
            case LOGO:
                showCompanyLogo();
                break;
            default:
                restoreToNormal();
                break;
        }
    }

}
