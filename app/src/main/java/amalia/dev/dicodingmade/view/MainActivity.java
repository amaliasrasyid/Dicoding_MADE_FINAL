package amalia.dev.dicodingmade.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.R;
import amalia.dev.dicodingmade.model.GenreResult;
import amalia.dev.dicodingmade.reminder.ReminderReceiver;
import amalia.dev.dicodingmade.repository.remote.ApiInterface;
import amalia.dev.dicodingmade.repository.remote.ApiRepository;
import amalia.dev.dicodingmade.repository.local.RealmHelper;
import amalia.dev.dicodingmade.view.settings.SettingsActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("JustOnce", false)) {
            loadGenre();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("JustOnce", true);
            editor.apply();
        }
        //run Service for alarm/reminder
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.setAction(ReminderReceiver.ACTION_DESTROYED_ALARM);
        sendBroadcast(intent);
    }

    private void loadGenre() {
        Retrofit retrofit = ApiRepository.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String apiKey = BuildConfig.TMDB_API_KEY;
        Call<GenreResult> call = apiInterface.getGenres(apiKey);
        call.enqueue(new Callback<GenreResult>() {
            @Override
            public void onResponse(@Nullable Call<GenreResult> call, @Nullable Response<GenreResult> response) {
                if(response != null){
                    if (response.isSuccessful()) {
                        GenreResult result = response.body();
                        for (int i = 0; i < Objects.requireNonNull(result).getGenres().size(); i++) {
                            //open Realm
                            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
                            Realm realm = Realm.getInstance(realmConfiguration);
                            RealmHelper realmHelper = new RealmHelper(realm);
                            realmHelper.insertGenre(result.getGenres().get(i));
                            realm.close();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@Nullable  Call<GenreResult> call, @Nullable  Throwable t) {

            }
        });

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
