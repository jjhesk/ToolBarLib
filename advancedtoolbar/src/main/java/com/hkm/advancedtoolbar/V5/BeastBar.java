package com.hkm.advancedtoolbar.V5;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 28/10/15.
 */
public class BeastBar {
    private String text_title;

    private Context mContext;
    private Toolbar container;
    private TextView mtv;
    private ImageView mImage;
    private RelativeLayout mBackground;
    private LinearLayout mRightContainer, mLeftContainer;
    private TintImageView mSearchButton, mTopLeftButton;
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
    private boolean isCompanyLogoShown, isTitleShown, isBackButtonShown, isSearchButtonShown;
    private Builder setup;
    private int leftSide = 0, rightSide = 0;

    public static class Builder {
        private int ic_company, ic_search, ic_back, ic_background, tb_textsize = 0, tb_title_color = 0;
        private Typeface typeface;
        private String title_default;

        public Builder() {

        }

        public Builder setFontFace(@NonNull Context mContext, @NonNull final String fontNameInFontFolder) {
            typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + fontNameInFontFolder);
            return this;
        }

        public Builder companyIcon(@DrawableRes final int res) {
            this.ic_company = res;
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
            return this;
        }
    }

    private Point size = new Point();

    public static BeastBar withToolbar(AppCompatActivity res, Toolbar original, final Builder beastbuilder) {
        Display display = res.getWindowManager().getDefaultDisplay();
        res.setSupportActionBar(original);
        ActionBar actionbar = res.getSupportActionBar();
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayShowHomeEnabled(false);
        actionbar.setDefaultDisplayHomeAsUpEnabled(false);
        original.setBackgroundResource(beastbuilder.ic_background);
        View homeIcon = res.findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home : android.R.id.home);
        // ((View) homeIcon.getParent()).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        //  ((View) homeIcon).setVisibility(View.GONE);


        final BeastBar bb = new BeastBar(res);
        bb.setToolBar(original);
        bb.setup = beastbuilder;
        display.getSize(bb.size);
        bb.init();
        bb.adjustmentTV();
        return bb;
    }

    public BeastBar(Context res) {
        this.mContext = res;
    }

    private BeastBar setToolBar(Toolbar tb) {
        this.container = tb;
        return this;
    }

    private void adjustmentTV() {
        mtv.requestLayout();
        if (setup.tb_textsize > 0) {
            mtv.setTextSize(mContext.getResources().getDimensionPixelSize(setup.tb_textsize));
        }
    }

    private void init() {
        isBackButtonShown = false;
        isSearchButtonShown = false;
        View v = LayoutInflater.from(mContext).inflate(R.layout.beastbar, null, false);
        mLeftContainer = (LinearLayout) v.findViewById(R.id.left_container);
        mBackground = (RelativeLayout) v.findViewById(R.id.ios_background);
        mRightContainer = (LinearLayout) v.findViewById(R.id.right_container);
        mtv = (TextView) v.findViewById(R.id.ios_actionbar_title);
        mImage = (ImageView) v.findViewById(R.id.logo_k);
        mSearchButton = (TintImageView) v.findViewById(R.id.ios_find_icon);
        mTopLeftButton = (TintImageView) v.findViewById(R.id.ios_back_button);
        this.container.addView(v);
        main_logo_in = AnimationUtils.loadAnimation(mContext, R.anim.company_logo_in);
        main_logo_out = AnimationUtils.loadAnimation(mContext, R.anim.company_logo_out);
        title_in = AnimationUtils.loadAnimation(mContext, R.anim.company_logo_in);
        title_out = AnimationUtils.loadAnimation(mContext, R.anim.company_logo_out);
        back_in = AnimationUtils.loadAnimation(mContext, R.anim.back_button_in);
        back_out = AnimationUtils.loadAnimation(mContext, R.anim.back_button_out);
        back_in_from_right = AnimationUtils.loadAnimation(mContext, R.anim.back_in_from_right);
        back_out_to_right = AnimationUtils.loadAnimation(mContext, R.anim.back_out_to_right);
        if (setup.tb_title_color != 0) {
            final int color_title = ContextCompat.getColor(mContext, setup.tb_title_color);
            mtv.setTextColor(color_title);
        }
        if (setup.typeface != null) {
            mtv.setTypeface(setup.typeface);
        }
        mTopLeftButton.setVisibility(View.INVISIBLE);

        if (setup.ic_search != 0) {
            mSearchButton.setImageResource(setup.ic_search);
            isSearchButtonShown = true;
        }

        if (setup.ic_company != 0) {
            mImage.setImageResource(setup.ic_company);
            mtv.setVisibility(View.INVISIBLE);
            isCompanyLogoShown = true;
            isTitleShown = false;
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
        // mBackground.setBackgroundResource(setup.ic_background);
    }

    public void displayRightFirstIcon(boolean b, boolean withAnimation) {
        final displayManagement dm = new displayManagement(b, withAnimation, mSearchButton);
    }

    private void removeLayoutListener(View layout, ViewTreeObserver.OnGlobalLayoutListener lb) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            layout.getViewTreeObserver().removeOnGlobalLayoutListener(lb);
        } else {
            layout.getViewTreeObserver().removeGlobalOnLayoutListener(lb);
        }
        mtv.setMaxWidth(size.x - leftSide - rightSide);
    }

    public BeastBar setFindIconFunc(@Nullable final Runnable func) {
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
                    func.run();
                }
            });
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

        if (!isTitleShown) {
            isTitleShown = true;
            mayCancelAnimation(mtv);
            main_logo_in.setAnimationListener(new ListenerAnimation() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mtv.setVisibility(View.VISIBLE);
                }
            });
            mtv.startAnimation(main_logo_in);
        }
        if (isCompanyLogoShown) {
            mayCancelAnimation(mImage);
            isCompanyLogoShown = false;
            main_logo_out.setAnimationListener(new ListenerAnimation() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mImage.setVisibility(View.INVISIBLE);
                }
            });
            mImage.startAnimation(main_logo_out);
        }
        return this;
    }

    public BeastBar showMainLogo() {
        if (!isCompanyLogoShown) {
            isCompanyLogoShown = true;
            mayCancelAnimation(mImage);
            main_logo_in.setAnimationListener(new ListenerAnimation() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mImage.setVisibility(View.VISIBLE);
                }
            });
            mImage.startAnimation(main_logo_in);
        }
        if (isTitleShown) {
            isTitleShown = false;
            mayCancelAnimation(mtv);
            main_logo_out.setAnimationListener(new ListenerAnimation() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    mtv.setVisibility(View.INVISIBLE);
                }
            });
            mtv.startAnimation(main_logo_out);
        }
        return this;
    }


    public BeastBar setBackIconFunc(@Nullable final Runnable func) {
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
                    func.run();
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


}
