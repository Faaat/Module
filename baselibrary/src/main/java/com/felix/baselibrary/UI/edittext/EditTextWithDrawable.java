package com.felix.baselibrary.UI.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Created by fangxin on 2020/4/15.
 */

public class EditTextWithDrawable extends AppCompatEditText {
    private static final String TAG = EditTextWithDrawable.class.getSimpleName();

    private Drawable[] mCompoundDrawables;
    private Drawable mCompoundRightDrawable;
    private Drawable mCompoundLeftDrawable;
    private Drawable mCompoundTopDrawable;
    private Drawable mCompoundBottomDrawable;
    private onDrawableRightClick mRightClickListener;
    private onDrawableLeftClick mLeftClickListener;
    private onDrawableTopClick mTopClickListener;
    private onDrawableBottomClick mBottomClickListener;

    public void clearText() {
        setText("");
    }

    public EditTextWithDrawable(Context context) {
        super(context);
        init();
    }

    public EditTextWithDrawable(Context context, AttributeSet attrs) {
//        super(context, attrs);
        super(context, attrs, android.R.attr.editTextStyle);
        init();
    }

    public EditTextWithDrawable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mCompoundDrawables = getCompoundDrawables();//getCompoundDrawables() -> Returns drawables for the left, top, right, and bottom borders.
        mCompoundLeftDrawable = mCompoundDrawables[0];
        mCompoundTopDrawable = mCompoundDrawables[1];
        mCompoundRightDrawable = mCompoundDrawables[2];
        mCompoundBottomDrawable = mCompoundDrawables[3];
    }

    public void setOnRightDrawableClickListener(onDrawableRightClick clickListener) {
        this.mRightClickListener = clickListener;
    }

    public void setOnLeftDrawableClickListener(onDrawableLeftClick clickListener) {
        this.mLeftClickListener = clickListener;
    }

    public void setOnTopDrawableClickListener(onDrawableTopClick clickListener) {
        this.mTopClickListener = clickListener;
    }

    public void setOnBottomDrawableClickListener(onDrawableBottomClick clickListener) {
        this.mBottomClickListener = clickListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //右侧照片点击监听
            if (mCompoundRightDrawable != null) {
                if (getWidth() - getCompoundPaddingRight() < event.getX() && getWidth() - getPaddingRight() > event.getX()) {
                    if (mRightClickListener != null) {
                        mRightClickListener.onClick();
                        return true;
                    }
                }
            }
            //左侧照片点击
            if (mCompoundLeftDrawable != null) {
                if (getCompoundPaddingLeft() > event.getX() && event.getX() > getPaddingLeft()) {
                    if (mLeftClickListener != null) {
                        mLeftClickListener.onClick();
                        return true;
                    }
                }
            }
            //上侧照片点击
            if (mCompoundTopDrawable != null) {
                if (getCompoundPaddingTop() > event.getY() && event.getY() > getPaddingTop()) {
                    if (mTopClickListener != null) {
                        mTopClickListener.onClick();
                        return true;
                    }
                }
            }
            //下侧照片点击
            if (mCompoundBottomDrawable != null) {
                if (getHeight() - getPaddingBottom() > event.getY() && event.getY() > getHeight() - getCompoundPaddingBottom()) {
                    if (mBottomClickListener != null) {
                        mBottomClickListener.onClick();
                        return true;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public interface onDrawableRightClick {
        void onClick();
    }

    public interface onDrawableLeftClick {
        void onClick();
    }

    public interface onDrawableTopClick {
        void onClick();
    }

    public interface onDrawableBottomClick {
        void onClick();
    }
}
