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
import amalia.dev.dicodingmade.reminder.daily.ReminderReceiver;

public class SettingsActivity extends AppCompatActivity {
    public static String PREF_SWITCH_DAILY = "dailyReminder";
    public static String PREF_SWITCH_RELEASE_TODAY  = "releaseTodayReminder";

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
        private ReminderReceiver reminderReceiver = new ReminderReceiver();
        private SwitchPreference dailyReminderPref,releaseTodayReminderPref;
        private Preference changeLanguagePref;
        private Context context;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            context = getActivity();

             dailyReminderPref = findPreference(getString(R.string.key_notification_daily_reminder));
             releaseTodayReminderPref = findPreference(getString(R.string.key_noification_release_today));
             changeLanguagePref = findPreference(getString(R.string.key_languange_change));

            //set listener
            dailyReminderPref.setOnPreferenceChangeListener(this);
            releaseTodayReminderPref.setOnPreferenceChangeListener(this);
            changeLanguagePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    context.startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });
        }



        private void startReminderToday() {
            String message = "Catalogue Movie Missing You!";
            reminderReceiver.setRepeatingReminder(Objects.requireNonNull(getActivity()),message);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
           if(preference.getKey().equals(getString(R.string.key_noification_release_today))){
               if(releaseTodayReminderPref.isChecked()){
                   reminderReceiver.cancelReminder(Objects.requireNonNull(getActivity()));
               }else{
                   startReminderToday();
               }
           }else if(preference.getKey().equals(getString(R.string.key_notification_daily_reminder))){

           }
            return true;
        }
    }

}