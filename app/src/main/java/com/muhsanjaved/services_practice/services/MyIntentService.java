package com.muhsanjaved.services_practice.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.muhsanjaved.services_practice.MainActivity;


public class MyIntentService extends IntentService {

    private static final String TAG = "MyTag";
    public static final String INTENT_SERVICE_MESSAGE = "intent_service_message";

    public MyIntentService() {
        super("MyIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Called " );
//        Log.d(TAG, "onCreate: ThreadName: " +Thread.currentThread().getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: Called ");
//        Log.d(TAG, "onHandleIntent: ThreadName: " +Thread.currentThread().getName());
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);
        downloadSong(songName);
        sendMessageToUi(songName);
    }

    private void sendMessageToUi(String songName){
        Intent intent = new Intent(INTENT_SERVICE_MESSAGE);
        intent.putExtra(MainActivity.MESSAGE_KEY,songName);

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void downloadSong(final String songName){
        Log.d(TAG,"run: Starting download");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"download Song: "+songName+ " downloaded...");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: Called ");
//        Log.d(TAG, "onStart: ThreadName: " +Thread.currentThread().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Called ");
//        Log.d(TAG, "onDestroy: ThreadName: "+Thread.currentThread().getName());
    }

}