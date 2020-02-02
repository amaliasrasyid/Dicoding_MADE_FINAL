package amalia.dev.dicodingmade.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.reminder.ReminderReceiver;
import amalia.dev.dicodingmade.service.LoadGenreIntentService;
import amalia.dev.dicodingmade.view.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottom_nav_view_main);
        NavController navController = Navigation.findNavController(this, R.id.container_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_navigation_movies, R.id.menu_navigation_tvshows, R.id.menu_navigation_favorites).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        //run service load genre
        Intent mIntentService = new Intent(this, LoadGenreIntentService.class);
        startService(mIntentService);

        //send broadcast for reschedule alarm
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.setAction(ReminderReceiver.ACTION_DESTROYED_ALARM);
        sendBroadcast(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu top
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            Intent mIntent = new Intent(this, SettingsActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}
