
package com.aug.androidtest.jsbridge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aug.androidtest.tool.LogUtils;

public class JsBridgeWebview extends WebView {

    public interface IWebClientCallback {
        void onWebClientError(String msg);

        void onCloseWebview();
    }

    private class InnerWebviewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class InnerWebChromeClient extends WebChromeClient {

        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            super.onConsoleMessage(message, lineNumber, sourceID);
            if (message != null && webClientCallback != null && message.contains("ReferenceError")) {
                webClientCallback.onWebClientError(message);
            }
        }
    }

    private Context mContext = null;
    private JsBridgeInterface jsBridgeInterface;
    private IWebClientCallback webClientCallback;

    public JsBridgeWebview(Context context) {
        super(context);
        init(context);
    }

    public JsBridgeWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public JsBridgeWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        mContext = context;
        jsBridgeInterface = new JsBridgeInterface(mContext, this);

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setSavePassword(false);

        this.addJavascriptInterface(jsBridgeInterface, "android");
        this.setWebViewClient(new InnerWebviewClient());
        this.setWebChromeClient(new InnerWebChromeClient());
    }

    public void setWebClientCallback(IWebClientCallback webClientCallback) {
        this.webClientCallback = webClientCallback;
    }

    public void callJs(String funcName, String... params) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String string : params) {
            if (TextUtils.isEmpty(string)) {
                continue;
            }
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(", ");
            }
            sb.append("'");
            sb.append(string);
            sb.append("'");
        }
        String js = String.format("javascript:%s(%s)", funcName, sb.toString());
        LogUtils.d("webclient", "call js: " + js);
        loadUrl(js);
    }

    public boolean onKeyEvent(String keyCode) {
        callJs("onKeyEvent", keyCode);
        return true;
    }

    public void onCloseWebview() {
        if (webClientCallback != null) {
            webClientCallback.onCloseWebview();
        }
    }

}
