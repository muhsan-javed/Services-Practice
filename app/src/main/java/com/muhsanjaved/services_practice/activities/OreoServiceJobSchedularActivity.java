package com.muhsanjaved.services_practice.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.muhsanjaved.services_practice.R;
import com.muhsanjaved.services_practice.services.MyDownloadJob;

public class OreoServiceJobSchedularActivity extends AppCompatActivity {
    private static final int JOB_ID = 1001;
    private static final String TAG = "MyTag";
    Button btnScheduleService, btnCancelService;
    TextView output_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oreo_service_job_schedular);

        btnScheduleService = findViewById(R.id.btnScheduleService);
        btnCancelService = findViewById(R.id.btnCancelService);
        output_TextView = findViewById(R.id.output_text);

        // This method cancels the scheduled job
        btnCancelService.setOnClickListener(v -> {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(JOB_ID);
            Log.d(TAG, "cancelService: Job cancelled");

        });
        //This method schedules the job
        btnScheduleService.setOnClickListener(v -> {

            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

            ComponentName componentName = new ComponentName(this, MyDownloadJob.class);
            JobInfo jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setMinimumLatency(0)
//                    .setPeriodic(15*60*1000)
                    .setPeriodic(5000)
                    .setPersisted(true)
                    .build();

            int result = jobScheduler.schedule(jobInfo);

            if (result == JobScheduler.RESULT_SUCCESS){
                Log.d(TAG, "scheduleService: Job Scheduled");
            }else {
                Log.d(TAG, "scheduleService: Job not Scheduled");
            }

        });
    }
}