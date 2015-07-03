package com.aug.androidtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AugTestRemoteService extends Service {
    
    private static final String TAG = "ajj";
    
    private IServiceCallback serviceCallback = null;

//    private RemoteCallbackList<IServiceCallback> mCallbacks = new RemoteCallbackList<IServiceCallback>();  
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service onCreate");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "service onRebind");
        super.onRebind(intent);
    }
    
    @Override
    public void onDestroy() {
        Log.d(TAG, "service onDestroy");
        super.onDestroy();
        System.exit(0);
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "service onBind");
        return stub;
    }

    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "service onStart");
        super.onStart(intent, startId);
    }

    //步骤2.1：具体实现接口中暴露给client的Stub，提供一个stub inner class来具体实现。 
    private IAugService.Stub stub = new IAugService.Stub() {

        //步骤2.1：具体实现AIDL文件中接口的定义的各个方法。
        @Override
        public String getFormatTime() throws RemoteException {
            SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm:ss");
            String LgTime = sdformat.format(new Date());
            Log.e(TAG, "service getFormatTime: " + LgTime);
            
            String tips = "";
            if (serviceCallback != null) {
                tips = serviceCallback.getResult(null, null, null);
            }
            
            Intent i = new Intent();
            i.setClassName("com.aug.temp.test", "com.aug.temp.test.MainActivity");  
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AugTestRemoteService.this.startActivity(i);
            
            return tips + LgTime;
        }

        @Override
        public void registerCallback(IServiceCallback callback) throws RemoteException {
            // TODO Auto-generated method stub
            serviceCallback = callback;
            Log.d(TAG, "registerCallback: " + callback);
        }

        @Override
        public void unregisterCallback(IServiceCallback callback) throws RemoteException {
            // TODO Auto-generated method stub
            serviceCallback = null;
        }
    };
}
