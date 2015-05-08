package com.hkm.toolbarlib;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;

import com.hkm.advancedtoolbar.SearchViewCustomize;
import com.hkm.advancedtoolbar.SearchViewFix;

/**
 * Created by hesk on 27/4/15.
 */
public class SearchViewImple implements View.OnClickListener, SearchView.OnCloseListener, SearchView.OnQueryTextListener {
    private SearchViewFix searchView;
    private MainActivity mmainActivity;
    private String TAG = "search_tag";

    public SearchViewImple(MenuItem findelement, MainActivity mainActivity) {
        if (findelement != null) {
            SearchViewCustomize m = new SearchViewCustomize<SearchViewFix>();
            searchView = (SearchViewFix) MenuItemCompat.getActionView(findelement);
            searchView.setOnQueryTextListener(this);
            searchView.setOnSearchClickListener(this);
            searchView.setOnCloseListener(this);
            mmainActivity = mainActivity;

            m.fixlayout(searchView);
          /*  int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            // Getting the 'search_plate' LinearLayout.
            View searchPlate = searchView.findViewById(searchPlateId);
            // Setting background of 'search_plate' to earlier defined drawable.
            searchPlate.setBackgroundResource(R.drawable.textfield_searchview_holo_light);*/

        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {


    }

    @Override
    public boolean onClose() {
        //mmainActivity.getFragmentManager().beginTransaction().remove().
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (searchView.getQuery().toString().equalsIgnoreCase("")) {
            // Tool.trace(mmainActivity, "please enter your query for the search");
        } else {
            final CharSequence keywords = searchView.getQuery();

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
