package com.hkm.advancedtoolbar.Util;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hkm.advancedtoolbar.iOS.SearchCustomActionBar;


/**
 * Created by hesk on 15/5/15.
 */
public class actionSupportForActionBarActivity<MaterdrawerActivity extends ActionBarActivity
        > implements SearchCustomActionBar.OnSearchListener {
    /**
     * search engine start part
     */
    protected SearchCustomActionBar searchView;
    protected MaterdrawerActivity mmainActivity;
    protected String TAG = "search_tag";

    public actionSupportForActionBarActivity(MaterdrawerActivity mainActivity) {
        mmainActivity = mainActivity;
    }

    protected MaterdrawerActivity getActivty() {
        return mmainActivity;
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
     * @param componenet the searchview iOSSearchActionBar
     * @param query      the string in chars of the search query
     */
    @Override
    public void onSearchClick(SearchCustomActionBar componenet, CharSequence query) {
        if (query.toString().equalsIgnoreCase("")) {
            Log.d("log", "please enter your query for the search");
        } else {
            /**
             * work on your own query api exchange in here
             */
            onSearch(componenet, query);
        }
    }

    protected void onSearch(SearchCustomActionBar searchview, CharSequence query) {


    }
}
