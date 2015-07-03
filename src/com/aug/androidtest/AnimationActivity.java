package com.aug.androidtest;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class AnimationActivity extends Activity implements OnClickListener {

    private LinearLayout root_layout;
    private View header;
    private Button btn_up;
    private Button btn_down;
    private ListView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity_layout);

        root_layout = (LinearLayout) findViewById(R.id.root_layout);
        header = findViewById(R.id.header);
        btn_up = (Button) findViewById(R.id.btn_up);
        btn_down = (Button) findViewById(R.id.btn_down);
        content = (ListView) findViewById(R.id.content);

        btn_up.setOnClickListener(this);
        btn_down.setOnClickListener(this);

        content.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item, new String[] {
                "11", "22", "33", "44", "55", "66", "77", "88", "99", "00", "211", "222", "233",
                "244", "255", "266", "277", "288", "299", "200"
        }) {
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btn_up) {
            int h = header.getHeight();
            ValueAnimator anim = ValueAnimator.ofFloat(0, -h).setDuration(1000);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.start();

            anim.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    LayoutParams lp = (LinearLayout.LayoutParams) header.getLayoutParams();
                    lp.topMargin = (int) cVal;
                    header.setLayoutParams(lp);

                    Log.i("ajj", "set top = " + cVal);
                }
            });

        } else if (v == btn_down) {

            int h = header.getHeight();
            ValueAnimator anim = ValueAnimator.ofFloat(-h, 0).setDuration(1000);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.start();
            
            anim.addUpdateListener(new AnimatorUpdateListener() {
                
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    LayoutParams lp = (LinearLayout.LayoutParams) header.getLayoutParams();
                    lp.topMargin = (int) cVal;

                    header.setLayoutParams(lp);

                    Log.d("ajj", "set top = " + cVal);
                }
            });
        }

    }

}
