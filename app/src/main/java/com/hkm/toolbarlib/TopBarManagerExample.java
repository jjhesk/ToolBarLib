package com.hkm.toolbarlib;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hkm.advancedtoolbar.V3.LayoutAsset;
import com.hkm.advancedtoolbar.V3.LiveIcon;
import com.hkm.advancedtoolbar.V3.TopBarManager;
import com.hkm.advancedtoolbar.V3.layout.SimpleSearchCallBack;
import com.hkm.advancedtoolbar.V3.layout.commonSearchBarMgr;
import com.hkm.advancedtoolbar.socialbar.combar;

/**
 * Created by hesk on 31/7/15.
 */
public class TopBarManagerExample extends AppCompatActivity {
    private ActionBar actionbar;
    private TopBarManager worker;
    private LiveIcon dynamic_icon;
    private Toolbar toolbar;
    //example for combar
    private combar social;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        try {
            worker = TopBarManager.Builder.with(this)
                    .companyLogo(R.drawable.starz_logo)
                    .searchBarEvents(new SimpleSearchCallBack() {
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

                        @Override
                        public void onPressSearchButton(ActionBar toolbar) {
                            Log.d("tolk", toolbar.toString());
                        }
                    })
                    .burgerIcon(R.drawable.ic_burger)
                    .searchMagnetifyIcon(R.drawable.ic_search_action_icn)
                    .searchView(LayoutAsset.classic_3)
                    .searchCancelColorId(R.color.amber_a100)
                    .searchCancalIconColorId(R.mipmap.cross_mi)
                    .setLiveIcon(R.layout.dynamic_icon_p, R.mipmap.crossmp)
                    .background(R.drawable.actionbar_bg_dark_black)
                    .searchArea(R.drawable.search_area)
                    .build(toolbar);

            toolbar.setTitleTextColor(Color.WHITE);
            worker.showCompanyLogo();
            dynamic_icon = worker.getDynamicIcon();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Button b = (Button) findViewById(R.id.b3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worker.triggerfromSearchIcon();
            }
        });

        social = (combar) findViewById(R.id.social_bar_component);
        social
                .connectAlert(getFragmentManager())
                .setShareContent(
                        "Share item now",
                        "This is the best to share the items",
                        "http://www.wonderful.com");

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
            worker.triggerfromSearchIcon(item);
            // worker.triggerForCustomExternalCustomView();
            //    worker.triggerfromSearchIcon();
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
