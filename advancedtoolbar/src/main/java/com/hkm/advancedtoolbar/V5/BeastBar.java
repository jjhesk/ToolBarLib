package com.hkm.advancedtoolbar.V5;

import android.content.Context;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.TintImageView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
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
    private Animation main_logo_in, main_logo_out, title_in, title_out, back_in, back_out;
    private boolean isCompanyLogoShown, isTitleShown, isBackButtonShown;
    private Builder setup;

    public static class Builder {
        private int ic_company, ic_search, ic_back, ic_background;

        public Builder() {

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
    }

    public static BeastBar withToolbar(AppCompatActivity res, Toolbar original, final Builder beastbuilder) {
        res.setSupportActionBar(original);
        ActionBar actionbar = res.getSupportActionBar();
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setDisplayShowHomeEnabled(false);
        actionbar.setDefaultDisplayHomeAsUpEnabled(false);
        original.setBackgroundResource(beastbuilder.ic_background);
        View homeIcon = res.findViewById(
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        android.R.id.home : android.R.id.home);
        // ((View) homeIcon.getParent()).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        //  ((View) homeIcon).setVisibility(View.GONE);


        final BeastBar bb = new BeastBar(res);
        bb.setToolBar(original);
        bb.setup = beastbuilder;


        bb.init();
        return bb;
    }

    public BeastBar(Context res) {
        this.mContext = res;
    }

    private BeastBar setToolBar(Toolbar tb) {
        this.container = tb;
        return this;
    }

    private void init() {
        isCompanyLogoShown = true;
        isTitleShown = false;
        isBackButtonShown = false;
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
        mtv.setVisibility(View.INVISIBLE);
        mTopLeftButton.setVisibility(View.INVISIBLE);
        mSearchButton.setImageResource(setup.ic_search);
        mImage.setImageResource(setup.ic_company);
        mTopLeftButton.setImageResource(setup.ic_back);
        // mBackground.setBackgroundResource(setup.ic_background);
    }


    public BeastBar setFindIconFunc(@Nullable final Runnable func) {
        if (func == null) {
            mSearchButton.setOnClickListener(null);
        } else mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func.run();
            }
        });
        return this;
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
}
