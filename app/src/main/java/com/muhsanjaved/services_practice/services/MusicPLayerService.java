package com.muhsanjaved.services_practice.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.muhsanjaved.services_practice.MainActivity;
import com.muhsanjaved.services_practice.R;

public class MusicPLayerService extends Service {

    private static final String TAG = "MyTag";
    public static final String MUSIC_COMPLETE = "MUSIC_COMPLETE";
    private final Binder mBinder = new MyServiceBinder();
    private MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.music);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(MUSIC_COMPLETE);
                intent.putExtra(MainActivity.MESSAGE_KEY, "done");

                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                stopSelf(); //Music compete auto stop Service
            }
        });
        Log.d(TAG, "onCreate:....... ");
    }

    public class MyServiceBinder extends Binder{

        public MusicPLayerService getService(){

        return MusicPLayerService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: .........");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ........");

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: .....");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ........");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: .....");
        super.onDestroy();
        mPlayer.release();
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }
    public void play(){
        mPlayer.start();
    }

    public void pause(){
        mPlayer.pause();
    }
    /*public String getValue(){
        return "data from service";
    }*/
}
