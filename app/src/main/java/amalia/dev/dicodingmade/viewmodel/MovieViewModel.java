package amalia.dev.dicodingmade.viewmodel;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import amalia.dev.dicodingmade.etc.ApiInterface;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.MovieResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> listMovies;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "6aaf0b4b68f88ddc23dbe4cf46fb2ddd";


    public LiveData<ArrayList<Movie>> getMovies(){
      if(listMovies == null){
          listMovies = new MutableLiveData<>();
          loadMovies();
      }
      return listMovies;
    }


    private void loadMovies() {
        // Melakukan proses asynchronous untuk mendapatkan data pengguna.
        //create instance retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<MovieResult> call = apiInterface.getListMovies(API_KEY);
        call.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(@Nullable Call<MovieResult> call, @Nullable Response<MovieResult> response) {
                if(response.isSuccessful()){
                    //save response into objek MovieResult
                    MovieResult result = response.body();

                    // moving List data(data dr server berupa list) into Arraylist, before saving in MutableLiveData
                    ArrayList<Movie> listDataDb = new ArrayList<>(result.getMoviesResults());
                    //inserting to MutableLiveData
                    listMovies.postValue(listDataDb);
                }else{
                    Log.e("Load Movies","Response is NULL");
                }

            }

            @Override
            public void onFailure(@Nullable Call<MovieResult> call, @Nullable  Throwable t) {
                Log.e("Load Movies ",t.toString());
            }
        });
    }
}
