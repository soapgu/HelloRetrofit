package com.soapgu.helloretrofit;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    @Override
    public void onCreate() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("SoapAPP")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        Logger.i("-------APP Create-------");
        super.onCreate();
    }
}
