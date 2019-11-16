package amalia.dev.dicodingmade.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

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
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "6aaf0b4b68f88ddc23dbe4cf46fb2ddd";

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
        Call<TvShowResult> call = apiInterface.getListTvShow(API_KEY);
        call.enqueue(new Callback<TvShowResult>() {
            @Override
            public void onResponse(Call<TvShowResult> call, Response<TvShowResult> response) {
                if(response.isSuccessful()){
                    //saving response into an object
                    TvShowResult result = response.body();

                    //moving list into arraylist before inserting into MutableLiveData<ArrayList<TvShow>>
                    ArrayList<TvShow> listDataDb = new ArrayList<>();
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
}
