package com.hkm.advancedtoolbar.V5;

import android.support.annotation.AnimRes;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 20/1/16.
 */
public enum animaionset {


    slideLogo(R.anim.company_logo_in, R.anim.company_logo_out),

    slideText(R.anim.back_button_in, R.anim.back_button_out);

    private int in, out;

    animaionset(@AnimRes final int in, @AnimRes final int out) {
        this.in = in;
        this.out = out;
    }

    @AnimRes
    public int getOutAnimation() {
        return out;
    }

    @AnimRes
    public int getInAnimation() {
        return in;
    }
}
