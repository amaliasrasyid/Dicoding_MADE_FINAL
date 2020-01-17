package amalia.dev.dicodingmade.view.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import java.util.Objects;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.reminder.ReminderReceiver;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

        private final ReminderReceiver reminderReceiver = new ReminderReceiver();
        private SwitchPreference dailyReminderPref, releaseTodayReminderPref;
        private Context context;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            context = getActivity();

            dailyReminderPref = findPreference(getString(R.string.key_notification_daily_reminder));
            releaseTodayReminderPref = findPreference(getString(R.string.key_noification_release_today));
            Preference changeLanguagePref = findPreference(getString(R.string.key_languange_change));

            //set listener
            dailyReminderPref.setOnPreferenceChangeListener(this);
            releaseTodayReminderPref.setOnPreferenceChangeListener(this);
            Objects.requireNonNull(changeLanguagePref).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    context.startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference.getKey().equals(getString(R.string.key_noification_release_today))) {
                if (releaseTodayReminderPref.isChecked()) {
                    reminderReceiver.cancelReminder(Objects.requireNonNull(getActivity()),ReminderReceiver.NOTIF_ID_RELEASE_TODAY);
                } else {
                    reminderReceiver.setReleaseToday(Objects.requireNonNull(getActivity()));
                }
            } else if (preference.getKey().equals(getString(R.string.key_notification_daily_reminder))) {
                if (dailyReminderPref.isChecked()) {
                    reminderReceiver.cancelReminder(Objects.requireNonNull(getActivity()),ReminderReceiver.NOTIF_ID_DAILY);
                } else {
                    reminderReceiver.setRepeatingDaily(Objects.requireNonNull(getActivity()));
                }
            }
            return true;
        }
    }

}