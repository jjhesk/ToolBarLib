package com.hkm.toolbarlib.templates.searchactionbar;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


/**
 * Created by hesk on 27/4/15.
 */
public class acationbardefault<MaterdrawerActivity extends AppCompatActivity> implements View.OnClickListener, SearchView.OnCloseListener, SearchView.OnQueryTextListener {
    private SearchView searchView;
    private MaterdrawerActivity mmainActivity;
    private String TAG = "search_tag";

    public acationbardefault(MenuItem findelement, MaterdrawerActivity mainActivity) {
        if (findelement != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(findelement);
            searchView.setOnQueryTextListener(this);
            searchView.setOnSearchClickListener(this);
            searchView.setOnCloseListener(this);
            mmainActivity = mainActivity;
          /*

            int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
            // Getting the 'search_plate' LinearLayout.
            View searchPlate = searchView.findViewById(searchPlateId);
            // Setting background of 'search_plate' to earlier defined drawable.
            searchPlate.setBackgroundResource(R.drawable.textfield_searchview_holo_light);


          */

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
            Log.d("log", "please enter your query for the search");
        } else {
            final CharSequence keywords = searchView.getQuery();
/**
 * work on your own query api exchange in here
 */
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
