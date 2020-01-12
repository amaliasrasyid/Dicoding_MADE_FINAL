package amalia.dev.dicodingmade.reminder.daily;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import amalia.dev.dicodingmade.R;

public class ReminderReceiver extends BroadcastReceiver {
    public static final int ID_REPEATING = 500;
    public static final String EXTRA_MESSAGE = "ExtraMessage";
    public static final String ACTION_REMINDER_RECEIVER = "action reminder receiver";
    public static final String  CHANNEL_ID_DAILY = "ChannelIdDaily";
    private static final String TAG = "reminderReceiver";
    NotificationChannel channel;
    NotificationManager notifManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = "Dicoding MADE";
        showReminderNotification(context,title,message,ID_REPEATING);
    }

    public void setRepeatingReminder(Context context, String message) {
        AlarmManager reminderManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.setAction(ACTION_REMINDER_RECEIVER);
        intent.putExtra(EXTRA_MESSAGE, message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);//Retrieve a PendingIntent that will perform a broadcast (PendingIntent need Intent as the value that will pased)

//        //set the time every day at 7 am
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY,7);
//        calendar.set(Calendar.MINUTE,0);
//        calendar.set(Calendar.MILLISECOND,0);
//        //scedule an alarm
//        reminderManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


        //EXAMPLE : set interval is 2 minutes
        reminderManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000*60, pendingIntent);
//        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Repeating alarm set up");

        //CHECKING IF ALARM MANAGER WORKING/RUNNING
        Intent intent1 = new Intent(context,ReminderReceiver.class);
        intent1.setAction(ACTION_REMINDER_RECEIVER);
        intent.putExtra(EXTRA_MESSAGE, message);
        boolean isWorking = (PendingIntent.getBroadcast(context,ID_REPEATING,intent,0)) != null;
//        Toast.makeText(context,"alarm is " + (isWorking ? "" : "not") + " working...",Toast.LENGTH_LONG).show();
        Log.d(TAG,"alarm is " + (isWorking ? "" : "not") + " working...");
    }

    private void showReminderNotification(Context context, String title,String message, int notifId){
        //first create channel id (for android 8.0 or higher)
        //android before oreo dont need NotificationChannel
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            CharSequence name = "Channel Daily";
            String description = "notification for Daily Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

             channel = new NotificationChannel(CHANNEL_ID_DAILY,name,importance);
             channel.setDescription(description);
            // after registering channel, importance cant change or other notification behavior
            notifManager  = context.getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(channel);
        }


         notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
         NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID_DAILY)
                .setSmallIcon(R.drawable.ic_movie_24px)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context,android.R.color.transparent));

        notifManager.notify(notifId,builder.build());
    }

    public void cancelReminder(Context context){
        AlarmManager reminderManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context,ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,ID_REPEATING,intent,0);
        reminderManager.cancel(pendingIntent);
//        Toast.makeText(context, "Repeating alarm Canceled", Toast.LENGTH_LONG).show();
        Log.d(TAG,"Repeating alarm canceled");
    }


}
