package com.hkm.advancedtoolbar.materialsearch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.widget.Filter;
import android.widget.FrameLayout;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 10/11/15.
 */
public abstract class SearchViewBase extends FrameLayout implements Filter.FilterListener {

    protected Context mContext;

    public SearchViewBase(Context context) {
        this(context, null);
    }

    public SearchViewBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        mContext = context;
        initiateView();
        initStyle(attrs, defStyleAttr);
    }

    protected abstract void initiateView();

    public abstract void setTextColor(@ColorInt int color);

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    public abstract void setVoiceIcon(Drawable drawable);

    public abstract void setCloseIcon(Drawable drawable);

    public abstract void setBackIcon(Drawable drawable);

    public abstract void setHintTextColor(@ColorInt int color);

    public abstract void setHint(CharSequence color);

    public abstract void setOverLay(@ColorInt int color);

    public abstract void setSuggestionBackground(Drawable color);

    protected void initStyle(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.SearchViewBase, defStyleAttr, 0);
        if (a != null) {
            if (a.hasValue(R.styleable.SearchViewBase_searchBackground)) {
                setBackground(a.getDrawable(R.styleable.SearchViewBase_searchBackground));
            }

            if (a.hasValue(R.styleable.SearchViewBase_android_textColor)) {
                setTextColor(a.getColor(R.styleable.SearchViewBase_android_textColor, 0));
            }

            if (a.hasValue(R.styleable.SearchViewBase_android_textColorHint)) {
                setHintTextColor(a.getColor(R.styleable.SearchViewBase_android_textColorHint, 0));
            }

            if (a.hasValue(R.styleable.SearchViewBase_android_hint)) {
                setHint(a.getString(R.styleable.SearchViewBase_android_hint));
            }

            if (a.hasValue(R.styleable.SearchViewBase_searchVoiceIcon)) {
                setVoiceIcon(a.getDrawable(R.styleable.SearchViewBase_searchVoiceIcon));
            }

            if (a.hasValue(R.styleable.SearchViewBase_searchCloseIcon)) {
                setCloseIcon(a.getDrawable(R.styleable.SearchViewBase_searchCloseIcon));
            }

            if (a.hasValue(R.styleable.SearchViewBase_searchBackIcon)) {
                setBackIcon(a.getDrawable(R.styleable.SearchViewBase_searchBackIcon));
            }

            if (a.hasValue(R.styleable.SearchViewBase_searchOverlayColor)) {
                setOverLay(a.getColor(R.styleable.SearchViewBase_searchOverlayColor, 0));
            }

            if (a.hasValue(R.styleable.SearchViewBase_searchSuggestionBackground)) {
                setSuggestionBackground(a.getDrawable(R.styleable.SearchViewBase_searchSuggestionBackground));
            }

            a.recycle();
        }
    }



    static class SavedState extends BaseSavedState {
        String query;
        boolean isSearchOpen;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.query = in.readString();
            this.isSearchOpen = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(query);
            out.writeInt(isSearchOpen ? 1 : 0);
        }

        //required field that makes Parcelables from a Parcel
        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

    public interface OnQueryTextListener {

        /**
         * Called when the user submits the query. This could be due to a key press on the
         * keyboard or due to pressing a submit button.
         * The listener can override the standard behavior by returning true
         * to indicate that it has handled the submit request. Otherwise return false to
         * let the SearchView handle the submission by launching any associated intent.
         *
         * @param query the query text that is to be submitted
         * @return true if the query has been handled by the listener, false to let the
         * SearchView perform the default action.
         */
        boolean onQueryTextSubmit(String query);

        /**
         * Called when the query text is changed by the user.
         *
         * @param newText the new content of the query text field.
         * @return false if the SearchView should perform the default action of showing any
         * suggestions if available, true if the action was handled by the listener.
         */
        boolean onQueryTextChange(String newText);
    }

    public interface SearchViewListener {
        void onSearchViewShown();

        void onSearchViewClosed();
    }

}
