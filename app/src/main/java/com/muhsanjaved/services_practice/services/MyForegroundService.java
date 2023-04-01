package com.muhsanjaved.services_practice.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.muhsanjaved.services_practice.R;

public class MyForegroundService extends Service {
    private static final String TAG = "MyTag";

    public MyForegroundService() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        showNotification();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "run: starting download");
                int i = 0;
                while (i<= 10){
                    Log.d(TAG, "run: Progress is: "+ (i+1));
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    i++;
                }
                Log.d(TAG, "run: Download completed");
                stopForeground(true);
                stopSelf();
            }
        });
        thread.start();

        return START_STICKY;
    }

    private void showNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelId");

        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("This is service notification")
                .setContentTitle("Title")
                ;
        Notification notification = builder.build();

        startForeground(123, notification);
    }
    @Override
    public IBinder onBind(Intent intent) {

      return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: .....");
        super.onDestroy();
    }

}