package com.muhsanjaved.services_practice.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.muhsanjaved.services_practice.MainActivity;
import com.muhsanjaved.services_practice.R;
import com.muhsanjaved.services_practice.constants.Constants;

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

                stopForeground(true);
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

        switch (intent.getAction()){
            case Constants.MUSIC_SERVICE_ACTION_PLAY:{
                Log.d(TAG, "onStartCommand: PLAY called");
                play();
                break;
            }
            case Constants.MUSIC_SERVICE_ACTION_PAUSE:{
                Log.d(TAG, "onStartCommand: PAUsE called");
                pause();
                break;
            }
            case Constants.MUSIC_SERVICE_ACTION_STOP:{
                Log.d(TAG, "onStartCommand: STOP called");
                stopForeground(true);
                break;
            }
            case Constants.MUSIC_SERVICE_ACTION_START:{
                Log.d(TAG, "onStartCommand: START ");
                showNotification();
                break;
            }
            default:{
//                stopSelf();
            }
        }

//        showNotification();
        Log.d(TAG, "onStartCommand: .........");
        return START_NOT_STICKY;
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelId");

        // Intent for PLay button
        Intent pIntent = new Intent(this,MusicPLayerService.class);
        pIntent.setAction(Constants.MUSIC_SERVICE_ACTION_PLAY);

        PendingIntent playIntent =PendingIntent.getService(this,100,pIntent,0);

        // Intent for PLay button
        Intent psIntent = new Intent(this,MusicPLayerService.class);
        pIntent.setAction(Constants.MUSIC_SERVICE_ACTION_PAUSE);

        PendingIntent pauseIntent =PendingIntent.getService(this,100,pIntent,0);

        // Intent for PLay button
        Intent sIntent = new Intent(this,MusicPLayerService.class);
        pIntent.setAction(Constants.MUSIC_SERVICE_ACTION_STOP);

        PendingIntent stopIntent =PendingIntent.getService(this,100,pIntent,0);

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("This is dome music player")
                .setContentTitle("Music Player")
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,"PLay",playIntent))
                .addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause,"PLay",pauseIntent))
                .addAction(new NotificationCompat.Action(android.R.drawable.star_off,"Stop",stopIntent))
        ;

        startForeground(123, builder.build());

    }    @Override
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
