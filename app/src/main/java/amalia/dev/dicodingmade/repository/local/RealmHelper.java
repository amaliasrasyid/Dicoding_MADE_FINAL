package amalia.dev.dicodingmade.repository.local;

import android.util.Log;

import java.util.List;

import amalia.dev.dicodingmade.model.Movie;
import amalia.dev.dicodingmade.model.TvShow;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    private static final String LOG = "RealmHelper";

    Realm realm;

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
            public void execute(Realm realm) {
                if(realm != null){
                    //find the latest number PK table in movie table
                    Number currentIdNum = realm.where(Movie.class).max("id");
                    int nextId;
                    //for determining PK table
                    if(currentIdNum == null){
                        nextId = 1;
                    }else{
                        nextId = currentIdNum.intValue()+1;
                    }
                    //set the number for PK
                    movie.setId(nextId);
                    //and inserting into db
                    realm.copyToRealm(movie);
                }else{
                    Log.e(LOG,"Database not exist");
                }

            }
        });
    }

    //create or insert data tvshow
    public void insertTvShow(final TvShow tvShow){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(realm != null){
                    Number currentIdNum = realm.where(TvShow.class).max("id");
                    int nextId;
                    //for determining PK table
                    if(currentIdNum == null){
                        nextId = 1;
                    }else{
                        nextId = currentIdNum.intValue()+1;
                    }
                    //set the number PK into current data that will be inserted
                    tvShow.setId(nextId);
                    //and insert into db local
                    realm.copyToRealm(tvShow);
                }else{
                    Log.e(LOG,"realm is null");
                }
            }
        });
    }

    //get list movie from local db
    public List<Movie> getListFavoriteMovies(){
        RealmResults<Movie> results = realm.where(Movie.class).findAll();//query(?)
        return  results;
    }

    //get list tvshow from local db
    public List<TvShow> getListFavoriteTvShows(){
        RealmResults<TvShow> results = realm.where(TvShow.class).findAll();
        return results;
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
            public void execute(Realm realm) {
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
            public void execute(Realm realm) {
                tvShow.deleteFromRealm(0);
            }
        });
    }
}
