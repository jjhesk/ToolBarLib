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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.V3.layout.SearchCustom;
import com.hkm.advancedtoolbar.ToolbarHelper;
import com.hkm.advancedtoolbar.V3.layout.CLayO;
import com.hkm.advancedtoolbar.V3.layout.barControl;
import com.hkm.advancedtoolbar.V3.layout.commonSearchBarMgr;
import com.hkm.advancedtoolbar.iOS.ActionBarActionListener;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;

/**
 * Created by hesk on 16/7/15.
 */
public class TopBarManager implements barControl {
    private final AppCompatActivity activity;
    private final Toolbar toolbar;
    private final ActionBar actionbar;
    private final Context ctx;
    private final commonSearchBarMgr listener;
    private int logo = 0, background = 0, icon = 0, customtitleview = 0, searchLayout = 0, burger = 0, toolbar_resid = 0;
    private Builder mb;
    private String actionbartitle_current;
    private ActionBarActionListener ablistener;
    private TypedArray theme_SearchBarStyle;
    private SearchCustom search;
    private LayoutAsset layoutPreset, mainPreset;
    private String searchHint;
    private final int window_default_configuration;
    private boolean animationCustomBar = false;
    private float customBarHeight;
    private LiveIcon iconDynamic;
    private CLayO custommanager;

    public TopBarManager(AppCompatActivity activity, Toolbar toolbar, Builder mb) {
        window_default_configuration = mb.defaultconfig;

        this.ctx = mb.ctx;
        this.listener = mb.listener;
        this.background = mb.background;
        this.icon = mb.searchIcon;
        this.layoutPreset = mb.layoutPreset;
        this.mainPreset = mb.startheadr;
        this.searchHint = mb.searchHint;
        this.burger = mb.burger;
        this.mb = mb;
        this.iconDynamic = mb.icon;
        this.toolbar_resid = mb.toolbar_resid;
        getThemedSettings();

        this.toolbar = toolbar;
        this.activity = activity;
        activity.setSupportActionBar(toolbar);
        customBarHeight = 100f;
        //ctx.getResources().getDimension(R.dimen.height_custom_bar);
        actionbar = activity.getSupportActionBar();
        actionbartitle_current = null;

        if (mainPreset == null)
            showCompanyLogo();
        else showMainPreset(mainPreset);
    }

