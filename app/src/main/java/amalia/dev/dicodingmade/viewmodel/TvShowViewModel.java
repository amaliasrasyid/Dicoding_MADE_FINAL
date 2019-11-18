package amalia.dev.dicodingmade.viewmodel;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.etc.ApiInterface;
import amalia.dev.dicodingmade.model.TvShow;
import amalia.dev.dicodingmade.model.TvShowResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShow>> listTvShows;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private String apiKey = BuildConfig.TMDB_API_KEY;

    public LiveData<ArrayList<TvShow>> getTvShows(){
        if(listTvShows == null){
            listTvShows = new MutableLiveData<>();
            loadTvShows();
        }
        return listTvShows;
    }

    private void loadTvShows() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<TvShowResult> call = apiInterface.getListTvShow(apiKey);
        call.enqueue(new Callback<TvShowResult>() {
            @Override
            public void onResponse(@Nullable Call<TvShowResult> call, @Nullable Response<TvShowResult> response) {
                if (response != null) {
                    if(response.isSuccessful()){
                        //saving response into an object
                        TvShowResult result = response.body();

                        //moving list into arraylist before inserting into MutableLiveData<ArrayList<TvShow>>
                        ArrayList<TvShow> listDataDb = null;
                        if (result != null) {
                            listDataDb = new ArrayList<>(result.getTvShowsResults());
                        }
                        //inserting to MutableLiveData
                        listTvShows.postValue(listDataDb);
                    }else{
                        Log.e("Load Tvshow","Response is NULL");
                    }
                }
            }

            @Override
            public void onFailure(@Nullable Call<TvShowResult> call, @Nullable Throwable t) {
                if (t != null) {
                    Log.e("Load Tvshow",t.toString());
                }
            }
        });
    }

}
