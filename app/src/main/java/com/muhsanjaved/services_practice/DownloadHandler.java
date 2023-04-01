package com.muhsanjaved.services_practice;

import android.os.Bundle;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import com.muhsanjaved.services_practice.services.MyDownloadService;

public class DownloadHandler {
    private static final String TAG = "MyTag";
    private MyDownloadService mDownloadService;
//    private ResultReceiver mResultReceiver;

    public DownloadHandler(){

    }

    public void handleMessage(Message msg){
        downloadSong(msg.obj.toString());
//        boolean stopSelfResult = mDownloadService.stopSelfResult(msg.arg1);
        mDownloadService.stopSelfResult(msg.arg1);
        Log.d(TAG, "handleMessage: stopSelfResult"+ "stopSelfResult" +" startId : " +msg.arg1);

        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.MESSAGE_KEY, msg.obj.toString());
//        mResultReceiver.send(MainActivity.RESULT_OK , bundle);
    }

    public void setDownloadService(MyDownloadService myDownloadService){
        this.mDownloadService = myDownloadService;
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