    private void showMainPreset(LayoutAsset preset) {
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        if (burger != 0) toolbar.setNavigationIcon(burger);
        custommanager = new CLayO(preset, ctx, mb);
        if (preset == LayoutAsset.i_logo_ir) {
            iconDynamic.customact(custommanager);
        }
        custommanager.init(toolbar);
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

    /**
     * custom internal layout - included in the library
     */
    @Override
    public void triggerfromSearchIcon() {
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setDisplayShowHomeEnabled(false);
        toolbar.removeAllViews();
        // toolbar.addView();
    }

    /**
     * triggered the traditional custom view
     *
     * @param meun the menu item in here
     */
    public void triggerfromSearchIcon(MenuItem meun) {
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setDisplayShowHomeEnabled(false);
        if (this.searchLayout != 0) {
            actionbar.setCustomView(this.searchLayout);
        }
        search = new SearchCustom(actionbar.getCustomView());
        search.setOnSearchListener(this.listener);
        search.setSearchPlaceholder(searchHint);
        if (meun.isVisible()) {
            meun.setVisible(false);
        }
    }


    public void triggerForCustomExternalCustomView() {
        if (toolbar_resid == 0) return;
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP;
        final View h = ToolbarHelper.generateView(toolbar_resid, ctx);
        toolbar.addView(h, layoutParams);
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //   h.bringToFront();
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
        search = new SearchCustom(actionbar.getCustomView());
        search.setOnSearchListener(this.listener);
        search.setSearchPlaceholder(searchHint);
        listener.onPressSearchButton(actionbar);
        if (meun.isVisible()) {
            meun.setVisible(false);
        }
    }

    public LiveIcon getDynamicIcon() {
        return iconDynamic;
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
        //   if (withHomeButton) {
        //     actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        //   } else {
        //      actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //    }
        // actionbar.setCustomView(customtitleview != 0 ? customtitleview : R.layout.centertextview);
        // final TV text = (TV) actionbar.getCustomView().findViewById(R.id.title_display);
        // text.setText(title);
        actionbar.setTitle(title.toString());
        actionbartitle_current = title;
        if (ablistener != null) {
            ablistener.onShowCenterTextActionBar(actionbar, title);
        }


        if (burger != 0) toolbar.setNavigationIcon(burger);
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayShowCustomEnabled(false);
        actionbar.setDisplayUseLogoEnabled(false);

    }

    @Override
    public void showBack() {
        if (actionbartitle_current != null) {
            showCenterTitleNormalHome(actionbartitle_current);
        } else {
            showCompanyLogo();
        }
    }

    @Override
    public void showLogo() {
        showCompanyLogo();
    }

    @Override
    public void showTitle(String title) {

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


    public static class Builder {
        private AppCompatActivity ctx;
        private SearchCustom search;
        private commonSearchBarMgr listener;
        private int defaultconfig, logo = 0, background = 0, searchIcon = 0, searchlayout = 0, customtitleview = 0, burger = 0, toolbar_resid = 0, k1 = 0, k2 = 0, k3 = 0;
        private LayoutAsset layoutPreset, startheadr;
        private String searchHint;
        private ActionItemBadgeAdder iconbadget;
        private LiveIcon icon;
        public CLayO.OnInteract customListener;

        public static Builder with(AppCompatActivity ctx) {
            return new Builder(ctx);
        }

        public Builder(AppCompatActivity ctx) {
            this.ctx = ctx;
        }

        public Builder companyLogo(final @DrawableRes int thelogo) {
            this.logo = thelogo;
            return this;
        }

        public Builder background(final @DrawableRes int thebackgroundID) {
            this.background = thebackgroundID;
            return this;
        }

        public Builder searchMagnetifyIcon(final @DrawableRes int icon) {
            this.searchIcon = icon;
            return this;
        }

        public Builder customTitleView(final @LayoutRes int customTitleView) {
            this.customtitleview = customTitleView;
            return this;
        }

        public Builder searchView(final LayoutAsset searchlayout) {
            this.layoutPreset = searchlayout;
            return this;
        }

        public Builder searchView(final @LayoutRes int searchlayout) {
            this.searchlayout = searchlayout;
            return this;
        }

        public Builder setCustomMainBar(final LayoutAsset l) {
            this.startheadr = l;
            return this;
        }

        public Builder searchView(SearchCustom search) {
            this.search = search;
            return this;
        }

        public Builder searchBarEvents(commonSearchBarMgr listener) {
            this.listener = listener;
            return this;
        }

        public Builder burgerIcon(final @DrawableRes int burger) {
            this.burger = burger;
            return this;
        }

        public Builder searchHint(String r) {
            searchHint = r;
            return this;
        }

        public Builder setDefaultWindowConfiguration(int e) {
            this.defaultconfig = e;
            return this;
        }

        public Builder searchHint(final @StringRes int hint) {
            searchHint = ctx.getResources().getString(hint);
            return this;
        }

        public Builder setLiveIcon(final @LayoutRes int layout, final @DrawableRes int icon) {
            this.icon = new LiveIcon(layout, icon).act(ctx);
            return this;
        }

        public Builder setLiveIcon(LiveIcon icon) {
            this.icon = icon;
            return this;
        }

        public Builder genLiveIcon(LiveIcon icon) {
            icon = new LiveIcon(ctx);
            this.icon = icon;
            return this;
        }

        public Builder setIconBadget(final ActionItemBadgeAdder actionitem) {
            this.iconbadget = actionitem;
            return this;
        }

        public Builder externalLayoutOutToolBar(final @LayoutRes int resId) {
            this.toolbar_resid = resId;
            return this;
        }

        public Builder setOnCustomItemClickListener(final CLayO.OnInteract listener) {
            this.customListener = listener;
            return this;
        }

        public Builder overrideIcons(final @DrawableRes int k1, final @DrawableRes int k2, final @DrawableRes int k3) {
            this.k1 = k1;
            this.k2 = k2;
            this.k3 = k3;
            return this;
        }

        public TopBarManager build(Toolbar thetoolbar) throws Exception {
            if (this.listener == null) throw new Exception("listener is not setup");
            if (thetoolbar == null) throw new Exception("ToolBar is not setup");
            if (defaultconfig == 0)
                defaultconfig = ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME;
            return new TopBarManager(ctx, thetoolbar, this);
        }

        public int getCompanyLogoRes() {
            return logo;
        }

        public int[] getOverrideIcons_i_t_ii() {
            return new int[]{k1, k2, k3};
        }
    }
}
