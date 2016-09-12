package com.example.framgia.imarketandroid.ui.views;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by toannguyen201194 on 06/09/2016.
 */
public class HideFloatingButton
    extends android.support.design.widget.CoordinatorLayout.Behavior<FloatingActionsMenu> {
    private static final Interpolator mINTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;
    private boolean mIsAnimatingIn = false;

    public HideFloatingButton(Context context, AttributeSet attrs) {
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionsMenu child,
                                   View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionsMenu child,
                                          View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionsMenu child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
            super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionsMenu child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
            dyUnconsumed);
        if (dyConsumed > 10 && !this.mIsAnimatingOut && !this.mIsAnimatingIn &&
            child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < -10 && !this.mIsAnimatingOut && !this.mIsAnimatingIn &&
            child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }
    }

    private void animateOut(final FloatingActionsMenu button) {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).translationYBy(200F).alpha(0.0F).setInterpolator(
                mINTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        HideFloatingButton.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        HideFloatingButton.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        HideFloatingButton.this.mIsAnimatingOut = false;
                        view.setVisibility(View.GONE);
                    }
                }).start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.support
                .design.R.anim.design_fab_out);
            anim.setInterpolator(mINTERPOLATOR);
            anim.setDuration(200L);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    HideFloatingButton.this.mIsAnimatingOut = true;
                }

                public void onAnimationEnd(Animation animation) {
                    HideFloatingButton.this.mIsAnimatingOut = false;
                    button.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                }
            });
            button.startAnimation(anim);
        }
    }

    private void animateIn(FloatingActionsMenu button) {
        button.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(button).translationYBy(-200F).alpha(1.0F)
                .setInterpolator(mINTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {
                        HideFloatingButton.this.mIsAnimatingIn = true;
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        HideFloatingButton.this.mIsAnimatingIn = false;
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        HideFloatingButton.this.mIsAnimatingIn = false;
                    }
                })
                .start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.support
                .design.R.anim.design_fab_in);
            anim.setDuration(200L);
            anim.setInterpolator(mINTERPOLATOR);
            button.startAnimation(anim);
        }
    }
}