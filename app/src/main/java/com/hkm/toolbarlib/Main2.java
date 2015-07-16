package com.hkm.toolbarlib;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.hkm.advancedtoolbar.V3.SearchCustom;
import com.hkm.advancedtoolbar.V3.TopBarManager;

/**
 * Created by hesk on 16/7/15.
 */
public class Main2 extends AppCompatActivity {
    private ActionBar actionbar;
    private TopBarManager worker;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generl);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            worker = TopBarManager.ManagerBulider.with(this)
                    .companyLogo(R.drawable.starz_logo)
                    .searchView(SearchCustom.LAYOUT.classic_3)
                    .searchBarEvents(new TopBarManager.searchBarListener() {
                        @Override
                        public void onKeySearchStartConfirm(String text) {
                            Log.d("start", text);
                        }

                        @Override
                        public void onKeySearchLetter(String text) {
                            Log.d("start", text);
                        }

                        @Override
                        public void onRestoreToNormal(ActionBar toolbar) {
                            worker.showBack();
                        }
                    })

                    .build(toolbar, getSupportActionBar());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem find = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idt = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (idt == R.id.action_search) {
            worker.triggerfromSearchIcon(item);
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
