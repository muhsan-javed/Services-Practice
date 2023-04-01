package com.muhsanjaved.services_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.muhsanjaved.services_practice.constants.Constants;
import com.muhsanjaved.services_practice.services.MusicPLayerService;
import com.muhsanjaved.services_practice.services.MyDownloadService;
import com.muhsanjaved.services_practice.services.MyForegroundService;
import com.muhsanjaved.services_practice.services.MyIntentService;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    Button btnClear, btnRunCode,btnPlay;
    TextView output_text;
    private static final String TAG ="MyTag";
    private ProgressBar progressBar;
    //private Handler mHandler;
    private MusicPLayerService mMusicPLayerService;
    private boolean mBound = false;


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {

            MusicPLayerService.MyServiceBinder  myServiceBinder = (MusicPLayerService.MyServiceBinder) iBinder;

            mMusicPLayerService = myServiceBinder.getService();
            mBound = true;
            Log.d(TAG, "onServiceConnected: Called");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };


    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           // String songName = intent.getStringExtra(MESSAGE_KEY);

//            log(songName + " Downloaded....");
            Log.d(TAG, "onReceive: Thread name: "+ Thread.currentThread().getName());
            String result = intent.getStringExtra(MESSAGE_KEY);
            if (result == "done")
                btnPlay.setText("Play");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent= new Intent(MainActivity.this, MusicPLayerService.class);
        bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,new IntentFilter(MusicPLayerService.MUSIC_COMPLETE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(mServiceConnection);
            mBound = false;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mBroadcastReceiver);
    }

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
            log("Playing Music !");
//            log(mMusicPLayerService.getValue());
            displayProgressBar(true);

//            ResultReceiver resultReceiver = new MyDownloadResultReceiver(null);

            // Send intent to download service
          /*  for (String song: PLayList.songs){
                Intent intent= new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra(MESSAGE_KEY,song);
//                intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);
                startService(intent);
            }
*/
            Intent intent= new Intent(MainActivity.this, MyForegroundService.class);
            startService(intent);
        });

//        mHandler = new Handler();

        btnPlay.setOnClickListener(v -> {
            if (mBound){
                if (mMusicPLayerService.isPlaying()){
                    mMusicPLayerService.pause();
                    btnPlay.setText("PLay");
                }else {
                    Intent intent = new Intent(MainActivity.this, MusicPLayerService.class);
                    intent.setAction(Constants.MUSIC_SERVICE_ACTION_START);
                    startService(intent);

                    mMusicPLayerService.play();
                    btnPlay.setText("Pause");
                }
            }
        });
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
        btnPlay = findViewById(R.id.btnPlayMusic);
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