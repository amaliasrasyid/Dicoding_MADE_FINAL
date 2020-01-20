package amalia.dev.dicodingmade.reminder;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ReminderWorker extends Worker {

    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context,params);
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }

}
