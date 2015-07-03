package com.aug.androidtest.service;

import com.aug.androidtest.service.IServiceCallback;
import android.os.Bundle;

interface IAugService {
    String getFormatTime();
    void registerCallback(in IServiceCallback callback);
    void unregisterCallback(in IServiceCallback callback);
}
