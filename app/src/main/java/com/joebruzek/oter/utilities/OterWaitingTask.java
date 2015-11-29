package com.joebruzek.oter.utilities;

import android.os.AsyncTask;
import android.util.Log;

/**
 * OterWaitingTask is kinda lame, but it takes a list of Oter ids and waits for a set period of
 * time on a side thread and then returns the id's back to the main thread
 *
 * Created by jbruzek on 11/14/15.
 */
public class OterWaitingTask extends AsyncTask<Void, Integer, Integer[]> {

    /**
     * OterWaitingTaskListener provides a callback for the waiting task
     */
    public interface OterWaitingTaskListener {
        public void onWaitingTaskCompleted(Integer[] oters);
    }

    //task waiting time in milliseconds - 5 minutes
    private final int TASK_WAITING_TIME = 10000;

    private Integer[] oters;
    private OterWaitingTaskListener listener;

    /**
     * Constructor
     * @param listener The OterWaitingTaskListener
     * @param oters an array of oter id's that are dependant on the same location and are waiting together
     */
    public OterWaitingTask(OterWaitingTaskListener listener, Integer[] oters) {
        this.oters = oters;
        this.listener = listener;
    }

    /**
     * Sleep for TASK_WAITING_TIME
     * @return the list of oter ids
     */
    @Override
    protected Integer[] doInBackground(Void... voids) {
        Log.e("WAITING TASK", "executing");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(TASK_WAITING_TIME / 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onProgressUpdate(i + 1);
        }
        return oters;
    }

    /**
     * Pot a progress update in the form of percent completed
     * @param values int [1 - 100]
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Post the return value, which is the oters list
     * @param result the array of oter ids
     */
    @Override
    protected void onPostExecute(Integer[] result) {
        super.onPostExecute(result);
        listener.onWaitingTaskCompleted(result);
    }
}
