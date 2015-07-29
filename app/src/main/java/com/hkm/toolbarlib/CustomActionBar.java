package com.hkm.toolbarlib;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hkm.advancedtoolbar.Util.advBar;
import com.hkm.advancedtoolbar.iOS.SearchCustomActionBar;
import com.hkm.advancedtoolbar.iOS.iOSActionBarWorker;
import com.hkm.advancedtoolbar.iOS.trigger;
import com.hkm.toolbarlib.templates.searchactionbar.actionSupportForAppCompatActivity;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;

/**
 * Created by hesk on 12/5/15.
 */
public class CustomActionBar extends AppCompatActivity {

    private advBar toolbar;
    private iOSActionBarWorker worker;
    private Menu menu;

    private static final int SAMPLE2_ID = 34535;
    private boolean isCustomLayout = true;

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
      //  new ActionItemBadgeAdder().act(this).menu(menu).title("xttxtx").itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(ActionItemBadge.BadgeStyles.GREY_LARGE, 1);

        return true;
    }

    private int badgeCount = 100;

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
        if (item.getItemId() == R.id.item_samplebadge) {
            Toast.makeText(this, "update now", Toast.LENGTH_SHORT).show();
            badgeCount--;
            invalidateOptionsMenu();
            return true;
        } else if (item.getItemId() == SAMPLE2_ID) {
            Toast.makeText(this, "ID 2", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
