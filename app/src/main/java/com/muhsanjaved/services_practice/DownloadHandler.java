package com.muhsanjaved.services_practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.muhsanjaved.services_practice.services.MyDownloadService;

public class DownloadHandler {
    private static final String TAG = "MyTag";
    private MyDownloadService mDownloadService;
//    private ResultReceiver mResultReceiver;
    public static  final String SERVICE_MESSAGE = "serviceMessage";
    private Context context;

    public DownloadHandler(){

    }

    public void handleMessage(Message msg){
        downloadSong(msg.obj.toString());
//        boolean stopSelfResult = mDownloadService.stopSelfResult(msg.arg1);
        mDownloadService.stopSelfResult(msg.arg1);
        Log.d(TAG, "handleMessage: stopSelfResult"+ "stopSelfResult" +" startId : " +msg.arg1);

        sendMessageToUi(msg.obj.toString());

//        Bundle bundle = new Bundle();
//        bundle.putString(MainActivity.MESSAGE_KEY, msg.obj.toString());
//        mResultReceiver.send(MainActivity.RESULT_OK , bundle);
    }

    private void sendMessageToUi(String s) {

        Intent intent = new Intent(SERVICE_MESSAGE);
        intent.putExtra(MainActivity.MESSAGE_KEY, s);

        // local broad cast recevier

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    public void setDownloadService(MyDownloadService myDownloadService){
        this.mDownloadService = myDownloadService;
    }
    public void setContext(Context context){
        this.context = context;
    }

  /*  public void setResultReceiver(ResultReceiver mResultReceiver){
        this.mResultReceiver = mResultReceiver;
    }
*/
    private void downloadSong(final String songName){
        Log.d(TAG,"run: Starting download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"download Song: "+songName+ " downloaded...");
    }

}
