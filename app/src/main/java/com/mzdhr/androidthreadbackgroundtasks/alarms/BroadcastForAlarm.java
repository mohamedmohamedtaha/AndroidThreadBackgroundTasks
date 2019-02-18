package com.mzdhr.androidthreadbackgroundtasks.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by MohammadL on 13/2/2019
 * Contact me at mmlaif@gmail.com
 */
public class BroadcastForAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract data from the intent
        String name = intent.getStringExtra("NAME_KEY");

        // Create an Intent to run our IntentService
        Intent intentForService = new Intent(context, IntentServiceForAlarm.class);
        intentForService.putExtra("NAME_KEY", name);

        // Trigger our service to be run (e.g. a service that runs sounds or shows notification).
        context.startService(intentForService);
    }

}
