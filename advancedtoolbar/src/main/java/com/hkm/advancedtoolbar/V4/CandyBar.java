package com.hkm.advancedtoolbar.V4;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.V3.LayoutAsset;
import com.hkm.advancedtoolbar.V3.LiveIcon;
import com.hkm.advancedtoolbar.V3.TopBarManager;
import com.hkm.advancedtoolbar.V3.barControl;
import com.hkm.advancedtoolbar.V3.layout.CLayO;
import com.hkm.advancedtoolbar.V3.layout.SearchCustom;
import com.hkm.advancedtoolbar.ToolbarHelper;
import com.hkm.advancedtoolbar.iOS.ActionBarActionListener;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 3/8/15.
 */
public class CandyBar implements View.OnClickListener, barControl {
    private final AppCompatActivity activity;
    private final Toolbar toolbar;
    private final ActionBar actionbar;
    private final Context ctx;
    private final TopBarManager.searchBarListener searchListener;
    private final candyAll barlistener;
    private int logo = 0, background = 0, customtitleview = 0,
            searchLayout = 0, burger = 0,
            toolbar_resid = 0, notification_count = 0,
            notification_color = 0;
    private final int window_default_configuration;
    private Builder mb;
    private String actionbartitle_current;
    private ActionBarActionListener ablistener;
    private TypedArray theme_SearchBarStyle;
    private SearchCustom search;
    private LayoutAsset searchLayoutPreset, mainPreset;
    private String searchHint, cachedTitle, cachedSubtitle;
    private boolean animationCustomBar = false;
    private float customBarHeight;
    private LiveIcon iconDynamic;
    private TextView numberView, titlebar;
    private final int MAIN = 19, TITLE = 21, SEARCH = 36;
    private ImageView logoview;

    private List<Integer> status_mode = new ArrayList<>();

    public CandyBar(AppCompatActivity activity, Toolbar toolbar, Builder mb) {
        this.window_default_configuration = mb.defaultconfig;
        this.ctx = mb.ctx;
        this.searchListener = mb.listener;
        this.background = mb.background;

        this.searchLayoutPreset = mb.layoutPreset;
        this.searchHint = mb.searchHint;
        this.burger = mb.burger;
        this.mb = mb;
        this.logo = mb.logo;
        this.iconDynamic = mb.icon;
        this.toolbar_resid = mb.toolbar_resid;
        this.barlistener = mb.candyBarListener;
        this.notification_count = mb.default_count;
        this.mainPreset = mb.main_layout;
        this.toolbar = toolbar;
        this.activity = activity;
        this.notification_color = mb.n_color;
        if (mb.background != 0)
            toolbar.setBackgroundResource(mb.background);
        activity.setSupportActionBar(toolbar);
        customBarHeight = 100f;
        //ctx.getResources().getDimension(R.dimen.height_custom_bar);
        actionbar = activity.getSupportActionBar();
        actionbartitle_current = null;
        init();
        showLogo();
    }

    private void init() {
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View t;
        if (this.toolbar_resid != 0) {
            t = ToolbarHelper.renewView(activity, toolbar, this.toolbar_resid);
        } else
            t = ToolbarHelper.renewView(activity, toolbar, mainPreset);

        if (burger != 0) toolbar.setNavigationIcon(burger);


        FrameLayout dynamic_button_frame = (FrameLayout) t.findViewById(R.id.liveicon_counterpanel);
        ImageView k3 = (ImageView) t.findViewById(R.id.liveiconloc);
        prepareNotificationCustomization(t);
        invalidateNotificationBox();
        logoview = (ImageView) t.findViewById(R.id.logo_k);
        if (logo != 0)
            logoview.setImageResource(logo);
        TintImageView k2 = (TintImageView) t.findViewById(R.id.i_kr2);
        final int[] overrides = mb.getOverrideIcons_i_t_ii();
        if (mainPreset == LayoutAsset.i_logo_ir) {
            TintImageView k1 = (TintImageView) t.findViewById(R.id.i_kl1);
            if (overrides[0] != 0)
                k1.setImageResource(overrides[0]);
            k1.setOnClickListener(this);
            if (overrides[1] != 0)
                k2.setImageResource(overrides[1]);
            if (overrides[2] != 0)
                k3.setImageResource(overrides[2]);
        }

        if (mainPreset == LayoutAsset.i_logo_ir2) {
            if (overrides[0] != 0)
                k2.setImageResource(overrides[0]);
            if (overrides[1] != 0)
                k3.setImageResource(overrides[1]);
        }

        titlebar = (TextView) t.findViewById(R.id.i_text_wide);
        k2.setOnClickListener(this);
        dynamic_button_frame.setOnClickListener(this);

    }

    private void prepareNotificationCustomization(View u) {
        numberView = (TextView) u.findViewById(R.id.liveicon_text);
        if (this.mb.notification_drawable != 0)
            numberView.setBackgroundResource(this.mb.notification_drawable);
        if (this.mb.offset_vertical != 0)
            ToolbarHelper.changeTopMargin(numberView,
                    ToolbarHelper.getDP(this.mb.offset_vertical,
                            activity.getResources()));

        if (this.notification_color != 0)
            numberView.setTextColor(activity.getResources().getColor(notification_color));
    }

