package com.felix.baselibrary.UI.captcha;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import com.felix.baselibrary.R;


/**
 * Created by fangxin on 2020/4/16.
 */

public class TextViewWithIdentifyingCode extends AppCompatTextView {
    private String textTitle;
    private int textColor;
    private int textSize;
    private Paint paint;
    private Rect rect;
    private int bgColor = -1;
    private int mSum = -1;
    private OnChangeListener mOnChangeListener;
    private static final int DEFAULT_NAM = 4;
    private String mRes = "2020";

    private static String[] types = new String[]{"num", "lower", "higher", ""};
    private static char[] nums = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] lowers = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public void setTextLength(int sum) {
        // TODO: 2020-04-16 修改字符长度失效
        this.mSum = sum;
        refreshText();
    }

    public void setTextOnChangedLisenter(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
        refreshText();
    }

    public void refreshText() {
        if (mSum == -1) {
            mSum = DEFAULT_NAM;
        }
        random(mSum);
        setTextTitle(mRes);
        if (mOnChangeListener != null) {
            mOnChangeListener.onChanged(mRes);
        }
    }

    private void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
        invalidate();
        requestLayout();
    }

    public TextViewWithIdentifyingCode(Context context) {
        this(context, null);
    }

    public TextViewWithIdentifyingCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewWithIdentifyingCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextViewWithIdentifyingCode, defStyleAttr, 0);
        int length = array.length();
        for (int i = 0; i < length; i++) {
            int attr = array.getIndex(i);
            if (attr == R.styleable.TextViewWithIdentifyingCode_text_color) {
                textColor = array.getColor(attr, Color.BLUE);
            } else if (attr == R.styleable.TextViewWithIdentifyingCode_text_size) {
                textSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()));
            } else if (attr == R.styleable.TextViewWithIdentifyingCode_text_title) {
                textTitle = array.getString(attr);
                textTitle = mRes;
            } else if (attr == R.styleable.TextViewWithIdentifyingCode_background_color) {
                bgColor = array.getColor(attr, Color.BLUE);
            }
        }
        array.recycle();
        paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        // 创建一个矩形
        rect = new Rect();
        /*
          @param text  String to measure and return its bounds 要测量的文字
         * @param start Index of the first char in the string to measure    测量的起始位置
         * @param end   1 past the last char in the string measure  测量的最后一个字符串的位置
         * @param bounds Returns the unioned bounds of all the text. Must be    rect对象
         *               allocated by the caller.
         */
        paint.getTextBounds(textTitle, 0, textTitle.length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(bgColor == -1 ? Color.GREEN : bgColor);
        //设置矩形长和高
        //drawRect(float left, float top, float right, float bottom,Paint paint)
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        paint.setColor(textColor);
//        canvas.drawText(textTitle, getWidth() / 2 - rect.width() / 2, getHeight() / 2 + rect.height() / 2, paint);
        canvas.drawText(textTitle, getPaddingLeft(), getHeight() / 2 + rect.height() / 2, paint);


        // 获取到自定义的view宽高
        final int height = getHeight();
        final int width = getWidth();
        // 绘制小圆点
        int[] point;
        for (int i = 0; i < 50; i++) {
            point = getPoint(height, width);
            /**
             * drawCircle (float cx, float cy, float radius, Paint paint)
             * float cx：圆心的x坐标。
             * float cy：圆心的y坐标。
             * float radius：圆的半径。
             * Paint paint：绘制时所使用的画笔。
             */
            canvas.drawCircle(point[0], point[1], 1, paint);
        }

        int[] line;
        for (int i = 0; i < 100; i++) {
            line = getLine(height, width);
            /**
             * startX：起始端点的X坐标。
             *startY：起始端点的Y坐标。
             *stopX：终止端点的X坐标。
             *stopY：终止端点的Y坐标。
             *paint：绘制直线所使用的画笔。
             */
            canvas.drawLine(line[0], line[1], line[2], line[3], paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取到宽高的测量模式以及测量值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("tag", "" + widthMode + widthSize);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("tag", "" + heightMode + heightSize);
        int width;
        int height;
        // 宽如果是精确测量
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            paint.setTextSize(textSize);
            paint.getTextBounds(textTitle, 0, textTitle.length(), rect);
            int textWidth = rect.width();
            int measureWidth = getPaddingLeft() + textWidth + getPaddingRight();
            width = measureWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            paint.setTextSize(textSize);
            paint.getTextBounds(textTitle, 0, textTitle.length(), rect);
            float textHeight = rect.height();
            int measureHeight = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = measureHeight;
        }

        // 重新设置空间的宽高
        setMeasuredDimension(width, height);
    }

    public int[] getLine(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        for (int i = 0; i < 4; i += 2) {
            tempCheckNum[i] = (int) (Math.random() * width);
            tempCheckNum[i + 1] = (int) (Math.random() * height);
        }
        return tempCheckNum;
    }

    private int[] getPoint(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        tempCheckNum[0] = (int) (Math.random() * width);
        tempCheckNum[1] = (int) (Math.random() * height);
        return tempCheckNum;
    }

    private void random(int num) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < num; i++) {
            String type = types[(int) (Math.random() * types.length)];
            if (type.equals(types[0])) {
                res.append(nums[(int) (Math.random() * nums.length)]);
            } else if (type.equals(types[1])) {
                res.append(lowers[(int) (Math.random() * lowers.length)]);
            } else if (type.equals(types[2])) {
                res.append((char) (lowers[(int) (Math.random() * lowers.length)] - 32));
            } else {
                res.append(nums[(int) (Math.random() * nums.length)]);
            }
        }
        mRes = res.toString();
    }

    public interface OnChangeListener {
        void onChanged(String s);
    }
}

