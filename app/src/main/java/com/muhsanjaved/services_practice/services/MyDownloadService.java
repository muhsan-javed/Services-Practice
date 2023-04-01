package com.muhsanjaved.services_practice.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.muhsanjaved.services_practice.DownloadHandler;
import com.muhsanjaved.services_practice.DownloadThread;
import com.muhsanjaved.services_practice.MainActivity;

public class MyDownloadService extends Service {
    private static final String TAG = "MyTag";
    private DownloadThread mDownloadThread;
    private ResultReceiver mResultReceiver;

    public MyDownloadService(){}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Called");
        mDownloadThread = new DownloadThread();
        mDownloadThread.start();

        while (mDownloadThread.mHandler == null){

        }

        mDownloadThread.mHandler.setDownloadService(this);
        mDownloadThread.mHandler.setContext(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called"+ intent.getStringExtra(MainActivity.MESSAGE_KEY)
        + "With startId:: "+startId);
        final String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);

//        mDownloadThread.mHandler.setResultReceiver((ResultReceiver)
//                intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER));

       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadSong(songName);
            }
        });
        thread.start();*/

        Message message = Message.obtain();
        message.obj = songName;
        message.arg1=startId;

        mDownloadThread.mHandler.handleMessage(message);

        MyDownloadTask myDownloadTask = new MyDownloadTask();
        myDownloadTask.execute(songName);


        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Called");
        return null;
    }

   /* private void downloadSong(final String songName){
        Log.d(TAG,"run: Starting download...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"download Song: "+songName+ " downloaded");
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Called");
    }

    class MyDownloadTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            for (String song: strings){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(song);
            }
            return "All Songs have been downloaded";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d(TAG, "onProgressUpdate: Song Download: "+ values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: Result is: "+ s);
        }
    }
}
