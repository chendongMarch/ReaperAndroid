package com.march.reaper.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.march.reaper.R;

import java.util.Random;

/**
 * Project  : CdLibsTest
 * Package  : com.march.cdlibstest.widget
 * CreateAt : 2016/11/22
 * Describe :
 *
 * @author chendong
 */

public class LeProgressView extends View {

    private String TAG = getClass().getSimpleName();
    /**
     * 半径
     */
    private float radius = 50;
    /**
     * 点的数目
     */
    private int pointCount = 6;
    /**
     * 点的半径
     */
    private float pointRadius = 10;
    /**
     * 加载进度
     */
    private float loadPercent = 0f;

    /**
     * 默认随机颜色
     */
    private int[] colorInit;
    /**
     * 旋转动画
     */
    private ObjectAnimator rotateAnim;

    /**
     * 画点的笔
     */
    private Paint pointPaint;

    private int loadingAnimDuration = 500;
    private int prepareAnimDuration = 1000;
    private int stopAnimDuration = 300;

    private boolean isLoading = false;

    public LeProgressView(Context context) {
        this(context, null);
    }

    public LeProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 画笔
        pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LeProgressView);
        radius = typedArray.getDimension(R.styleable.LeProgressView_radius, 50);
        pointCount = typedArray.getInt(R.styleable.LeProgressView_pointCount, 6);
        pointRadius = typedArray.getDimension(R.styleable.LeProgressView_pointRadius, 10);
        typedArray.recycle();

        // 圆的半径
        radius = radius - pointRadius / 2;

        // 初始化随机颜色
        colorInit = new int[pointCount];
        randomColor();

        if (isInEditMode()) {
            loadPercent = 1f;
        }
    }

    // 随机颜色生成
    private void randomColor() {
        Random random = new Random();
        int randomInt = 225;
        for (int i = 0; i < pointCount; i++) {
            colorInit[i] = Color.argb(225, random.nextInt(randomInt), random.nextInt(randomInt), random.nextInt(randomInt));
        }
    }

    // 在开始旋转动画之前小圆点挨个出来的效果
    public void prepareLoading(float percent) {
        if (isLoading)
            return;
        this.loadPercent = percent;
        postInvalidateOnAnimation();
    }

    private void completeLoadPercent() {
        loadPercent = 0.9999999f;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (loadPercent <= 0)
            return;

        if (loadPercent >= 1.0f)
            completeLoadPercent();


        int x = getMeasuredWidth() / 2;
        int y = getMeasuredHeight() / 2;

        float percentLoad4OnePoint = 1f / pointCount;
        float tempLoadPercent = loadPercent;
        float calculate = (tempLoadPercent / percentLoad4OnePoint);
        int drawCount = (int) calculate;
//        Logger.e(" drawCount = " + drawCount + " drawCountF = " + (tempLoadPercent / percentLoad4OnePoint) + "   loadPercent = " + loadPercent + " percentLoad4OnePoint= " + percentLoad4OnePoint);

        double[] pointAtCircle;
        //绘制完整的圆
        for (int i = 0; i < drawCount + 1; i++) {
            pointPaint.setColor(colorInit[i]);
            pointAtCircle = getPointAtCircle(x, y, radius, 360 / pointCount * i);
            if (tempLoadPercent > percentLoad4OnePoint) {
                canvas.drawCircle((float) pointAtCircle[0], (float) pointAtCircle[1], pointRadius, pointPaint);
            } else {
                canvas.drawCircle((float) pointAtCircle[0], (float) pointAtCircle[1], pointRadius * (tempLoadPercent / percentLoad4OnePoint), pointPaint);
            }
            tempLoadPercent = tempLoadPercent - percentLoad4OnePoint;
        }
    }

    // 获取圆上的位置
    private double[] getPointAtCircle(int centerX, int centerY, float radius, int angle) {
        double x = centerX + radius * Math.cos((angle - 90) * Math.PI / 180);
        double y = centerY + radius * Math.sin((angle - 90) * Math.PI / 180);
        return new double[]{x, y};
    }


    // 准备动画之后自动播放加载动画
    public void startLoadingWithPrepare() {
        loadPercent = 0f;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(prepareAnimDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                prepareLoading(animatedValue);
            }
        });
        animator.addListener(new SimpleAnimListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startLoading();
            }
        });
        animator.start();
    }

    // 准备动画之后自动播放加载动画
    public void startLoadingWithPrepare(final Runnable afterPrepareRunnable) {
        loadPercent = 0f;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(prepareAnimDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                prepareLoading(animatedValue);
            }
        });
        animator.addListener(new SimpleAnimListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startLoading();
                if (afterPrepareRunnable != null) {
                    afterPrepareRunnable.run();
                }
            }
        });
        animator.start();
    }

    // 开始加载，没有准备动画
    public void startLoading() {
        completeLoadPercent();
        postInvalidate();
        isLoading = true;
        rotateAnim = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f).setDuration(loadingAnimDuration);
        rotateAnim.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.start();
    }

    // 停止加载动画
    public void stopLoading(final Runnable runnable) {
        if (rotateAnim != null && rotateAnim.isRunning()) {
            rotateAnim.end();
        }
        setRotation(0);

        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0.8f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0.8f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, pvhY, pvhZ).setDuration(stopAnimDuration);

        animator.addListener(new SimpleAnimListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (runnable != null) {
                    runnable.run();
                }
                randomColor();
            }
        });
        animator.start();
        isLoading = false;
    }

    // 停止加载动画
    public void stopLoading() {
        stopLoading(null);
    }

    class SimpleAnimListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
    }

    public void setPointRadius(float pointRadius) {
        this.pointRadius = pointRadius;
    }

    public void setColorInit(int[] colorInit) {
        this.colorInit = colorInit;
    }

    public void setLoadingAnimDuration(int loadingAnimDuration) {
        this.loadingAnimDuration = loadingAnimDuration;
    }

    public void setPrepareAnimDuration(int prepareAnimDuration) {
        this.prepareAnimDuration = prepareAnimDuration;
    }

    public void setStopAnimDuration(int stopAnimDuration) {
        this.stopAnimDuration = stopAnimDuration;
    }
}
