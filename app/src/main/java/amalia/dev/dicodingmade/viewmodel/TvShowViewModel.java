package amalia.dev.dicodingmade.viewmodel;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.repository.ApiInterface;
import amalia.dev.dicodingmade.model.TvShow;
import amalia.dev.dicodingmade.model.TvShowResult;
import amalia.dev.dicodingmade.repository.ApiRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TvShowResult> listTvShows;

    public LiveData<TvShowResult> getTvShows(){
        if(listTvShows == null){
            listTvShows = new MutableLiveData<>();
            loadTvShows();
        }
        return listTvShows;
    }

    private void loadTvShows() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
       Retrofit retrofit = ApiRepository.getInstance();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String apiKey = BuildConfig.TMDB_API_KEY;
        Call<TvShowResult> call = apiInterface.getListTvShow(apiKey);
        call.enqueue(new Callback<TvShowResult>() {
            @Override
            public void onResponse(@Nullable Call<TvShowResult> call, @Nullable Response<TvShowResult> response) {
                if (response != null) {
                    if(response.isSuccessful()){
                        //saving response into an object
                        TvShowResult result = response.body();
                        //inserting to MutableLiveData
                        listTvShows.postValue(result);
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
