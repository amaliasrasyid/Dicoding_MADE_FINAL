package amalia.dev.dicodingmade.repository.realm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import amalia.dev.dicodingmade.model.Genre;
import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.TvShow;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    private final Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    /**HOW TO INSERT
     * there is two way,first by creating the object itself and second using
     * copyToRealm() method and pass the object into it
     * */
    //create or insert data Movie
    public void insertMovie(final Movie movie){
        //by using executeTransaction the beginTransaction and commintTransaction will call automatically
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                    //set the number for PK
                    movie.setId(movie.getId());
                    //and inserting into db
                    realm.copyToRealm(movie);

            }
        });
    }

    //create or insert data tvshow
    public void insertTvShow(final TvShow tvShow){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                    tvShow.setId(tvShow.getId());
                    //and insert into db local
                    realm.copyToRealm(tvShow);
            }
        });
    }

    //get list movie from local db
    public RealmResults<Movie> getListFavoriteMovies(){
        return realm.where(Movie.class).equalTo("tmpDelete",false).findAll();
    }

    //get list tvshow from local db
    public RealmResults<TvShow> getListFavoriteTvShows(){
        return realm.where(TvShow.class).equalTo("tmpDelete",false).findAll();
    }

    //search data for checking if marked or not (favorite)
    public boolean isMovieExist(int id){
        RealmResults<Movie> movie = realm.where(Movie.class).equalTo("id",id).findAll();
        return (movie.size() != 0);//when there is no data, it will return list with size 0 not null
    }

    public boolean isTvShowExist(int id){
        RealmResults<TvShow> tvShow = realm.where(TvShow.class).equalTo("id",id).findAll();
        return (tvShow.size() != 0);//when there is no data, it will return list with size 0 not null
    }

    //delete data fav movie
    public void deleteFavMovies(int id){
        //first, find the movie with the id
        final RealmResults<Movie> movie = realm.where(Movie.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull  Realm realm) {
                movie.deleteFromRealm(0);//todo:why "0" ?
            }
        });
    }

    //delete data fav tv show
    public void deleteFavTvShow(int id){
        //first, find the tvshow based the given id
        final RealmResults<TvShow> tvShow = realm.where(TvShow.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                tvShow.deleteFromRealm(0);
            }
        });
    }

    //update tmpDelete status movie
    public void updateTmpDeleteM(final int id, final boolean tmpDeleteStatus){
        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull  Realm realm) {
                Movie mMovie = realm.where(Movie.class).equalTo("id",id).findFirst();
                if(mMovie != null){
                    mMovie.setTmpDelete(tmpDeleteStatus);
                    realm.insertOrUpdate(mMovie);
                }
            }
        },new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                Log.d("Realm Helper","succes update status");
            }
        },new Realm.Transaction.OnError() {
            @Override
            public void onError(@Nullable  Throwable error) {
                if(error != null){
                    error.printStackTrace();
                }
            }
        });
    }

    //update tmpDelete status tvshow
    public void updateTmpDeleteTS(final int id, final boolean tmpDeleteStatus){
        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                TvShow tvShow = realm.where(TvShow.class).equalTo("id",id).findFirst();
                if(tvShow != null){
                    tvShow.setTmpDelete(tmpDeleteStatus);
                    realm.insertOrUpdate(tvShow);
                }
            }
        },new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                Log.d("Realm Helper","succes update status");
            }
        },new Realm.Transaction.OnError() {
            @Override
            public void onError(@Nullable Throwable error) {
                if(error != null){
                    error.printStackTrace();
                }
            }
        });
    }

    //load genre data from json (API)
    public void insertGenre(final Genre genre){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                genre.setId(genre.getId());
                realm.copyToRealm(genre);
            }
        });
    }

    public String getGenreName(int id){
        Genre genre = realm.where(Genre.class).equalTo("id",id).findFirst();
        if(genre != null){
            return genre.getName();
        }else{
            return "";
        }
    }


}