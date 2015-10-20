package com.hkm.advancedtoolbar.socialbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.Util.ErrorMessage;

/**
 * Created by hesk on 24/7/15.
 * com.facebook.messenger.intents.ShareIntentHandler
 * com.facebook.orca
 * jp.naver.line.android.activity.selectchat.SelectChatActivity
 * jp.naver.line.android
 * com.pinterest.activity.create.PinItActivity
 * com.pinterest
 */
public enum Hg {
    pintrest("com.pinterest", R.drawable.pinterest, R.id.social_bar_pinterest),
    facebook("com.facebook.katana", R.drawable.fb, R.id.social_bar_facebook),
    whatsapp("com.whatsapp", R.drawable.whatsapp, R.id.social_bar_whatsapp),
    twitter("com.twitter.android", R.drawable.twitter, R.id.social_bar_twitter),
    message("com.tencent.mm.ui.tools.ShareToTimeLineUI", R.drawable.message, R.id.social_bar_sms);

    private String package_name;
    private int drawbable, extn;

    Hg(final String packagename, final @DrawableRes int fl, final @IdRes int extn) {
        this.package_name = packagename;
        this.drawbable = fl;
        this.extn = extn;
    }

    public String getPackageName() {
        return package_name;
    }

    @DrawableRes
    public int getDrawable() {
        return drawbable;
    }

    @IdRes
    public int Id() {
        return extn;
    }

    public void alert(@Nullable FragmentManager g, @Nullable Context context) {
        if (g == null || context == null) return;
        String message = context.getString(R.string.not_found_share_app);
        ErrorMessage.alert(message, g);
    }

    public static Hg reverseId(final @IdRes int id) throws Exception {
        final int l = Hg.values().length;
        for (int i = 0; i < l; i++) {
            if (Hg.values()[i].Id() == id) {
                return Hg.values()[i];
            }
        }
        throw new Exception("not found here");
    }
}
