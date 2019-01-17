package com.mzdhr.androidthreadbackgroundtasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mzdhr.androidthreadbackgroundtasks.service.MyIntentService;
import com.mzdhr.androidthreadbackgroundtasks.service.MyStartedService;

public class Main6Activity extends AppCompatActivity {

    private TextView mResultTextView;

    // Used for Started Service Example (Started Service)
    private BroadcastReceiver mStartedServiceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(Constant.RESULT_NUMBER);
            mResultTextView.setText(result);
        }
    };

    // Register our Local Broadcast (Started Service)
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.my_started_service.to.activity");    // Modifier to determined this broadcast
        registerReceiver(mStartedServiceBroadcastReceiver, intentFilter);
    }

    // Unregister our Local Broadcast (Started Service)
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mStartedServiceBroadcastReceiver);
    }


    // Field used for IntentService (IntentService)
    private Handler mHandler = new Handler();

    // Used for IntentService (IntentService)
    private class MyIntentServiceResultReceiver extends ResultReceiver {
        private static final String TAG = "MyIntentServiceResultRe";

        public MyIntentServiceResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.d(TAG, "onReceiveResult: " + Thread.currentThread().getName());

            if (resultCode == 88 && resultData != null) {
                final String result = resultData.getString(Constant.RESULT_NUMBER);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mResultTextView.setText(result);
                        Log.d(TAG, "run: " + Thread.currentThread().getName());
                    }
                });

            }

        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResultTextView = findViewById(R.id.resultTextView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void startStartedService(View view) {
        Intent intent = new Intent(Main6Activity.this, MyStartedService.class);
        intent.putExtra(Constant.FIRST_NUMBER, 10);
        intent.putExtra(Constant.SECOND_NUMBER, 20);
        startService(intent);
    }

    public void stopStartedService(View view) {
        Intent intent = new Intent(Main6Activity.this, MyStartedService.class);
        stopService(intent);
        // Or we can use stopSelf() inside the service class ot stop it, after our work has done.
    }


    public void startIntentService(View view) {


        Intent intent = new Intent(Main6Activity.this, MyIntentService.class);
        intent.putExtra(Constant.FIRST_NUMBER, 10);
        intent.putExtra(Constant.SECOND_NUMBER, 20);

        ResultReceiver resultReceiver = new MyIntentServiceResultReceiver(null);
        intent.putExtra(Constant.RECEIVE_RESULT_KEY, resultReceiver);

        startService(intent);
    }

    private void stopIntentService() {
        // No need.
    }


}
