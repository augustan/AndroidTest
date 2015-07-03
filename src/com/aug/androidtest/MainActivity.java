
package com.aug.androidtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aug.androidtest.movie.gif.MovieGifActivity;

public class MainActivity extends Activity implements OnClickListener {
    
    private LinearLayout contentRoot;
    private ArrayList<Class<?>> mTestClass = new ArrayList<Class<?>>();
    {
        mTestClass.add(MySurfaceViewActivity.class);
        mTestClass.add(GLSurfaceViewActivity.class);
        mTestClass.add(MovieGifActivity.class);
        mTestClass.add(MeasureText.class);
        mTestClass.add(Transition3d.class);
        mTestClass.add(DrawTextActivity.class);
        mTestClass.add(AnimationActivity.class);
        mTestClass.add(LaunchModeActivity.class);
        mTestClass.add(ServiceTestActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_main);
        contentRoot = (LinearLayout) findViewById(R.id.content_root);
        
        for(int i = 0; i < mTestClass.size();i++) {
            Class<?> item = mTestClass.get(i);
            addTestBtn(item.getSimpleName(), i);
        }
    }
    
    private void addTestBtn(String desc, int id) {
        Button btn = new Button(this);
        btn.setText(id + "  " + desc);
        btn.setId(id);
        btn.setOnClickListener(this);
        contentRoot.addView(btn);
    }

    @Override
    public void onClick(View v) {
        Class<?> item = mTestClass.get(v.getId());
        this.startActivity(new Intent(this, item));
    }
}
