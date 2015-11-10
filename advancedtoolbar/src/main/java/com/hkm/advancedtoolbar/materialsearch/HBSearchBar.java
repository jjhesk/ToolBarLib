package com.hkm.advancedtoolbar.materialsearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hkm.advancedtoolbar.R;

/**
 * Created by hesk on 10/11/15.
 */
public class HBSearchBar extends MaterialSearchView {


    public HBSearchBar(Context context) {
        this(context, null);
    }

    public HBSearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HBSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initiateView();
    }

    @Override
    protected void initiateView() {
        LayoutInflater.from(mContext).inflate(setLayout(1), this, true);
        mSearchLayout = findViewById(R.id.ios_layout_wrapper);

        mSearchTopBar = (RelativeLayout) mSearchLayout.findViewById(R.id.search_top_bar);
        mSuggestionsListView = (ListView) mSearchLayout.findViewById(R.id.suggestion_list);
        mSearchSrcTextView = (EditText) mSearchLayout.findViewById(R.id.searchTextView);
        mBackBtn = (ImageButton) mSearchLayout.findViewById(R.id.action_up_btn);
        mVoiceBtn = (ImageButton) mSearchLayout.findViewById(R.id.action_voice_btn);
        mEmptyBtn = (ImageButton) mSearchLayout.findViewById(R.id.action_empty_btn);
        mTintView = mSearchLayout.findViewById(R.id.transparent_view);

        mSearchSrcTextView.setOnClickListener(mOnClickListener);
        mBackBtn.setOnClickListener(mOnClickListener);
        mVoiceBtn.setOnClickListener(mOnClickListener);
        mEmptyBtn.setOnClickListener(mOnClickListener);
        mTintView.setOnClickListener(mOnClickListener);

        allowVoiceSearch = false;

        showVoice(true);

        initSearchView();

        mSuggestionsListView.setVisibility(GONE);
    }

}
