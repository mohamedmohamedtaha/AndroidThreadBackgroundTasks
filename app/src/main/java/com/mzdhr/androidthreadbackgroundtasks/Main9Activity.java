package com.mzdhr.androidthreadbackgroundtasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.alarms.BroadcastForAlarm;

import java.util.Calendar;

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
        // Create a Broadcast class, to fire when alarm triggered (Don't forget to put it in manifest.xml).
        // Create a Service class, that hold our code (Dont forget to put it in manifest.xml).
        // Create a new Thread inside the Service to run your code (or use IntentService in the previous step).

        // Create an Intent, that runs our Broadcast class
        Intent intent = new Intent(Main9Activity.this, BroadcastForAlarm.class);
        intent.putExtra("NAME_KEY", "Mohammad");

        // Create a Pending Intent, that hold our Intent.
        // Action that need to be fired, Pending Intent.
        PendingIntent alarmOperation = PendingIntent.getBroadcast(
                Main9Activity.this,
                0,  // to determine alarms if you have many.
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Prepare the Alarm Type
        // Alarm Type: ELAPSED_REALTIME or ELAPSED_REALTIME_WAKEUP | RTC or RTC_WAKEUP.
        int AlarmType = AlarmManager.ELAPSED_REALTIME;

        // Prepare time
        // Time in milliseconds + Elapsed Time when device is booted.
        long alarmTime = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES;

        // Get Alarm Manager from the system.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the Alarm
        // See Also -> alarmManager.setAndAllowWhileIdle();
        // See Also -> alarmManager.setExactAndAllowWhileIdle();
        alarmManager.set(AlarmType, alarmTime, alarmOperation);

        // Set Repeat Alarm (First after 15 minutes, then each day.
        // Better to use -> alarmManager.setInexactRepeating();
        long alarmRepeatTime = SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_DAY;
        alarmManager.setRepeating(AlarmType, alarmTime, alarmRepeatTime, alarmOperation);


        // For Real Time Clock (RTC) type
        int AlarmType2 = AlarmManager.RTC;

        // Time for RTC
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 30);

        alarmManager.set(AlarmType2, calendar.getTimeInMillis(), alarmOperation);

    }

    private void stopAlarm() {
        // Prepare operation to be off
        // The Intent.
        Intent intent = new Intent(Main9Activity.this, BroadcastForAlarm.class);
        intent.putExtra("NAME_KEY", "Mohammad");

        // The Pending Intent (Watch the Request Code if you have multiple alarms).
        PendingIntent alarmOperation = PendingIntent.getBroadcast(
                Main9Activity.this,
                0,  // to determine alarms if you have many.
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Get Alarm Manager from the system.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(alarmOperation);

    }

    private void setAlarmByRealTimeClock() {

    }

    /**
     * An example of requiring a power lock, used in old way service or broadcast, so that android
     * will run them when our Alarm triggered. Now system will do that, but in case he didn't.
     */
    private void exampleOfRequirePowerLock() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MY_APP:MY_POWER_TAG");

        try {
            wakeLock.acquire();
            // Code goes here
            // Code goes here
            // Code goes here
        } catch (Exception e) {

        } finally {
            wakeLock.release();
        }

    }
}

