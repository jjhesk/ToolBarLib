package com.hkm.toolbarlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hkm.advancedtoolbar.V5.BeastBar;

/**
 * Created by hesk on 28/10/15.
 */
public class BeastBarDemo_v1 extends AppCompatActivity implements View.OnClickListener {
    private BeastBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        Toolbar temp = (Toolbar) findViewById(R.id.toolbar);
        BeastBar.Builder bb = new BeastBar.Builder();
        bb.back(R.drawable.ic_m_back);
        bb.companyIcon(R.drawable.starz_logo);
        bb.background(R.drawable.actionbar_bg_dark_black);
        bb.search(R.drawable.ic_find_mg);
        toolbar = BeastBar.withToolbar(this, temp, bb);

        Button b1 = (Button) findViewById(R.id.b1); //show title
        Button b2 = (Button) findViewById(R.id.b2); //how main bar
        Button b3 = (Button) findViewById(R.id.b3); //show search bar
        Button b4 = (Button) findViewById(R.id.b4); //other function
        b4.setText("show back");
        Button b5 = (Button) findViewById(R.id.b5); //other function
        b5.setText("remove back");
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
                toolbar.setActionTitle("XX Delgnew");
                break;
            case R.id.b2:
                toolbar.showMainLogo();
                break;
            case R.id.b3:
                toolbar.setActionTitle("Kepler 452b");
                break;
            case R.id.b4:
                toolbar.setBackIconFunc(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                break;
            case R.id.b5:
                toolbar.setBackIconFunc(null);
                break;
            case R.id.bxx:
                finish();
                break;
        }
    }
}
