package com.hkm.advancedtoolbar.V3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.iOS.ActionBarActionListener;

/**
 * Created by hesk on 16/7/15.
 */
public class TopBarManager {
    private final Toolbar toolbar;
    private final ActionBar actionbar;
    private final Context ctx;
    private final searchBarListener listener;
    private int logo = 0, background = 0, icon = 0, customtitleview = 0, searchLayout = 0, burger = 0;
    private ManagerBulider mb;
    private String actionbartitle_current;
    private ActionBarActionListener ablistener;
    private TypedArray theme_SearchBarStyle;
    private SearchCustom search;
    private SearchCustom.LAYOUT layoutPreset;
    private String searchHint;
    private final int window_default_configuration;
    private boolean animationCustomBar = false;
    private float customBarHeight;

    public TopBarManager(AppCompatActivity activity, Toolbar toolbar, ManagerBulider mb) {
        window_default_configuration = mb.defaultconfig;

        this.ctx = mb.ctx;
        this.listener = mb.listener;
        this.background = mb.background;
        this.icon = mb.searchIcon;
        this.layoutPreset = mb.layoutPreset;
        this.searchHint = mb.searchHint;
        this.burger = mb.burger;
        this.mb = mb;
        getThemedSettings();

        this.toolbar = toolbar;
        activity.setSupportActionBar(toolbar);
        customBarHeight = 100f;
        //ctx.getResources().getDimension(R.dimen.height_custom_bar);
        actionbar = activity.getSupportActionBar();
        preDefault();
    }

    private void setDefaultDisplayOptions() {
        actionbar.setDisplayOptions(window_default_configuration);
    }

    private void preDefault() {
        setDefaultDisplayOptions();
        if (background != 0) toolbar.setBackgroundResource(background);
        if (logo != 0) toolbar.setLogo(logo);
        if (burger != 0) toolbar.setNavigationIcon(burger);
        if (animationCustomBar) {
            reverseCustomBar();
        }
    }

    /**
     * get hint color
     */
    //int resId = values.getResourceId(R.styleable.SearchBarStyle_search_bar_hint_color, -1);
    private void getThemedSettings() {
        /**
         * theme location
         */
        Resources.Theme theme = ctx.getTheme();
        TypedValue typedValue = new TypedValue();

        theme.resolveAttribute(R.attr.iOSSearchBarStyle, typedValue, true);
        theme_SearchBarStyle = theme.obtainStyledAttributes(typedValue.resourceId, R.styleable.SearchBarStyle);
        if (theme_SearchBarStyle != null) {

        }
        theme.resolveAttribute(R.attr.iOSActionBarCompanyLogo, typedValue, true);
        if (typedValue.data != 0) {
            logo = typedValue.data;
        } else if (mb.logo != 0) {
            logo = mb.logo;
        } else logo = R.drawable.ab_logo;


        theme.resolveAttribute(R.attr.iOSSearchBarLayout, typedValue, true);
        if (typedValue.data != 0) {
            searchLayout = typedValue.data;
        } else if (mb.searchlayout != 0) {
            searchLayout = mb.searchlayout;
        } else
            searchLayout = this.layoutPreset != null ? this.layoutPreset.getResourceId() : R.layout.material_search_ios_classic;


        theme.resolveAttribute(R.attr.iOSCustomBarTitleLayout, typedValue, true);
        if (typedValue.data != 0) {
            customtitleview = typedValue.data;
        } else if (mb.customtitleview != 0) {
            customtitleview = mb.customtitleview;
        } else
            customtitleview = R.layout.centertextview;

    }

    private RelativeLayout customViewForAction;

    public void triggerFromSlideInSearchIcon(MenuItem menu) {
        View vm = ToolbarHelper.generateView(this.searchLayout, ctx);
        toolbar.addView(vm);
        customViewForAction = (RelativeLayout) vm.findViewById(R.id.ios_layout_wrapper);
        ViewCompat.setTranslationY(customViewForAction, customBarHeight);
        customViewForAction.animate().translationY(0);
        // actionbar.hide();
    }

    private void reverseCustomBar() {
        if (customViewForAction != null) {
            customViewForAction.animate().translationY(-1 * customBarHeight).withEndAction(new Runnable() {
                @Override
                public void run() {
                    toolbar.removeView(customViewForAction);
                    customViewForAction = null;
                    // actionbar.show();
                }
            });
        }
    }

    public void triggerfromSearchIcon(MenuItem meun) {
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setDisplayShowHomeEnabled(false);
        if (this.searchLayout != 0) {
            actionbar.setCustomView(this.searchLayout);
        }
        search = new SearchCustom(actionbar, actionbar.getCustomView());
        search.setOnSearchListener(this.listener);
        search.setSearchPlaceholder(searchHint);
        if (meun.isVisible()) {
            meun.setVisible(false);
        }
    }


