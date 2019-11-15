package amalia.dev.dicodingmade.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import amalia.dev.dicodingmade.ApiInterface;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.MovieResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies;
    private ArrayList<Movie> listmovie = new ArrayList<>();
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "6aaf0b4b68f88ddc23dbe4cf46fb2ddd";
    private static final String LANGUAGE = "en-US";


    public LiveData<ArrayList<Movie>> getMovies(){
      if(listMovies == null){
          listMovies = new MutableLiveData<>();
          loadMovies();
      }
      return listMovies;
    }

    private void loadMovies() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
        //connect & get data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getListMovies(API_KEY,LANGUAGE);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if(response.isSuccessful()){
                    //save response into objek MovieResult
                    MovieResult movieResult = response.body();

                    // moving List data(data dr server berupa list) into Arraylist, before saving in MutableLiveData
                    listmovie = new ArrayList<>();
                    for (int i=0;i<movieResult.getMoviesResults().size();i++){
                        listmovie.add(movieResult.getMoviesResults().get(i));
                    }
                    //inserting to MutableLiveData
                    listMovies.postValue(listmovie);
                }else{
                    Log.e("FAILURE","Response is NULL");
                }

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e("FAILED connection ",t.toString());
            }
        });
    }
}
