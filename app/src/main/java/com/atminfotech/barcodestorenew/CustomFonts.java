package com.atminfotech.barcodestorenew;

import android.app.Application;

public class CustomFonts extends Application {

    private static CustomFonts mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/MavenPro-Regular.ttf");
    }
    public static synchronized CustomFonts getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}