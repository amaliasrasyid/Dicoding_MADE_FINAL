package amalia.dev.dicodingmade.reminder.release_today;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReleaseTodayJobService extends JobService {
    private static final String CHANNEL_ID_RELEASE_TODAY = "channelReleaseToday";
    public static int notificationId = 600;
    ArrayList<MovieRealmObject> listNewRelease;
    NotificationChannel channel;
    NotificationManager notificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        getListReleaseToday(params);
        return true;
    }

    private void getListReleaseToday(final JobParameters params) {
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
                try {
                    if (response != null && response.body() != null) {
                        listNewRelease.addAll(response.body().getMoviesResults());
                        //add notification per movies
                        if (listNewRelease != null && listNewRelease.size() != 0) {
                            for (int i = 0; i < listNewRelease.size(); i++) {
                                String title = listNewRelease.get(i).getTitle();
                                String message = title + " has been released today!";
                                showNotification(getApplicationContext(), title, message, notificationId++);
                            }
                        }
                        jobFinished(params, false);
                    }
                } catch (Exception e) {
                    Log.e("release job service", e.toString());
                    jobFinished(params, true);
                }


            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, @Nullable Throwable t) {
                if (t != null) {
                    Log.e("release job service", t.toString());
                    jobFinished(params, true);
                }

            }
        });


    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        //convert date into YYYY-MM-DD
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD", Locale.US);

        return dateFormat.format(currentTime);
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        //first create channel id (for android 8.0 or higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //set value for params that needed for creating NotificationChannel
            CharSequence name = "Channel Release Today";
            String description = "notification about movies new release on today";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //create channel
            channel = new NotificationChannel(CHANNEL_ID_RELEASE_TODAY, name, importance);
            channel.setDescription(description);
            //registering channel
            notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_RELEASE_TODAY)
                .setSmallIcon(R.drawable.ic_movie_24px)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent));

        notificationManager.notify(notifId, builder.build());

    }
}
