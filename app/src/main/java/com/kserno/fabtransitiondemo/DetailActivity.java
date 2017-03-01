package com.kserno.fabtransitiondemo;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by filipsollar on 1.3.17.
 */

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.tv_text) TextView tvText;
    @Nullable @BindView(R.id.fab_add) FloatingActionButton fabAdd;
    @Nullable @BindView(R.id.linear_layout_reveal) LinearLayout linearLayoutReveal;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mUnbinder = ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupAnimation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupAnimation() {
        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                circularReveal(linearLayoutReveal);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void circularReveal(View viewRoot) {
        viewRoot.setVisibility(View.VISIBLE);
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());


        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fadeInViews();
                linearLayoutReveal.setBackgroundColor(Color.WHITE);
                fabAdd.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        anim.start();
    }


    private void fadeInViews() {
        tvText.animate()
                .alpha(1)
                .setDuration(300)
                .start();
    }

    private void fadeOutViews() {
        tvText.animate()
                .alpha(0)
                .setDuration(300)
                .start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
