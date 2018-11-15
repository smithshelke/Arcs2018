package com.example.smith.arcs2018;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mImageView = (ImageView)findViewById(R.id.logoSplash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();*/
            }
        },2000);
        mImageView.animate()
                .scaleYBy(.1f)
                .scaleXBy(.1f)
                .setDuration(1000)
                .setInterpolator(new CycleInterpolator(5));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageView.animate()
                        .scaleYBy(100f)
                        .scaleXBy(100f)
                        .alpha(0)
                        .setDuration(1000)
                        .setInterpolator(new AnticipateOvershootInterpolator(.1f))
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                finish();
                            }
                        });
            }
        },1000);
    }
}
