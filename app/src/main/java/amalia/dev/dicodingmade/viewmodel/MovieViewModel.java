package amalia.dev.dicodingmade.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

import amalia.dev.dicodingmade.BuildConfig;
import amalia.dev.dicodingmade.repository.ApiInterface;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.MovieResult;
import amalia.dev.dicodingmade.repository.ApiRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieResult> listMovies; //for storing data


    public LiveData<MovieResult> getMovies(){
      if(listMovies == null){
          listMovies = new MutableLiveData<>();
          loadMovies();
      }
      return listMovies;
    }

    private void loadMovies() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
        //create instance retrofit
       Retrofit retrofit =  ApiRepository.getInstance();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String apiKey = BuildConfig.TMDB_API_KEY;
        Call<MovieResult> call = apiInterface.getListMovies(apiKey);
        call.enqueue(new Callback<MovieResult>() {
            MovieResult result = new MovieResult();
            @Override
            public void onResponse(@Nullable Call<MovieResult> call, @Nullable Response<MovieResult> response) {
                if (response != null) {
                    if(response.isSuccessful()){
                        //save response into objek MovieResult
                          result = response.body();
                        //inserting to MutableLiveData
                        listMovies.postValue(result);
                    }else{
                        Log.e("Load Movies","Response is NULL");
                    }
                }

            }

            @Override
            public void onFailure(@Nullable Call<MovieResult> call, @Nullable  Throwable t) {
                if (t != null) {
                    Log.e("Load Movies ",t.toString());
                }
            }
        });
    }



}
