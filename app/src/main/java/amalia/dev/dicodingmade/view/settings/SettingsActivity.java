package amalia.dev.dicodingmade.view.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import java.util.Objects;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.reminder.ReminderReceiver;
//import amalia.dev.dicodingmade.reminder.ReminderService;

public class SettingsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        //setting up button (custom)
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public void onBackStackChanged(){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        //this method is called when the up button is pressed. just destroy/finish activity
        finish();
        return true;
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
            Objects.requireNonNull(changeLanguagePref).setOnPreferenceClickListener(preference -> {
                context.startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
            });
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference.getKey().equals(getString(R.string.key_noification_release_today))) {
                if (releaseTodayReminderPref.isChecked()) {
                    reminderReceiver.cancelReminder(Objects.requireNonNull(getActivity()), ReminderReceiver.REQ_CODE_RELEASE_TODAY);
                } else {
                    reminderReceiver.setReleaseToday(Objects.requireNonNull(getActivity()));
                }
            } else if (preference.getKey().equals(getString(R.string.key_notification_daily_reminder))) {
                if (dailyReminderPref.isChecked()) {
                    reminderReceiver.cancelReminder(Objects.requireNonNull(getActivity()), ReminderReceiver.REQ_CODE_DAILY);
                } else {
                    reminderReceiver.setRepeatingDaily(Objects.requireNonNull(getActivity()));
                }
            }
            return true;
        }
    }

}