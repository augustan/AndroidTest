
package com.aug.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aug.androidtest.launchmode.LaunchModeActivityA;
import com.aug.androidtest.launchmode.LaunchModeActivityB;
import com.aug.androidtest.launchmode.LaunchModeActivityC;
import com.aug.androidtest.launchmode.LaunchModeActivityD;

public class LaunchModeActivity extends Activity implements OnClickListener {
    
    Button btn1 = null;
    Button btn2 = null;
    Button btn3 = null;
    Button btn4 = null;
    protected Button btnAction = null;
    protected TextView tv = null;
    
    protected String whoAmI() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_mode_activity_layout);
        
        btn1 = (Button)findViewById(R.id.btn_1);
        btn2 = (Button)findViewById(R.id.btn_2);
        btn3 = (Button)findViewById(R.id.btn_3);
        btn4 = (Button)findViewById(R.id.btn_4);
        btnAction = (Button)findViewById(R.id.btnAction);
        tv = (TextView)findViewById(R.id.textview);
        
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnAction.setOnClickListener(this);

        tv.setText(tv.getText() + "\n" + 
                "A: standed\nB: single top\nC: single task\nD: single instance\n--------------------");
        initTextView();
        
        tv.setText(tv.getText() + "\n" + "onCreate " + whoAmI());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        tv.setText(tv.getText() + "\n" + "onResume " + whoAmI());
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        tv.setText(tv.getText() + "\n" + "onPause " + whoAmI());
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tv.setText(tv.getText() + "\n" + "onNewIntent " + whoAmI());
    }
    
    @Override
    protected void onDestroy() {
        Toast.makeText(this, "destroy activity " + whoAmI(), Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
    
    protected void initTextView() {
        tv.setText(tv.getText() + "\nthis is " + this + "\ntask = " + this.getTaskId());
    }

    @Override
    public void onClick(View v) {
        if (v == btn1) {
            Intent intent = new Intent(this, LaunchModeActivityA.class);
            this.startActivity(intent);
        } else if (v == btn2) {
            Intent intent = new Intent(this, LaunchModeActivityB.class);
            this.startActivity(intent);
        } else if (v == btn3) {
            Intent intent = new Intent(this, LaunchModeActivityC.class);
            this.startActivity(intent);
        } else if (v == btn4) {
            Intent intent = new Intent(this, LaunchModeActivityD.class);
            this.startActivity(intent);
        } else if (v == btnAction) {
            onActionBtnClicked();
        }
        
    }
    
    protected void onActionBtnClicked() {
        // simple make crash.
        int k = 0;
        int j = 100/k;
    }

}
