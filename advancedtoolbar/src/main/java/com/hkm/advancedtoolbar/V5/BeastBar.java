package com.hkm.advancedtoolbar.V5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.ToolbarHelper;
import com.hkm.advancedtoolbar.Util.TitleStorage;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.lang.ref.WeakReference;
import java.util.Iterator;

/**
 * Created by hesk on 28/10/15.
 */
public class BeastBar {
    public static int SINGLELINE = 1, MULTILINE = 2;
    private String text_title;
    private Context mContext;
    private Toolbar container;
    private TextView mtv;
    private ImageView mImage;
    private RelativeLayout mBackground;
    private LinearLayout mRightContainer, mLeftContainer;
    private ImageButton mSearchButton, mTopLeftButton;
    private Runnable mFindFunction;

    private Animation
            main_logo_in,
            main_logo_out,
            title_in,
            title_out,
            back_in,
            back_out,
            back_in_from_right,
            back_out_to_right;

    private boolean isCompanyLogoShown, isTitleShown, isBackButtonShown, isSearchButtonShown, isLayoutSwapped;
    private Builder setup;
    private int leftSide = 0, rightSide = 0;
    private TitleStorage mTitle;

    public static class Builder {

        private int
                ic_center_icon,
                ic_left_icon,
                ic_search,
                ic_back,
                ic_background,
                tb_textsize = 0,
                tb_title_color = 0,
                title_line_config = 1,
                rbuttons_width = -1,
                animation_duration_logo = -1,
                animation_duration = -1;

        private Typeface typeface;
        private String title_default;
        private int base_layout_id = R.layout.beastbar_base_v1_body;
        private int right_side_button_style = R.style.actionButtonhkm;
        private boolean enable_logo_anim = true;
        private boolean no_title = false;
        private boolean save_title_navigation = false;

        public Builder() {

        }

        public Builder setFontFace(@NonNull Context mContext, @NonNull final String fontNameInFontFolder) {
            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + fontNameInFontFolder);
            return this;
        }

        public Builder companyIcon(@DrawableRes final int res) {
            this.ic_center_icon = res;
            return this;
        }

        /**
         * the most basic Icon configuration on the left side
         *
         * @param res drawable
         * @return n
         */
        public Builder leftIcon(@DrawableRes final int res) {
            this.ic_left_icon = res;
            return this;
        }

        public Builder enableLogoAnimation(boolean res) {
            this.enable_logo_anim = res;
            return this;
        }

        public Builder search(@DrawableRes final int res) {
            this.ic_search = res;
            return this;
        }

        public Builder back(@DrawableRes final int res) {
            this.ic_back = res;
            return this;
        }

        public Builder background(@DrawableRes final int res) {
            this.ic_background = res;
            return this;
        }

        public Builder setToolBarTitleSize(@DimenRes final int res) {
            this.tb_textsize = res;
            return this;
        }

        public Builder setToolBarTitleColor(@ColorRes final int res) {
            this.tb_title_color = res;
            return this;
        }

        public Builder defaultTitle(String title) {
            this.title_default = title;
            this.no_title = false;
            return this;
        }

        public Builder setCustomLayoutID(@LayoutRes int layout_id) {
            base_layout_id = layout_id;
            return this;
        }

        public Builder useLayoutV2() {
            base_layout_id = R.layout.beastbar_base_v2_body;
            return this;
        }

        public Builder useCustomWidgetStyling(@StyleRes int k) {
            right_side_button_style = k;
            return this;
        }

        /**
         * using specific style for customizations
         *
         * @return builder
         */
        public Builder useCustomWidgetStylingCompact40() {
            right_side_button_style = R.style.actionButtonCompact40;
            rbuttons_width = 40;
            return this;
        }

        /**
         * using specific style for customizations
         *
         * @return builder
         */
        public Builder useCustomWidgetStylingCompact30() {
            right_side_button_style = R.style.actionButtonCompact30;
            rbuttons_width = 30;
            return this;
        }

        /**
         * using no padding specific layout for the left side
         *
         * @return builder
         */
        public Builder useNoPaddingWidgetButton() {
            right_side_button_style = R.style.actionButtonNoPadding;
            return this;
        }

