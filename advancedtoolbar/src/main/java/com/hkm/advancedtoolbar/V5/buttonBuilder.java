package com.hkm.advancedtoolbar.V5;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.SwitchCompat;

import com.hkm.advancedtoolbar.R;

import java.util.ArrayList;


/**
 * Created by zJJ on 4/30/2016.
 */
public class buttonBuilder {
    public buttonBuilder() {

    }

    private ArrayList<BeastBar.itemMenu> b = new ArrayList<>();

    public ArrayList<BeastBar.itemMenu> getInternalItems() {
        return b;
    }


    public buttonBuilder addApply(Runnable mRun) {
        return addCustomIconButtonFunction(mRun, R.drawable.ic_correct_black_24dp);
    }

    public buttonBuilder addNextText(Runnable mRun) {
        return addBasicTextLabel(R.string.next, mRun);
    }

    public buttonBuilder addApplyText(Runnable mRun) {
        return addBasicTextLabel(R.string.apply, mRun);
    }

    public buttonBuilder addApplyTextMaterial(Runnable mRun) {
        return addBasicTextLabel(R.string.apply, mRun);
    }

    public buttonBuilder addCustomIconButtonFunction(Runnable mRun, @DrawableRes final int res) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setIcon(res);
        item.setExec(mRun);
        b.add(item);
        return this;
    }

    public buttonBuilder addCustomeBadge(@DrawableRes final int res, @ColorRes final int color, @StringRes final int stringres) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setIcon(res);
        item.setBadgetConfig(color, stringres);
        b.add(item);
        return this;
    }

    public buttonBuilder addCustomeBadge(@DimenRes final int fixed_area_width, @DrawableRes final int res, @ColorRes final int color, @StringRes final int stringres) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setIcon(res);
        item.setBadgetConfig(color, stringres);
        b.add(item);
        return this;
    }

    public buttonBuilder addCustomeBadge(@DimenRes final int fixed_area_width, @DrawableRes final int res, @ColorRes final int color, @StringRes final int stringres, @DimenRes final int width_diem) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setIcon(res);
        item.setBadgetConfig(color, stringres, width_diem);
        b.add(item);
        return this;
    }

    public buttonBuilder addRealTimeLoadingIndicator() {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setLoadIndicator();
        b.add(item);
        return this;
    }

    public buttonBuilder addLoadProgressIndicator(String font, @ColorRes int resColor, Context mContext) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setLoadIndicator(
                Typeface.createFromAsset(mContext.getAssets(), font),
                resColor
        );
        b.add(item);
        return this;
    }

    public buttonBuilder addTextLabelButton(final @StringRes int resStr, @ColorRes int resColor, Runnable exe) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setItemTextAdv(resStr, resColor);
        item.setExec(exe);
        b.add(item);
        return this;
    }

    public buttonBuilder addBasicTextLabel(final @StringRes int resStr, @ColorRes int resColor, Runnable exe) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setItemText(resStr, resColor);
        item.setExec(exe);
        b.add(item);
        return this;
    }

    public buttonBuilder addBasicTextLabel(final @StringRes int resStr, Runnable exe) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setItemText(resStr);
        item.setExec(exe);
        b.add(item);
        return this;
    }

    public buttonBuilder addSwitcher(final @StringRes int resStr, @ColorRes int resColor, SwitchCompat.OnCheckedChangeListener exeSwitch) {
        BeastBar.itemMenu item = new BeastBar.itemMenu();
        item.setSwitcher(resColor, resStr, exeSwitch);
        b.add(item);
        return this;
    }

}
