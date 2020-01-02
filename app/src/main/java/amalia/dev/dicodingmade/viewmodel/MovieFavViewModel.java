package amalia.dev.dicodingmade.viewmodel;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import amalia.dev.dicodingmade.model.MovieRealmObject;

public class MovieFavViewModel extends ViewModel {
    private MutableLiveData<List<MovieRealmObject>> listFavMovies;
    private Context context;

    public LiveData<List<MovieRealmObject>> getListFavMovies(){
        if(listFavMovies == null){
            listFavMovies = new MutableLiveData<>();
            loadFavMovies();
        }
        return listFavMovies;
    }

    private void loadFavMovies() {

        class LoadMovieFavAsync {


        }
    }
}
