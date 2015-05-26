package com.hkm.toolbarlib;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hkm.advancedtoolbar.advBar;
import com.hkm.advancedtoolbar.iOS.SearchCustomActionBar;
import com.hkm.advancedtoolbar.iOS.iOSActionBarWorker;
import com.hkm.advancedtoolbar.iOS.trigger;
import com.hkm.toolbarlib.templates.searchactionbar.actionSupportForAppCompatActivity;

/**
 * Created by hesk on 12/5/15.
 */
public class CustomActionBar extends AppCompatActivity {

    private advBar toolbar;
    private iOSActionBarWorker worker;
    private Menu menu;

    private boolean isCustomLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isCustomLayout) {
            setContentView(R.layout.main2);
            toolbar = (advBar) findViewById(R.id.toolbar);
            toolbar.colorize(R.color.grey_1000, this);
            setSupportActionBar(toolbar);

        } else {
            setContentView(R.layout.activity_main);


        }

        worker = new iOSActionBarWorker(getSupportActionBar());
        worker.setSearchLayoutBuiltIn(SearchCustomActionBar.LAYOUT.classic_1);
        worker.setSearchEngineListener(new actionSupportForAppCompatActivity(this));
    }


    /**
     * menu items control
     * copy these into the activity
     */

    protected void enableMenu(boolean enable) {
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(enable);
        // invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem find = menu.findItem(R.id.action_search);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            worker.showSearchBar(new trigger() {
                @Override
                public void before(ActionBar ab) {
                    enableMenu(false);

                }
            });
            return true;
        }


        //noinspection SimplifiableIfStatement
        // if (id == R.id.toggle_actionbar) {

        //     if (actionbar.isShowing()) actionbar.hide();
        //     else actionbar.show();

        //     return true;
        //  }

        return super.onOptionsItemSelected(item);
    }
}
