package com.muhsanjaved.services_practice.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicPLayerService extends Service {

    private static final String TAG = "MyTag";
    private final Binder mBinder = new MyServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate:....... ");
    }

    public class MyServiceBinder extends Binder{

        public MusicPLayerService getService(){

        return MusicPLayerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ......");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ........");

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: .....");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: .....");
        super.onDestroy();
    }

    public String getValue(){
        return "data from service";
    }
}
