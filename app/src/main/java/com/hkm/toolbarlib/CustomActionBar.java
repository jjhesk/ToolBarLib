package com.hkm.toolbarlib;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hkm.advancedtoolbar.advBar;

/**
 * Created by hesk on 12/5/15.
 */
public class CustomActionBar extends AppCompatActivity {

    private advBar toolbar;
    private ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        toolbar = (advBar) findViewById(R.id.toolbar);
        toolbar.colorize(R.color.red_300, this);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem find = menu.findItem(R.id.action_search);
        new SearchViewImple(find, this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //    return true;
        //  }
        //noinspection SimplifiableIfStatement
        // if (id == R.id.toggle_actionbar) {

        //     if (actionbar.isShowing()) actionbar.hide();
        //     else actionbar.show();

        //     return true;
        //  }

        return super.onOptionsItemSelected(item);
    }
}
