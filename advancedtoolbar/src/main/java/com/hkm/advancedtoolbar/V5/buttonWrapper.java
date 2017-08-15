package com.hkm.advancedtoolbar.V5;

/**
 * Created by hesk on 2/3/16.
 */
public class buttonWrapper implements BeastBar.onButtonPressListener {
    /**
     * @param historysize the previous title to be found in the history or otherwise it is nothing
     * @return false for not showing the personalPage logo
     */
    @Override
    public boolean onBackPress(int historysize) {
        return false;
    }

    @Override
    public void onSearchPress() {

    }
}
