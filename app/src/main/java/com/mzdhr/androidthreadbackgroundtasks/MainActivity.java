package com.mzdhr.androidthreadbackgroundtasks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.thread.MyRunnable;
import com.mzdhr.androidthreadbackgroundtasks.thread.MyThread;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playMusic();
        downloadImage();
        updateUserInfo();
        DeleteTemp();


        MyThread myThread = new MyThread();
        myThread.start();

        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();

        Handler handler = new Handler();
        handler.postDelayed(myRunnable, 5000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: I'm running from Thread -> " + Thread.currentThread().getName());
            }
        });


        int age1 = 20;
        for (int i = 0; i < 10; i++) {
            age1++;
        }

        int age2 = 10;
        for (int i = 0; i < 10; i++) {
            age2++;
        }



    }

    private void DeleteTemp() {

    }

    private void updateUserInfo() {

    }

    private void downloadImage() {

    }

    private void playMusic() {
    }

    public void openThreadLooperHandlerActivity(View view) {
        // Pattern Thread, Looper, Handler
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

    public void openHandlerThreadsActivity(View view) {
        // Pattern HandlerThreads
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        startActivity(intent);
    }

    public void openThreadPoolsActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Main4Activity.class);
        startActivity(intent);
    }


    public void openAsyncTaskActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Main5Activity.class);
        startActivity(intent);
    }

    public void openServicesActivity(View view) {
        Intent intent = new Intent(MainActivity.this, Main6Activity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
