package com.aug.androidtest.tool;

import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;

import android.content.Context;

public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler myCrashHandler = new CrashHandler();
    private Context context;
    private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dataFormatFileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return myCrashHandler;
    }

    public void initOnAppCreate(Context context) {
        this.context = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread arg0, Throwable arg1) {

        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
