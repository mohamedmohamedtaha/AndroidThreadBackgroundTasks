package com.mzdhr.androidthreadbackgroundtasks.pattern;

import android.os.AsyncTask;

/**
 * Created by MohammadL on 09/1/2019
 * Contact me at mmlaif@gmail.com
 *
 * Take a name, return how many characters in it.
         */
public class MyAsyncTask extends AsyncTask<String, Integer, Long> {

    private static final String TAG = "MyAsyncTask";
    private MyAsyncTaskCallbacks mMyAsyncTaskCallbacks;


    public MyAsyncTask(MyAsyncTaskCallbacks myAsyncTaskCallbacks) {
        mMyAsyncTaskCallbacks = myAsyncTaskCallbacks;
    }


    /**
     * Runs on Main Thread
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    /**
     * Runs on Background Thread
     */
    @Override
    protected Long doInBackground(String... strings) {
        String name = strings[0];
        Long result = 0L;

        for (int i = 0; i < name.length(); i++) {
            result = result + 1;
            publishProgress(i);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * Runs on Main Thread
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mMyAsyncTaskCallbacks != null) {
            mMyAsyncTaskCallbacks.onTriggeredProgressUpdate(values[0]);
        }
        super.onProgressUpdate(values);
    }


    /**
     * Runs on Main Thread
     */
    @Override
    protected void onPostExecute(Long aLong) {
        //Log.d(TAG, "onFinishPostExecute: result -> " + aLong);
        if (mMyAsyncTaskCallbacks != null) {
            mMyAsyncTaskCallbacks.onFinishPostExecute(aLong);
        }

        super.onPostExecute(aLong);
    }


}
