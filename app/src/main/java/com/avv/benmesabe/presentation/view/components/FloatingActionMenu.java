package com.avv.benmesabe.presentation.view.components;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
public class FloatingActionMenu extends ViewGroup {

    private static final long ANIMATION_DURATION = 300;
    private FloatingActionButton mMenuButton;
    private ArrayList<FloatingActionButton> mMenuItems;
    private ArrayList<Button> mMenuItemLabels;
    private ArrayList<ItemAnimator> mMenuItemAnimators;
    private int mItemMargin;


    private AnimatorSet mOpenAnimatorSet = new AnimatorSet();
    private AnimatorSet mCloseAnimatorSet = new AnimatorSet();
    private static final int DEFAULT_CHILD_GRAVITY = Gravity.END | Gravity.BOTTOM;
    private ImageView mIcon;
    private boolean mOpen;
    private boolean animating;
    private boolean mIsSetClosedOnTouchOutside = true;

    public interface OnMenuToggleListener {
        void onMenuToggle(boolean opened);
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(FloatingActionMenu fam, int index, FloatingActionButton item);
    }


    private OnMenuItemClickListener onMenuItemClickListener;
    private OnMenuToggleListener onMenuToggleListener;

    public FloatingActionMenu(Context context) {
        this(context, null, 0);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMenuItems = new ArrayList<>(3);
        mMenuItemAnimators = new ArrayList<>(3);

        mMenuItemLabels = new ArrayList<>(3);
        mIcon = new ImageView(context);
    }


    @Override
    protected void onFinishInflate() {
        bringChildToFront(mMenuButton);
        bringChildToFront(mIcon);
        super.onFinishInflate();
    }

