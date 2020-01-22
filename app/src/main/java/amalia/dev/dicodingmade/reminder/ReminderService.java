package amalia.dev.dicodingmade.reminder;


import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class ReminderService extends IntentService {

    public ReminderService() {
        super("Reminder Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ReminderReceiver reminderReceiver = new ReminderReceiver();
        //check if alarm exist
        boolean reminderDaily = (PendingIntent.getBroadcast(
                this, ReminderReceiver.REQ_CODE_DAILY, new Intent(ReminderReceiver.ACTION_DAILY_RECEIVER), PendingIntent.FLAG_NO_CREATE) != null);

        boolean reminderReleaseToday = (PendingIntent.getBroadcast(
                this, ReminderReceiver.REQ_CODE_RELEASE_TODAY, new Intent(ReminderReceiver.ACTION_RELEASE_TODAY_RECEIVER), PendingIntent.FLAG_NO_CREATE) != null
        );
        if(reminderDaily){
            Log.d("ReminderService","Alarm/Reminder is already active");
        }else{
            reminderReceiver.setRepeatingDaily(this);
        }
        if (reminderReleaseToday) {
            Log.d("ReminderService","Alarm/Reminder is already active");
        }else{
            reminderReceiver.setReleaseToday(this);
        }
    }
}
