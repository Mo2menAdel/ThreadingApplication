package com.example.threadingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class AsynchTaskTotorial extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynch_task_totorial);

        progressBar = findViewById(R.id.progress_bar);
    }

    public void startAsyncTask(View v) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(10);
    }

    private
    static // to not make memorey leakedge
    class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<MainActivity> activityWeakReference; // no memorey leak

        ExampleAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity activity = activityWeakReference.get(); // Strong activity only in this function scope
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Finished!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
