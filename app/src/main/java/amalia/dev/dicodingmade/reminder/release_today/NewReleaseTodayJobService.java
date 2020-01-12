package amalia.dev.dicodingmade.reminder.release_today;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class NewReleaseTodayJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
