package com.felix.baselibrary.UI.banner;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Observable;

public class FxBannerAdapter extends PagerAdapter {
    private static final String TAG = FxBannerAdapter.class.getSimpleName();
    private OnAdapterItemClickListener mItemClickListener;

    public void setImageViews(ArrayList<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    private ArrayList<ImageView> imageViews;

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position % imageViews.size();
        ImageView imageView = imageViews.get(realPosition);
        ViewPager parent = (ViewPager) imageView.getParent();
        if (parent != null) {
            Log.d(TAG, "removeView(imageView)");
            parent.removeView(imageView);
        }
        container.addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(v);
                }
                Log.d("BannerAdapter", "onClick");
            }
        });
        imageView.setTag(position);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://手指按下
//                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP://手指抬起
//                        mHandler.removeCallbacksAndMessages(null);
//                        mHandler.sendEmptyMessageDelayed(0, 4000);
                        break;
                    case MotionEvent.ACTION_MOVE://手指滑动
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return false;
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    void setOnItemClickListener(OnAdapterItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnAdapterItemClickListener {
        void onItemClick(View v);
    }
}