    @Override
    public void addView(@NonNull View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() > 1) {
            if (child instanceof FloatingActionButton) {
                addMenuItem((FloatingActionButton) child);
            }
        } else {
            mMenuButton = (FloatingActionButton) child;
            /*mIcon.setImageDrawable(mMenuButton.getDrawable());
            addView(mIcon);
            mMenuButton.setImageDrawable(null);*/
            createDefaultIconAnimation();
            mMenuButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle();
                }
            });
        }
    }

    public void toggle() {
        if (!mOpen) {
            open();
        } else {
            close();
        }
    }

    public void open() {
        d("open");
        startOpenAnimator();
        mOpen = true;
        if (onMenuToggleListener != null) {
            onMenuToggleListener.onMenuToggle(true);
        }
    }

    public void close() {
        startCloseAnimator();
        mOpen = false;
        if (onMenuToggleListener != null) {
            onMenuToggleListener.onMenuToggle(true);
        }
    }

    protected void startCloseAnimator() {
        mCloseAnimatorSet.start();
        for (ItemAnimator anim : mMenuItemAnimators) {
            anim.startCloseAnimator();
        }
    }

    protected void startOpenAnimator() {
        mOpenAnimatorSet.start();
        for (ItemAnimator anim : mMenuItemAnimators) {
            anim.startOpenAnimator();
        }
    }

    public void addMenuItem(FloatingActionButton item) {
        mMenuItems.add(item);
        mMenuItemAnimators.add(new ItemAnimator(item));
        Button button = new Button(getContext());
        button.setText(item.getContentDescription());
        addView(button);
        mMenuItemLabels.add(button);
        item.setTag(button);
        item.setOnClickListener(mOnItemClickListener);
        button.setOnClickListener(mOnItemClickListener);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height;
        final int count = getChildCount();
        int maxChildWidth = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        for (int i = 0; i < mMenuItems.size(); i++) {
            FloatingActionButton fab = mMenuItems.get(i);
            Button label = mMenuItemLabels.get(i);
            maxChildWidth = Math.max(maxChildWidth, label.getMeasuredWidth() + fab.getMeasuredWidth() + mItemMargin);
        }

        maxChildWidth = Math.max(mMenuButton.getMeasuredWidth(), maxChildWidth);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = maxChildWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int heightSum = 0;
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                heightSum += child.getMeasuredHeight();
            }
            height = heightSum;
        }

        setMeasuredDimension(resolveSize(width, widthMeasureSpec),
                resolveSize(height, heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (mIsSetClosedOnTouchOutside) {
            return mGestureDetector.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }

    GestureDetector mGestureDetector = new GestureDetector(getContext(),
            new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onDown(MotionEvent e) {
                    return mIsSetClosedOnTouchOutside && isOpened();
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    close();
                    return true;
                }
            });

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println("onLayout:" + changed);
        if (changed) {
            int right = r - getPaddingRight();
            int bottom = b - getPaddingBottom();
            int top = bottom - mMenuButton.getMeasuredHeight();


            mMenuButton.layout(right - mMenuButton.getMeasuredWidth(), top, right, bottom);
            int dw = (mMenuButton.getMeasuredWidth() - mIcon.getMeasuredWidth()) / 2;
            int dh = (mMenuButton.getMeasuredHeight() - mIcon.getMeasuredHeight()) / 2;
            mIcon.layout(right - mIcon.getMeasuredWidth() - dw, bottom - mIcon.getMeasuredHeight() - dh, right - dw, bottom - dh);
            for (int i = 0; i < mMenuItems.size(); i++) {
                FloatingActionButton item = mMenuItems.get(i);
                Button label = mMenuItemLabels.get(i);
                bottom = top;
                top -= item.getMeasuredHeight();
                int width = item.getMeasuredWidth();
                int d = (mMenuButton.getMeasuredWidth() - width) / 2;
                item.layout(right - width - d, top, right - d, bottom);
                d = (item.getMeasuredHeight() - label.getMeasuredHeight()) / 2;
                label.layout(item.getLeft() - mItemMargin - label.getMeasuredWidth(), item.getTop() + d, item.getLeft() - mItemMargin, item.getTop() + d + label.getMeasuredHeight());
                if (!animating) {
                    if (!mOpen) {
                        item.setTranslationY(mMenuButton.getTop() - item.getTop());
                        item.setVisibility(GONE);
                        label.setVisibility(GONE);
                    } else {
                        item.setTranslationY(0);
                        item.setVisibility(VISIBLE);
                        label.setVisibility(VISIBLE);
                    }
                }
            }
            if (!animating && getBackground() != null) {
                if (!mOpen) {
                    getBackground().setAlpha(0);
                } else {
                    getBackground().setAlpha(0xff);
                }
            }
        }
    }

    private void createDefaultIconAnimation() {
        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animating = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        ObjectAnimator collapseAnimator = ObjectAnimator.ofFloat(
                mMenuButton,
                "rotation",
                135f,
                0f
        );

        ObjectAnimator expandAnimator = ObjectAnimator.ofFloat(
                mMenuButton,
                "rotation",
                0f,
                135f
        );

        if (getBackground() != null) {


            ValueAnimator hideBackgroundAnimator = ObjectAnimator.ofInt(0xff, 0);
            hideBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer alpha = (Integer) animation.getAnimatedValue();
                    //System.out.println(alpha);
                    getBackground().setAlpha(alpha > 0xff ? 0xff : alpha);
                }
            });
            ValueAnimator showBackgroundAnimator = ObjectAnimator.ofInt(0, 0xff);
            showBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    Integer alpha = (Integer) animation.getAnimatedValue();
                    //System.out.println(alpha);
                    getBackground().setAlpha(alpha > 0xff ? 0xff : alpha);
                }
            });

            mOpenAnimatorSet.playTogether(expandAnimator, showBackgroundAnimator);
            mCloseAnimatorSet.playTogether(collapseAnimator, hideBackgroundAnimator);
        } else {
            mOpenAnimatorSet.playTogether(expandAnimator);
            mCloseAnimatorSet.playTogether(collapseAnimator);
        }

        mOpenAnimatorSet.setInterpolator(DEFAULT_OPEN_INTERPOLATOR);
        mCloseAnimatorSet.setInterpolator(DEFAULT_CLOSE_INTERPOLATOR);

        mOpenAnimatorSet.setDuration(ANIMATION_DURATION);
        mCloseAnimatorSet.setDuration(ANIMATION_DURATION);

        mOpenAnimatorSet.addListener(listener);
        mCloseAnimatorSet.addListener(listener);
    }

    static final TimeInterpolator DEFAULT_OPEN_INTERPOLATOR = new OvershootInterpolator();
    static final TimeInterpolator DEFAULT_CLOSE_INTERPOLATOR = new AnticipateInterpolator();

    public boolean isOpened() {
        return mOpen;
    }

    private class ItemAnimator implements Animator.AnimatorListener {
        private View mView;
        private boolean playingOpenAnimator;

        public ItemAnimator(View v) {
            v.animate().setListener(this);
            mView = v;
        }

        public void startOpenAnimator() {
            mView.animate().cancel();
            playingOpenAnimator = true;
            mView.animate().translationY(0).setInterpolator(DEFAULT_OPEN_INTERPOLATOR).start();
        }

        public void startCloseAnimator() {
            mView.animate().cancel();
            playingOpenAnimator = false;
            mView.animate().translationY((mMenuButton.getTop() - mView.getTop())).setInterpolator(DEFAULT_CLOSE_INTERPOLATOR).start();
        }

        @Override
        public void onAnimationStart(Animator animation) {
            if (playingOpenAnimator) {
                mView.setVisibility(VISIBLE);
            } else {
                ((Button) mView.getTag()).setVisibility(GONE);
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (!playingOpenAnimator) {
                mView.setVisibility(GONE);
            } else {
                ((Button) mView.getTag()).setVisibility(VISIBLE);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }


    @Override
    public Parcelable onSaveInstanceState() {
        d("onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putBoolean("mOpen", mOpen);
        // ... save everything
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        d("onRestoreInstanceState");
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mOpen = bundle.getBoolean("mOpen");
            // ... load everything
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDetachedFromWindow() {
        d("onDetachedFromWindow");
        //getBackground().setAlpha(bgAlpha);//reset default alpha
        super.onDetachedFromWindow();
    }


    @Override
    public void setBackground(Drawable background) {
        if (background instanceof ColorDrawable) {
            // after activity finish and relaucher , background drawable state still remain?
            int bgAlpha = Color.alpha(((ColorDrawable) background).getColor());
            d("bg:" + Integer.toHexString(bgAlpha));
            super.setBackground(background);
        } else {
            throw new IllegalArgumentException("floating only support color background");
        }
    }

    private OnClickListener mOnItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof FloatingActionButton) {
                int i = mMenuItems.indexOf(v);
                if (onMenuItemClickListener != null) {
                    onMenuItemClickListener.onMenuItemClick(FloatingActionMenu.this, i, (FloatingActionButton) v);
                }
            } else if (v instanceof Button) {
                int i = mMenuItemLabels.indexOf(v);
                if (onMenuItemClickListener != null) {
                    onMenuItemClickListener.onMenuItemClick(FloatingActionMenu.this, i, mMenuItems.get(i));
                }
            }
            close();
        }
    };

    public OnMenuToggleListener getOnMenuToggleListener() {
        return onMenuToggleListener;
    }

    public void setOnMenuToggleListener(OnMenuToggleListener onMenuToggleListener) {
        this.onMenuToggleListener = onMenuToggleListener;
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return onMenuItemClickListener;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }


    protected void d(String msg) {
        Log.d("FAM", msg == null ? null : msg);
    }
}