package amalia.dev.dicodingmade.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Objects;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.model.GenreResult;
import amalia.dev.dicodingmade.repository.local.RealmHelper;
import amalia.dev.dicodingmade.repository.remote.ApiInterface;
import amalia.dev.dicodingmade.repository.remote.ApiRepository;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadGenreIntentService extends IntentService {
    private static final String TAG = LoadGenreIntentService.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    public LoadGenreIntentService() {
        super("LoadGenreIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "running onHandleIntent");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean("JustOnce", false)) {
            loadGenre();
        }
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
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("JustOnce", true);
                        editor.apply();
                    }
                }
            }

            @Override
            public void onFailure(@Nullable  Call<GenreResult> call, @Nullable  Throwable t) {
                if (t != null) {
                    Log.e(TAG,t.toString());
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG," on Destroy");
    }
}
