package com.hkm.toolbarlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hkm.advancedtoolbar.V5.BeastBar;
import com.hkm.advancedtoolbar.materialsearch.MaterialSearchView;
import com.hkm.advancedtoolbar.socialbar.combar;

/**
 * Created by hesk on 28/10/15.
 */
public class BeastBarDemo_v1 extends AppCompatActivity implements View.OnClickListener {
    private BeastBar toolbar;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general);
        Toolbar temp = (Toolbar) findViewById(R.id.toolbar);
        combar cBar = (combar) findViewById(R.id.social_bar_component);
        cBar.setShareContent("exiting news", "this is the bredf", "http://www.popbee.com");
        BeastBar.Builder bb = new BeastBar.Builder();
        bb.back(R.drawable.ic_m_back);
        //  bb.companyIcon(R.drawable.starz_logo);
        bb.background(R.drawable.actionbar_bg_dark_black);
        //     bb.search(R.drawable.ic_find_mg);

        bb.setToolBarTitleSize(R.dimen.tb_title);
        bb.defaultTitle("Wrtie Comment");
        bb.setFontFace(this, "Pacifico.ttf");
        bb.setToolBarTitleColor(R.color.amber_700);

        toolbar = BeastBar.withToolbar(this, temp, bb);

        searchView = (MaterialSearchView) findViewById(R.id.cmarterialsearch);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        // searchView.setSuggestions(getSuggestions());
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG).show();
                // searchSubmission(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
        Button b1 = (Button) findViewById(R.id.b1); //show title
        Button b2 = (Button) findViewById(R.id.b2); //how main bar
        Button b3 = (Button) findViewById(R.id.b3); //show search bar
        Button b4 = (Button) findViewById(R.id.b4); //other function
        b4.setText("show back");
        Button b5 = (Button) findViewById(R.id.b5); //other function
        b5.setText("remove back");
        Button bx = (Button) findViewById(R.id.bxx); //other function
        Button bs = (Button) findViewById(R.id.b_search); //other function
        bx.setText("close this App");
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        bx.setOnClickListener(this);
        bs.setOnClickListener(this);
        toolbar.setFindIconFunc(new Runnable() {
            @Override
            public void run() {
                searchView.showSearch();
            }
        });

    }

    protected void search_start() {
        searchView.showSearch();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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
                toolbar.setActionTitle("XX Delgnew fnei onie nfien foisni nsion snid 535 353 53442453535 345435 353 53");
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
            case R.id.b_search:
                search_start();
                break;
        }
    }
}
