package com.hkm.toolbarlib.templates.searchactionbar;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.hkm.advancedtoolbar.iOS.SearchCustomActionBar;


/**
 * Created by hesk on 15/5/15.
 */
public class actionSupportForAppCompatActivity<MaterdrawerActivity extends AppCompatActivity
        > implements SearchCustomActionBar.OnSearchListener {
    /**
     * search engine start part
     */
    private SearchCustomActionBar searchView;
    private MaterdrawerActivity mmainActivity;
    private String TAG = "search_tag";

    public actionSupportForAppCompatActivity(MaterdrawerActivity mainActivity) {
        mmainActivity = mainActivity;
    }

    /**
     * This method is called every time the EditText change it content
     *
     * @param searchview the searchview  iOSSearchActionBar
     * @param constraint the current input data
     */
    @Override
    public void onQuickSearch(SearchCustomActionBar searchview, CharSequence constraint) {

    }

    /**
     * This method is called when the user press the search button on the keyboard
     *
     * @param searchview the searchview iOSSearchActionBar
     * @param constraint the current input data
     */
    @Override
    public void onSearchHint(SearchCustomActionBar searchview, CharSequence constraint) {

    }

    /**
     * This method is called when the click is pressed
     */
    @Override
    public void onClose() {
        mmainActivity.invalidateOptionsMenu();
    }

    /**
     * This is called when the search button is clicked
     *
     * @param searchview the searchview iOSSearchActionBar
     * @param query      the string in chars of the search query
     */
    @Override
    public void onSearchClick(SearchCustomActionBar searchview, CharSequence query) {
        if (query.toString().equalsIgnoreCase("")) {
            Log.d("log", "please enter your query for the search");
        } else {
            final CharSequence keywords = query;
/**
 * work on your own query api exchange in here
 */
        }
    }
}
