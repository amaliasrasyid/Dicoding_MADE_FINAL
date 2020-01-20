package amalia.dev.dicodingmade.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.MovieRealmObject;
import amalia.dev.dicodingmade.model.MovieResult;
import amalia.dev.dicodingmade.repository.api.ApiInterface;
import amalia.dev.dicodingmade.repository.api.ApiRepository;
import amalia.dev.dicodingmade.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReminderReceiver extends BroadcastReceiver {
    public static final int NOTIF_ID_DAILY = 500;
    public static final int NOTIF_ID_RELEASE_TODAY = 600;
    public static final String ACTION_DAILY_RECEIVER = "action daily receiver";
    public static final String ACTION_RELEASE_TODAY_RECEIVER = "action release today receiver";
    public static final String CHANNEL_ID = "ChannelId";

    ArrayList<MovieRealmObject> listNewRelease;

    private static final String TAG = "reminderReceiver";
    NotificationChannel channel;
    NotificationManager notifManager;
    int position;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ACTION_DAILY_RECEIVER)) {


                showNotification(context, "Catalogue Movie", "Catalogue Movie Missing You", NOTIF_ID_DAILY);
            } else if (intent.getAction().equals(ACTION_RELEASE_TODAY_RECEIVER)) {
                getListReleaseToday(context);
            }
        }


    }

    public  void setRepeatingDaily(Context context) {
        AlarmManager reminderManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.setAction(ACTION_DAILY_RECEIVER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_DAILY, intent, PendingIntent.FLAG_UPDATE_CURRENT);//Retrieve a PendingIntent that will perform a broadcast (PendingIntent need Intent as the value that will pased)

        //set the time every day at 7 am
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 7);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        //get Current Time
        Calendar currentTime = Calendar.getInstance();

        //scedule an alarm
        if (currentTime.before(startTime)) {
            //if it's not 7.00 am yet, start alarm
            if (reminderManager != null) {
                reminderManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }

        } else {
            //after the set time, run alarm for tomorrow
            startTime.add(Calendar.DATE, 1);
            if (reminderManager != null) {
                reminderManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }

    }

    public  void setReleaseToday(Context context) {
        AlarmManager releaseToday = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.setAction(ACTION_RELEASE_TODAY_RECEIVER);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_RELEASE_TODAY, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //start at every 8 am
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);

        //get current time
        Calendar currentTime = Calendar.getInstance();

        //schedule an alarm
        if (currentTime.before(startTime)) {
            //if it's not 8.00 am yet, start alarm
            if (releaseToday != null) {
                releaseToday.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        } else {
            //if it was after the set time, run alarm for tomorrow
            startTime.add(Calendar.DATE, 1);
            if (releaseToday != null) {
                releaseToday.setInexactRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }

    }

    private void showNotification(Context context, String title, String message, int notifId) {
        //first create channel id (for android 8.0 or higher)
        //android before oreo dont need NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Daily";
            String description = "notification for Daily Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // after registering channel, importance cant change or other notification behavior
            notifManager = context.getSystemService(NotificationManager.class);
            if (notifManager != null) {
                notifManager.createNotificationChannel(channel);
            }
        }


        notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //add action moving to app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //cause we need to run activity, so use getActivity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_24px)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                //add action in notification
                .setContentIntent(pendingIntent);

        notifManager.notify(notifId, builder.build());

    }


    public void cancelReminder(Context context, int notifId) {
        AlarmManager reminderManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notifId, intent, 0);
        if (reminderManager != null) {
            reminderManager.cancel(pendingIntent);
        }
//        Toast.makeText(context, "Repeating alarm Canceled", Toast.LENGTH_LONG).show();
        Log.d(TAG, "alarm canceled");
    }

    private void getListReleaseToday(final Context context) {
        listNewRelease = new ArrayList<>();
        //instance retrofit
        Retrofit retrofit = ApiRepository.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        //get value params
        String apiKey = BuildConfig.TMDB_API_KEY;
        String todayDate = getCurrentTime();


        Call<MovieResult> call = apiInterface.getListNewReleaseMovies(apiKey, todayDate, todayDate);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @Nullable Response<MovieResult> response) {
                int notificationId = NOTIF_ID_RELEASE_TODAY;
                try {
                    if (response != null && response.body() != null) {
                        listNewRelease.addAll(response.body().getMoviesResults());
                        //add notification per movies
                        if (listNewRelease != null && listNewRelease.size() != 0) {
                            for (int i = 0; i < listNewRelease.size(); i++) {
                                String title = listNewRelease.get(i).getTitle();
                                String message = title + " has been released today!";
                                position = i;
                                showNotification(context, title, message, notificationId++);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e("release job service", e.toString());
                }


            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @Nullable Throwable t) {
                if (t != null) {
                    Log.e("release job service", t.toString());
                }

            }
        });


    }

    private String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        //convert date into YYYY-MM-DD
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        return dateFormat.format(currentTime);
    }


}
