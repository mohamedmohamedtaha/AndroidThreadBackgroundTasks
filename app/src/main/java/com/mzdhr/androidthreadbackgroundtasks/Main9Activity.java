package com.mzdhr.androidthreadbackgroundtasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.alarms.BroadcastForAlarm;

public class Main9Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void setAlarmByElapsedRealTime() {
        // Create a Broadcast class, to fire when alarm triggered.
        // Create a Service class, that hold our code.
        // Create a Thread (or use IntentService in the previous step).

        // Create an Intent, that runs our Broadcast class
        Intent intent = new Intent(Main9Activity.this, BroadcastForAlarm.class);
        intent.putExtra("NAME_KEY", "Mohammad");

        // Create a Pending Intent, that hold our Intent.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                Main9Activity.this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Get Alarm Manager from the system.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the Alarm
        // Alarm Type: ELAPSED_REALTIME or ELAPSED_REALTIME_WAKEUP | RTC or RTC_WAKEUP.
        // Time in milliseconds + Elapsed Time when device is booted.
        // Action that need to be fired, Pending Intent.
        alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);


    }

    private void stopAlarm() {
        // Get Alarm Manager from the system.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel();

    }

    private void setAlarmByRealTimeClock() {

    }

}
