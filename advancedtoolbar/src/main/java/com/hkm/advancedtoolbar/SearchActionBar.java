package com.hkm.advancedtoolbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hesk on 12/5/15.
 *
 * @Hesk Kam
 */
public class SearchActionBar extends FrameLayout implements TextWatcher, TextView.OnEditorActionListener, View.OnClickListener {

    private ImageView wrappedSearchCloseBtn;
    private EditText wrappedEditText;
    private OnSearchListener searchListener;

    public SearchActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SearchActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchActionBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public SearchActionBar(Context context) {
        super(context);
        init(context);
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
     * Sets the search text
     *
     * @param searchText the text to set on the search
     * @see #setSearchText(int)
     */
    public void setSearchText(CharSequence searchText) {
        wrappedEditText.setText(searchText);
    }

    /**
     * Sets the search text using a resource
     *
     * @param searchTextRes the resource to set the text
     * @see #setSearchText(CharSequence)
     */
    public void setSearchText(int searchTextRes) {
        wrappedEditText.setText(searchTextRes);
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

    private Context mcontext;

    /**
     * Inflate the layout to this FrameLayout wrapper
     *
     * @param context for inflate views
     */
    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.search_material, this, true);
        wrappedEditText = (EditText) findViewById(R.id.wrapped_search);
        wrappedEditText.addTextChangedListener(this);
        wrappedEditText.setOnEditorActionListener(this);
        wrappedSearchCloseBtn = (ImageView) findViewById(R.id.search_close_btn);
        wrappedSearchCloseBtn.setOnClickListener(this);
        mcontext = context;
     /*   final int maxWidth = a.getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
        if (maxWidth != -1) {
            setMaxWidth(maxWidth);
        }*/


    }

    @Override
    public void beforeTextChanged(CharSequence constraint, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence constraint, int start, int count, int after) {
        if (searchListener != null) {
            searchListener.onSearch(this, constraint);
            return;
        }
        Log.w(getClass().getName(), "SearchListener == null");
    }

    @Override
    public void onClick(View e) {
        if (e.getId() == R.id.search_close_btn) {
            if (searchListener != null) {
                wrappedEditText.setText("");
                searchListener.onClose();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
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
        void onSearch(SearchActionBar searchview, CharSequence constraint);

        /**
         * This method is called when the user press the search button on the keyboard
         *
         * @param searchview the searchview
         * @param constraint the current input data
         */
        void onSearchHint(SearchActionBar searchview, CharSequence constraint);

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
        void onSearchClick(SearchActionBar searchview, CharSequence query);
    }
}
