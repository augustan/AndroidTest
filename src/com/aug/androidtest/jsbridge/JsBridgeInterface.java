package com.aug.androidtest.jsbridge;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.aug.androidtest.tool.SingleHandler;

public class JsBridgeInterface {
    
    private Context context;
    private JsBridgeWebview jsBridgeWebview;
    
    public JsBridgeInterface(Context c, JsBridgeWebview jsBridgeWebview) {
        context = c;
        this.jsBridgeWebview = jsBridgeWebview;
    }

    @JavascriptInterface
    public void showToast(String message) {
        ToastTool.show(context, message);
    }
    
    @JavascriptInterface
    public void getToken() {
        SingleHandler.getInstance(true).post(new Runnable() {
            
            @Override
            public void run() {
                jsBridgeWebview.callJs("setToken", "123");
            }
        });
    }

    @JavascriptInterface
    public void closeWebview() {
        if (jsBridgeWebview != null) {
            jsBridgeWebview.onCloseWebview();
        }
    }
}
