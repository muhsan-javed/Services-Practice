package com.muhsanjaved.services_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.muhsanjaved.services_practice.services.MyDownloadService;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    Button btnClear, btnRunCode;
    TextView output_text;
    private static final String TAG ="MyTag";
    private ProgressBar progressBar;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        btnClear.setOnClickListener(v -> {output_text.setText("");progressBar.setVisibility(View.INVISIBLE);
            scrollTextToEnd();
            Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
            stopService(intent);
        });

        btnRunCode.setOnClickListener(v -> {
            log("Runner code");
            displayProgressBar(true);

//            ResultReceiver resultReceiver = new MyDownloadResultReceiver(null);

            // Send intent to download service
            for (String song: PLayList.songs){
                Intent intent= new Intent(MainActivity.this, MyDownloadService.class);
                intent.putExtra(MESSAGE_KEY,song);
//                intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);
                startService(intent);
            }

        });

        mHandler = new Handler();
    }

    private void scrollTextToEnd() {}
    public void log(String message) {
        Log.i(TAG, message);
        output_text.append(message + "\n");
        scrollTextToEnd();
    }
    public void displayProgressBar(boolean display){
        if (display)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }
    private void initViews() {
        btnClear = findViewById(R.id.btnClear);
        btnRunCode = findViewById(R.id.btnRunCode);
        output_text = findViewById(R.id.output_text);
        progressBar = findViewById(R.id.progressBar);
        //scrollView = findViewById(R.id.scrollViewId);
    }

  /*  public class MyDownloadResultReceiver extends ResultReceiver {

        public MyDownloadResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (resultCode == RESULT_OK && resultData!=null){
                Log.d(TAG, "onReceiveResult: Thread name: "+ Thread.currentThread().getName());

                final String songName = resultData.getString(MESSAGE_KEY);
              *//*  MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        log(songName + "Downloaded");
                    }
                });*//*


                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        log(songName + "Downloaded");
                    }
                });
            }
        }
    }*/
}