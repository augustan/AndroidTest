package com.aug.androidtest;

import com.aug.androidtest.service.IAugService;
import com.aug.androidtest.service.IServiceCallback;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ServiceTestActivity extends Activity implements OnClickListener {
    
    private IAugService service = null;
    
    private IServiceCallback serviceCallback = new IServiceCallback.Stub() {
        
        @Override
        public void startActivity(String actionName, Bundle bundle) throws RemoteException {
            // TODO Auto-generated method stub

            Log.e("ajj", "activit == startActivity");
        }

        @Override
        public String getResult(String payResult, String payDetailInfomation, Bundle bundle)
                throws RemoteException {
            // TODO Auto-generated method stub

            Log.e("ajj", "activit == getResult");
            return "i am in activity. ";
        }
    };

    Button btn1 = null;
    Button btn2 = null;
    Button btn3 = null;
    Button btn4 = null;
    protected Button btnAction = null;
    protected TextView tv = null;

    private ServiceConnection connect = new ServiceConnection() {
        
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.w("ajj", "onServiceDisconnected");
            ServiceTestActivity.this.service = null;
            tv.setText("service = null");
        }
        
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w("ajj", "onServiceConnected");
            IAugService s = IAugService.Stub.asInterface(service);
            tv.setText("service = " + s);
            ServiceTestActivity.this.service = s;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.service_test_activity);

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
    }
    
    @Override
    protected void onDestroy() {
        if (service != null) {
            unbindService(connect);
            service = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == btn1) {
            tv.setText("not implemented");
        } else if (v == btn2) {
            tv.setText("not implemented");
        } else if (v == btn3) {
            Intent i = new Intent(); 
            i.setClassName("com.aug.androidtest",
                    "com.aug.androidtest.service.AugTestRemoteService");
            bindService(i, connect, Context.BIND_AUTO_CREATE);
            tv.setText("binding...");
        } else if (v == btn4) {
            if (service != null) {
                unbindService(connect);
                service = null;
            }
            tv.setText("unbind");
        } else if (v == btnAction) {
            onActionBtnClicked();
        }
    }

    protected void onActionBtnClicked() {
        if (service != null) {
            try {
                service.registerCallback(serviceCallback);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
            try {
                tv.setText(service.getFormatTime());
            } catch (RemoteException e) {
                e.printStackTrace();
                tv.setText(e.getMessage());
            }
        } else {
            tv.setText("service is null");
        }
    }
}