    private void invalidateNotificationBox() {
        if (notification_count == 0) {
            numberView.setText("");
            numberView.setVisibility(View.GONE);
        } else if (notification_count > 99) {
            numberView.setText("99+");
            numberView.setVisibility(View.VISIBLE);
        } else {
            numberView.setText(notification_count + "");
            numberView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLogo() {
        if (getCurrentStatus() == MAIN) return;
        if (getCurrentStatus() != TITLE)
            init();
        titlebar.setVisibility(View.GONE);
        logoview.setVisibility(View.VISIBLE);
        addStep(MAIN);
    }

    @Override
    public void showTitle(String title) {
        if (getCurrentStatus() == TITLE) return;
        if (getCurrentStatus() != MAIN)
            init();
        cachedTitle = title;
        titlebar.setText(title);
        titlebar.setVisibility(View.VISIBLE);
        logoview.setVisibility(View.GONE);
        addStep(TITLE);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (barlistener != null) {
            if (i == R.id.i_kl1) {
                barlistener.left();
            } else if (i == R.id.i_kr2) {
                barlistener.right_second();
            } else if (i == R.id.liveicon_counterpanel) {
                barlistener.right_first();
            }
        }
    }

    @Override
    public void triggerfromSearchIcon() {
        if (getCurrentStatus() == SEARCH) return;
        View t = ToolbarHelper.renewView(activity, toolbar, searchLayoutPreset);
        SearchCustom search = new SearchCustom(t);
        search.setOnSearchListener(mb.listener);
        search.setSearchPlaceholder(mb.searchHint);
        search.showkeyboard();
        addStep(SEARCH);
    }

    private void addStep(int tag) {
        status_mode.add(0, tag);
        if (status_mode.size() > 100) status_mode = status_mode.subList(0, 100);
    }

    private int getPreviousStatus() {
        if (status_mode.size() < 2) return -1;
        return status_mode.get(1);
    }

    private int getCurrentStatus() {
        if (status_mode.size() == 0) return -1;
        return status_mode.get(0);
    }

    @Override
    public void showBack() {
        switch (getPreviousStatus()) {
            case TITLE:
                showTitle(cachedTitle);
                break;
            case SEARCH:
                triggerfromSearchIcon();
                break;
            case MAIN:
                showLogo();
                break;
        }
    }

    public void updateCount(final int n) {
        if (numberView != null) {
            notification_count = n;
            invalidateNotificationBox();
        }
    }

    public static class Builder {
        private AppCompatActivity ctx;
        private SearchCustom search;
        private TopBarManager.searchBarListener listener;
        private int defaultconfig = 0, notification_drawable = 0,
                logo = 0, background = 0, searchlayout = 0,
                offset_vertical = 0, n_color = 0,
                customtitleview = 0, burger = 0, toolbar_resid = 0,
                k1 = 0, k2 = 0, k3 = 0, default_count = 0;
        private LayoutAsset layoutPreset, main_layout;
        private String searchHint;
        private ActionItemBadgeAdder iconbadget;
        private LiveIcon icon;

        public CLayO.OnInteract customListener;
        private candyAll candyBarListener;

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

        public Builder searchView(SearchCustom search) {
            this.search = search;
            return this;
        }

        public Builder searchBarEvents(TopBarManager.searchBarListener listener) {
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

        public Builder presetCountNotification(int n) {
            this.default_count = n;
            return this;
        }

        public Builder setNotificationDrawableId(final @DrawableRes int dre) {
            this.notification_drawable = dre;
            return this;
        }

        public Builder setNotifcationTextColor(final @ColorRes int m) {
            this.n_color = m;
            return this;
        }

        public Builder setNotificationOffset(final int offset_margin_vertical) {
            this.offset_vertical = offset_margin_vertical;
            return this;
        }

        public Builder apply_full_custom_layout(final @LayoutRes int resId) {
            this.toolbar_resid = resId;
            return this;
        }

        public Builder overrideIcons(final @DrawableRes int right, final @DrawableRes int cart) {
            this.k2 = right;
            this.k3 = cart;
            this.main_layout = LayoutAsset.i_logo_ir2;
            return this;
        }

        public Builder overrideIcons(final @DrawableRes int left, final @DrawableRes int right, final @DrawableRes int cart) {
            this.k1 = left;
            this.k2 = right;
            this.k3 = cart;
            this.main_layout = LayoutAsset.i_logo_ir;
            return this;
        }

        public Builder setBarListener(candyAll listener) {
            this.candyBarListener = listener;
            return this;
        }

        public CandyBar build(Toolbar thetoolbar) throws Exception {
            if (this.listener == null && this.main_layout == LayoutAsset.i_logo_ir)
                throw new Exception("listener is not setup");
            if (thetoolbar == null)
                throw new Exception("ToolBar is not setup");
            if (defaultconfig == 0)
                defaultconfig = ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME;
            return new CandyBar(ctx, thetoolbar, this);
        }

        public int[] getOverrideIcons_i_t_ii() {
            if (k1 == 0) {
                return new int[]{k2, k3};
            } else
                return new int[]{k1, k2, k3};
        }
    }
}
