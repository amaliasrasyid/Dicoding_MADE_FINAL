package amalia.dev.dicodingmade.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import amalia.dev.dicodingmade.model.TvShow;
import amalia.dev.dicodingmade.model.TvShowResult;
import amalia.dev.dicodingmade.repository.ApiInterface;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.MovieResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies;
    private MutableLiveData<ArrayList<TvShow>> listTvShows;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "6aaf0b4b68f88ddc23dbe4cf46fb2ddd";
    private Retrofit retrofit;


    public LiveData<ArrayList<Movie>> getMovies(){
      if(listMovies == null){
          listMovies = new MutableLiveData<>();
          loadMovies();
      }
      return listMovies;
    }

    public LiveData<ArrayList<TvShow>> getTvShows(){
        if(listTvShows == null){
            listTvShows = new MutableLiveData<>();
            loadTvShows();
        }
        return listTvShows;
    }

    private void loadTvShows() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<TvShowResult> call = apiInterface.getListTvShow(API_KEY);
        call.enqueue(new Callback<TvShowResult>() {
            @Override
            public void onResponse(Call<TvShowResult> call, Response<TvShowResult> response) {
                if(response.isSuccessful()){
                    //saving response into an object
                    TvShowResult result = response.body();

                    //moving list into arraylist before inserting into MutableLiveData<ArrayList<TvShow>>
                    ArrayList <TvShow> listDataDb = new ArrayList<>();
                    for(int i=0; i<result.getTvShowsResults().size();i++){
                        listDataDb.add(result.getTvShowsResults().get(i));
                    }
                    //inserting to MutableLiveData
                    listTvShows.postValue(listDataDb);
                }
            }

            @Override
            public void onFailure(Call<TvShowResult> call, Throwable t) {
                Log.e("FAILED connection tv",t.toString());
            }
        });
    }

    private void loadMovies() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
        //create instance retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getListMovies(API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                if(response.isSuccessful()){
                    //save response into objek MovieResult
                    MovieResult result = response.body();

                    // moving List data(data dr server berupa list) into Arraylist, before saving in MutableLiveData
                    ArrayList <Movie> listDataDb = new ArrayList<Movie>();
                    for (int i = 0; i< result.getMoviesResults().size(); i++){
                        listDataDb.add(result.getMoviesResults().get(i));
                    }
                    //inserting to MutableLiveData
                    listMovies.postValue(listDataDb);
                }else{
                    Log.e("FAILURE","Response is NULL");
                }

            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                Log.e("FAILED connection movie",t.toString());
            }
        });
    }
}
