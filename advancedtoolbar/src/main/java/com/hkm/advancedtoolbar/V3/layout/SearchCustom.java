package com.hkm.advancedtoolbar.V3.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;
import com.hkm.advancedtoolbar.V3.TopBarManager;
import com.hkm.advancedtoolbar.iOS.ActionBarActionListener;
import com.hkm.advancedtoolbar.iOS.SearchCustomActionBar;
import com.hkm.advancedtoolbar.iOS.iOSActionBarWorker;

/**
 * Created by hesk on 16/7/15.
 */
public class SearchCustom<TV extends TextView, EditT extends EditText> implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {


    private String default_placeholder = "Search on Hypebeast";
    private ImageView wrappedSearchCloseBtn;
    private EditT wrappedEditText;
    private TopBarManager.searchBarListener searchListener;
    private TV searchTextHint;
    private ActionBar control;
    private RelativeLayout rl;
    private final Runnable fadeInDone = new Runnable() {
        @Override
        public void run() {
            wrappedEditText.setEnabled(true);
            wrappedSearchCloseBtn.setEnabled(true);
        }
    };
    private View getview;
    private Context mcontext;


    enum behavior {
        SHOW_KEYBOARD_BEFORE_ANIMATION,
        SHOW_KEYBOARD_AFTER_ANIMATION
    }

    private behavior keyboard_prioity;

    public SearchCustom(View getcustomview) {
        getview = getcustomview;
        wrappedEditText = (EditT) getcustomview.findViewById(R.id.ios_actionbar_wrapped_search);
        wrappedEditText.addTextChangedListener(this);
        wrappedEditText.setOnEditorActionListener(this);
        wrappedSearchCloseBtn = (ImageView) getcustomview.findViewById(R.id.ios_search_close_btn);
        wrappedSearchCloseBtn.setOnClickListener(this);
        wrappedEditText.setEnabled(false);
        wrappedSearchCloseBtn.setEnabled(false);
        keyboard_prioity = behavior.SHOW_KEYBOARD_AFTER_ANIMATION;
        rl = (RelativeLayout) getcustomview.findViewById(R.id.ios_layout_wrapper);
        rl.setAlpha(0f);
        rl.animate().alpha(1f).withEndAction(fadeInDone);
        revealWithAnimation(false);
        mcontext = getcustomview.getContext();
    }


    @SuppressLint("WrongViewCast")
    protected void revealWithAnimation(boolean bool) {
        searchTextHint = (TV) getview.findViewById(R.id.ios_hinting);
        if (bool) {
            final Animation anim = AnimationUtils.loadAnimation(getview.getContext(), R.anim.slidefromright);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    showkeyboard();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            searchTextHint.setText(default_placeholder);
            searchTextHint.startAnimation(anim);
        } else {
            searchTextHint.setText(default_placeholder);
            showkeyboard();
        }
    }


    /**
     * Set the search listener to be used on this search
     *
     * @param searchListener the search listener to be used on this search
     */
    public void setOnSearchListener(TopBarManager.searchBarListener searchListener) {
        this.searchListener = searchListener;
    }

    /**
     * get the object item in the view
     *
     * @return the customized edit text field
     */
    public EditT getSearchField() {
        return wrappedEditText;
    }

    /**
     * @return the current text on the search
     */
    public CharSequence getSearchText() {
        return wrappedEditText.getText();
    }

    /**
     * Set the search placeholder (hint)
     *
     * @param placeholder the placeholder
     * @see #setSearchPlaceholder(int)
     */
    public void setSearchPlaceholder(CharSequence placeholder) {
        wrappedEditText.setHint(placeholder);
    }

    /**
     * Set the search placeholder (hint)
     *
     * @param placeholderRes the placeholder
     * @see #setSearchPlaceholder(CharSequence)
     */
    public void setSearchPlaceholder(int placeholderRes) {
        wrappedEditText.setHint(placeholderRes);
    }


    protected String getplaccholder() {
        return default_placeholder;
    }

    protected Handler hlr = new Handler();

    @Override
    public void onTextChanged(CharSequence constraint, int start, int count, int after) {
        if (searchListener != null) {
            searchListener.onKeySearchLetter(constraint.toString());
            return;
        }
        Log.w(getClass().getName(), "SearchListener == null");
    }

    @Override
    public void onClick(View e) {
        if (e.getId() == R.id.ios_search_close_btn) {
            if (searchListener != null) {
                hidekeyboard();
                if (control != null)
                    control.invalidateOptionsMenu();
                searchListener.onRestoreToNormal(control);
            }
        }
    }

    public void hidekeyboard() {
        hlr.postDelayed(new Runnable() {
            @Override
            public void run() {
                wrappedEditText.setText("");
                InputMethodManager m = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
                m.hideSoftInputFromWindow(wrappedEditText.getWindowToken(), 0);
                //  imm.toggleSoftInputFromWindow(wrappedEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                // imm.showSoftInput(wrappedEditText, 0);
                // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                //  wrappedEditText.clearFocus();
            }
        }, 1);
    }

    public void showkeyboard() {
        hlr.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
                // imm.toggleSoftInputFromWindow(wrappedEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                m.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                // imm.showSoftInput(wrappedEditText, InputMethodManager.SHOW_IMPLICIT);
                // imm.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                wrappedEditText.requestFocus();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence constraint, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() == 0) {
            searchTextHint.setText(getplaccholder());
        } else {
            searchTextHint.setText("");
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

        if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) {

            if (searchListener != null) {
                searchListener.onKeySearchLetter(textView.getText().toString());
                return true;
            }

        } else if (actionId == EditorInfo.IME_ACTION_SEARCH
                || event == null
                || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (searchListener != null) {
                InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                searchListener.onKeySearchStartConfirm(textView.getText().toString());
                return true;
            }
        }


        Log.w(getClass().getName(), "SearchListener == null");
        return false;
    }
}

