package com.felix.baselibrary.UI.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import com.felix.baselibrary.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class FxAlterDialog extends AlertDialog {

    protected FxAlterDialog(@NonNull Context context) {
        this(context, 0);
    }

    protected FxAlterDialog(@NonNull Context context, int themeResId) {
        this(context, false, null);
    }

    protected FxAlterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder extends AlertDialog.Builder {
        private View mContainerView;
        private Context mContext;
        private AlertDialog mDialog;
        private OnSuccessClickListener mOnSuccessClickListener;
        private OnFailClickListener mOnFailClickListener;
        private OnCancelClickListener mOnCancelClickListener;
        private Button btnSuccess;
        private Button btnFail;
        private Button btnCancel;
        private TextView mTvTitle;
        private TextView mTvMessage;
        private RelativeLayout mRlViewResult;
        private ImageView mIvResult;

        public final static int DEFAULT = -1;
        public final static int OK = 0;
        public final static int ERROR = 1;
        public final static int WARNING = 2;

        @IntDef({
                DEFAULT, OK, ERROR, WARNING
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface Result {
        }

        @Result
        int curResult = DEFAULT;

        public Builder setResult(@Result int result) {
            curResult = result;
            return this;
        }

        public Builder(Context context) {
            super(context);
            mContext = context;
            if (mContainerView == null) {
                mContainerView = LayoutInflater.from(mContext).inflate(R.layout.dialog_container_view, null);
            }
        }

        public Builder setSuccessButtonOnClick(CharSequence text, @NonNull OnSuccessClickListener listener) {
            mOnSuccessClickListener = listener;
            btnSuccess = mContainerView.findViewById(R.id.btn_success);
            btnSuccess.setVisibility(View.VISIBLE);
            btnSuccess.setText(text);
            btnSuccess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnSuccessClickListener.onClick(mDialog);
                }
            });
            return this;
        }

        public Builder setFailButtonOnClick(CharSequence text, OnFailClickListener listener) {
            mOnFailClickListener = listener;
            btnFail = mContainerView.findViewById(R.id.btn_fail);
            btnFail.setVisibility(View.VISIBLE);
            btnFail.setText(text);
            btnFail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnFailClickListener != null) {
                        mOnFailClickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        public Builder setCancelButtonOnClick(CharSequence text, OnCancelClickListener listener) {
            mOnCancelClickListener = listener;
            btnCancel = mContainerView.findViewById(R.id.btn_cancel);
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(text);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnCancelClickListener != null) {
                        mOnCancelClickListener.onClick(mDialog);
                    }
                }
            });
            return this;
        }

        @Override
        public Builder setNegativeButton(CharSequence text, OnClickListener listener) {
            return this;
        }

        @Override
        public Builder setView(View view) {
            return this;
        }

        @Override
        public Builder setTitle(int titleId) {
            this.setTitle(mContext.getResources().getString(titleId));
            return this;
        }

        @Override
        public Builder setTitle(CharSequence s) {
            mTvTitle = mContainerView.findViewById(R.id.tv_title);
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(s);
            return this;
        }

        @Override
        public Builder setMessage(int messageId) {
            this.setMessage(mContext.getResources().getString(messageId));
            return this;
        }

        @Override
        public Builder setMessage(@Nullable CharSequence message) {
            mTvMessage = mContainerView.findViewById(R.id.tv_message);
            mTvMessage.setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
            mTvMessage.setText(message);
            return this;
        }

        @Override
        public AlertDialog create() {
            mDialog = super.create();
            return mDialog;
        }

        @Override
        public AlertDialog show() {
            initContentView();
            mDialog.show();
            return mDialog;
        }

        private void initContentView() {
            mDialog.setView(mContainerView);
            mRlViewResult = mContainerView.findViewById(R.id.rl_item1);
            mIvResult = mContainerView.findViewById(R.id.iv_result);
            switch (curResult) {
                case DEFAULT:
//                    mRlViewResult.setVisibility(View.VISIBLE);
//                    mRlViewResult.setBackground(null);
//                    mIvResult.setBackground(mContainerView.getResources().getDrawable(R.drawable.ic_circle));
//                    Animation annotation = AnimationUtils.loadAnimation(mContext, R.anim.rotate360);
//                    annotation.setInterpolator(new LinearInterpolator());
//                    annotation.setRepeatCount(-1);
//                    mIvResult.startAnimation(annotation);
                    break;
                case OK:
                    mTvTitle.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    mRlViewResult.setVisibility(View.VISIBLE);
                    mRlViewResult.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    mIvResult.setBackground(mContext.getResources().getDrawable(R.drawable.ic_success));
                    break;
                case ERROR:
                    mTvTitle.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                    mRlViewResult.setVisibility(View.VISIBLE);
                    mRlViewResult.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                    mIvResult.setBackground(mContext.getResources().getDrawable(R.drawable.ic_error));
                    break;
                case WARNING:
                    mTvTitle.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    mRlViewResult.setVisibility(View.VISIBLE);
                    mRlViewResult.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                    mIvResult.setBackground(mContext.getResources().getDrawable(R.drawable.ic_warning));
            }
        }
    }

    public interface OnSuccessClickListener {
        void onClick(AlertDialog dialog);
    }

    public interface OnFailClickListener {
        void onClick(AlertDialog dialog);
    }

    public interface OnCancelClickListener {
        void onClick(AlertDialog dialog);
    }
}
