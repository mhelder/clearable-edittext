package com.cielyang.android.clearableedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 */
public class ClearableTextInputEditText extends TextInputEditText {

    private final ClearableHelper mClearableHelper;

    public ClearableTextInputEditText(Context context) {
        this(context, null);
    }

    public ClearableTextInputEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.design.R.attr.editTextStyle);
    }

    public ClearableTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mClearableHelper.onFocusChanged(focused);
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override public boolean onTouchEvent(MotionEvent event) {
        return mClearableHelper.onTouchEvent(event) && super.onTouchEvent(event);
    }

    public void setOnClearListener(OnClearListener onClearListener) {
        mClearableHelper.setOnClearListener(onClearListener);
    }
}