    public void triggerfromSearchIconV2(MenuItem meun) {
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayShowHomeEnabled(false);
        //   actionbar.hide();
        if (this.searchLayout != 0) {
            actionbar.setCustomView(this.searchLayout);
        }
        search = new SearchCustom(actionbar, actionbar.getCustomView());
        search.setOnSearchListener(this.listener);
        search.setSearchPlaceholder(searchHint);
        if (meun.isVisible()) {
            meun.setVisible(false);
        }
    }


    public void showCenterTitleNormalHome(final String title) {
        showCenterTitleNormal(title, true);
    }

    public void showCenterTitleNormalHome(final @StringRes int title) {
        showCenterTitleNormal(ctx.getResources().getString(title), true);
    }

    public void showCenterTitleNormal(final @StringRes int title, boolean withHomeButton) {
        showCenterTitleNormal(ctx.getResources().getString(title), withHomeButton);
    }

    @SuppressLint("WrongViewCast")
    public <TV extends TextView> void showCenterTitleNormal(final String title, boolean withHomeButton) {
        if (withHomeButton) {
            actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        } else {
            actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        }
        // actionbar.setCustomView(customtitleview != 0 ? customtitleview : R.layout.centertextview);
        // final TV text = (TV) actionbar.getCustomView().findViewById(R.id.title_display);
        // text.setText(title);
        actionbar.setTitle(title.toString());
        actionbartitle_current = title;
        if (ablistener != null) {
            ablistener.onShowCenterTextActionBar(actionbar, title);
        }
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayUseLogoEnabled(false);
        actionbar.setDisplayShowCustomEnabled(false);
        //    actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void showBack() {
        if (actionbartitle_current != null) {
            showCenterTitleNormalHome(actionbartitle_current);
        } else {
            showCompanyLogo();
        }
    }

    /**
     * show and display the company logo only
     */
    private void showCompanyLogoPre() {
        actionbar.setDisplayOptions(window_default_configuration);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayUseLogoEnabled(true);
        actionbartitle_current = null;
    }

    public void showCompanyLogo() {
        preDefault();
        showCompanyLogoPre();
        actionbar.setLogo(logo);
    }

    public void showCompanyLogo(final @DrawableRes int newCompanyLogo) {
        preDefault();
        showCompanyLogoPre();
        actionbar.setLogo(newCompanyLogo);
    }

    public interface searchBarListener {
        void onKeySearchStartConfirm(String text);

        void onKeySearchLetter(String text);

        void onRestoreToNormal(ActionBar toolbar);

    }

    public static class ManagerBulider {
        private Context ctx;
        private SearchCustom search;
        private searchBarListener listener;
        private int defaultconfig, logo = 0, background = 0, searchIcon = 0, searchlayout = 0, customtitleview = 0, burger = 0;
        private SearchCustom.LAYOUT layoutPreset;
        private String searchHint;

        public static ManagerBulider with(Context ctx) {
            return new ManagerBulider(ctx);
        }

        public ManagerBulider(Context ctx) {
            this.ctx = ctx;
        }

        public ManagerBulider companyLogo(final @DrawableRes int thelogo) {
            this.logo = thelogo;
            return this;
        }

        public ManagerBulider background(final @DrawableRes int thebackgroundID) {
            this.background = thebackgroundID;
            return this;
        }

        public ManagerBulider searchMagnetifyIcon(final @DrawableRes int icon) {
            this.searchIcon = icon;
            return this;
        }

        public ManagerBulider customTitleView(final @LayoutRes int customTitleView) {
            this.customtitleview = customTitleView;
            return this;
        }

        public ManagerBulider searchView(final SearchCustom.LAYOUT searchlayout) {
            this.layoutPreset = searchlayout;
            return this;
        }

        public ManagerBulider searchView(final @LayoutRes int searchlayout) {
            this.searchlayout = searchlayout;
            return this;
        }

        public ManagerBulider searchView(SearchCustom search) {
            this.search = search;
            return this;
        }

        public ManagerBulider searchBarEvents(searchBarListener listener) {
            this.listener = listener;
            return this;
        }

        public ManagerBulider burgerIcon(final @DrawableRes int burger) {
            this.burger = burger;
            return this;
        }

        public ManagerBulider searchHint(String r) {
            searchHint = r;
            return this;
        }

        public ManagerBulider setDefaultWindowConfiguration(int e) {
            this.defaultconfig = e;
            return this;
        }

        public ManagerBulider searchHint(final @StringRes int hint) {
            searchHint = ctx.getResources().getString(hint);
            return this;
        }

        public TopBarManager build(AppCompatActivity act, Toolbar thetoolbar) throws Exception {
            if (this.listener == null) throw new Exception("listener is not setup");
            if (thetoolbar == null) throw new Exception("ToolBar is not setup");
            if (defaultconfig == 0)
                defaultconfig = ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME;
            return new TopBarManager(act, thetoolbar, this);
        }
    }
}
