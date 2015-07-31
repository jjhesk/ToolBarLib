package com.hkm.toolbarlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hkm.advancedtoolbar.V3.LayoutAsset;
import com.hkm.advancedtoolbar.V3.LiveIcon;
import com.hkm.advancedtoolbar.V3.TopBarManager;

/**
 * Created by hesk on 31/7/15.
 */
public class Main3 extends AppCompatActivity {
    private ActionBar actionbar;
    private TopBarManager worker;
    private LiveIcon dynamic_icon;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generl);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            worker = TopBarManager.Builder.with(this)
                    .companyLogo(R.drawable.starz_logo)
                    .searchView(LayoutAsset.classic_3)
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
                    .setLiveIcon(R.layout.dynamic_icon_p, R.mipmap.crossmp)
                    .build(toolbar);
            //.externalLayoutOutToolBar(R.layout.topbarlayout)
            dynamic_icon = worker.getDynamicIcon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        dynamic_icon.onOptionItemInit(menu, R.id.dynamic);
        return true;
    }

    private int u = 2;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idt = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (idt == R.id.action_search) {
            // worker.triggerfromSearchIcon(item);
            worker.triggerForCustomExternalCustomView();
            return true;
        } else if (idt == R.id.custombar) {
            Intent g = new Intent(this, CustomActionBar.class);
            startActivity(g);
        } else if (idt == R.id.dynamic) {
            dynamic_icon.update(item, u++);
        }
        return super.onOptionsItemSelected(item);
    }
}
