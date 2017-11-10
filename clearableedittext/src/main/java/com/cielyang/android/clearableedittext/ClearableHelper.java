package com.cielyang.android.clearableedittext;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mhelder on 10/11/2017.
 */

final class ClearableHelper implements TextWatcher {

    @DrawableRes private static final int DEFAULT_CLEAR_ICON_RES_ID = R.drawable.ic_clear;

    private final TextView mTarget;

    private Drawable mClearIconDrawable;

    private boolean mIsClearIconShown = false;
    private boolean mClearIconDrawWhenFocused = true;

    ClearableHelper(TextView target) {
        mTarget = target;
        mTarget.addTextChangedListener(this);
    }

    void initFromAttributes(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = mTarget.getContext().obtainStyledAttributes(attrs, R.styleable.ClearableTextInputEditText, defStyle, 0);

        try {
            if (a.hasValue(R.styleable.ClearableTextInputEditText_clearIconDrawable)) {
                mClearIconDrawable = a.getDrawable(R.styleable.ClearableTextInputEditText_clearIconDrawable);
                if (mClearIconDrawable != null) {
                    mClearIconDrawable.setCallback(mTarget);
                }
            }

            mClearIconDrawWhenFocused = a.getBoolean(R.styleable.ClearableEditText_clearIconDrawWhenFocused, true);

        } finally {
            a.recycle();
        }
    }

    Parcelable onSaveInstanceState(Parcelable superState) {
        return mIsClearIconShown ? new ClearableHelper.ClearIconSavedState(superState, true) : superState;
    }

    Parcelable onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof ClearableHelper.ClearIconSavedState)) return state;

        ClearableHelper.ClearIconSavedState savedState = (ClearableHelper.ClearIconSavedState) state;
        mIsClearIconShown = savedState.mIsClearIconShown;
        showClearIcon(mIsClearIconShown);
        return savedState.getSuperState();
    }

    private void showClearIcon(boolean show) {
        if (show) {
            // show icon on the right
            if (mClearIconDrawable != null) {
                mTarget.setCompoundDrawablesWithIntrinsicBounds(null, null, mClearIconDrawable, null);
            } else {
                mTarget.setCompoundDrawablesWithIntrinsicBounds(0, 0, DEFAULT_CLEAR_ICON_RES_ID, 0);
            }
        } else {
            // remove icon
            mTarget.setCompoundDrawables(null, null, null, null);
        }
        mIsClearIconShown = show;
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // no op
    }

    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mTarget.hasFocus()) {
            showClearIcon(!TextUtils.isEmpty(s));
        }
    }

    @Override public void afterTextChanged(Editable s) {
        // no op
    }

    void onFocusChanged(boolean focused) {
        showClearIcon((!mClearIconDrawWhenFocused || focused) && !TextUtils.isEmpty(mTarget.getText()));
    }

    boolean onTouchEvent(MotionEvent event) {
        if (isClearIconTouched(event)) {
            mTarget.setText(null);
            event.setAction(MotionEvent.ACTION_CANCEL);
            showClearIcon(false);
            return false;
        }
        return true;
    }

    private boolean isClearIconTouched(MotionEvent event) {
        if (!mIsClearIconShown) return false;

        final int touchPointX = (int) event.getX();

        final int widthOfView = mTarget.getWidth();
        final int compoundPaddingRight = mTarget.getCompoundPaddingRight();

        return touchPointX >= widthOfView - compoundPaddingRight;
    }

    protected static class ClearIconSavedState extends View.BaseSavedState {

        public static final Creator<ClearableHelper.ClearIconSavedState> CREATOR = new Creator<ClearableHelper.ClearIconSavedState>() {
            @Override public ClearableHelper.ClearIconSavedState createFromParcel(Parcel source) {
                return new ClearableHelper.ClearIconSavedState(source);
            }

            @Override public ClearableHelper.ClearIconSavedState[] newArray(int size) {
                return new ClearableHelper.ClearIconSavedState[size];
            }
        };

        private final boolean mIsClearIconShown;

        private ClearIconSavedState(Parcel source) {
            super(source);
            mIsClearIconShown = source.readByte() != 0;
        }

        ClearIconSavedState(Parcelable superState, boolean isClearIconShown) {
            super(superState);
            mIsClearIconShown = isClearIconShown;
        }

        @Override public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeByte((byte) (mIsClearIconShown ? 1 : 0));
        }

    }

}
