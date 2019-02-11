package com.mzdhr.androidthreadbackgroundtasks.pattern;

/**
 * Created by MohammadL on 09/1/2019
 * Contact me at mmlaif@gmail.com
 */
public interface MyAsyncTaskCallbacks {
    public void onTriggeredProgressUpdate(Integer values);
    public void onFinishPostExecute(Long result);
}
