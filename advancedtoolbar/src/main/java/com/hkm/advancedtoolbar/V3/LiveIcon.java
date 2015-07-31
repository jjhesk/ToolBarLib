package com.hkm.advancedtoolbar.V3;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.V3.layout.CLayO;


/**
 * Created by hesk on 29/7/15.
 */
public class LiveIcon {
    private int layout, icon;
    private TextView holder;
    private ImageView image_view;
    private String holder_text = "w99";
    private Activity activity;
    private CLayO usingOClay;


    public LiveIcon(final @LayoutRes int layout, final @DrawableRes int icon) {
        this.layout = layout;
        this.icon = icon;
    }

    public LiveIcon(final Activity act) {
        this.layout = R.layout.dynamic_icon;
        this.icon = android.R.drawable.btn_star;
        this.activity = act;
    }

    public LiveIcon act(final Activity act) {
        this.activity = act;
        return this;
    }

    public LiveIcon customact(final CLayO act) {
        this.usingOClay = act;
        return this;
    }

    public void onOptionItemInit(final Menu m, final @IdRes int menuId) {
        MenuItem item = m.findItem(menuId);
        initView(item);
    }

    public void update(final MenuItem m, final int _num) {
        holder_text = String.valueOf(_num);
        initView(m);
    }

    public void update(final int _num) {
        holder_text = String.valueOf(_num);
        this.usingOClay.updateNumber(holder_text);
    }

    private View initView(final MenuItem item_menu) {
        //  View vm = ToolbarHelper.generateView(this.layout, n);
        item_menu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        View vm = item_menu.setActionView(this.layout).getActionView();
        holder = (TextView) vm.findViewById(R.id.liveicon_text);
        image_view = (ImageView) vm.findViewById(R.id.liveiconloc);
        if (this.icon != 0) {
            this.image_view.setImageResource(this.icon);
        }
        if (this.holder != null && holder_text != null) {
            holder.setText(holder_text);
        }
        FrameLayout frm = (FrameLayout) vm.findViewById(R.id.liveicon_counterpanel);
        frm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onOptionsItemSelected(item_menu);
            }
        });
        item_menu.setVisible(true);
        return vm;
    }
}
