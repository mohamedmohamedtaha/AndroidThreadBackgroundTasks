package com.mzdhr.androidthreadbackgroundtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.service.MyStartedService;

public class Main6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void startStartedService() {
        Intent intent = new Intent(Main6Activity.this, MyStartedService.class);

        intent.putExtra(Constant.FIRST_NUMBER, 10);
        intent.putExtra(Constant.SECOND_NUMBER, 20);

        startService(intent);
    }

    private void stopStartedService() {
        Intent intent = new Intent(Main6Activity.this, MyStartedService.class);
        stopService(intent);
        // Or we can use stopSelf() inside the service class ot stop it, after our work has done.
    }



}
