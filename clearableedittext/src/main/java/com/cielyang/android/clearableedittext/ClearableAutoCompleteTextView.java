package com.cielyang.android.clearableedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 */
public class ClearableAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private final ClearableHelper mClearableHelper;

    public ClearableAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.design.R.attr.autoCompleteTextViewStyle);
    }

    public ClearableAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mClearableHelper = new ClearableHelper(this);
        mClearableHelper.initFromAttributes(attrs, defStyleAttr);
    }

    @Override public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return mClearableHelper.onSaveInstanceState(superState);
    }

    @Override public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(mClearableHelper.onRestoreInstanceState(state));
    }

    @Override protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        mClearableHelper.onFocusChanged(focused);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override public boolean onTouchEvent(MotionEvent event) {
        return mClearableHelper.onTouchEvent(event) && super.onTouchEvent(event);
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        mClearableHelper.setOnClearListener(onClearListener);
    }
}
