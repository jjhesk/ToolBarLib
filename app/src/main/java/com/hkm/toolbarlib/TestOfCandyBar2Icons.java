package com.hkm.toolbarlib;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.hkm.advancedtoolbar.V3.TopBarManager;
import com.hkm.advancedtoolbar.V3.layout.commonSearchBarMgr;
import com.hkm.advancedtoolbar.V4.CandyBar;

/**
 * Created by hesk on 7/8/15.
 */
public class TestOfCandyBar2Icons extends AppCompatActivity implements View.OnClickListener {
    private CandyBar worker;
    private Toolbar toolbar;
    private int u = 2;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        try {
            worker = CandyBar.Builder.with(this)
                    .companyLogo(R.drawable.starz_logo)
                    .searchView(LayoutAsset.classic_3)
                    .searchBarEvents(new commonSearchBarMgr() {
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
                            Log.d("addNow", toolbar.toString());
                        }
                    })
                    .setNotifcationTextColor(R.color.main_background)
                    .setNotificationOffset(15)
                    .setNotificationDrawableId(R.drawable.notg)
                    .overrideIcons(R.mipmap.ic_action_close, R.mipmap.crossmp)
                    .background(R.drawable.bottom_line)
                    .presetCountNotification(u)

                    .build(toolbar);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Button b1 = (Button) findViewById(R.id.b1); //show title
        Button b2 = (Button) findViewById(R.id.b2); //how main bar
        Button b3 = (Button) findViewById(R.id.b3); //show search bar
        Button b4 = (Button) findViewById(R.id.b4); //other function
        Button b5 = (Button) findViewById(R.id.b5); //other function
        Button bx = (Button) findViewById(R.id.bxx); //other function
        bx.setText("close this App");
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        bx.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                worker.showTitle("fill this up now");
                break;
            case R.id.b2:
                worker.showLogo();
                break;
            case R.id.b3:
                worker.triggerfromSearchIcon();
                break;
            case R.id.b4:
                worker.updateCount(u++);
                break;
            case R.id.b5:
                worker.updateCount(0);
                break;
            case R.id.bxx:
                finish();
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idt = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (idt == R.id.action_search) {
            // worker.triggerfromSearchIcon(item);
            worker.triggerfromSearchIcon();
            return true;
        } else if (idt == R.id.custombar) {
            Intent g = new Intent(this, CustomActionBar.class);
            startActivity(g);
        } else if (idt == R.id.dynamic) {

        }
        return super.onOptionsItemSelected(item);
    }
}