package com.medvision.vrmc.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.medvision.vrmc.R;

import java.util.Random;

/**
 * Created by raytine on 2017/8/7.
 */

public class LoveLayout extends RelativeLayout {
    private ImageView imageView;
    private Drawable[] drawables;
    private Random random;
    private Object bezierAnimotion;
    private Interpolator[] mInterpolators;
    private  int mWidht,mHeight,mDrawalbeWidth,mDrawableHeight;
    public LoveLayout(Context context) {
        this(context,null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        random = new Random();
        drawables = new Drawable[]{getResources().getDrawable(R.drawable.blue), getResources().getDrawable(R.drawable.yellow),getResources().getDrawable(R.drawable.green)};
        mDrawalbeWidth = drawables[0].getIntrinsicWidth();
        mDrawableHeight = drawables[0].getIntrinsicHeight();
        initInterpolator();
    }
    /**
     * 初始化几种插补器
     */
    private void initInterpolator() {
        mInterpolators = new Interpolator[4];
        mInterpolators[0] = new LinearInterpolator();// 线性
        mInterpolators[1] = new AccelerateDecelerateInterpolator();// 先加速后减速
        mInterpolators[2] = new AccelerateInterpolator();// 加速
        mInterpolators[3] = new DecelerateInterpolator();// 减速
    }

    public void addImageView() {
        imageView = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageDrawable(drawables[random.nextInt(drawables.length)]);
        addView(imageView);
        AnimatorSet animotion = getAnimotion(imageView);
        animotion.start();
        ValueAnimator bezierAnimotion = getBezierAnimotion(imageView);
        bezierAnimotion.setInterpolator(mInterpolators[random.nextInt(mInterpolators.length)]);
        bezierAnimotion.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidht = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    public AnimatorSet getAnimotion(ImageView imageView) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0.3f, 1f);
        ObjectAnimator alpha1 = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1f);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(alpha,alpha1,alpha2);
        animatorSet.setTarget(imageView);
        return animatorSet;
    }

    public ValueAnimator getBezierAnimotion(final ImageView imageView) {
        PointF p0  = new PointF(mWidht/2 - mDrawalbeWidth/2,mHeight-mDrawableHeight);
        PointF p1  = getPoint(1);
        PointF p2 = getPoint(2);
        PointF p3 = new PointF(random.nextInt(mWidht- mDrawalbeWidth),0);
        BerziEvaluator berziEvaluator = new BerziEvaluator(p1, p2);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(berziEvaluator,p0,p3);
        valueAnimator.setDuration(3000);
        valueAnimator.setTarget(imageView);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                imageView.setX(p.x);
                imageView.setY(p.y);
                imageView.setAlpha(1-animation.getAnimatedFraction()+0.1f);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(imageView);
            }
        });
        return valueAnimator;
    }

    private PointF getPoint(int i) {
        PointF p = new PointF();
        switch (i){
            case 1:
                p.x= random.nextInt(mWidht);
                p.y = random.nextInt(mHeight/2);
                break;
            case  2:
                p.x= random.nextInt(mWidht);
                p.y = random.nextInt(mHeight/2)+mHeight/2;
                break;
        }
        return p;
    }
}
