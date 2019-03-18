package com.mss.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.mss.custom.R;
import com.mss.custom.util.DisplayUtil;

/**
 * Created by Dong on 2019/2/21.
 */
public class RingView extends View {
    private int mArcColor;
    private int mArcWidth;
    private int mCenterTextColor;
    private int mCenterTextSize;
    private int mCircleRadius;
    private Paint circlePaint;
    private Paint arcCirclePaint;
    private Paint centerTextPaint;
    private RectF arcRectF;
    private Rect textBoundRect;
    private String mCenterText;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentBar, defStyleAttr, 0);
        mArcWidth = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_arcWidth, DisplayUtil.dp2px(context, 20));
        mArcColor = typedArray.getColor(R.styleable.CirclePercentBar_arcColor, 0xff0000);
        mCenterTextColor = typedArray.getColor(R.styleable.CirclePercentBar_centerTextColor, 0x0000ff);
        mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_centerTextSize, DisplayUtil.dp2px(context, 20));
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CirclePercentBar_circleRadius, DisplayUtil.dp2px(context, 100));
        mCenterText = typedArray.getString(R.styleable.CirclePercentBar_centerText);

        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        // 内部圆形画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        //startCirclePaint.setStrokeWidth(mArcWidth);
        circlePaint.setColor(Color.RED);

        // 未填充部分的圆环颜色
        arcCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcCirclePaint.setStyle(Paint.Style.STROKE);
        arcCirclePaint.setStrokeWidth(mArcWidth);
        arcCirclePaint.setColor(mArcColor);
        arcCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        // 文字画笔
        centerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerTextPaint.setStyle(Paint.Style.STROKE);
        centerTextPaint.setColor(mCenterTextColor);
        centerTextPaint.setTextSize(mCenterTextSize);

        //圆弧的外接矩形
        arcRectF = new RectF();

        //文字的边界矩形
        textBoundRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureView(widthMeasureSpec), measureView(heightMeasureSpec));
    }

    private int measureView(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mCircleRadius * 2;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        arcRectF.set(getWidth() / 2 - mCircleRadius + mArcWidth / 2, getHeight() / 2 - mCircleRadius + mArcWidth / 2
                , getWidth() / 2 + mCircleRadius - mArcWidth / 2, getHeight() / 2 + mCircleRadius - mArcWidth / 2);
        // 内环透明部分
        canvas.drawArc(arcRectF, 0, 360, false, arcCirclePaint);
        // 圆环文字
        centerTextPaint.getTextBounds(mCenterText, 0, mCenterText.length(), textBoundRect);
        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */
        // 内圆
        canvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                mCircleRadius / 2,
                circlePaint
        );
        canvas.drawText(mCenterText, getWidth() / 2 - textBoundRect.width() / 2, getHeight() / 2 + textBoundRect.height() / 2, centerTextPaint);
    }

    // 设置外环颜色
    public void setOuterRingColor(int ringColor) {
        arcCirclePaint.setColor(ringColor);
        invalidate();
    }

    // 设置内环颜色
    public void setInnerRingColor(int ringColor) {
        circlePaint.setColor(ringColor);
        invalidate();
    }
}
