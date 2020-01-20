package amalia.dev.dicodingmade.reminder;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

//THIS CLASS FOR MAKE SURE ALARM STILL RUNNING EVEN THE APP CLOSED
public class ReminderService extends IntentService {
    public static final String EXTRA_DAILY = "extraDaily";
    public static final String EXTRA_RELEASE_TODAY = "extraReleaseToday";

    public ReminderService() {
        //to name the worker thread, important only for debugging.
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("reminder service","service running");
//        if(intent.getStringExtra(EXTRA_DAILY).equals("true")){
//            ReminderReceiver.setRepeatingDaily(this);
//        }else if(intent.getStringExtra(EXTRA_RELEASE_TODAY).equals("true")){
//            ReminderReceiver.setReleaseToday(this);
        }

}
