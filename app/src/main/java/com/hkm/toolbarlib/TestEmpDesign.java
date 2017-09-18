package com.hkm.toolbarlib;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hkm.advancedtoolbar.V5.BeastBar;
import com.hkm.advancedtoolbar.V5.buttonBuilder;

/**
 * Created by hesk on 18/9/2017.
 */

public class TestEmpDesign extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private int u = 2;
    private BeastBar meba;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        BeastBar.Builder bb = new BeastBar.Builder();
        bb.back(R.drawable.ic_m_back);
        bb.companyIcon(R.drawable.starz_logo);
        bb.useCustomWidgetStylingCompact40();
        // bb.background(R.drawable.actionbar_bg_dark_black);
        //bb.search(R.drawable.ic_find_mg);
        bb.setToolBarTitleSize(R.dimen.tb_title);
        // bb.defaultTitle("EMP Ds Direct");
        // bb.setFontFace(this, "Pacifico.ttf");
        bb.setToolBarTitleColor(R.color.amber_700);
        //  bb.enableLogoAnimation(false);

        meba = BeastBar.withToolbar(this, toolbar, bb);

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
        buttonBuilder bon = new buttonBuilder();
        bon.addCustomIconButtonFunction(new Runnable() {
            @Override
            public void run() {

            }
        }, R.drawable.ic_action_navigation_close);
        meba.setNewButtonLayout(bon);
    }


    @Override
    public void onClick(View v) {

    }
}
