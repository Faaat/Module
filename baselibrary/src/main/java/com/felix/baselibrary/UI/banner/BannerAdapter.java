package com.felix.baselibrary.UI.banner;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {

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
            parent.removeView(imageView);
        }
        container.addView(imageView, 0);
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

}
