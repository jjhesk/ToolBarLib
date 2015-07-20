package com.hkm.advancedtoolbar.iOS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 14/5/15.
 */
public class SearchCustomActionBar<TV extends TextView, EditT extends EditText> implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {
    public enum LAYOUT {
        classic_1(R.layout.material_search_ios_classic),
        classic_2(R.layout.material_search_ios);
        private final int id;

        private LAYOUT(int id) {
            this.id = id;
        }

        public int getResourceId() {
            return id;
        }
    }

    private String default_placeholder = "Search on Hypebeast";
    private final ImageView wrappedSearchCloseBtn;
    private final EditT wrappedEditText;
    private SearchCustomActionBar.OnSearchListener searchListener;
    private TV searchTextHint;
    private final iOSActionBarWorker control;
    private final RelativeLayout rl;
    private final Runnable fadeInDone = new Runnable() {
        @Override
        public void run() {
            wrappedEditText.setEnabled(true);
            wrappedSearchCloseBtn.setEnabled(true);
        }
    };
    private final View getview;
    private final Context mcontext;

    enum behavior {
        SHOW_KEYBOARD_BEFORE_ANIMATION,
        SHOW_KEYBOARD_AFTER_ANIMATION
    }

    private behavior keyboard_prioity;

    @SuppressLint("WrongViewCast")
    public SearchCustomActionBar(iOSActionBarWorker isoactionbar, @Nullable int measurewith) {
        getview = isoactionbar.ab.getCustomView();
        wrappedEditText = (EditT) getview.findViewById(R.id.wrapped_search);
        wrappedEditText.addTextChangedListener(this);
        wrappedEditText.setOnEditorActionListener(this);

        wrappedSearchCloseBtn = (ImageView) getview.findViewById(R.id.search_close_btn);
        wrappedSearchCloseBtn.setOnClickListener(this);
        wrappedEditText.setEnabled(false);
        wrappedSearchCloseBtn.setEnabled(false);
        keyboard_prioity = behavior.SHOW_KEYBOARD_AFTER_ANIMATION;
        rl = (RelativeLayout) getview.findViewById(R.id.layout_wrapper);
        rl.setAlpha(0f);
        rl.animate().alpha(1f).withEndAction(fadeInDone);

        revealWithAnimation(false);
        control = isoactionbar;
        mcontext = getview.getContext();
    }

    @SuppressLint("WrongViewCast")
    protected void revealWithAnimation(boolean bool) {
        //if (bool) {

        final Animation anim = AnimationUtils.loadAnimation(getview.getContext(), R.anim.slidefromright);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // searchTextHint.setVisibility(View.GONE);
                // control.afterSearchBarShown(SearchCustomActionBar.this);
                showkeyboard();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        searchTextHint = (TV) getview.findViewById(R.id.hinting);
        searchTextHint.setText(default_placeholder);
        searchTextHint.startAnimation(anim);
        //} else {
        //  wrappedEditText.setHint(default_placeholder);
        // searchTextHint.setVisibility(View.INVISIBLE);
        // }
    }


    /**
     * Set the search listener to be used on this search
     *
     * @param searchListener the search listener to be used on this search
     * @see OnSearchListener
     */
    public void setOnSearchListener(OnSearchListener searchListener) {
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
            searchListener.onQuickSearch(this, constraint);
            return;
        }
        Log.w(getClass().getName(), "SearchListener == null");
    }

    @Override
    public void onClick(View e) {
        if (e.getId() == R.id.search_close_btn) {
            if (searchListener != null) {
                control.closeSearchBar();
                hidekeyboard();
                searchListener.onClose();
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
                //           imm.toggleSoftInputFromWindow(wrappedEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                m.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                //         imm.showSoftInput(wrappedEditText, InputMethodManager.SHOW_IMPLICIT);
                //   imm.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
                searchListener.onSearchHint(this, textView.getText());
                return true;
            }

        } else if (actionId == EditorInfo.IME_ACTION_SEARCH
                || event == null
                || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            if (searchListener != null) {
                InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                searchListener.onSearchClick(this, textView.getText());
                return true;
            }
        }


        Log.w(getClass().getName(), "SearchListener == null");
        return false;
    }

    /**
     * This interface is an custom method to wrapp the
     * TextWatcher implementation and provide the search constraint
     *
     * @author Hesk Kam
     */
    public interface OnSearchListener {

        /**
         * This method is called every time the EditText change it content
         *
         * @param searchview the searchview
         * @param constraint the current input data
         */
        void onQuickSearch(SearchCustomActionBar searchview, CharSequence constraint);

        /**
         * This method is called when the user press the search button on the keyboard
         *
         * @param searchview the searchview
         * @param constraint the current input data
         */
        void onSearchHint(SearchCustomActionBar searchview, CharSequence constraint);

        /**
         * This method is called when the click is pressed
         */
        void onClose();

        /**
         * This is called when the search button is clicked
         *
         * @param searchview the searchview
         * @param query      the string in chars of the search query
         */
        void onSearchClick(SearchCustomActionBar searchview, CharSequence query);
    }
}
