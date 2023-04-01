package com.muhsanjaved.services_practice;

import android.os.Looper;

public class DownloadThread extends Thread {
    private static final String TAG = "MyTag";
    public DownloadHandler mHandler;

    public DownloadThread() {
    }

    @Override
    public void run() {

        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();
    }
}
