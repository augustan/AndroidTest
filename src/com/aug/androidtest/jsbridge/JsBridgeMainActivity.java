
package com.aug.androidtest.jsbridge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.aug.androidtest.R;
import com.aug.androidtest.jsbridge.JsBridgeWebview.IWebClientCallback;
import com.aug.androidtest.tool.LogUtils;

public class JsBridgeMainActivity extends Activity {
    
    private static final String WEBVIEW_START_URL = "webview_start_url";

    private JsBridgeWebview jswebview;

    private String startUrl = "";
    private static IWebListener webListener;

    public static void launch(IWebListener listener, Context context, String url) {
        webListener = listener;
        Intent intent = new Intent(context, JsBridgeMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEBVIEW_START_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_bridge_main_activity);
        parseIntent();
        initViews();
    }

    public void initViews() {
        jswebview = (JsBridgeWebview) findViewById(R.id.jswebview);

        ViewGroup.LayoutParams lp = jswebview.getLayoutParams();
        lp.width = 800;
        lp.height = 600;

        jswebview.setWebClientCallback(new IWebClientCallback() {

            @Override
            public void onWebClientError(String msg) {
                ToastTool.show(JsBridgeMainActivity.this, "网页有错误，自动退出");
                LogUtils.e("webclient", msg);
                finish();
            }

            @Override
            public void onCloseWebview() {
                finish();
            }
        });

        jswebview.loadUrl(startUrl);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntent(getIntent());
    }

    public void parseIntent() {
        parseIntent(getIntent());
    }

    private void parseIntent(Intent intent) {
        startUrl = null;
        if (intent != null) {
            startUrl = intent.getStringExtra(WEBVIEW_START_URL);
        }
        if (TextUtils.isEmpty(startUrl)) {
            startUrl = "file:///android_asset/jsbridge_index.html";
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = false;
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_ESCAPE:
                if (jswebview.canGoBack()) {
                    jswebview.goBack();
                    handled = true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    handled = true;
                } else {
                    handled = jswebview.onKeyEvent("KEYCODE_BACK");
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    handled = true;
                } else {
                    handled = jswebview.onKeyEvent("KEYCODE_UP");
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    handled = true;
                } else {
                    handled = jswebview.onKeyEvent("KEYCODE_DOWN");
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    handled = true;
                } else {
                    handled = jswebview.onKeyEvent("KEYCODE_LEFT");
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    handled = true;
                } else {
                    handled = jswebview.onKeyEvent("KEYCODE_RIGHT");
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    handled = true;
                } else {
                    handled = jswebview.onKeyEvent("KEYCODE_OK");
                }
                break;
            default:
                handled = false;
                break;
        }

        if (!handled) {
            handled = super.dispatchKeyEvent(event);
        }
        return handled;
    }

//    @Override
//    public void onBackPressed() {
//        // check callback from html5
//        List<String> callbacks = mJsObj.getBackCallbacks();
//        boolean consumedByJs = false;
//        if (callbacks != null && callbacks.size() > 0) {
//            for (String jsCallback : callbacks) {
//                if (!StringUtils.isEmpty(jsCallback)) {
//                    fireBackCallback(jsCallback);
//                    consumedByJs = true;
//                }
//            }
//        }
//        if (consumedByJs) {
//            return;
//        }
//
//        if (jswebview.canGoBack()) {
//            jswebview.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onResume() {
        if (TextUtils.isEmpty(startUrl)) {
            finish();
        }
        super.onResume();
    }

    @Override
    public void finish() {
        if (webListener != null) {
            webListener.onWebClose();
            webListener = null;
        }
        super.finish();
    }
}
