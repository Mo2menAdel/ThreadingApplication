package com.example.threadingapplication;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ExampleLooperThread extends Thread {

    private static final String TAG = "ExampleLooperThread";

    public Looper looper;
    public Handler handler;

    @Override
    public void run() {
        Looper.prepare();

        looper = Looper.myLooper();

        handler = new ExampleHandler();

        Looper.loop();

        Log.d(TAG, "End of run()");
    }
}