        /**
         * fully customized layout style
         *
         * @param k        parent from the buttonHkm style R.style.actionButtonhkm
         * @param dp_width using the width items for specific width
         * @return the builder
         */
        public Builder useCustomButtionLayoutStyle(@StyleRes int k, int dp_width) {
            right_side_button_style = k;
            rbuttons_width = dp_width;
            return this;
        }


        /**
         * to allow title to display two rows on the toolbar
         *
         * @param lines numbers of line
         * @return using the chain method to build up the object
         */
        public Builder setTitleLine(int lines) {
            this.title_line_config = lines;
            this.no_title = false;
            return this;
        }

        /**
         * Call this to disable title display
         *
         * @return chain object call
         */
        public Builder disableTitle() {
            this.no_title = true;
            this.title_default = "";
            return this;
        }

        public Builder setAnimationDuration(int millisec_time) {
            this.animation_duration = millisec_time;
            return this;
        }

        public Builder setLogoAnimationDuration(int millisec_time) {
            this.animation_duration_logo = millisec_time;
            return this;
        }

        public Builder enableTitleHistory(boolean save) {
            this.save_title_navigation = save;
            return this;
        }
    }

    private onButtonPressListener mButtonBack;
    private WeakReference<onButtonPressListener> default_find_function;

    public interface onButtonPressListener {
        /**
         * @param previousTitleSteps the previous title to be found in the history or otherwise it is nothing
         * @return true to allow the personalPage logo to show
         */
        boolean onBackPress(final int previousTitleSteps);

        void onSearchPress();
    }

    private Point size = new Point();

    public static BeastBar withToolbar(AppCompatActivity res, Toolbar original, final Builder beastbuilder) {
        Display display = res.getWindowManager().getDefaultDisplay();
        res.setSupportActionBar(original);
        ActionBar actionbar = res.getSupportActionBar();
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayShowHomeEnabled(false);
        // actionbar.setDefaultDisplayHomeAsUpEnabled(false);
        original.setBackgroundResource(beastbuilder.ic_background);
        View homeIcon = res.findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home : android.R.id.home);
        //  ((View) homeIcon.getParent()).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        //  ((View) homeIcon).setVisibility(View.GONE);
        final BeastBar bb = new BeastBar(res);
        bb.setToolBar(original);
        bb.setup = beastbuilder;
        display.getSize(bb.size);
        bb.init();
        return bb;
    }

    public BeastBar(Context res) {
        this.mContext = res;
        isLayoutSwapped = false;
    }

    private BeastBar setToolBar(Toolbar tb) {
        this.container = tb;
        return this;
    }


    private void animationTitle() {
        showTitle();
        hideMainLogo();
    }

    private void showTitle() {
        if (!isTitleShown) {
            isTitleShown = true;
            if (setup.enable_logo_anim) {
                mayCancelAnimation(mtv);
                main_logo_in.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mtv.setVisibility(View.VISIBLE);
                    }
                });
                mtv.startAnimation(main_logo_in);
            } else {
                mtv.setVisibility(View.VISIBLE);
            }
        }
    }

    public void hideMainLogo() {
        if (isCompanyLogoShown) {
            isCompanyLogoShown = false;
            if (setup.enable_logo_anim) {
                mayCancelAnimation(mImage);
                main_logo_out.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mImage.setVisibility(View.INVISIBLE);
                    }
                });
                mImage.startAnimation(main_logo_out);
            } else {
                mImage.setVisibility(View.INVISIBLE);
            }
        }
    }

    private buttonBuilder mButtonBuilderCache;
    /**
     * This is the categories for the menu item presentation
     */
    public static final int
            DEFAULT = 0,
            BADGET = 1,
            CIRCULAR_LOAD_INDICATOR = 2,
            TEXT_ONLY = 3,
            SWITCHER = 4,
            CIRCULAR_PROGRESS_INDICATOR = 5,
            TEXT_BUTTON_ADVANCED = 6;

    public static class itemMenu {

        private int icon;
        // private WeakReference<Runnable> exec;
        private Runnable exec;
        private int color;
        private int style;
        private int resstring;
        private int custom_width;
        private ProgressBar load;
        private TextView loadprogressindicator;
        private SwitchCompat mSwitchCompat;
        private SwitchCompat.OnCheckedChangeListener mSwitchCompatListener;
        private Typeface mTypeface;

        public itemMenu() {
            this.color = this.resstring = this.custom_width = 0;
            this.style = DEFAULT;
        }

        /**
         * this is the loading indicator with progress
         *
         * @param font_type_asset_location the font type
         * @param font_color               the color from the font using color id
         */
        public void setLoadIndicator(Typeface font_type_asset_location, @ColorRes int font_color) {
            this.mTypeface = font_type_asset_location;
            this.color = font_color;
            this.style = CIRCULAR_PROGRESS_INDICATOR;
        }

        public void setLoadIndicator() {
            this.style = CIRCULAR_LOAD_INDICATOR;
        }

        public void setSwitcher(final @ColorRes int color, final @StringRes int label, SwitchCompat.OnCheckedChangeListener change) {
            this.style = SWITCHER;
            this.resstring = label;
            this.color = color;
            this.mSwitchCompatListener = change;
        }

        public void setBadgetConfig(final @ColorRes int color, final @StringRes int label) {
            this.style = BADGET;
            this.resstring = label;
            this.color = color;
        }

        public void setBadgetConfig(final @ColorRes int color, final @StringRes int label, @DimenRes final int width) {
            setBadgetConfig(color, label);
            this.custom_width = width;
        }

        public void setIcon(int image) {
            icon = image;
        }

        public void setExec(Runnable e) {
            exec = e;
        }

        public void removeExe() {
            exec = null;
        }

        public void setItemText(final @StringRes int label) {
            setItemText(label, android.R.color.black);
        }

        public void setItemText(final @StringRes int label, final @ColorRes int color) {
            this.resstring = label;
            this.style = TEXT_ONLY;
            this.color = color;
        }

        public void setItemTextAdv(final @StringRes int label) {
            setItemTextAdv(label, android.R.color.black);
        }

        public void setItemTextAdv(final @StringRes int label, final @ColorRes int color) {
            this.resstring = label;
            this.style = TEXT_BUTTON_ADVANCED;
            this.color = color;
        }

        private void patchBackground(ImageButton button, Context mContext) {
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            button.setBackgroundResource(backgroundResource);
            if (color != 0) {
                int h = ContextCompat.getColor(mContext, color);
                button.setColorFilter(h);
            }
            typedArray.recycle();
        }

        private void patchBackground(Button button, Context mContext) {
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            button.setBackgroundResource(backgroundResource);
         /*   if (color != 0) {
                int h = ContextCompat.getColor(mContext, color);
                button.setColorFilter(h);
            }*/
            typedArray.recycle();
        }

        /**
         * only use this when the loading is triggered
         *
         * @param n bool to turn it on
         */
        private void triggerLoading(boolean n) {
            if (style != CIRCULAR_LOAD_INDICATOR && style != CIRCULAR_PROGRESS_INDICATOR) return;
            dis_circular_load(n);
            dis_progress_text(n);
        }

        private void dis_progress_text(boolean enabled) {
            if (loadprogressindicator == null) return;
            if (enabled) {
                loadprogressindicator.setVisibility(View.VISIBLE);
            } else {
                loadprogressindicator.setVisibility(View.INVISIBLE);
            }
        }

        private void dis_circular_load(boolean n) {
            if (load == null) return;
            try {
                load.getAnimation().cancel();
            } catch (Exception e) {
                e.fillInStackTrace();
            }

            if (!n) {
                load.animate().alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        load.setVisibility(View.INVISIBLE);
                    }
                });

            } else {
                load.animate().alpha(1f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        load.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        private void setProgressPercentage(int contHundred, boolean sign) {
            if (loadprogressindicator == null) return;
            StringBuilder b = new StringBuilder();
            b.append(contHundred);
            if (sign) {
                b.append("%");
            }
            loadprogressindicator.setText(b.toString());
        }

        @SuppressWarnings("depreciated")
        private View getView(Context c, Builder configStep) {
            View out = null;
            if (style == DEFAULT) {
                ContextThemeWrapper u = new ContextThemeWrapper(c, configStep.right_side_button_style);
                ImageButton y = new ImageButton(u);

                y.setMaxWidth(c.getResources().getDimensionPixelOffset(R.dimen.icon_width));
                y.setImageResource(icon);
                y.setClickable(true);
                y.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exec != null) {
                            exec.run();
                        }
                    }
                });

                if (configStep.rbuttons_width > -1) {
                    LinearLayout.LayoutParams lP = new LinearLayout.LayoutParams(ToolbarHelper.dpToPx(c, configStep.rbuttons_width), LinearLayout.LayoutParams.MATCH_PARENT); // dpToPx is convert method
                    y.setLayoutParams(lP);
                }

                patchBackground(y, c);
                out = y;
            } else if (style == BADGET) {
                View custom_layout = LayoutInflater.from(c).inflate(R.layout.beastbar_item_badget, null, false);
                int m = this.custom_width == 0 ? c.getResources().getDimensionPixelOffset(R.dimen.badget_width) : c.getResources().getDimensionPixelOffset(this.custom_width);
                custom_layout.setLayoutParams(new LinearLayout.LayoutParams(m, LinearLayout.LayoutParams.MATCH_PARENT));
                LinearLayout l = (LinearLayout) custom_layout.findViewById(R.id.badget_layout_container);
                l.setBackgroundColor(ContextCompat.getColor(c, this.color));
                l.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exec != null) {
                            exec.run();
                        }
                    }
                });
                CircularImageView y = (CircularImageView) custom_layout.findViewById(R.id.badget_icon);
                y.setImageResource(icon);
                TextView z = (TextView) custom_layout.findViewById(R.id.badget_label);
                z.setText(c.getString(this.resstring));
                out = custom_layout;
            } else if (style == CIRCULAR_LOAD_INDICATOR) {
                View load_indicator = LayoutInflater.from(c).inflate(R.layout.beastbar_loader, null, false);
                load_indicator.setLayoutParams(new RelativeLayout.LayoutParams(c.getResources().getDimensionPixelOffset(R.dimen.icon_width), ViewGroup.LayoutParams.MATCH_PARENT));
                load = (ProgressBar) load_indicator.findViewById(R.id.beast_bar_load);
                load.setVisibility(View.INVISIBLE);
                //l.setBackgroundColor(ContextCompat.getColor(c, this.color));
                out = load_indicator;
            } else if (style == CIRCULAR_PROGRESS_INDICATOR) {
                View lodcompat = LayoutInflater.from(c).inflate(R.layout.beastbar_progress_indicator, null, false);
                lodcompat.setLayoutParams(new RelativeLayout.LayoutParams(c.getResources().getDimensionPixelOffset(R.dimen.bb_width_load_progress), ViewGroup.LayoutParams.MATCH_PARENT));

                load = (ProgressBar) lodcompat.findViewById(R.id.beast_bar_load);
                loadprogressindicator = (TextView) lodcompat.findViewById(R.id.beast_bar_load_progress);
                load.setVisibility(View.INVISIBLE);
                loadprogressindicator.setTypeface(mTypeface);
                loadprogressindicator.setTextSize(25f);
                loadprogressindicator.setVisibility(View.INVISIBLE);
                loadprogressindicator.setTextColor(ContextCompat.getColor(c, this.color));

                out = lodcompat;
            } else if (style == TEXT_ONLY) {
                ContextThemeWrapper u = new ContextThemeWrapper(c, configStep.right_side_button_style);
                Button text_label = new Button(u);
                text_label.setClickable(true);
                text_label.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exec != null) {
                            exec.run();
                        }
                    }
                });
                text_label.setLayoutParams(new RelativeLayout.LayoutParams(-2, -1));
                text_label.setText(c.getString(this.resstring));
                text_label.setTextColor(ContextCompat.getColor(c, this.color));

                out = text_label;
            } else if (style == TEXT_BUTTON_ADVANCED) {
                ContextThemeWrapper u = new ContextThemeWrapper(c, configStep.right_side_button_style);
                Button text_label = new Button(u);
                text_label.setClickable(true);
                text_label.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exec != null) {
                            exec.run();
                        }
                    }
                });
                text_label.setLayoutParams(new RelativeLayout.LayoutParams(-2, -1));
                text_label.setText(c.getString(this.resstring));
                text_label.setTextColor(ContextCompat.getColor(c, this.color));
                patchBackground(text_label, c);
                out = text_label;
            } else if (style == SWITCHER) {
                View swit = LayoutInflater.from(c).inflate(R.layout.beastbar_switcher_h, null, false);
                swit.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                mSwitchCompat = (SwitchCompat) swit.findViewById(R.id.bb_switch);
                mSwitchCompat.setOnCheckedChangeListener(mSwitchCompatListener);
                TextView z = (TextView) swit.findViewById(R.id.bb_text);
                z.setText(c.getString(this.resstring));
                out = swit;
            }

            return out;
        }
    }

    public void setNewButtonLayout(buttonBuilder builder) {
        mRightContainer.removeAllViews();
        mButtonBuilderCache = builder;
        Iterator<itemMenu> h = mButtonBuilderCache.getInternalItems().iterator();
        while (h.hasNext()) {
            itemMenu item = h.next();
            mRightContainer.addView(item.getView(mContext, setup));
        }
        isLayoutSwapped = true;
        setFindIconFunc(null);
    }

    public void setActionLoadingIndicatorStatus(boolean n) {
        if (mButtonBuilderCache == null) return;
        Iterator<itemMenu> h = mButtonBuilderCache.getInternalItems().iterator();
        while (h.hasNext()) {
            itemMenu vb = h.next();
            vb.triggerLoading(n);
        }
    }

    public void setProgressPercentage(int hundred, boolean withPercent) {
        if (mButtonBuilderCache == null) return;
        Iterator<itemMenu> h = mButtonBuilderCache.getInternalItems().iterator();
        while (h.hasNext()) {
            itemMenu vb = h.next();
            vb.setProgressPercentage(hundred, withPercent);
        }
    }

    private ImageButton getDefGenerateRightButtonFunction() {
        ContextThemeWrapper themecontext = new ContextThemeWrapper(mContext, setup.right_side_button_style);
        ImageButton c = new ImageButton(themecontext);
        c.setMaxWidth(mContext.getResources().getDimensionPixelOffset(R.dimen.icon_width));
        c.setClickable(true);
        c.setId(R.id.ios_find_icon);
        patchBackground(c);
        if (default_find_function != null) {
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (default_find_function.get() != null)
                        default_find_function.get().onSearchPress();
                }

            });
        }
        return c;
    }

    private void patchBackground(ImageButton button) {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        button.setBackgroundResource(backgroundResource);
        typedArray.recycle();
    }

    public void setHotIconImageForSearch(@DrawableRes final int r) {
        if (mSearchButton == null) return;
        if (mSearchButton instanceof ImageButton) {
            ImageButton y = (ImageButton) mSearchButton;
            y.setImageResource(r);
            patchBackground(y);
        }
    }

    public void resetRightSideButtonLayout() {
        if (isLayoutSwapped) {
            isLayoutSwapped = false;
            mRightContainer.removeAllViews();
            if (setup.ic_search != 0) {
                mSearchButton = getDefGenerateRightButtonFunction();
                mSearchButton.setImageResource(setup.ic_search);
                isSearchButtonShown = true;
                mRightContainer.addView(mSearchButton);
            }
        }
    }

    public void removeToolbarBackground() {
        this.container.setBackground(null);
    }

    public void changeToolbarBackground(@DrawableRes final int drawres) {
        this.container.setBackgroundResource(drawres);
    }

    public void resetToolbarBackground() {
        this.container.setBackgroundResource(this.setup.ic_background);
    }

    /**
     * assume the start scroll position is 0 as its valuable is x
     * and the height is h
     *
     * @param y_scroll            x
     * @param control_full_height h is static
     */
    public final void setToolbarAlphaHeightScrollable(int y_scroll, int control_full_height) {
        int y1 = y_scroll < 0 ? 0 : y_scroll;
        int yh = y1 > control_full_height ? control_full_height : y_scroll;
        float alpha = Math.abs((float) yh) / (float) control_full_height;
        setToolbarAlpha(1f - alpha);
    }

    /**
     * provide dynamic alpha control by program
     *
     * @param alpha 1.0f
     */
    public final void setToolbarAlpha(Float alpha) {
        //    Drawable mActionBarBackgroundDrawable = container.getBackground();
        // int newAlpha = (int) (alpha * 255);
        container.setAlpha(alpha);
        // container.getBackground().setAlpha(newAlpha);
    }

    public final void resetAllRightSide() {
        mRightContainer.removeAllViews();
    }

    private void init() {
        isBackButtonShown = false;
        isSearchButtonShown = false;


        View v = LayoutInflater.from(mContext).inflate(setup.base_layout_id, null, false);
        mLeftContainer = (LinearLayout) v.findViewById(R.id.bslft_container);
        mBackground = (RelativeLayout) v.findViewById(R.id.ios_background);
        mRightContainer = (LinearLayout) v.findViewById(R.id.bsrft_container);
        mtv = (TextView) v.findViewById(R.id.ios_actionbar_title);
        mImage = (ImageView) v.findViewById(R.id.bb_logo);
        this.container.addView(v);
        main_logo_in = AnimationUtils.loadAnimation(mContext, animaionset.slideLogo.getInAnimation());
        main_logo_out = AnimationUtils.loadAnimation(mContext, animaionset.slideLogo.getOutAnimation());
        title_in = AnimationUtils.loadAnimation(mContext, animaionset.slideLogo.getInAnimation());
        title_out = AnimationUtils.loadAnimation(mContext, animaionset.slideLogo.getOutAnimation());
        back_in = AnimationUtils.loadAnimation(mContext, animaionset.slideText.getInAnimation());
        back_out = AnimationUtils.loadAnimation(mContext, animaionset.slideText.getOutAnimation());
        back_in_from_right = AnimationUtils.loadAnimation(mContext, R.anim.back_in_from_right);
        back_out_to_right = AnimationUtils.loadAnimation(mContext, R.anim.back_out_to_right);

        mSearchButton = (ImageButton) v.findViewById(R.id.ios_find_icon);
        //getDefGenerateRightButtonFunction();
        mTopLeftButton = (ImageButton) v.findViewById(R.id.ios_back_button);

        if (setup.animation_duration > -1) {
            title_in.setDuration(setup.animation_duration);
            title_out.setDuration(setup.animation_duration);
            back_in.setDuration(setup.animation_duration);
            back_out.setDuration(setup.animation_duration);
            back_in_from_right.setDuration(setup.animation_duration);
            back_out_to_right.setDuration(setup.animation_duration);
            main_logo_in.setDuration(setup.animation_duration);
            main_logo_out.setDuration(setup.animation_duration);
        }
        if (setup.animation_duration_logo > -1) {
            main_logo_in.setDuration(setup.animation_duration_logo);
            main_logo_out.setDuration(setup.animation_duration_logo);
        }
        if (setup.tb_title_color != 0) {
            final int color_title = ContextCompat.getColor(mContext, setup.tb_title_color);
            mtv.setTextColor(color_title);
        }
        if (setup.typeface != null) {
            mtv.setTypeface(setup.typeface);
        }
        mTopLeftButton.setVisibility(View.INVISIBLE);
        if (setup.title_line_config == SINGLELINE) {
            mtv.setSingleLine(true);
        } else {
            mtv.setSingleLine(false);
            mtv.setMaxLines(setup.title_line_config);
        }

        if (setup.tb_textsize > 0) {
            mtv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(setup.tb_textsize));
        }

        mtv.requestLayout();
        if (setup.ic_search != 0) {
            mSearchButton.setImageResource(setup.ic_search);
            isSearchButtonShown = true;
        }
        if (setup.ic_center_icon != 0) {
            isCompanyLogoShown = true;
            isTitleShown = false;
            mtv.setVisibility(View.INVISIBLE);
            mImage.setImageResource(setup.ic_center_icon);
            setPositionCenter(mImage);
            //mImage.setImageDrawable(ContextCompat.getDrawable(mContext, setup.ic_center_icon));
        }

        if (setup.ic_left_icon != 0) {
            isCompanyLogoShown = true;
            isTitleShown = false;
            mtv.setVisibility(View.INVISIBLE);
            mImage.setImageResource(setup.ic_left_icon);
            setPositionLeft(mImage);
            // mImage.setImageDrawable(ContextCompat.getDrawable(mContext, setup.ic_left_icon));
        }

        if (setup.title_default != null) {
            mtv.setVisibility(View.VISIBLE);
            mImage.setVisibility(View.GONE);
            mtv.setText(setup.title_default);
            isCompanyLogoShown = false;
            isTitleShown = true;
        }

        if (setup.ic_back != 0) {
            mTopLeftButton.setImageResource(setup.ic_back);
        }

        mLeftContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                leftSide = mLeftContainer.getWidth();
                removeLayoutListener(mLeftContainer, this);
            }
        });

        mRightContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rightSide = mRightContainer.getWidth();
                removeLayoutListener(mRightContainer, this);
            }
        });

        if (!isSearchButtonShown) {
            displayRightFirstIcon(false, false);
        }

        if (setup.save_title_navigation) {
            mTitle = new TitleStorage();
        }
        // mBackground.setBackgroundResource(setup.ic_background);
    }

    private void setPositionLeft(View positiveTarget) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) positiveTarget.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        positiveTarget.setLayoutParams(layoutParams);
    }

    private void setPositionCenter(View positiveTarget) {
        //RelativeLayout.CENTER_IN_PARENT
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) positiveTarget.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        positiveTarget.setLayoutParams(layoutParams);
    }

    public void updateBackgoundColor(@ColorInt int c) {
        mBackground.setBackgroundColor(c);
    }

    public void displayRightFirstIcon(boolean b, boolean withAnimation) {
        final displayManagement dm = new displayManagement(b, withAnimation, mSearchButton);
    }

    private void removeLayoutListener(View layout, ViewTreeObserver.OnGlobalLayoutListener lb) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layout.getViewTreeObserver().removeOnGlobalLayoutListener(lb);
        } else {
            layout.getViewTreeObserver().removeGlobalOnLayoutListener(lb);
        }
        mtv.setMaxWidth(size.x - leftSide - rightSide);
    }

    public View getSearchButtonDefault() {
        return mSearchButton;
    }

    public BeastBar setFindIconFunc(@Nullable final onButtonPressListener func) {
        if (func == null) {
            if (isSearchButtonShown) {
                isSearchButtonShown = false;
                mayCancelAnimation(mSearchButton);
                back_out_to_right.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mSearchButton.setVisibility(View.INVISIBLE);
                    }
                });
                mSearchButton.startAnimation(back_out_to_right);
                mSearchButton.setOnClickListener(null);
            }
            default_find_function = null;
        } else {
            if (!isSearchButtonShown) {
                isSearchButtonShown = true;
                mayCancelAnimation(mSearchButton);
                back_in_from_right.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mSearchButton.setVisibility(View.VISIBLE);

                    }
                });
                mSearchButton.startAnimation(back_in_from_right);
            }
            mSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    func.onSearchPress();
                }
            });
            default_find_function = new WeakReference<onButtonPressListener>(func);
        }
        return this;
    }


    private static void mayCancelAnimation(View anything) {
        if (anything.getAnimation() != null) {
            anything.getAnimation().cancel();
        }
    }


    public BeastBar setActionTitle(final String title) {
        mtv.setText(title);
        if (setup.save_title_navigation) {
            mTitle.saveTitle(title);
        }
        animationTitle();
        return this;
    }

    /**
     * @return the total length of the history
     */
    public int titlePopBack() {
        if (setup.save_title_navigation && mTitle.getHistorySteps() > 1) {
            final String history_title = mTitle.popback();
            mtv.setText(history_title);
            animationTitle();
            return mTitle.getHistorySteps();
        }
        return -1;
    }

    public void resetTitleHistory() {
        if (setup.save_title_navigation) {
            mTitle.reset();
        }
    }

    public BeastBar showMainLogo() {
        if (!isCompanyLogoShown) {
            isCompanyLogoShown = true;
            if (setup.enable_logo_anim) {
                mayCancelAnimation(mImage);
                main_logo_in.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mImage.setVisibility(View.VISIBLE);
                    }
                });
                mImage.startAnimation(main_logo_in);
            } else {
                mImage.setVisibility(View.VISIBLE);
            }
        }
        if (isTitleShown) {
            isTitleShown = false;
            if (setup.enable_logo_anim) {
                mayCancelAnimation(mtv);
                main_logo_out.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mtv.setVisibility(View.INVISIBLE);
                    }
                });
                mtv.startAnimation(main_logo_out);
            } else {
                mtv.setVisibility(View.INVISIBLE);
            }
        }
        return this;
    }

    public boolean isBackButtonShown() {
        return isBackButtonShown;
    }

    public boolean isCompanyLogoShown() {
        return isCompanyLogoShown;
    }


    public BeastBar setBackIconFunc(@Nullable final onButtonPressListener func) {
        if (func == null) {
            if (isBackButtonShown) {
                isBackButtonShown = false;
                mayCancelAnimation(mTopLeftButton);
                back_out.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTopLeftButton.setVisibility(View.INVISIBLE);

                    }
                });
                mTopLeftButton.startAnimation(back_out);
                mTopLeftButton.setOnClickListener(null);
            }
        } else {
            if (!isBackButtonShown) {
                isBackButtonShown = true;
                mayCancelAnimation(mTopLeftButton);
                back_in.setAnimationListener(new ListenerAnimation() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mTopLeftButton.setVisibility(View.VISIBLE);

                    }
                });
                mTopLeftButton.startAnimation(back_in);
            }
            mTopLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    func.onBackPress(titlePopBack());
                }
            });
        }
        return this;
    }

    private class enhancedAnimation extends ListenerAnimation {
        private View target;


        public enhancedAnimation(View target) {
            this.target = target;
        }


        @Override
        public void onAnimationEnd(Animation animation) {
            target.setVisibility(View.GONE);
        }


    }

    private class displayManagement {
        private boolean shown, withanimation;
        private View display_target;

        public displayManagement(
                final boolean shown,
                final boolean withanimation,
                final View target) {
            this.shown = shown;
            this.withanimation = withanimation;
            this.display_target = target;
            init();
        }

        private void init() {
            if (!withanimation) {
                if (shown) {
                    if (display_target.getVisibility() == View.GONE) {
                        display_target.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (display_target.getVisibility() == View.VISIBLE) {
                        display_target.setVisibility(View.GONE);
                    }
                }
            } else {
                if (shown) {
                    if (display_target.getVisibility() == View.GONE) {
                        display_target.setVisibility(View.VISIBLE);
                        mayCancelAnimation(display_target);
                        display_target.startAnimation(main_logo_in);
                    }
                } else {
                    if (display_target.getVisibility() == View.VISIBLE) {
                        mayCancelAnimation(display_target);
                        display_target.getAnimation().setAnimationListener(new enhancedAnimation(display_target));
                        display_target.startAnimation(main_logo_out);
                    }
                }
            }
        }
    }


    private class ListenerAnimation implements Animation.AnimationListener {
        /**
         * <p>Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animation The animation which reached its end.
         */
        @Override
        public void onAnimationEnd(Animation animation) {

        }

        /**
         * <p>Notifies the repetition of the animation.</p>
         *
         * @param animation The animation which was repeated.
         */
        @Override
        public void onAnimationRepeat(Animation animation) {

        }

        /**
         * <p>Notifies the start of the animation.</p>
         *
         * @param animation The started animation.
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }
    }

    public TitleStorage getTitleStorageInstance() {
        return mTitle;
    }


    public final void onSaveInstanceState(Bundle out) {
        if (mTitle != null) mTitle.onStateInstaceState(out);
        out.putBoolean(TitleStorage.IS_LOGOSHOWN, isCompanyLogoShown);
        out.putBoolean(TitleStorage.IS_SEARCHSHOWN, isSearchButtonShown);
        out.putBoolean(TitleStorage.IS_TITLESHOWN, isTitleShown);
        out.putBoolean(TitleStorage.IS_BACKSHOWN, isBackButtonShown);
    }


    public final void onRestoreInstanceState(@Nullable Bundle input) {
        if (input == null) return;
        if (mTitle != null) mTitle.onRestoreInstanceState(input);
        isTitleShown = input.getBoolean(TitleStorage.IS_TITLESHOWN, isTitleShown);
        isCompanyLogoShown = input.getBoolean(TitleStorage.IS_LOGOSHOWN, isCompanyLogoShown);
        isSearchButtonShown = input.getBoolean(TitleStorage.IS_SEARCHSHOWN, isSearchButtonShown);
        isBackButtonShown = input.getBoolean(TitleStorage.IS_BACKSHOWN, isBackButtonShown);

        /**
         * there we can only take care of two features in this library
         */
        if (isTitleShown && mTitle != null) setActionTitle(mTitle.getCurrentTitle());

        if (isCompanyLogoShown) showMainLogo();


    }
}
