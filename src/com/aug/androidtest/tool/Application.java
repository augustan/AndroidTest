
package com.aug.androidtest.tool;

public class Application extends android.app.Application {

    private static Application instance = null;

    public Application() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().initOnAppCreate(getApplicationContext());
    }
}
