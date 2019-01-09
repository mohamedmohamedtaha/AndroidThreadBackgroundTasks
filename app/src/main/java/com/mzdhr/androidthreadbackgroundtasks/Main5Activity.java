package com.mzdhr.androidthreadbackgroundtasks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.pattern.MyAsyncTask;
import com.mzdhr.androidthreadbackgroundtasks.pattern.MyAsyncTaskCallbacks;

public class Main5Activity extends AppCompatActivity implements MyAsyncTaskCallbacks {

    private static final String TAG = "Main5Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerAsyncTask();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void triggerAsyncTask() {
        new MyAsyncTask(this).execute("Mohammad");
    }

    @Override
    public void onProgressUpdate(Integer values) {
        // Update Progress bar here
        Log.d(TAG, "onProgressUpdate: " + values);
    }

    @Override
    public void onPostExecute(Long result) {
        // Publish result here
        Log.d(TAG, "onPostExecute: result -> " + result);
    }

}
