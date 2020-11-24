package org.maktab.photogallery.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.maktab.photogallery.utilities.ServicesUtils;

public class PollWorker extends Worker {

    public static final String TAG = "PollWorker";

    public PollWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ServicesUtils.pollAndShowNotification(getApplicationContext(), TAG);
        return null;
    }
}
