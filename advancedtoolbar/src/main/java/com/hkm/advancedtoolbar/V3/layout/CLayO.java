package com.hkm.advancedtoolbar.V3.layout;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.V3.LayoutAsset;
import com.hkm.advancedtoolbar.V3.TopBarManager;

/**
 * Created by hesk on 31/7/15.
 */
public class CLayO implements manifestlayout, View.OnClickListener {
    private LayoutAsset layoutRes;
    private Context mContext;

    public interface OnInteract {
        void OnClick(int resId);
    }

    private TopBarManager.Builder topBuilder;
    private OnInteract customListener;

    public CLayO(final LayoutAsset layoutR, Context c, TopBarManager.Builder mb, OnInteract b) {
        this(layoutR, c, mb);
        customListener = b;
    }

    public CLayO(final LayoutAsset layoutR, Context c, TopBarManager.Builder mb) {
        this.layoutRes = layoutR;
        this.mContext = c;
        this.topBuilder = mb;
        if (mb.customListener != null) {
            customListener = mb.customListener;
        }
    }

    @Override
    public void init(Toolbar toolbar) {
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.TOP;
        toolbar.addView(inflate(), layoutParams);
    }

    private TextView numberView;

    @Override
    public View inflate() {
        final View t = ToolbarHelper.generateView(layoutRes.getResourceId(), mContext);
        if (layoutRes == LayoutAsset.i_logo_ii) {
            ImageView k1 = (ImageView) t.findViewById(R.id.i_kl1);
            ImageView k2 = (ImageView) t.findViewById(R.id.i_kr1);
            ImageView k3 = (ImageView) t.findViewById(R.id.i_kr2);
            ImageView k4 = (ImageView) t.findViewById(R.id.logo_k);
            if (topBuilder.getCompanyLogoRes() != 0)
                k4.setImageResource(topBuilder.getCompanyLogoRes());
            final int[] overrides = topBuilder.getOverrideIcons_i_t_ii();
            if (overrides[0] != 0)
                k1.setImageResource(overrides[0]);

            if (overrides[1] != 0)
                k2.setImageResource(overrides[1]);

            if (overrides[2] != 0)
                k3.setImageResource(overrides[2]);

            k1.setOnClickListener(this);
            k2.setOnClickListener(this);
            k3.setOnClickListener(this);
        } else if (layoutRes == LayoutAsset.i_logo_ir) {
            ImageView k1 = (ImageView) t.findViewById(R.id.i_kl1);
            ImageView k2 = (ImageView) t.findViewById(R.id.i_kr2);
            FrameLayout dynamic_button_frame = (FrameLayout) t.findViewById(R.id.liveicon_counterpanel);
            ImageView k3 = (ImageView) t.findViewById(R.id.liveiconloc);
            ImageView k4 = (ImageView) t.findViewById(R.id.logo_k);

            if (topBuilder.getCompanyLogoRes() != 0)
                k4.setImageResource(topBuilder.getCompanyLogoRes());
            final int[] overrides = topBuilder.getOverrideIcons_i_t_ii();
            if (overrides[0] != 0)
                k1.setImageResource(overrides[0]);

            if (overrides[1] != 0)
                k2.setImageResource(overrides[1]);

            if (overrides[2] != 0)
                k3.setImageResource(overrides[2]);
            numberView = (TextView) t.findViewById(R.id.liveicon_text);
            k1.setOnClickListener(this);
            k2.setOnClickListener(this);
            dynamic_button_frame.setOnClickListener(this);
        } else if (layoutRes == LayoutAsset.classic_3) {
            mSearchCustom = new SearchCustom()
        }

        return t;
    }

    private SearchCustom mSearchCustom;

    public SearchCustom getSearchObject() {
        return mSearchCustom;
    }

    public void updateNumber(String tt) {
        numberView.setText(tt);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (customListener != null)
            customListener.OnClick(v.getId());
    }
}
