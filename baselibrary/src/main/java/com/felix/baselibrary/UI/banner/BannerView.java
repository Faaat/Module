package com.felix.baselibrary.UI.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.felix.baselibrary.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 广告条
 * 必须使用setData方法将数据传入，否则不能显示
 */
public class BannerView extends RelativeLayout {
    // TODO: 2020/4/20 数据来源可配置
    private ViewPager mViewpagerMain;
    private TextView mTvName;
    private LinearLayout mLlPointGroup;


    private LinkedHashMap<Integer, String> mData = new LinkedHashMap<>();
    private ArrayList<Integer> imgs = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();
    private ArrayList<ImageView> imageViews;
    private final static String TAG = BannerView.class.getSimpleName();
    private int prePosition = 0;

    private boolean isCycle = true;
    private long mDelayMillis = 4000;
    private BannerAdapter myAdapter;
    private Context mContext;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mViewpagerMain.setCurrentItem(mViewpagerMain.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(0, mDelayMillis);
            return true;
        }
    });
    private LinearLayout mLlStatusBar;

    /**
     * @param adapter BannerAdapter
     * @param map     KV结构map，HashMap<Integer, String>
     *                Integer：图片的标识，R.drawable.xxx
     *                String：图片描述
     */
    public void setAdapter(BannerAdapter adapter, LinkedHashMap<Integer, String> map) {
        myAdapter = new BannerAdapter();
        mData = map;
        initData();
        initView(mContext);
    }

    /**
     * 设置是否自动轮播
     *
     * @param b true or false
     * @hide
     */
    private void setAutoRotation(boolean b) {
        isCycle = b;
    }

    private void setAutoRotationDuration(int mills) {
        mDelayMillis = mills;
    }

    public BannerView(Context context) {
        this(context, null);
        mContext = context;
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;

    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initData() {
        imgs.clear();
        strings.clear();
        for (Object o : mData.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            imgs.add((int) entry.getKey());
            strings.add((String) entry.getValue());
        }
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.banner_view, this);
        mLlPointGroup = root.findViewById(R.id.ll_point_group);
        mLlStatusBar = root.findViewById(R.id.ll_status_bar);
        mTvName = root.findViewById(R.id.tv_name);
        mViewpagerMain = root.findViewById(R.id.viewpager_main);
        imageViews = new ArrayList<>();
        if (myAdapter == null) {
            return;
        }
        mLlStatusBar.setVisibility(VISIBLE);
        for (int i = 0; i < imgs.size(); i++) {
            int imgSrc = imgs.get(i);
//            添加图
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(imgSrc);
            imageViews.add(imageView);
//            添加点
            ImageView pointView = new ImageView(context);
            pointView.setBackgroundResource(R.drawable.point_seletor);
//            px转dp
            float scale = getResources().getDisplayMetrics().density;
            int width = (int) (scale * 8 + 0.5f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.rightMargin = width;
            pointView.setLayoutParams(params);
            mLlPointGroup.addView(pointView);
            if (i == 0) {
                pointView.setEnabled(true);
            } else {
                pointView.setEnabled(false);
            }
        }
        if (imageViews.isEmpty()) {
            return;
        }
        myAdapter.setImageViews(imageViews);
        mViewpagerMain.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        mViewpagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position % imageViews.size();
                mTvName.setText(strings.get(realPosition));
                mLlPointGroup.getChildAt(prePosition).setEnabled(false);
                mLlPointGroup.getChildAt(realPosition).setEnabled(true);
                prePosition = realPosition;

            }

            /**
             当页面滚动状态变化的时候回调这个方法
             静止->滑动
             滑动-->静止
             静止-->拖拽
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    isCycle = true;
                    mHandler.removeCallbacksAndMessages(null);
                } else if (state == ViewPager.SCROLL_STATE_IDLE && isCycle) {
                    isCycle = false;
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(0, mDelayMillis);
                }
            }
        });

        int midItem = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();
        mViewpagerMain.setCurrentItem(midItem);
        mTvName.setText(strings.get(prePosition));
        if (isCycle) mHandler.sendEmptyMessageDelayed(0, mDelayMillis);
    }


}
