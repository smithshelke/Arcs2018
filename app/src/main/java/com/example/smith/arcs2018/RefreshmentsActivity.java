package com.example.smith.arcs2018;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Toast;

public class RefreshmentsActivity extends AppCompatActivity {

    private CardView mCardView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshments);
        mCardView = (CardView)findViewById(R.id.qr_code_container);
        animateStartCode();
    }
    private void animateStartCode(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCardView.animate()
                        .alpha(1)
                        .scaleX(1)
                        .scaleY(1)
                        .setDuration(300).start();
            }
        },250);
    }

    @Override
    public void onBackPressed() {
        mCardView.animate()
                .scaleX(.7f)
                .scaleY(.7f)
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        RefreshmentsActivity.super.onBackPressed();
                    }
                })
                .setDuration(250).start();
    }
}
