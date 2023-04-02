package com.muhsanjaved.services_practice.services;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyDownloadJob extends JobService {

    private static final String TAG = "MyTag";
    private boolean isJobCancelled = false;
    private boolean mSuccess = false;

    public MyDownloadJob() {}

    @Override
    public boolean onStartJob(final JobParameters params) {

        Log.d(TAG, "onStartJob: called");
        Log.d(TAG, "onStartJob: Thread name"+ Thread.currentThread().getName());

        new Thread(new Runnable() {
            @Override
            public void run() {

                int  i =0;
                while (i<10){
                    if (isJobCancelled)
                        return;

                    Log.d(TAG, "run: Download Progress: "+ (i+1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                Log.d(TAG, "run: Download Completed");

                mSuccess = true;
                jobFinished(params,mSuccess);
            }
        }).start();


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        isJobCancelled = true;

        return true;
    }
}