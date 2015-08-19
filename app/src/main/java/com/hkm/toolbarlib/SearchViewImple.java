package com.hkm.toolbarlib;

import android.app.ActionBar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.hkm.advancedtoolbar.Util.SearchActionBar;

/**
 * Created by hesk on 27/4/15.
 */
public class SearchViewImple<TAppcompat extends AppCompatActivity> implements SearchActionBar.OnSearchListener {
    private SearchActionBar searchView;
    private iOSActionBarWorkerExample mmainActivity;
    private String TAG = "search_tag";

    public SearchViewImple(MenuItem findelement, TAppcompat mainActivity) {
        if (findelement != null) {
            searchView = (SearchActionBar) MenuItemCompat.getActionView(findelement);
            if (searchView != null) {
                searchView.setOnSearchListener(this);
                searchView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.RIGHT));
                searchView.requestLayout();
            }
        }

    }


    /**
     * This method is called every time the EditText change it content
     *
     * @param searchview the searchview
     * @param constraint the current input data
     */
    @Override
    public void onSearch(SearchActionBar searchview, CharSequence constraint) {

    }

    /**
     * This method is called when the user press the search button on the keyboard
     *
     * @param searchview the searchview
     * @param constraint the current input data
     */
    @Override
    public void onSearchHint(SearchActionBar searchview, CharSequence constraint) {

    }

    /**
     * This method is called when the click is pressed
     */
    @Override
    public void onClose() {

    }

    /**
     * This is called when the search button is clicked
     *
     * @param searchview the searchview
     * @param query      the string in chars of the search query
     */
    @Override
    public void onSearchClick(SearchActionBar searchview, CharSequence query) {

    }
}
