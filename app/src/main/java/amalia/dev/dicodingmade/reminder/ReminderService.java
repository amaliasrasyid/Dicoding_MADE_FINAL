package amalia.dev.dicodingmade.reminder;


import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class ReminderService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ReminderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
